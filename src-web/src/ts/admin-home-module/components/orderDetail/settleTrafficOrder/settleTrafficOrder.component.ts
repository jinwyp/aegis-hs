import {Component, Input, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'



import { HttpService } from '../../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../../bs-form-module/validators/validator'

import { HSUserService } from '../../../../services/hsUser.service'
import { HSOrderService } from '../../../../services/hsOrder.service'


import {getEnum} from '../../../../services/localStorage'


@Component({
    selector: 'app-settletraffic',
    templateUrl: './settleTrafficOrder.component.html',
    styleUrls: ['./settleTrafficOrder.component.css']
})
export class SettleTrafficOrderComponent implements OnInit {

    @Input() currentOrder : any
    @Input() businessType : string
    @Input() party : any = {}

    currentSettleId : number = 1

    settleTrafficForm: FormGroup
    settleTrafficSearchForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    settleTrafficList : any[] = []


    unitList : any[] = []

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

        this.createSettleTrafficSearchForm()
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

        query = (<any>Object).assign(query, this.settleTrafficSearchForm.value)

        console.log('Query: ', query)

        this.hsOrderService.getSettleTrafficListByID(this.businessType, this.currentOrder.id, query).subscribe(
            data => {
                this.settleTrafficList = data.data.results

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


    createSettleTrafficSearchForm(): void {

        this.settleTrafficSearchForm = this.fb.group({
            'hsId'    : ['' ],
            'settleDateStart'    : [null ],
            'settleDateEnd'    : [null ],
            'amount'    : ['' ],
            'money'    : ['' ],
            'trafficCompanyId'    : ['' ]
        } )
    }


    settleTrafficFormError : any = {}
    settleTrafficFormValidationMessages: any = {
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

        'trafficCompanyId'  : {
            'required'      : '与哪个运输方结算!'
        }

    }


    settleTrafficFormInputChange(formInputData : any) {
        this.settleTrafficFormError = formErrorHandler(formInputData, this.settleTrafficForm, this.settleTrafficFormValidationMessages)
    }

    createSettleForm(): void {

        this.settleTrafficForm = this.fb.group({
            'hsId'    : ['', [Validators.required ] ],
            'settleDate'    : [null, [Validators.required ] ],

            'amount'    : ['', [Validators.required ] ],
            'money'    : ['', [Validators.required ] ],
            'trafficCompanyId'    : ['', [Validators.required ] ]

        } )


        this.settleTrafficForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.settleTrafficFormInputChange(data)
        })
    }





    settleTrafficFormSubmit() {

        if (this.settleTrafficForm.invalid) {
            this.settleTrafficFormInputChange(this.settleTrafficForm.value)
            this.ignoreDirty = true

            console.log('当前信息: ', this.settleTrafficForm, this.settleTrafficFormError)
            return
        }

        const postData = this.settleTrafficForm.value
        postData.orderId = this.currentOrder.id


        if (this.isAddNew) {

            this.hsOrderService.createNewSettleTraffic(this.businessType, this.currentOrder.id, postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getSettleList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )

        } else {
            postData.id = this.currentSettleId


            this.hsOrderService.modifySettleTraffic(this.businessType, this.currentOrder.id, this.currentSettleId, postData).subscribe(
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


    showForm(isAddNew : boolean = true, settle?: any) {

        this.ignoreDirty = false

        if (isAddNew) {
            this.isAddNew = true
            this.currentSettleId = 0

            this.settleTrafficForm.patchValue({
                'hsId'    : '',
                'settleDate'    : null,
                'amount'    : '',
                'money'    : '',

                'trafficCompanyId'    : ''
            })

        } else {
            this.isAddNew = false
            this.currentSettleId = settle.id

            this.settleTrafficForm.patchValue(settle)
        }

        this.isShowForm = !this.isShowForm
    }


    deleteItem (settle : any) {

        this.hsOrderService.delSettleTraffic(this.businessType, this.currentOrder.id, settle.id).subscribe(
            data => {
                console.log('保存成功: ', data)
                this.httpService.successHandler(data)

                this.getSettleList()
            },
            error => {this.httpService.errorHandler(error) }
        )

    }



}

