import {Component, Input, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'



import { HttpService } from '../../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../../bs-form-module/validators/validator'

import { HSUserService } from '../../../../services/hsUser.service'
import { HSOrderService } from '../../../../services/hsOrder.service'


import {getEnum} from '../../../../services/localStorage'


@Component({
    selector: 'app-settle',
    templateUrl: './settleOrder.component.html',
    styleUrls: ['./settleOrder.component.css']
})
export class SettleOrderComponent implements OnInit {

    @Input() currentOrder : any
    @Input() businessType : string
    @Input() settleType : string = '' // ying : 1 settlesellerupstream 2 settlebuyerdownstream 3 settletraffic . // cang : 1 settlesellerdownstream 2 settlebuyerupstream
    @Input() party : any = {}

    currentSettleId : number = 1

    settleForm: FormGroup
    settleSearchForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    settleYingUpstreamList : any[] = []
    settleCangUpstreamList : any[] = []
    settleYingDownstreamList : any[] = []
    settleCangDownstreamList : any[] = []


    unitList : any[] = []
    unitListStat : any[] = []
    unitListStatObject : any = {}
    unitListStatAdditionalInfoObject : any = {}

    totalAmount : number = 0
    totalMoney : number = 0


    settleDiscountModeList : any[] = getEnum('DiscountMode')


    pagination: any = {
        pageSize : 10000,
        pageNo : 1,
        total : 1
    }

    yingOrderSettleTypeList : any[] = [
        { id : 'settlesellerupstream', name : '上游结算'},
        { id : 'settlebuyerdownstream', name : '下游结算'},
        { id : 'settletraffic', name : '运输方结算'}
    ]
    cangOrderSettleTypeList : any[] = [
        { id : 'settlesellerdownstream', name : '下游结算'},
        { id : 'settlebuyerupstream', name : '上游结算'}
    ]


    constructor(
        private httpService: HttpService,
        private fb: FormBuilder,
        private hsUserService: HSUserService,
        private hsOrderService: HSOrderService

    ) {

    }



    ngOnInit(): void {

        this.createSettleSearchForm()
        this.getOrderUnitList()
        this.getSettleList()
        this.createSettleForm()

    }


    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }


    getSettleList () {
        let query : any = {
            pageSize: this.pagination.pageSize,
            pageNo: this.pagination.pageNo
        }

        query = (<any>Object).assign(query, this.settleSearchForm.value)

        console.log('Query: ', query)

        if (this.businessType === 'ying') {
            if (this.settleType === 'settlesellerupstream') {
                this.hsOrderService.getSettleUpstreamListByID(this.businessType, this.settleType, this.currentOrder.id, query).subscribe(
                    data => {
                        this.settleYingUpstreamList = data.data.results

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

            if (this.settleType === 'settlebuyerdownstream') {
                this.hsOrderService.getSettleDownstreamListByID(this.businessType, this.settleType, this.currentOrder.id, query).subscribe(
                    data => {
                        this.settleYingDownstreamList = data.data.results

                        if (Array.isArray(data.data.results)) {

                            this.totalAmount = 0
                            this.totalMoney = 0

                            data.data.results.forEach( settle => {
                                this.totalAmount = this.totalAmount + settle.amount
                                this.totalMoney = this.totalMoney + settle.money
                            })
                        }

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

        } else {
            if (this.settleType === 'settlebuyerupstream') {
                this.hsOrderService.getSettleUpstreamListByID(this.businessType, this.settleType, this.currentOrder.id, query).subscribe(
                    data => {
                        this.settleCangUpstreamList = data.data.results

                        if (Array.isArray(data.data.results)) {

                            this.totalAmount = 0
                            this.totalMoney = 0

                            data.data.results.forEach( settle => {
                                this.totalAmount = this.totalAmount + settle.amount
                                this.totalMoney = this.totalMoney + settle.money
                            })
                        }

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

            if (this.settleType === 'settlesellerdownstream') {
                this.hsOrderService.getSettleDownstreamListByID(this.businessType, this.settleType, this.currentOrder.id, query).subscribe(
                    data => {
                        this.settleCangDownstreamList = data.data.results

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }
        }

    }

    getAdditionalInfo () {
        this.hsOrderService.getSettleUpstreamAdditionalInfo(this.businessType, this.currentOrder.id, this.settleType).subscribe(
            data => {

                if (Array.isArray(data.data)) {

                    data.data.forEach( unit => {
                        this.unitListStatAdditionalInfoObject[unit.hsId] = unit
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


    createSettleSearchForm(): void {

        this.settleSearchForm = this.fb.group({
            'hsId'    : ['' ],
            'settleDateStart'    : [null ],
            'settleDateEnd'    : [null ],
            'amount'    : ['' ],
            'money'    : ['' ],
            'settleGap'    : ['' ],

            'discountType'    : ['' ]
        } )
    }


    settleFormError : any = {}
    settleFormValidationMessages: any = {
        'hsId'  : {
            'required'      : '请选择核算月!'
        },

        'settleDate'  : {
            'required'      : '请填写结算日期!'
        },
        'amount'  : {
            'required'      : '请填写结算数量!'
        },
        'money'  : {
            'required'      : '请填写结算金额!'
        },
        'settleGap'  : {
            'required'      : '结算扣吨!'
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
        }

    }


    settleFormInputChange(formInputData : any) {
        this.settleFormError = formErrorHandler(formInputData, this.settleForm, this.settleFormValidationMessages)
    }

    createSettleForm(): void {

        this.settleForm = this.fb.group({
            'hsId'    : ['', [Validators.required ] ],
            'settleDate'    : [null, [Validators.required ] ],

            'discountType'    : ['', [Validators.required ] ],
            'discountInterest'    : ['', [] ],
            'discountDays'    : ['', [] ],
            'discountAmount'    : ['', [] ],

            'amount'    : ['', [Validators.required ] ],
            'money'    : ['', [Validators.required ] ],
            'settleGap'    : ['', [Validators.required ] ]

        } )


        this.settleForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.settleFormInputChange(data)
        })
    }





    settleFormSubmit() {

        if (this.settleType === 'settlesellerupstream' || this.settleType === 'settlesellerdownstream') {
            if (!this.settleForm.value.amount) {
                this.settleForm.patchValue({amount : 99999999})
            }
            if (!this.settleForm.value.money) {
                this.settleForm.patchValue({money : 99999999})
            }
            if (!this.settleForm.value.settleGap) {
                this.settleForm.patchValue({settleGap : 99999999})
            }
        }

        if (this.settleType === 'settlebuyerdownstream' || this.settleType === 'settlebuyerupstream') {
            if (!this.settleForm.value.discountType) {
                this.settleForm.patchValue({ discountType : '99999999' })
            }
        }




        if (this.settleForm.invalid) {
            this.settleFormInputChange(this.settleForm.value)
            this.ignoreDirty = true

            console.log('当前信息: ', this.settleForm, this.settleFormError)
            return
        }

        const postData = this.settleForm.value
        postData.orderId = this.currentOrder.id


        if (this.isAddNew) {

            if (this.settleType === 'settlesellerupstream') {
                this.hsOrderService.createNewSettleUpstream(this.businessType, this.settleType, this.currentOrder.id, postData).subscribe(
                    data => {
                        console.log('保存成功: ', data)
                        this.httpService.successHandler(data)

                        this.getSettleList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

            if (this.settleType === 'settlebuyerdownstream') {
                this.hsOrderService.createNewSettleDownstream(this.businessType, this.settleType, this.currentOrder.id, postData).subscribe(
                    data => {
                        console.log('保存成功: ', data)
                        this.httpService.successHandler(data)

                        this.getSettleList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }


            if (this.settleType === 'settlebuyerupstream') {
                this.hsOrderService.createNewSettleUpstream(this.businessType, this.settleType, this.currentOrder.id, postData).subscribe(
                    data => {
                        console.log('保存成功: ', data)
                        this.httpService.successHandler(data)

                        this.getSettleList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

            if (this.settleType === 'settlesellerdownstream') {
                this.hsOrderService.createNewSettleDownstream(this.businessType, this.settleType, this.currentOrder.id, postData).subscribe(
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


            if (this.settleType === 'settlesellerupstream') {
                this.hsOrderService.modifySettleUpstream(this.businessType, this.settleType, this.currentOrder.id, this.currentSettleId, postData).subscribe(
                    data => {
                        console.log('修改成功: ', data)
                        this.httpService.successHandler(data)

                        this.getSettleList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

            if (this.settleType === 'settlebuyerdownstream') {
                delete postData.amount
                this.hsOrderService.modifySettleDownstream(this.businessType, this.settleType, this.currentOrder.id, this.currentSettleId, postData).subscribe(
                    data => {
                        console.log('修改成功: ', data)
                        this.httpService.successHandler(data)

                        this.getSettleList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

            if (this.settleType === 'settlebuyerupstream') {
                this.hsOrderService.modifySettleUpstream(this.businessType, this.settleType, this.currentOrder.id, this.currentSettleId, postData).subscribe(
                    data => {
                        console.log('修改成功: ', data)
                        this.httpService.successHandler(data)

                        this.getSettleList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

            if (this.settleType === 'settlesellerdownstream') {
                delete postData.amount
                this.hsOrderService.modifySettleDownstream(this.businessType, this.settleType, this.currentOrder.id, this.currentSettleId, postData).subscribe(
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

            this.settleForm.patchValue({
                'hsId'    : '',
                'settleDate'    : null,
                'amount'    : '',
                'money'    : '',


                'discountType'    : '',
                'discountInterest'    : '',
                'discountDays'    : '',
                'discountAmount'    : '',

                'settleGap'    : ''
            })

        } else {
            this.isAddNew = false
            this.currentSettleId = settle.id

            this.settleForm.patchValue(settle)
        }

        this.getAdditionalInfo(this.settleType)

        this.isShowForm = !this.isShowForm
    }


    deleteItem (settle : any) {

        if (this.settleType === 'settlesellerupstream') {
            this.hsOrderService.delSettleUpstream(this.businessType, this.settleType, this.currentOrder.id, settle.id).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getSettleList()
                },
                error => {this.httpService.errorHandler(error) }
            )
        }

        if (this.settleType === 'settlebuyerdownstream') {
            this.hsOrderService.delSettleDownstream(this.businessType, this.settleType, this.currentOrder.id, settle.id).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getSettleList()
                },
                error => {this.httpService.errorHandler(error) }
            )
        }

        if (this.settleType === 'settlebuyerupstream') {
            this.hsOrderService.delSettleUpstream(this.businessType, this.settleType, this.currentOrder.id, settle.id).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getSettleList()
                },
                error => {this.httpService.errorHandler(error) }
            )
        }

        if (this.settleType === 'settlesellerdownstream') {
            this.hsOrderService.delSettleDownstream(this.businessType, this.settleType, this.currentOrder.id, settle.id).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getSettleList()
                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }


    changeUnit (event : any) {

        if (this.unitListStatAdditionalInfoObject && this.unitListStatAdditionalInfoObject[event.id]) {

            if (this.unitListStatAdditionalInfoObject[event.id].hasSettled) {
                this.settleForm.patchValue({
                    'settleDate'    : this.unitListStatAdditionalInfoObject[event.id].lastHuikuanDate,
                    'amount'    : this.unitListStatAdditionalInfoObject[event.id].totalBuyerNums,
                    'money'    : this.unitListStatAdditionalInfoObject[event.id].purchaseCargoAmountOfMoney
                })
            }

            // hasSettled:true
            // hsId:6
            // lastHuikuanDate:"2017-06-23 00:00:00"
            // name:undefined
            // orderId:12
            // purchaseCargoAmountOfMoney:23816677.57
            // totalBuyerNums:40530
        }



    }
}

