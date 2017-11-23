import {Component, Input, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'



import { HttpService } from '../../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../../bs-form-module/validators/validator'

import { HSUserService } from '../../../../services/hsUser.service'
import { HSOrderService } from '../../../../services/hsOrder.service'


import {getEnum} from '../../../../services/localStorage'


@Component({
    selector: 'app-deposit',
    templateUrl: './deposit.component.html',
    styleUrls: ['./deposit.component.css']
})
export class DepositComponent implements OnInit {

    @Input() currentOrder : any
    @Input() businessType : string
    @Input() party : any = {
        normal : [],
        orderIncluded : []
    }

    currentDepositId : number = 1

    depositForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    depositList : any[] = []

    unitList : any[] = []


    depositTypeList : any[] = getEnum('BailType')
    // depositTypeList : any[] = [
    //     { id : 1, name : '收上游保证金'},
    //     { id : 2, name : '退上游保证金'},
    //     { id : 3, name : '付下游保证金'},
    //     { id : 4, name : '下游退保证金'}
    // ]



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
        this.getOrderUnitList()
        this.getDepositList()
        this.createDepositForm()
    }


    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }


    getDepositList () {
        this.hsOrderService.getDepositListByID(this.businessType, this.currentOrder.id).subscribe(
            data => {
                this.depositList = data.data.results

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


    depositFormError : any = {}
    depositFormValidationMessages: any = {
        'hsId'  : {
            'required'      : '请选择核算月!'
        },
        'bailDate'  : {
            'required'      : '请填写交纳日期!'
        },
        'bailType'  : {
            'required'      : '请填写类型!'
        },
        'bailAmount'  : {
            'required'      : '请填写金额!'
        },
        'openCompanyId'  : {
            'required'      : '请填写付款单位!'
        },
        'receiverId'  : {
            'required'      : '请填写收款单位!'
        }
    }

    depositFormInputChange(formInputData : any) {
        this.depositFormError = formErrorHandler(formInputData, this.depositForm, this.depositFormValidationMessages)
    }

    createDepositForm(): void {

        this.depositForm = this.fb.group({
            'hsId'    : ['', [Validators.required ] ],
            'bailDate'    : [null, [Validators.required ] ],
            'bailType'    : ['', [Validators.required ] ],
            'bailAmount'    : ['', [Validators.required ] ],
            'openCompanyId'    : ['', [Validators.required ] ],
            'receiverId'    : ['', [Validators.required ] ]
        } )


        this.depositForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.depositFormInputChange(data)
        })
    }



    depositFormSubmit() {

        if (this.depositForm.invalid) {
            this.depositFormInputChange(this.depositForm.value)
            this.ignoreDirty = true

            console.log('当前信息: ', this.depositForm, this.depositFormError)
            return
        }

        const postData = this.depositForm.value
        postData.orderId = this.currentOrder.id


        if (this.isAddNew) {
            this.hsOrderService.createNewDeposit(this.businessType, this.currentOrder.id, postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getDepositList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            postData.id = this.currentDepositId
            delete postData.bailAmount

            this.hsOrderService.modifyDeposit(this.businessType, this.currentOrder.id, this.currentDepositId, postData).subscribe(
                data => {
                    console.log('修改成功: ', data)
                    this.httpService.successHandler(data)

                    this.getDepositList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }


    showForm(isAddNew : boolean = true, deposit?: any ) {

        this.ignoreDirty = false

        if (isAddNew) {
            this.isAddNew = true
            this.currentDepositId = 0

            this.depositForm.patchValue({
                'hsId'    : '',
                'bailDate'    : null,
                'bailType'    : '',
                'bailAmount'  : ''
            })

        } else {
            this.isAddNew = false
            this.currentDepositId = deposit.id

            this.depositForm.patchValue(deposit)
        }

        this.isShowForm = !this.isShowForm
    }


    deleteItem (deposit : any) {

        this.hsOrderService.delDeposit(this.businessType, this.currentOrder.id, deposit.id).subscribe(
            data => {
                console.log('保存成功: ', data)
                this.httpService.successHandler(data)

                this.getDepositList()
            },
            error => {this.httpService.errorHandler(error) }
        )

    }

}

