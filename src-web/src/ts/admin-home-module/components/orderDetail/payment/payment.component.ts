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
        this.hsOrderService.getPaymentListByID(this.currentOrder.id).subscribe(
            data => {
                this.paymentList = data.data.results

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
        'capitalId'  : {
            'required'      : '请填写资金方!'
        }
    }

    paymentFormInputChange(formInputData : any, ignoreDirty : boolean = false) {
        this.paymentFormError = formErrorHandler(formInputData, this.paymentForm, this.paymentFormValidationMessages, ignoreDirty)
    }

    createPaymentForm(): void {

        this.paymentForm = this.fb.group({
            'hsId'    : ['', [Validators.required ] ],
            'payDate'    : [null, [Validators.required ] ],
            'receiveCompanyId'    : ['', [Validators.required ] ],

            'payUsage'    : ['', [Validators.required ] ],
            'payAmount'    : ['', [Validators.required ] ],
            'payMode'    : ['', [Validators.required ] ],

            'capitalId'    : ['', [Validators.required ] ],
            'useInterest'    : ['', [] ],
            'useDays'    : ['', [ ] ]
        } )


        this.paymentForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.paymentFormInputChange(data)
        })
    }





    paymentFormSubmit() {

        if (this.paymentForm.invalid) {
            this.paymentFormInputChange(this.paymentForm.value, true)
            this.ignoreDirty = true

            console.log('当前信息: ', this.paymentForm, this.paymentFormError)
            return
        }

        const postData = this.paymentForm.value
        postData.orderId = this.currentOrder.id


        if (this.isAddNew) {
            this.hsOrderService.createNewPayment(this.currentOrder.id, postData).subscribe(
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

            this.hsOrderService.modifyPayment(this.currentOrder.id, this.currentPaymentId, postData).subscribe(
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
                'payMode'    : '',

                'capitalId'    : '',
                'useInterest'    : '',
                'useDays'    : ''
            })


        } else {
            this.isAddNew = false
            this.currentPaymentId = shippingOrder.id

            this.paymentForm.patchValue(shippingOrder)
        }


        this.isShowForm = !this.isShowForm
    }



}

