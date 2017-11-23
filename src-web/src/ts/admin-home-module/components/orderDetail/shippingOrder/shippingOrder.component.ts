import {Component, Input, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'



import { HttpService } from '../../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../../bs-form-module/validators/validator'

import { HSOrderService } from '../../../../services/hsOrder.service'


import {getEnum} from '../../../../services/localStorage'


@Component({
  selector: 'app-shipping',
  templateUrl: './shippingOrder.component.html',
  styleUrls: ['./shippingOrder.component.css']
})
export class ShippingOrderComponent implements OnInit {

    @Input() currentOrder : any
    @Input() businessType : string
    @Input() party : any = {}

    currentShippingOrderId : number = 1

    shippingForm: FormGroup
    shippingSearchForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    shippingList : any[] = []

    unitList : any[] = []

    totalAmount : number = 0


    arriveStatusList : any[] = getEnum('CargoArriveStatus')
    trafficModeList : any[] = getEnum('TrafficMode')


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

        this.createShippingSearchForm()
        this.getOrderUnitList()
        this.getShippingList()
        this.createShippingForm()

    }


    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }


    getShippingList () {
        let query : any = {
            pageSize: this.pagination.pageSize,
            pageNo: this.pagination.pageNo
        }

        query = (<any>Object).assign(query, this.shippingSearchForm.value)

        console.log('Query: ', query)

        this.hsOrderService.getShippingListByID(this.businessType, this.currentOrder.id, query).subscribe(
            data => {
                this.shippingList = data.data.results

                if (Array.isArray(data.data.results)) {

                    this.totalAmount = data.data.results.reduce( (accumulator, shipping) => {
                        return accumulator + shipping.fyAmount
                    }, 0)
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


    createShippingSearchForm(): void {

        this.shippingSearchForm = this.fb.group({
            'hsId'    : ['' ],

            'fyDateStart'    : [null ],
            'fyDateEnd'    : [null ],

            'fyAmount'    : ['' ],
            'arriveStatus'    : ['' ],
            'upstreamTrafficMode'    : ['' ],
            'downstreamTrafficMode'    : ['' ]
        } )
    }

    shippingFormError : any = {}
    shippingFormValidationMessages: any = {
        'hsId'  : {
            'required'      : '请选择核算月!'
        },
        'fyDate'  : {
            'required'      : '请填写发运日期!'
        },
        'fyAmount'  : {
            'required'      : '请填写发运吨数!'
        },
        'arriveStatus'  : {
            'required'      : '请填写到场状态!'
        },
        'upstreamTrafficMode'  : {
            'required'      : '请填写运输方式!'
        },
        'downstreamTrafficMode'  : {
            'required'      : '请填写运输方式!'
        }
    }

    shippingFormInputChange(formInputData : any) {
        this.shippingFormError = formErrorHandler(formInputData, this.shippingForm, this.shippingFormValidationMessages)
    }

    createShippingForm(): void {

        this.shippingForm = this.fb.group({
            'hsId'    : ['', [Validators.required ] ],
            'fyDate'    : [null, [Validators.required ] ],
            'fyAmount'    : ['', [Validators.required ] ],
            'arriveStatus'    : ['', [Validators.required ] ],

            'upstreamTrafficMode'    : ['', [Validators.required ] ],
            'upstreamCars'    : ['', [ ] ],
            'upstreamJHH'    : ['', [ ] ],
            'upstreamShip'    : ['', [ ] ],

            'downstreamTrafficMode'    : ['', [Validators.required ] ],
            'downstreamCars'    : ['', [ ] ],
            'downstreamJHH'    : ['', [ ] ],
            'downstreamShip'    : ['', [ ] ]
        } )


        this.shippingForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.shippingFormInputChange(data)
        })
    }





    shippingFormSubmit() {

        if (this.shippingForm.invalid) {
            this.shippingFormInputChange(this.shippingForm.value)
            this.ignoreDirty = true

            console.log('当前信息: ', this.shippingForm, this.shippingFormError)
            return
        }

        const postData = this.shippingForm.value
        postData.orderId = this.currentOrder.id


        if (this.isAddNew) {
            this.hsOrderService.createNewShipping(this.businessType, this.currentOrder.id, postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getShippingList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            postData.id = this.currentShippingOrderId
            delete postData.fyAmount

            this.hsOrderService.modifyShipping(this.businessType, this.currentOrder.id, this.currentShippingOrderId, postData).subscribe(
                data => {
                    console.log('修改成功: ', data)
                    this.httpService.successHandler(data)

                    this.getShippingList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }


    showForm(isAddNew : boolean = true, shippingOrder?: any ) {

        if (isAddNew) {
            this.isAddNew = true
            this.currentShippingOrderId = 0

            this.shippingForm.patchValue({
                'hsId'    : '',
                'fyDate'    : null,
                'fyAmount'    : '',
                'arriveStatus'    : '',

                'upstreamTrafficMode'    : '',
                'upstreamCars'    : '',
                'upstreamJHH'    : '',
                'upstreamShip'    : '',

                'downstreamTrafficMode'    : '',
                'downstreamCars'    : '',
                'downstreamJHH'    : '',
                'downstreamShip'    : ''
            })


        } else {
            this.isAddNew = false
            this.currentShippingOrderId = shippingOrder.id

            this.shippingForm.patchValue(shippingOrder)
        }


        this.isShowForm = !this.isShowForm
    }



    deleteItem (shipping : any) {

        this.hsOrderService.delShipping(this.businessType, this.currentOrder.id, shipping.id).subscribe(
            data => {
                console.log('保存成功: ', data)
                this.httpService.successHandler(data)

                this.getShippingList()
            },
            error => {this.httpService.errorHandler(error) }
        )
    }


}

