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
    @Input() businessType : string
    @Input() warehouseType : string = '' // 入库 ruku 出库 chuku

    currentWarehouseId : number = 1

    warehouseForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    warehouseInList : any[] = []
    warehouseOutList : any[] = []

    unitList : any[] = []

    // warehouseStatusList : any[] = getEnum('InStorageStatus')
    warehouseStatusList : any[] = [
        {    id : 'IN_STORAGE' , name : '已入库' },
        {    id : 'IN_TRANIT' , name : '运输中' }
    ]
    trafficModeList : any[] = getEnum('TrafficMode')

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
            this.hsOrderService.getWarehouseInListByID('cang', this.currentOrder.id).subscribe(
                data => {
                    this.warehouseInList = data.data.results
                },
                error => {this.httpService.errorHandler(error) }
            )
        }

        if (this.warehouseType === 'chuku') {
            this.hsOrderService.getWarehouseOutListByID('cang', this.currentOrder.id).subscribe(
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

        'rukuDate'  : {
            'required'      : '请填写入库日期!'
        },
        'rukuStatus'  : {
            'required'      : '请填写入库状态!'
        },
        'rukuAmount'  : {
            'required'      : '请填写入库吨数!'
        },
        'rukuPrice'  : {
            'required'      : '请填写入库单价: 元/吨!'
        },
        'locality'  : {
            'required'      : '请填写库房场地!'
        },
        'trafficMode'  : {
            'required'      : '请填写运输方式!'
        },


        'chukuDate'  : {
            'required'      : '请填写出库日期!'
        },
        'chukuAmount'  : {
            'required'      : '请填写出库吨数!'
        },
        'chukuPrice'  : {
            'required'      : '请填写出库单价: 元/吨!'
        }

    }


    warehouseFormInputChange(formInputData : any) {
        this.warehouseFormError = formErrorHandler(formInputData, this.warehouseForm, this.warehouseFormValidationMessages)
    }

    createWarehouseForm(): void {

        this.warehouseForm = this.fb.group({
            'hsId'    : ['', [Validators.required ] ],

            'locality'    : ['', [Validators.required ] ],

            'rukuDate'    : [null, [Validators.required ] ],
            'rukuStatus'    : ['', [Validators.required ] ],
            'rukuAmount'    : ['', [Validators.required ] ],
            'rukuPrice'    : ['', [Validators.required ] ],
            'trafficMode'    : ['', [Validators.required ] ],
            'cars'    : ['', [ ] ],
            'jhh'    : ['', [ ] ],
            'ship'    : ['', [ ] ],

            'chukuDate'    : [null, [Validators.required ] ],
            'chukuAmount'    : ['', [Validators.required ] ],
            'chukuPrice'    : ['', [Validators.required ] ]

        } )


        this.warehouseForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.warehouseFormInputChange(data)
        })
    }





    warehouseFormSubmit() {

        if (this.warehouseType === 'ruku') {
            if (!this.warehouseForm.value.chukuDate) {
                this.warehouseForm.patchValue({chukuDate : '2099-12-30'})
            }
            if (!this.warehouseForm.value.chukuAmount) {
                this.warehouseForm.patchValue({chukuAmount : 99999999})
            }
            if (!this.warehouseForm.value.chukuPrice) {
                this.warehouseForm.patchValue({chukuPrice : 99999999})
            }
        }
        if (this.warehouseType === 'chuku') {
            if (!this.warehouseForm.value.rukuDate) {
                this.warehouseForm.patchValue({rukuDate : '2099-12-30'})
            }
            if (!this.warehouseForm.value.rukuAmount) {
                this.warehouseForm.patchValue({rukuAmount : 99999999})
            }
            if (!this.warehouseForm.value.rukuPrice) {
                this.warehouseForm.patchValue({rukuPrice : 99999999})
            }
            if (!this.warehouseForm.value.rukuStatus) {
                this.warehouseForm.patchValue({rukuStatus : 'NULLLL'})
            }
            if (!this.warehouseForm.value.trafficMode) {
                this.warehouseForm.patchValue({trafficMode : 'NULLLL'})
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
                this.hsOrderService.createNewWareInhouse('cang', this.currentOrder.id, postData).subscribe(
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
                this.hsOrderService.createNewWarehouseOut('cang', this.currentOrder.id, postData).subscribe(
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
            postData.id = this.currentWarehouseId


            if (this.warehouseType === 'ruku') {
                this.hsOrderService.modifyWarehouseIn('cang', this.currentOrder.id, this.currentWarehouseId, postData).subscribe(
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
                this.hsOrderService.modifyWarehouseOut('cang', this.currentOrder.id, this.currentWarehouseId, postData).subscribe(
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
            this.currentWarehouseId = 0

            this.warehouseForm.patchValue({
                'hsId' : '',

                'locality' : '',

                'rukuDate'    : null,
                'rukuStatus'  : '',
                'rukuAmount'  : '',
                'rukuPrice'   : '',
                'trafficMode' : '',
                'cars'        : '',
                'jhh'         : '',
                'ship'        : '',

                'chukuDate'   : null,
                'chukuAmount' : '',
                'chukuPrice'  : ''

            })

        } else {
            this.isAddNew = false
            this.currentWarehouseId = settle.id

            this.warehouseForm.patchValue(settle)
        }


        this.isShowForm = !this.isShowForm
    }


    deleteItem (settle : any) {

        if (this.warehouseType === 'ruku') {
            this.hsOrderService.delWarehouseIn('cang', this.currentOrder.id, settle.id).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getSettleList()
                },
                error => {this.httpService.errorHandler(error) }
            )
        }

        if (this.warehouseType === 'chuku') {
            this.hsOrderService.delWarehouseOut('cang', this.currentOrder.id, settle.id).subscribe(
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

