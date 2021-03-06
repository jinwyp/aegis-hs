import {Component, Input, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'



import { HttpService } from '../../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../../bs-form-module/validators/validator'

import { HSUserService } from '../../../../services/hsUser.service'
import { HSOrderService } from '../../../../services/hsOrder.service'


import {getEnum} from '../../../../services/localStorage'


@Component({
    selector: 'app-expense',
    templateUrl: './expense.component.html',
    styleUrls: ['./expense.component.css']
})
export class ExpenseComponent implements OnInit {

    @Input() currentOrder : any
    @Input() businessType : string
    @Input() party : any = {}

    currentExpenseId : number = 1

    expenseForm: FormGroup
    expenseSearchForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    expenseList : any[] = []

    unitList : any[] = []


    totalAmount : number = 0


    expensePurposeList : any[] = getEnum('FeeClass')


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

        this.createExpenseSearchForm()
        this.getOrderUnitList()
        this.getExpenseList()
        this.createExpenseForm()

    }


    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }


    getExpenseList () {
        let query : any = {
            pageSize: this.pagination.pageSize,
            pageNo: this.pagination.pageNo
        }

        query = (<any>Object).assign(query, this.expenseSearchForm.value)

        console.log('Query: ', query)

        this.hsOrderService.getExpenseListByID(this.businessType, this.currentOrder.id, query).subscribe(
            data => {
                this.expenseList = data.data.results

                if (Array.isArray(data.data.results)) {

                    this.totalAmount = 0

                    data.data.results.forEach( expense => {
                        this.totalAmount = this.totalAmount + expense.amount
                    })
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

    createExpenseSearchForm(): void {

        this.expenseSearchForm = this.fb.group({
            'hsId'    : ['' ],
            'name'    : ['' ],
            'settleDateStart'    : ['' ],
            'settleDateEnd'    : ['' ],
            'amount'    : ['' ],
            'quantity'    : ['' ],
            'otherPartyId'    : ['' ]
        } )
    }

    expenseFormError : any = {}
    expenseFormValidationMessages: any = {
        'hsId'  : {
            'required'      : '请选择核算月!'
        },
        'name'  : {
            'required'      : '请填写费用科目!'
        },
        'settleDate'  : {
            'required'      : '请填写结算日期!'
        },
        'amount'  : {
            'required'      : '请填写金额!'
        },
        'otherPartyId'  : {
            'required'      : '请填写对方单位!'
        }


    }

    expenseFormInputChange(formInputData : any) {
        this.expenseFormError = formErrorHandler(formInputData, this.expenseForm, this.expenseFormValidationMessages)
    }

    createExpenseForm(): void {

        this.expenseForm = this.fb.group({
            'hsId'    : ['', [Validators.required ] ],
            'name'    : ['', [Validators.required ] ],
            'settleDate'    : [null ],
            'amount'    : ['', [Validators.required ] ],
            'quantity'    : ['' ],
            'otherPartyId'    : ['', [Validators.required ] ]
        } )


        this.expenseForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.expenseFormInputChange(data)
        })
    }





    expenseFormSubmit() {

        if (this.expenseForm.invalid) {
            this.expenseFormInputChange(this.expenseForm.value)
            this.ignoreDirty = true

            console.log('当前信息: ', this.expenseForm, this.expenseFormError)
            return
        }

        const postData = this.expenseForm.value
        postData.orderId = this.currentOrder.id


        if (this.isAddNew) {
            this.hsOrderService.createNewExpense(this.businessType, this.currentOrder.id, postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getExpenseList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            postData.id = this.currentExpenseId
            // delete postData.huikuanAmount

            this.hsOrderService.modifyExpense(this.businessType, this.currentOrder.id, this.currentExpenseId, postData).subscribe(
                data => {
                    console.log('修改成功: ', data)
                    this.httpService.successHandler(data)

                    this.getExpenseList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }


    showForm(isAddNew : boolean = true, expense?: any ) {

        this.ignoreDirty = false

        if (isAddNew) {
            this.isAddNew = true
            this.currentExpenseId = 0

            this.expenseForm.patchValue({
                'hsId'    : '',
                'name'    : '',
                'settleDate'    : null,
                'amount'  : '',
                'quantity'    : '',
                'otherPartyId'    : ''
            })

        } else {
            this.isAddNew = false
            this.currentExpenseId = expense.id

            this.expenseForm.patchValue(expense)
        }

        this.isShowForm = !this.isShowForm
    }


    deleteItem (expense : any) {

        this.hsOrderService.delExpense(this.businessType, this.currentOrder.id, expense.id).subscribe(
            data => {
                console.log('保存成功: ', data)
                this.httpService.successHandler(data)

                this.getExpenseList()
            },
            error => {this.httpService.errorHandler(error) }
        )

    }

}

