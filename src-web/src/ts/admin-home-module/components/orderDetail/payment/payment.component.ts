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
    @Input() party : any = {}


    currentPaymentId : number = 0

    paymentForm: FormGroup
    paymentSearchForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    paymentList : any[] = []

    unitList : any[] = []
    unitListStat : any[] = []
    unitListStatObject : any = {}

    totalPaymentMoney : number = 0
    totalPayAmount : number = 0


    purposeList : any[] = getEnum('PaymentPurpose')
    payModeList : any[] = getEnum('PayMode')


    pagination: any = {
        pageSize : 10000,
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

        this.createPaymentForm()
        this.createPaymentSearchForm()
        this.getOrderUnitList()
        this.getPaymentList()

    }


    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }


    getPaymentList () {

        let query : any = {
            pageSize: this.pagination.pageSize,
            pageNo: this.pagination.pageNo
        }

        query = (<any>Object).assign(query, this.paymentSearchForm.value)

        console.log('Query: ', query)

        this.hsOrderService.getPaymentListByID(this.businessType, this.currentOrder.id, query).subscribe(
            data => {
                this.paymentList = data.data.results

                if (Array.isArray(data.data.results)) {
                    this.totalPayAmount = 0

                    data.data.results.forEach( payment => {

                        this.totalPayAmount = this.totalPayAmount + payment.payAmount
                    })
                }


            },
            error => {this.httpService.errorHandler(error) }
        )


        this.hsOrderService.getOrderStatisticsByID(this.businessType, this.currentOrder.id).subscribe(
            data => {
                this.unitListStat = data.data

                if (Array.isArray(data.data)) {

                    data.data.forEach( unit => {
                        unit.name = unit.hsMonth
                        this.unitListStatObject[unit.hsId] = unit
                    })

                    this.totalPaymentMoney = 0

                    if (this.paymentSearchForm.value.hsId) {
                        console.log(this.paymentSearchForm.value.hsId, this.unitListStatObject[this.paymentSearchForm.value.hsId])

                        this.totalPaymentMoney = this.unitListStatObject[this.paymentSearchForm.value.hsId].unitTotalPaymentAmount
                    } else {
                        if (this.unitListStat[0]) {
                            this.totalPaymentMoney = this.unitListStat[0].accumulativePaymentAmount
                        }
                    }

                }

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


    createPaymentSearchForm(): void {

        this.paymentSearchForm = this.fb.group({
            'hsId'    : ['' ],
            'payDateStart'    : [null ],
            'payDateEnd'    : [null ],
            'receiveCompanyId'    : ['' ],
            'payUsage'    : ['' ],
            'payMode'    : ['' ],
            'payAmount'    : ['' ]
        } )
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
            'receiveCompanyId' : [this.currentOrder.upstreamId, [Validators.required]],

            'payUsage'  : ['', [Validators.required]],
            'payMode'    : ['', [Validators.required ] ],
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

        if (this.paymentForm.get('capitalId').value === this.currentOrder.mainAccounting) {
            if (!this.paymentForm.value.amount) {
                this.paymentForm.patchValue({ amount : '99999999' })
            }
            if (!this.paymentForm.value.jiekuanDate) {
                this.paymentForm.patchValue({jiekuanDate : '2299-12-30'})
            }
            if (!this.paymentForm.value.useInterest) {
                this.paymentForm.patchValue({useInterest : '99999999'})
            }
            if (!this.paymentForm.value.useDays) {
                this.paymentForm.patchValue({useDays : '99999999'})
            }
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

            this.paymentForm.reset({
                'hsId'    : '',
                'payDate'    : null,
                'receiveCompanyId'    : this.currentOrder.upstreamId,

                'payUsage'    : '',
                'payMode'    : '',
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

            this.paymentForm.patchValue(shippingOrder)
        }

        this.ignoreDirty = false
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



    selectPayDate (event : any) {
        // console.log('Pay Date change: ', event)

        this.paymentForm.patchValue({
            jiekuanDate : event
        })
    }

    changePayAmount () {
        // console.log('Pay Amount change: ', this.paymentForm.value.payAmount)

        this.paymentForm.patchValue({
            amount : this.paymentForm.value.payAmount
        })
    }

}

