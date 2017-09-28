import {Component, Input, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'



import { HttpService } from '../../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../../bs-form-module/validators/validator'

import { HSUserService } from '../../../../services/hsUser.service'
import { HSOrderService } from '../../../../services/hsOrder.service'


import {getEnum} from '../../../../services/localStorage'


@Component({
    selector: 'app-repayment',
    templateUrl: './repayment.component.html',
    styleUrls: ['./repayment.component.css']
})
export class RepaymentComponent implements OnInit {

    @Input() currentOrder : any

    currentRepaymentId : number = 1

    repaymentForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    repaymentList : any[] = []
    partyList : any[] = []

    unitList : any[] = []

    purposeList : any[] = getEnum('ReceivePaymentPurpose')
    payModeList : any[] = getEnum('PayMode')


    pagination: any = {
        pageSize : 20,
        pageNo : 1,
        total : 1
    }

    dataIsGot : any [] = [
        { id : true , name : '是'},
        { id : false , name : '否'}
    ]


    constructor(
        private httpService: HttpService,
        private fb: FormBuilder,
        private hsUserService: HSUserService,
        private hsOrderService: HSOrderService

    ) {

    }



    ngOnInit(): void {

        this.getPartyList()
        this.getRepaymentList()
        this.createRepaymentForm()

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


    getRepaymentList () {
        this.hsOrderService.getRepaymentListByID(this.currentOrder.id).subscribe(
            data => {
                this.repaymentList = data.data.results

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



    repaymentFormError : any = {}
    repaymentFormValidationMessages: any = {
        'hsId'  : {
            'required'      : '请选择核算月!'
        },

        'huikuanCompanyId'  : {
            'required'      : '请填写回款公司!'
        },
        'huikuanDate'  : {
            'required'      : '请填写回款日期!'
        },
        'huikuanAmount'  : {
            'required'      : '请填写回款金额!'
        },
        'huikuanUsage'  : {
            'required'      : '请填写回款用途!'
        },
        'huikuanMode'  : {
            'required'      : '请填写回款方式!'
        }
    }

    repaymentFormInputChange(formInputData : any, ignoreDirty : boolean = false) {
        this.repaymentFormError = formErrorHandler(formInputData, this.repaymentForm, this.repaymentFormValidationMessages, ignoreDirty)
    }

    createRepaymentForm(): void {

        this.repaymentForm = this.fb.group({
            'hsId'    : ['', [Validators.required ] ],

            'huikuanCompanyId'    : ['', [Validators.required ] ],
            'huikuanDate'    : ['', [Validators.required ] ],
            'huikuanAmount'    : ['', [Validators.required ] ],
            'huikuanUsage'    : ['', [Validators.required ] ],
            'huikuanMode'    : ['', [Validators.required ] ],

            'huikuanBankPaper'    : ['', [] ],
            'huikuanBankPaperDate'    : ['', [] ],
            'huikuanBankDiscount'    : ['', [] ],
            'huikuanBankDiscountRate'    : ['', [] ],
            'huikuanBankPaperExpire'    : ['', [] ],

            'huikuanBusinessPaper'    : ['', [] ],
            'huikuanBusinessPaperDate'    : ['', [] ],
            'huikuanBusinessDiscount'    : ['', [] ],
            'huikuanBusinessDiscountRate'    : ['', [] ],
            'huikuanBusinessPaperExpire'    : ['', [ ] ]
        } )


        this.repaymentForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.repaymentFormInputChange(data)
        })
    }





    repaymentFormSubmit() {

        if (this.repaymentForm.invalid) {
            this.repaymentFormInputChange(this.repaymentForm.value, true)
            this.ignoreDirty = true

            console.log('当前信息: ', this.repaymentForm, this.repaymentFormError)
            return
        }

        const postData = this.repaymentForm.value
        postData.orderId = this.currentOrder.id

        if (typeof this.repaymentForm.value.huikuanDate === "object" ) {
            postData.huikuanDate = this.hsOrderService.formatDateTime(this.repaymentForm.value.huikuanDate)
        }

        if (typeof this.repaymentForm.value.huikuanBankPaperDate === "object" ) {
            postData.huikuanBankPaperDate = this.hsOrderService.formatDateTime(this.repaymentForm.value.huikuanBankPaperDate)
        }
        if (typeof this.repaymentForm.value.huikuanBankPaperExpire === "object" ) {
            postData.huikuanBankPaperExpire = this.hsOrderService.formatDateTime(this.repaymentForm.value.huikuanBankPaperExpire)
        }

        if (typeof this.repaymentForm.value.huikuanBusinessPaperDate === "object" ) {
            postData.huikuanBusinessPaperDate = this.hsOrderService.formatDateTime(this.repaymentForm.value.huikuanBusinessPaperDate)
        }
        if (typeof this.repaymentForm.value.huikuanBusinessPaperExpire === "object" ) {
            postData.huikuanBusinessPaperExpire = this.hsOrderService.formatDateTime(this.repaymentForm.value.huikuanBusinessPaperExpire)
        }

        if (this.isAddNew) {
            this.hsOrderService.createNewRepayment(this.currentOrder.id, postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getRepaymentList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            postData.id = this.currentRepaymentId
            delete postData.huikuanAmount

            this.hsOrderService.modifyRepayment(this.currentOrder.id, this.currentRepaymentId, postData).subscribe(
                data => {
                    console.log('修改成功: ', data)
                    this.httpService.successHandler(data)

                    this.getRepaymentList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }


    showForm(isAddNew : boolean = true, repayment?: any ) {

        if (isAddNew) {
            this.isAddNew = true
            this.currentRepaymentId = 0

            this.repaymentForm.patchValue({
                'hsId'    : '',

                'huikuanCompanyId'    : '',
                'huikuanDate'    : '',
                'huikuanAmount'    : '',
                'huikuanUsage'    : '',
                'huikuanMode'    : '',

                'huikuanBankPaper'    : '',
                'huikuanBankPaperDate'    : '',
                'huikuanBankDiscount'    : '',
                'huikuanBankDiscountRate'    : '',
                'huikuanBankPaperExpire'    : '',

                'huikuanBusinessPaper'    : '',
                'huikuanBusinessPaperDate'    : '',
                'huikuanBusinessDiscount'    : '',
                'huikuanBusinessDiscountRate'    : '',
                'huikuanBusinessPaperExpire'    : ''
            })

        } else {
            this.isAddNew = false
            this.currentRepaymentId = repayment.id

            this.repaymentForm.patchValue(repayment)
        }


        this.isShowForm = !this.isShowForm
    }



}

