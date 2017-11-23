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
    @Input() businessType : string
    @Input() party : any = {}

    currentRepaymentId : number = 1

    repaymentForm: FormGroup
    repaymentSearchForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    repaymentList : any[] = []
    partyList : any[] = []

    unitList : any[] = []

    purposeList : any[] = getEnum('ReceivePaymentPurpose')
    payModeList : any[] = getEnum('PayMode')


    pagination: any = {
        pageSize : 10000,
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

        this.createRepaymentSearchForm()
        this.getOrderUnitList()
        this.getPartyList()
        this.getRepaymentList()
        this.createRepaymentForm()
    }


    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }


    getRepaymentList () {
        let query : any = {
            pageSize: this.pagination.pageSize,
            pageNo: this.pagination.pageNo
        }

        query = (<any>Object).assign(query, this.repaymentSearchForm.value)

        console.log('Query: ', query)

        this.hsOrderService.getRepaymentListByID(this.businessType, this.currentOrder.id, query).subscribe(
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

    createRepaymentSearchForm(): void {

        this.repaymentSearchForm = this.fb.group({
            'hsId'    : ['' ],
            'huikuanUsage'    : ['' ],
            'huikuanMode'    : ['' ],
            'huikuanDateStart'    : [null ],
            'huikuanDateEnd'    : [null ],
            'huikuanAmount'    : ['' ]
        } )
    }

    repaymentFormError : any = {}
    repaymentFormValidationMessages: any = {
        'hsId'  : {
            'required'      : '请选择核算月!'
        },

        // 'huikuanCompanyId'  : {
        //     'required'      : '请填写回款公司!'
        // },
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

    repaymentFormInputChange(formInputData : any) {
        this.repaymentFormError = formErrorHandler(formInputData, this.repaymentForm, this.repaymentFormValidationMessages)
    }

    createRepaymentForm(): void {

        this.repaymentForm = this.fb.group({
            'hsId'    : ['', [Validators.required ] ],

            // 'huikuanCompanyId'    : ['', [Validators.required ] ],
            'huikuanDate'    : [null, [Validators.required ] ],
            'huikuanAmount'    : ['', [Validators.required ] ],
            'huikuanUsage'    : ['', [Validators.required ] ],
            'huikuanMode'    : ['', [Validators.required ] ],

            'huikuanBankPaper'    : ['', [] ],
            'huikuanBankPaperDate'    : [null, [] ],
            'huikuanBankDiscount'    : ['', [] ],
            'huikuanBankDiscountRate'    : ['', [] ],
            'huikuanBankPaperExpire'    : [null, [] ],

            'huikuanBusinessPaper'    : ['', [] ],
            'huikuanBusinessPaperDate'    : [null, [] ],
            'huikuanBusinessDiscount'    : ['', [] ],
            'huikuanBusinessDiscountRate'    : ['', [] ],
            'huikuanBusinessPaperExpire'    : [null, [ ] ]
        } )


        this.repaymentForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.repaymentFormInputChange(data)
        })
    }





    repaymentFormSubmit() {

        if (this.repaymentForm.invalid) {
            this.repaymentFormInputChange(this.repaymentForm.value)
            this.ignoreDirty = true

            console.log('当前信息: ', this.repaymentForm, this.repaymentFormError)
            return
        }

        const postData = this.repaymentForm.value
        postData.orderId = this.currentOrder.id


        if (this.isAddNew) {
            this.hsOrderService.createNewRepayment(this.businessType, this.currentOrder.id, postData).subscribe(
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

            this.hsOrderService.modifyRepayment(this.businessType, this.currentOrder.id, this.currentRepaymentId, postData).subscribe(
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

        this.ignoreDirty = false

        if (isAddNew) {
            this.isAddNew = true
            this.currentRepaymentId = 0

            this.repaymentForm.patchValue({
                'hsId'    : '',

                'huikuanCompanyId'    : '',
                'huikuanDate'    : null,
                'huikuanAmount'    : '',
                'huikuanUsage'    : '',
                'huikuanMode'    : '',

                'huikuanBankPaper'    : '',
                'huikuanBankPaperDate'    : null,
                'huikuanBankDiscount'    : '',
                'huikuanBankDiscountRate'    : '',
                'huikuanBankPaperExpire'    : null,

                'huikuanBusinessPaper'    : '',
                'huikuanBusinessPaperDate'    : null,
                'huikuanBusinessDiscount'    : '',
                'huikuanBusinessDiscountRate'    : '',
                'huikuanBusinessPaperExpire'    : null
            })

        } else {
            this.isAddNew = false
            this.currentRepaymentId = repayment.id

            this.repaymentForm.patchValue(repayment)
        }


        this.isShowForm = !this.isShowForm
    }


    deleteItem (repayment : any) {

        this.hsOrderService.delRepayment(this.businessType, this.currentOrder.id, repayment.id).subscribe(
            data => {
                console.log('保存成功: ', data)
                this.httpService.successHandler(data)

                this.getRepaymentList()
            },
            error => {this.httpService.errorHandler(error) }
        )
    }

}

