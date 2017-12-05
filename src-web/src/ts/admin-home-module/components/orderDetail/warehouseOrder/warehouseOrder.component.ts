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
    @Input() party : any = {}

    currentWarehouseId : number = 1

    warehouseForm: FormGroup
    warehouseSearchForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    warehouseInList : any[] = []
    warehouseOutList : any[] = []
    warehouseOutInfo : any = {}

    unitList : any[] = []


    totalRukuAmount : number = 0
    totalRukuPrice : number = 0
    totalChukuAmount : number = 0
    totalChukuPrice : number = 0


    warehouseStatusList : any[] = getEnum('InStorageStatus')
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

        this.createWarehouseSearchForm()
        this.getOrderUnitList()
        this.getWarehouseList()
        this.createWarehouseForm()
    }


    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }


    getWarehouseList () {

        let query : any = {
            pageSize: this.pagination.pageSize,
            pageNo: this.pagination.pageNo
        }

        query = (<any>Object).assign(query, this.warehouseSearchForm.value)

        console.log('Query: ', query)

        if (this.warehouseType === 'ruku') {
            this.hsOrderService.getWarehouseInListByID(this.businessType, this.currentOrder.id, query).subscribe(
                data => {
                    this.warehouseInList = data.data.results

                    if (Array.isArray(data.data.results)) {

                        this.totalRukuAmount = 0
                        this.totalRukuPrice = 0

                        data.data.results.forEach( warehouse => {
                            this.totalRukuAmount = this.totalRukuAmount + warehouse.rukuAmount
                            this.totalRukuPrice = this.totalRukuPrice + warehouse.rukuPrice
                        })
                    }
                },
                error => {this.httpService.errorHandler(error) }
            )
        }

        if (this.warehouseType === 'chuku') {
            this.hsOrderService.getWarehouseOutListByID(this.businessType, this.currentOrder.id, query).subscribe(
                data => {
                    this.warehouseOutList = data.data.results

                    if (Array.isArray(data.data.results)) {

                        this.totalChukuAmount = 0
                        this.totalChukuPrice = 0

                        data.data.results.forEach( warehouse => {
                            this.totalChukuAmount = this.totalChukuAmount + warehouse.chukuAmount
                            this.totalChukuPrice = this.totalChukuPrice + warehouse.chukuPrice
                        })
                    }
                },
                error => {this.httpService.errorHandler(error) }
            )
        }
    }

    getWarehouseOutInfo (event : any) {
        if (event.id) {
            this.hsOrderService.getWarehouseOutInfoByHsID(this.businessType, this.currentOrder.id, event.id).subscribe(
                data => {
                    this.warehouseOutInfo = data.data
                },
                error => {this.httpService.errorHandler(error) }
            )
        }
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



    createWarehouseSearchForm(): void {

        this.warehouseSearchForm = this.fb.group({
            'hsId'    : ['' ],
            'rukuDateStart'    : [null ],
            'rukuDateEnd'    : [null ],

            'rukuStatus'    : ['' ],
            'rukuAmount'    : ['' ],
            'rukuPrice'    : ['' ],
            'locality'    : ['' ],
            'trafficMode'    : ['' ],

            'chukuDateStart'    : [null ],
            'chukuDateEnd'    : [null ],
            'chukuAmount'    : ['' ],
            'chukuPrice'    : ['' ]

        } )
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
            'required'      : '请填写入库金额!'
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
            'required'      : '请填写出库金额!'
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
                this.hsOrderService.createNewWareInhouse(this.businessType, this.currentOrder.id, postData).subscribe(
                    data => {
                        console.log('保存成功: ', data)
                        this.httpService.successHandler(data)

                        this.getWarehouseList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

            if (this.warehouseType === 'chuku') {
                this.hsOrderService.createNewWarehouseOut(this.businessType, this.currentOrder.id, postData).subscribe(
                    data => {
                        console.log('保存成功: ', data)
                        this.httpService.successHandler(data)

                        this.getWarehouseList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

        } else {
            postData.id = this.currentWarehouseId


            if (this.warehouseType === 'ruku') {
                this.hsOrderService.modifyWarehouseIn(this.businessType, this.currentOrder.id, this.currentWarehouseId, postData).subscribe(
                    data => {
                        console.log('修改成功: ', data)
                        this.httpService.successHandler(data)

                        this.getWarehouseList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

            if (this.warehouseType === 'chuku') {
                delete postData.amount
                this.hsOrderService.modifyWarehouseOut(this.businessType, this.currentOrder.id, this.currentWarehouseId, postData).subscribe(
                    data => {
                        console.log('修改成功: ', data)
                        this.httpService.successHandler(data)

                        this.getWarehouseList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }
        }

    }


    showForm(isAddNew : boolean = true, warehouse?: any) {

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
            this.currentWarehouseId = warehouse.id

            this.warehouseForm.patchValue(warehouse)
        }


        this.isShowForm = !this.isShowForm
    }


    deleteItem (warehouse : any) {

        if (this.warehouseType === 'ruku') {
            this.hsOrderService.delWarehouseIn(this.businessType, this.currentOrder.id, warehouse.id).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getWarehouseList()
                },
                error => {this.httpService.errorHandler(error) }
            )
        }

        if (this.warehouseType === 'chuku') {
            this.hsOrderService.delWarehouseOut(this.businessType, this.currentOrder.id, warehouse.id).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getWarehouseList()
                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }



}

