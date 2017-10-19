import {Component, Input, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'



import { HttpService } from '../../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../../bs-form-module/validators/validator'

import { HSUserService } from '../../../../services/hsUser.service'
import { HSOrderService } from '../../../../services/hsOrder.service'


import {getEnum} from '../../../../services/localStorage'


@Component({
  selector: 'app-payment',
  templateUrl: './payment.component.html',
  styleUrls: ['./payment.component.css']
})
export class PaymentComponent implements OnInit {

    @Input() currentOrder : any
    @Input() businessType : string

    currentPaymentId : number = 0

    paymentForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    paymentList : any[] = []
    partyList : any[] = []

    unitList : any[] = []

    purposeList : any[] = getEnum('PaymentPurpose')
    payModeList : any[] = getEnum('PayMode')


    pagination: any = {
        pageSize : 20,
        pageNo : 1,
        total : 1
    }



    constructor(
        private httpService: HttpService,
        private fb: FormBuilder,
        private hsUserService: HSUserService,
        private hsOrderService: HSOrderService

    ) {

    }



    ngOnInit(): void {

        this.getOrderUnitList()
        this.getPartyList()
        this.getPaymentList()
        this.createPaymentForm()

        if (this.currentOrder) {
            if (Array.isArray(this.currentOrder.orderConfigList)) {

                const tempArray = []
                this.currentOrder.orderConfigList.forEach( unit => {
                    unit.name = unit.hsMonth
                    tempArray.push(unit)
                })

                this.unitList = tempArray
            }
        }
    }


    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }


    getPaymentList () {
        this.hsOrderService.getPaymentListByID(this.businessType, this.currentOrder.id).subscribe(
            data => {
                this.paymentList = data.data.results

            },
            error => {this.httpService.errorHandler(error) }
        )

        this.hsOrderService.getPaymentByID(this.businessType, this.currentOrder.id, 1).subscribe(
            data => {
                // this.paymentList = data.data.results

            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    getPartyList () {

        this.hsUserService.getPartyList().subscribe(
            data => {
                this.partyList = data.data.results

            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    getOrderUnitList () {
        this.hsOrderService.getOrderUnitListByID(this.businessType, this.currentOrder.id).subscribe(
            data => {
                this.unitList = data.data.results

                if (Array.isArray(data.data.results)) {

                    const tempArray = []
                    data.data.results.forEach( unit => {
                        unit.name = unit.hsMonth
                        tempArray.push(unit)
                    })

                    this.unitList = tempArray
                }
            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    paymentFormError : any = {}
    paymentFormValidationMessages: any = {
        'hsId'  : {
            'required'      : '请选择核算月!'
        },
        'payDate'  : {
            'required'      : '请填写付款日期!'
        },
        'receiveCompanyId'  : {
            'required'      : '请填写收款单位!'
        },
        'payUsage'  : {
            'required'      : '请填写付款用途!'
        },
        'payAmount'  : {
            'required'      : '请填写付款金额!'
        },
        'payMode'  : {
            'required'      : '请填写付款方式!'
        },

        'amount'  : {
            'required'      : '请填写借款金额!'
        },
        'jiekuanDate'  : {
            'required'      : '请填写借款日期!'
        },
        'capitalId'  : {
            'required'      : '请填写资金方!'
        },
        'useInterest'  : {
            'required'      : '请填写使用利率!'
        },
        'useDays'  : {
            'required'      : '请填写使用天数!'
        }
    }

    paymentFormInputChange(formInputData : any) {
        this.paymentFormError = formErrorHandler(formInputData, this.paymentForm, this.paymentFormValidationMessages)
    }

    createPaymentForm(): void {

        this.paymentForm = this.fb.group({
            'hsId'             : ['', [Validators.required]],
            'payDate'          : [null, [Validators.required]],
            'receiveCompanyId' : ['', [Validators.required]],

            'payUsage'  : ['', [Validators.required]],
            'payAmount' : ['', [Validators.required]],

            'amount'      : ['', [Validators.required]],
            'jiekuanDate' : [null, [Validators.required]],
            'capitalId'   : [this.currentOrder.mainAccounting, [Validators.required]],
            'useInterest' : ['', [Validators.required]],
            'useDays'     : ['', [Validators.required]]
        } )


        this.paymentForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.paymentFormInputChange(data)
        })
    }





    paymentFormSubmit() {

        if (!this.paymentForm.value.amount) {
            this.paymentForm.patchValue({ amount : '99999999' })
        }
        if (!this.paymentForm.value.jiekuanDate) {
            this.paymentForm.patchValue({jiekuanDate : '2299-12-30'})
        }


        if (this.paymentForm.invalid) {
            this.paymentFormInputChange(this.paymentForm.value)
            this.ignoreDirty = true

            console.log('当前信息: ', this.paymentForm, this.paymentFormError)
            return
        }

        const postData = this.paymentForm.value
        postData.orderId = this.currentOrder.id
        postData.jiekuan = {
            orderId : this.currentOrder.id,
            hsId : this.paymentForm.value.hsId,
            jiekuanDate : this.paymentForm.value.jiekuanDate,
            amount : this.paymentForm.value.amount,
            capitalId : this.paymentForm.value.capitalId,
            useInterest : this.paymentForm.value.useInterest,
            useDays : this.paymentForm.value.useDays
        }

        if (this.isAddNew) {
            this.hsOrderService.createNewPayment(this.businessType, this.currentOrder.id, postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getPaymentList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            postData.id = this.currentPaymentId
            delete postData.payDate
            delete postData.payAmount

            this.hsOrderService.modifyPayment(this.businessType, this.currentOrder.id, this.currentPaymentId, postData).subscribe(
                data => {
                    console.log('修改成功: ', data)
                    this.httpService.successHandler(data)

                    this.getPaymentList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }
    }


    showForm(isAddNew : boolean = true, shippingOrder?: any ) {

        if (isAddNew) {
            this.isAddNew = true
            this.currentPaymentId = 0

            this.paymentForm.patchValue({
                'hsId'    : '',
                'payDate'    : null,
                'receiveCompanyId'    : '',

                'payUsage'    : '',
                'payAmount'    : '',

                'amount'    : '',
                'jiekuanDate'    : null,
                'capitalId'    : this.currentOrder.mainAccounting,
                'useInterest'    : '',
                'useDays'    : ''
            })


        } else {
            this.isAddNew = false
            this.currentPaymentId = shippingOrder.id

            shippingOrder.jiekuanDate = shippingOrder.jiekuan.jiekuanDate
            shippingOrder.amount = shippingOrder.jiekuan.amount
            shippingOrder.useInterest = shippingOrder.jiekuan.useInterest
            shippingOrder.useDays = shippingOrder.jiekuan.useDays

            this.paymentForm.patchValue(shippingOrder)
        }

        this.isShowForm = !this.isShowForm
    }


    deleteItem (payment : any) {

        this.hsOrderService.delPayment(this.businessType, this.currentOrder.id, payment.id).subscribe(
            data => {
                console.log('保存成功: ', data)
                this.httpService.successHandler(data)

                this.getPaymentList()
            },
            error => {this.httpService.errorHandler(error) }
        )
    }

}

