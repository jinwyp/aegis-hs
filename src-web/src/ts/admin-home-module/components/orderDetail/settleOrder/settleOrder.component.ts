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

    currentSettleId : number = 1

    settleForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    settleUpstreamList : any[] = []
    settleDownstreamList : any[] = []
    settleTrafficList : any[] = []

    partyList : any[] = []

    unitList : any[] = []

    settleDiscountModeList : any[] = getEnum('DiscountMode')


    pagination: any = {
        pageSize : 20,
        pageNo : 1,
        total : 1
    }

    settleTypeList : any[] = [
        { id : 'upstream', name : '上游结算'},
        { id : 'downstream', name : '下游结算'},
        { id : 'traffic', name : '运输方结算'},
    ]


    constructor(
        private httpService: HttpService,
        private fb: FormBuilder,
        private hsUserService: HSUserService,
        private hsOrderService: HSOrderService

    ) {

    }



    ngOnInit(): void {

        this.getPartyList()
        this.getSettleList()
        this.createSettleForm()

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
        this.hsOrderService.getSettleUpstreamListByID(this.currentOrder.id).subscribe(
            data => {
                this.settleUpstreamList = data.data.results

            },
            error => {this.httpService.errorHandler(error) }
        )
        this.hsOrderService.getSettleDownstreamListByID(this.currentOrder.id).subscribe(
            data => {
                this.settleDownstreamList = data.data.results

            },
            error => {this.httpService.errorHandler(error) }
        )
        this.hsOrderService.getSettleTrafficListByID(this.currentOrder.id).subscribe(
            data => {
                this.settleTrafficList = data.data.results

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


    settleFormError : any = {}
    settleFormValidationMessages: any = {
        'tempSettleType'  : {
            'required'      : '请选择结算类型!'
        },
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


    settleFormInputChange(formInputData : any, ignoreDirty : boolean = false) {
        this.settleFormError = formErrorHandler(formInputData, this.settleForm, this.settleFormValidationMessages, ignoreDirty)
    }

    createSettleForm(): void {

        this.settleForm = this.fb.group({
            'tempSettleType'    : ['upstream', [Validators.required ] ],
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


        this.settleForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.settleFormInputChange(data)
        })
    }





    settleFormSubmit() {

        if (!this.settleForm.value.discountType) {
            this.settleForm.patchValue({ discountType : '99999999' })
        }
        if (!this.settleForm.value.settleGap) {
            this.settleForm.patchValue({settleGap : 99999999})
        }
        if (!this.settleForm.value.trafficCompanyId) {
            this.settleForm.patchValue({trafficCompanyId : 99999999})
        }


        if (this.settleForm.invalid) {
            this.settleFormInputChange(this.settleForm.value, true)
            this.ignoreDirty = true

            console.log('当前信息: ', this.settleForm, this.settleFormError)
            return
        }

        const postData = this.settleForm.value
        postData.orderId = this.currentOrder.id


        if (this.settleForm.value.settleDate && typeof this.settleForm.value.settleDate === "object" ) {
            postData.settleDate = this.hsOrderService.formatDateTime(this.settleForm.value.settleDate)
        }


        if (this.isAddNew) {

            if (this.settleForm.value.tempSettleType === 'upstream') {
                this.hsOrderService.createNewSettleUpstream(this.currentOrder.id, postData).subscribe(
                    data => {
                        console.log('保存成功: ', data)
                        this.httpService.successHandler(data)

                        this.getSettleList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

            if (this.settleForm.value.tempSettleType === 'downstream') {
                this.hsOrderService.createNewSettleDownstream(this.currentOrder.id, postData).subscribe(
                    data => {
                        console.log('保存成功: ', data)
                        this.httpService.successHandler(data)

                        this.getSettleList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

            if (this.settleForm.value.tempSettleType === 'traffic') {
                this.hsOrderService.createNewSettleTraffic(this.currentOrder.id, postData).subscribe(
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


            if (this.settleForm.value.tempSettleType === 'upstream') {
                this.hsOrderService.modifySettleUpstream(this.currentOrder.id, this.currentSettleId, postData).subscribe(
                    data => {
                        console.log('修改成功: ', data)
                        this.httpService.successHandler(data)

                        this.getSettleList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

            if (this.settleForm.value.tempSettleType === 'downstream') {
                delete postData.amount
                this.hsOrderService.modifySettleDownstream(this.currentOrder.id, this.currentSettleId, postData).subscribe(
                    data => {
                        console.log('修改成功: ', data)
                        this.httpService.successHandler(data)

                        this.getSettleList()
                        this.showForm()

                    },
                    error => {this.httpService.errorHandler(error) }
                )
            }

            if (this.settleForm.value.tempSettleType === 'traffic') {
                this.hsOrderService.modifySettleTraffic(this.currentOrder.id, this.currentSettleId, postData).subscribe(
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


    showForm(isAddNew : boolean = true, settle?: any, settleType?: string ) {

        this.ignoreDirty = false

        if (isAddNew) {
            this.isAddNew = true
            this.currentSettleId = 0

            this.settleForm.patchValue({
                'tempSettleType' : 'upstream',
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
            settle.tempSettleType = settleType

            this.settleForm.patchValue(settle)
        }


        this.isShowForm = !this.isShowForm
    }



}

