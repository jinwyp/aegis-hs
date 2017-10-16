import {Component, Input, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'



import { HttpService } from '../../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../../bs-form-module/validators/validator'

import { HSUserService } from '../../../../services/hsUser.service'
import { HSOrderService } from '../../../../services/hsOrder.service'


import {getEnum} from '../../../../services/localStorage'


@Component({
    selector: 'app-warehouse',
    templateUrl: './warehouseOrder.component.html',
    styleUrls: ['./warehouseOrder.component.css']
})
export class WarehouseOrderComponent implements OnInit {

    @Input() currentOrder : any
    @Input() warehouseType : string = '' // 入库 ruku 出库 chuku

    currentSettleId : number = 1

    warehouseForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    warehouseInList : any[] = []
    warehouseOutList : any[] = []

    unitList : any[] = []

    settleDiscountModeList : any[] = getEnum('DiscountMode')


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

        this.getSettleList()
        this.createWarehouseForm()

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


    getSettleList () {

        if (this.warehouseType === 'ruku') {
            this.hsOrderService.getWarehouseInListByID(this.currentOrder.id).subscribe(
                data => {
                    this.warehouseInList = data.data.results
                },
                error => {this.httpService.errorHandler(error) }
            )
        }

        if (this.warehouseType === 'chuku') {
            this.hsOrderService.getWarehouseOutListByID(this.currentOrder.id).subscribe(
                data => {
                    this.warehouseOutList = data.data.results
                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }



    warehouseFormError : any = {}
    warehouseFormValidationMessages: any = {
        'hsId'  : {
            'required'      : '请选择核算月!'
        },

        'settleDate'  : {
            'required'      : '请填写结算日期!'
        },
        'amount'  : {
            'required'      : '请填写结算数量(吨)!'
        },
        'money'  : {
            'required'      : '请填写结算金额!'
        },

        'discountType'  : {
            'required'      : '请填写折扣类型!'
        },
        'discountInterest'  : {
            'required'      : '请填写利率折扣!'
        },
        'discountDays'  : {
            'required'      : '请填写利率折扣天数!'
        },
        'discountAmount'  : {
            'required'      : '请填写折扣金额!'
        },

        'settleGap'  : {
            'required'      : '结算扣吨!'
        },

        'trafficCompanyId'  : {
            'required'      : '与哪个运输方结算!'
        }

    }


    warehouseFormInputChange(formInputData : any) {
        this.warehouseFormError = formErrorHandler(formInputData, this.warehouseForm, this.warehouseFormValidationMessages)
    }

    createWarehouseForm(): void {

        this.warehouseForm = this.fb.group({
            'hsId'    : ['', [Validators.required ] ],
            'settleDate'    : [null, [Validators.required ] ],
            'amount'    : ['', [Validators.required ] ],
            'money'    : ['', [Validators.required ] ],


            'discountType'    : ['', [Validators.required ] ],
            'discountInterest'    : ['', [] ],
            'discountDays'    : ['', [] ],
            'discountAmount'    : ['', [] ],

            'settleGap'    : ['', [Validators.required ] ],

            'trafficCompanyId'    : ['', [Validators.required ] ]

        } )


        this.warehouseForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.warehouseFormInputChange(data)
        })
    }





    warehouseFormSubmit() {

        if (!this.warehouseForm.value.discountType) {
            this.warehouseForm.patchValue({ discountType : '99999999' })
        }
        if (!this.warehouseForm.value.settleGap) {
            this.warehouseForm.patchValue({settleGap : 99999999})
        }
        if (!this.warehouseForm.value.trafficCompanyId) {
            this.warehouseForm.patchValue({trafficCompanyId : 99999999})
        }

        if (this.warehouseType === 'settlesellerupstream') {
            if (!this.warehouseForm.value.amount) {
                this.warehouseForm.patchValue({amount : 99999999})
            }
            if (!this.warehouseForm.value.money) {
                this.warehouseForm.patchValue({money : 99999999})
            }
        }



        if (this.warehouseForm.invalid) {
            this.warehouseFormInputChange(this.warehouseForm.value)
            this.ignoreDirty = true

            console.log('当前信息: ', this.warehouseForm, this.warehouseFormError)
            return
        }

        const postData = this.warehouseForm.value
        postData.orderId = this.currentOrder.id


        if (this.isAddNew) {

            if (this.warehouseType === 'ruku') {
                this.hsOrderService.createNewWareInhouse(this.currentOrder.id, postData).subscribe(
                    data => {
                        console.log('保存成功: ', data)
                        this.httpService.successHandler(data)

                        this.getSettleList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

            if (this.warehouseType === 'chuku') {
                this.hsOrderService.createNewWarehouseOut(this.currentOrder.id, postData).subscribe(
                    data => {
                        console.log('保存成功: ', data)
                        this.httpService.successHandler(data)

                        this.getSettleList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

        } else {
            postData.id = this.currentSettleId


            if (this.warehouseType === 'ruku') {
                this.hsOrderService.modifyWarehouseIn(this.currentOrder.id, this.currentSettleId, postData).subscribe(
                    data => {
                        console.log('修改成功: ', data)
                        this.httpService.successHandler(data)

                        this.getSettleList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

            if (this.warehouseType === 'chuku') {
                delete postData.amount
                this.hsOrderService.modifyWarehouseOut(this.currentOrder.id, this.currentSettleId, postData).subscribe(
                    data => {
                        console.log('修改成功: ', data)
                        this.httpService.successHandler(data)

                        this.getSettleList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }
        }

    }


    showForm(isAddNew : boolean = true, settle?: any) {

        this.ignoreDirty = false

        if (isAddNew) {
            this.isAddNew = true
            this.currentSettleId = 0

            this.warehouseForm.patchValue({
                'hsId'    : '',
                'settleDate'    : null,
                'amount'    : '',
                'money'    : '',


                'discountType'    : '',
                'discountInterest'    : '',
                'discountDays'    : '',
                'discountAmount'    : '',

                'settleGap'    : '',

                'trafficCompanyId'    : ''
            })

        } else {
            this.isAddNew = false
            this.currentSettleId = settle.id

            this.warehouseForm.patchValue(settle)
        }


        this.isShowForm = !this.isShowForm
    }


    deleteItem (settle : any) {

        if (this.warehouseType === 'ruku') {
            this.hsOrderService.delWarehouseIn(this.currentOrder.id, settle.id).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getSettleList()
                },
                error => {this.httpService.errorHandler(error) }
            )
        }

        if (this.warehouseType === 'chuku') {
            this.hsOrderService.delWarehouseOut(this.currentOrder.id, settle.id).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getSettleList()
                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }



}

