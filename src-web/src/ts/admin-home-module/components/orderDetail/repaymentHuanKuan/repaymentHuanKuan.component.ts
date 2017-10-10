import {Component, Input, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'



import { HttpService } from '../../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../../bs-form-module/validators/validator'

import { HSUserService } from '../../../../services/hsUser.service'
import { HSOrderService } from '../../../../services/hsOrder.service'


import {getEnum} from '../../../../services/localStorage'


@Component({
  selector: 'app-huankuan',
  templateUrl: './repaymentHuanKuan.component.html',
  styleUrls: ['./repaymentHuanKuan.component.css']
})
export class RepaymentHuanKuanComponent implements OnInit {

    @Input() currentOrder : any
    currentHuanKuanId : number = 1

    repaymentHKForm: FormGroup
    paymentForm: FormGroup
    ignoreDirty: boolean = false
    ignoreDirtyPayment: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    shippingList : any[] = []
    partyList : any[] = []
    partyListObject : any = {}

    unitList : any[] = []
    paymentDropDownList : any[] = []
    paymentPostList : any[] = []
    paymentListObject : any = {}


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
        this.getRepaymentHKList()

        this.createHKForm()
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


    getRepaymentHKList () {
        this.hsOrderService.getRepaymentHKListByID(this.currentOrder.id).subscribe(
            data => {
                this.shippingList = data.data.results

            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    getPartyList () {

        this.hsUserService.getPartyList().subscribe(
            data => {
                this.partyList = data.data.results

                if (Array.isArray(data.data.results)) {
                    data.data.results.forEach( (company) => {
                        this.partyListObject[company.id] = company
                    })
                }

                this.getPaymentList()

            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    getPaymentList () {
        this.hsOrderService.getPaymentListByID(this.currentOrder.id).subscribe(
            data => {

                if (Array.isArray(data.data.results)) {
                    data.data.results.forEach( (payment) => {
                        if (payment) {
                            this.paymentDropDownList.push ({
                                id : payment.id,
                                name : 'ID:' + payment.id + ' 日期:' + payment.payDate + ' 收款单位:' + this.partyListObject[payment.receiveCompanyId].name + ' 金额:' + payment.payAmount + ' 付款方式:' + payment.payMode
                            })
                        }

                        this.paymentListObject[payment.id] = payment
                    })
                }

            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    repaymentHKFormError : any = {}
    paymentFormError : any = {
        principal : 'aa',
        amount : 'bb'
    }
    repaymentHKFormValidationMessages: any = {
        'hsId'  : {
            'required'      : '请选择核算月!'
        },
        'skCompanyId'  : {
            'required'      : '请填写资金方!'
        },
        'huankuankDate'  : {
            'required'      : '请填写还款日期!'
        },
        'huankuanAmount'  : {
            'required'      : '请填写还款总额!'
        },
        'huankuanInterest'  : {
            'required'      : '请填写还款利息!'
        },
        'huankuanFee'  : {
            'required'      : '请填写还款服务费!'
        },


        'id'  : {
            'required'      : '请选择付款!'
        },
        'principal'  : {
            'required'      : '请填写本金!'
        },
        'amount'  : {
            'required'      : '请填写总计!'
        }
    }



    createHKForm(): void {

        this.repaymentHKForm = this.fb.group({
            'hsId'    : [-1, [Validators.required ] ],
            'skCompanyId'    : [-1, [Validators.required ] ],
            'huankuankDate'    : [null, [Validators.required ] ],
            'huankuanAmount'    : ['', [Validators.required ] ],
            'huankuanInterest'    : ['', [Validators.required ] ],
            'huankuanFee'    : ['', [Validators.required ] ]
        } )


        this.repaymentHKForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.repaymentHKFormError = formErrorHandler(data, this.repaymentHKForm, this.repaymentHKFormValidationMessages)
        })
    }


    createPaymentForm(): void {

        this.paymentForm = this.fb.group({
            'id'    : ['', [Validators.required ] ],
            'principal'    : ['', [Validators.required ] ],
            'amount'    : ['', [Validators.required ] ]
        } )

        this.paymentForm.valueChanges.subscribe(data => {
            this.ignoreDirtyPayment = false
            this.paymentFormError = formErrorHandler(data, this.paymentForm, this.repaymentHKFormValidationMessages)
        })
    }



    repaymentHKFormSubmit() {

        if (this.repaymentHKForm.invalid) {
            this.ignoreDirty = true
            this.repaymentHKFormError = formErrorHandler(this.repaymentHKForm.value, this.repaymentHKForm, this.repaymentHKFormValidationMessages)

            console.log('当前信息: ', this.repaymentHKForm, this.repaymentHKFormError)
            return
        }

        const postData = this.repaymentHKForm.value
        postData.orderId = this.currentOrder.id
        postData.huankuanMapList = this.paymentPostList

        if (this.isAddNew) {
            this.hsOrderService.createNewRepaymentHK(this.currentOrder.id, postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getRepaymentHKList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            postData.id = this.currentHuanKuanId
            delete postData.huankuanAmount

            this.hsOrderService.modifyRepaymentHK(this.currentOrder.id, this.currentHuanKuanId, postData).subscribe(
                data => {
                    console.log('修改成功: ', data)
                    this.httpService.successHandler(data)

                    this.getRepaymentHKList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }


    showForm(isAddNew : boolean = true, repaymentHKOrder?: any ) {

        this.ignoreDirty = false

        if (isAddNew) {
            this.isAddNew = true
            this.currentHuanKuanId = 0

            this.repaymentHKForm.patchValue({
                'hsId'    : -1,
                'skCompanyId'    : -1,
                'huankuankDate'    : null,
                'huankuanAmount'    : '',
                'huankuanInterest'    : '',
                'huankuanFee'    : ''
            })


        } else {
            this.isAddNew = false
            this.currentHuanKuanId = repaymentHKOrder.id

            this.repaymentHKForm.patchValue(repaymentHKOrder)
        }


        this.isShowForm = !this.isShowForm
    }


    createNewPayment () {
        if (this.paymentForm.invalid) {

            this.paymentFormError = formErrorHandler(this.paymentForm.value, this.paymentForm, this.repaymentHKFormValidationMessages)

            setTimeout( () => {
                this.ignoreDirtyPayment = true
            })

            return
        }

        this.paymentPostList.push(<any>Object.assign(this.paymentListObject[this.paymentForm.value.id], {fukuanId : this.paymentForm.value.id, principal : this.paymentForm.value.principal, amount : this.paymentForm.value.amount}) )
        this.ignoreDirtyPayment = false
    }

    delPayment (payment: any) {
        const index = this.paymentPostList.indexOf(payment)
        this.paymentPostList.splice(index, 1)
    }

}

