import {Component, Input, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'



import { HttpService } from '../../../../bs-form-module/services/http.service'

import { formErrorHandler, isInt } from '../../../../bs-form-module/validators/validator'

import { HSUserService } from '../../../../services/hsUser.service'
import { HSOrderService } from '../../../../services/hsOrder.service'


import {getEnum} from '../../../../services/localStorage'


@Component({
    selector: 'app-unit',
    templateUrl: './unit.component.html',
    styleUrls: ['./unit.component.css']
})
export class UnitComponent implements OnInit {

    @Input() currentOrder : any

    currentOrderUnitId : number

    orderUnitForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    unitList : any[] = []


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

        // this.getOrderUnitList()
        this.createOrderUnitForm()

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


    getOrderUnitList () {
        this.hsOrderService.getOrderUnitListByID(this.currentOrder.id).subscribe(
            data => {
                this.unitList = data.data.results

            },
            error => {this.httpService.errorHandler(error) }
        )
    }



    orderUnitFormError : any = {}
    orderUnitFormValidationMessages: any = {
        'hsMonth'  : {
            'required'      : '请填写名称!'
        },
        'maxPrepayRate'  : {
            'required'      : '请填写比例!'
        },
        'unInvoicedRate'  : {
            'required'      : '请填写比例!'
        },
        'contractBaseInterest'  : {
            'required'      : '请填写利率!'
        },
        'expectHKDays'  : {
            'required'      : '请填写天数!',
            'int'      : '请填写整数!'
        },
        'tradeAddPrice'  : {
            'required'      : '请填写加价!'
        },
        'weightedPrice'  : {
            'required'      : '请填写价格!'
        }
    }

    orderUnitFormInputChange(formInputData : any) {
        this.orderUnitFormError = formErrorHandler(formInputData, this.orderUnitForm, this.orderUnitFormValidationMessages)
    }

    createOrderUnitForm(): void {

        this.orderUnitForm = this.fb.group({
            'hsMonth'    : ['', [Validators.required ] ],
            'maxPrepayRate'    : ['', [Validators.required ] ],
            'unInvoicedRate'    : ['', [Validators.required ] ],
            'contractBaseInterest'    : ['', [Validators.required ] ],

            'expectHKDays'    : ['', [Validators.required, isInt() ] ],
            'tradeAddPrice'    : ['', [Validators.required ] ],
            'weightedPrice'    : ['', [Validators.required ] ]
        } )


        this.orderUnitForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.orderUnitFormInputChange(data)
        })
    }


    orderUnitFormSubmit() {

        if (this.orderUnitForm.invalid) {
            this.orderUnitFormInputChange(this.orderUnitForm.value)
            this.ignoreDirty = true

            console.log('当前信息: ', this.orderUnitForm, this.orderUnitFormError)
            return
        }

        const postData = this.orderUnitForm.value
        postData.expectHKDays = Number(this.orderUnitForm.value.expectHKDays)

        if (this.isAddNew) {
            this.hsOrderService.createNewOrderUnit(this.currentOrder.id, postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getOrderUnitList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            postData.id = this.currentOrderUnitId
            this.hsOrderService.modifyOrderUnit(this.currentOrder.id, this.currentOrderUnitId, postData).subscribe(
                data => {
                    console.log('修改成功: ', data)
                    this.httpService.successHandler(data)

                    this.getOrderUnitList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }


    showForm(isAddNew : boolean = true, unit?: any ) {

        if (isAddNew) {
            this.isAddNew = true
            this.currentOrderUnitId = 0

            this.orderUnitForm.patchValue({
                'hsMonth'    : '',
                'maxPrepayRate'    : '',
                'unInvoicedRate'    : '',
                'contractBaseInterest'    : '',

                'expectHKDays'    : '',
                'tradeAddPrice'    : '',
                'weightedPrice'    : ''

            })

        } else {
            this.isAddNew = false
            this.currentOrderUnitId = unit.id

            this.orderUnitForm.patchValue(unit)
        }


        this.isShowForm = !this.isShowForm
    }


}
