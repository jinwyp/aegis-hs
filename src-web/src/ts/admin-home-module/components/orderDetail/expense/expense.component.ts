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

    currentExpenseId : number = 1

    expenseForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    expenseList : any[] = []
    partyList : any[] = []

    unitList : any[] = []

    expensePurposeList : any[] = getEnum('FeeClass')


    pagination: any = {
        pageSize : 20,
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

        this.getExpenseList()
        this.createExpenseForm()

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


    getExpenseList () {
        this.hsOrderService.getExpenseListByID(this.currentOrder.id).subscribe(
            data => {
                this.expenseList = data.data.results

            },
            error => {this.httpService.errorHandler(error) }
        )
    }


    expenseFormError : any = {}
    expenseFormValidationMessages: any = {
        'hsId'  : {
            'required'      : '请选择核算月!'
        },
        'amount'  : {
            'required'      : '请填写金额!'
        },
        'name'  : {
            'required'      : '请填写费用科目!'
        }
    }

    expenseFormInputChange(formInputData : any) {
        this.expenseFormError = formErrorHandler(formInputData, this.expenseForm, this.expenseFormValidationMessages)
    }

    createExpenseForm(): void {

        this.expenseForm = this.fb.group({
            'hsId'    : ['', [Validators.required ] ],
            'name'    : ['', [Validators.required ] ],
            'amount'    : ['', [Validators.required ] ]
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
            this.hsOrderService.createNewExpense(this.currentOrder.id, postData).subscribe(
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

            this.hsOrderService.modifyExpense(this.currentOrder.id, this.currentExpenseId, postData).subscribe(
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
                'amount'  : ''
            })

        } else {
            this.isAddNew = false
            this.currentExpenseId = expense.id

            this.expenseForm.patchValue(expense)
        }

        this.isShowForm = !this.isShowForm
    }


    deleteItem (expense : any) {

        this.hsOrderService.delExpense(this.currentOrder.id, expense.id).subscribe(
            data => {
                console.log('保存成功: ', data)
                this.httpService.successHandler(data)

                this.getExpenseList()
            },
            error => {this.httpService.errorHandler(error) }
        )

    }

}

