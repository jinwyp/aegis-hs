import {Component, Input, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'



import { HttpService } from '../../../../bs-form-module/services/http.service'

import { formErrorHandler} from '../../../../bs-form-module/validators/validator'

import { HSOrderService } from '../../../../services/hsOrder.service'



@Component({
  selector: 'app-borrow',
  templateUrl: './borrow.component.html',
  styleUrls: ['./borrow.component.css']
})
export class BorrowComponent implements OnInit {

    @Input() currentOrder : any
    @Input() businessType : string
    @Input() party : any = {}
    currentBorrowId : number = 0

    borrowForm: FormGroup
    borrowSearchForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    borrowList : any[] = []

    unitList : any[] = []
    unitListStat : any[] = []
    unitListStatObject : any = {}


    totalLoanAmountDisplay : number = 0
    totalNonRepaymentLoanMoneyDisplay : number = 0



    borrowStatusList : any[] = [
        { id : 1, name : '已还款'},
        { id : 2, name : '未还款'},
        { id : 3, name : '部分已还款'}
    ]


    pagination: any = {
        pageSize : 10000,
        pageNo : 1,
        total : 1
    }



    constructor(
        private httpService: HttpService,
        private fb: FormBuilder,
        private hsOrderService: HSOrderService

    ) {

    }



    ngOnInit(): void {

        this.createBorrowSearchForm()
        this.createBorrowForm()
        this.getOrderUnitList()
        this.getBorrowList()

    }


    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }


    getBorrowList () {

        let query : any = {
            pageSize: this.pagination.pageSize,
            pageNo: this.pagination.pageNo
        }

        query = (<any>Object).assign(query, this.borrowSearchForm.value)

        console.log('Query: ', query)


        this.hsOrderService.getBorrowListByID(this.businessType, this.currentOrder.id, query).subscribe(
            data => {
                this.borrowList = data.data.results

                if (Array.isArray(data.data.results)) {
                    this.totalLoanAmountDisplay = 0
                    this.totalNonRepaymentLoanMoneyDisplay = 0

                        data.data.results.forEach( borrow => {

                        this.totalLoanAmountDisplay = this.totalLoanAmountDisplay + borrow.amount

                        if (borrow.loanStatus === '未还') {
                            this.totalNonRepaymentLoanMoneyDisplay = this.totalNonRepaymentLoanMoneyDisplay + borrow.amount
                        }
                    })
                }
            },
            error => {this.httpService.errorHandler(error) }
        )
    }


    getOrderUnitList () {
        this.hsOrderService.getOrderUnitListByID(this.businessType, this.currentOrder.id).subscribe(
            data => {
                // this.unitList = data.data.results

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


        this.hsOrderService.getOrderStatisticsByID(this.businessType, this.currentOrder.id).subscribe(
            data => {
                this.unitListStat = data.data

                if (Array.isArray(data.data)) {

                    data.data.forEach( unit => {
                        unit.name = unit.hsMonth
                        this.unitListStatObject[unit.hsId] = unit

                    })

                }

                // purchaseCargoAmountOfMoney 结算金额

                // finalSettleAmount 结算数量
            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    createBorrowSearchForm(): void {

        this.borrowSearchForm = this.fb.group({
            'hsId'    : ['' ],
            'loanStatus'    : ['' ],
            'jiekuanDateStart'    : [null ],
            'jiekuanDateEnd'    : [null ],
            'amount'    : ['' ],
            'capitalId'    : ['' ],
            'useInterest'    : ['' ],
            'useDays'    : ['' ]
        } )
    }


    borrowFormError : any = {}
    borrowFormValidationMessages: any = {
        'hsId'  : {
            'required'      : '请选择核算月!'
        },
        'jiekuanDate'  : {
            'required'      : '请填写借款日期!'
        },
        'amount'  : {
            'required'      : '请填写借款金额!'
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

    borrowFormInputChange(formInputData : any) {
        this.borrowFormError = formErrorHandler(formInputData, this.borrowForm, this.borrowFormValidationMessages)
    }

    createBorrowForm(): void {

        this.borrowForm = this.fb.group({
            'hsId'    : ['', [Validators.required ] ],
            'jiekuanDate'    : [null, [Validators.required ] ],
            'amount'    : ['', [Validators.required ] ],

            'capitalId'    : ['', [Validators.required ] ],
            'useInterest'    : ['', [Validators.required] ],
            'useDays'    : ['', [Validators.required ] ]
        } )

        this.borrowForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.borrowFormInputChange(data)
        })
    }





    borrowFormSubmit() {

        if (this.borrowForm.invalid) {
            this.borrowFormInputChange(this.borrowForm.value)
            this.ignoreDirty = true

            console.log('当前信息: ', this.borrowForm, this.borrowFormError)
            return
        }

        const postData = this.borrowForm.value
        postData.orderId = this.currentOrder.id


        if (this.isAddNew) {
            this.hsOrderService.createNewBorrow(this.businessType, this.currentOrder.id, postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getBorrowList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            postData.id = this.currentBorrowId
            delete postData.payDate
            delete postData.payAmount

            this.hsOrderService.modifyBorrow(this.businessType, this.currentOrder.id, this.currentBorrowId, postData).subscribe(
                data => {
                    console.log('修改成功: ', data)
                    this.httpService.successHandler(data)

                    this.getBorrowList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }
    }


    showForm(isAddNew : boolean = true, borrowOrder?: any ) {

        if (isAddNew) {
            this.isAddNew = true
            this.currentBorrowId = 0

            this.borrowForm.patchValue({
                'hsId'    : '',
                'jiekuanDate'    : null,
                'amount'    : '',

                'capitalId'    : '',
                'useInterest'    : '',
                'useDays'    : ''
            })

        } else {
            this.isAddNew = false
            this.currentBorrowId = borrowOrder.id

            this.borrowForm.patchValue(borrowOrder)
        }

        this.isShowForm = !this.isShowForm
    }


    deleteItem (borrow : any) {

        this.hsOrderService.delBorrow(this.businessType, this.currentOrder.id, borrow.id).subscribe(
            data => {
                console.log('保存成功: ', data)
                this.httpService.successHandler(data)

                this.getBorrowList()
            },
            error => {this.httpService.errorHandler(error) }
        )
    }


}

