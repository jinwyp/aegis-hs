import {Component, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'
import {ActivatedRoute, ParamMap} from '@angular/router'


import 'rxjs/add/operator/switchMap'


import { HttpService } from '../../../bs-form-module/services/http.service'

import { formErrorHandler, isInt, isMatched, checkFieldIsExist } from '../../../bs-form-module/validators/validator'

import { UserInfoService } from '../../../services/userInfo.service'
import { HSUserService } from '../../../services/hsUser.service'
import { HSOrderService } from '../../../services/hsOrder.service'





@Component({
  selector: 'app-order-detail',
  templateUrl: './orderDetail.component.html',
  styleUrls: ['./orderDetail.component.css']
})
export class OrderDetailComponent implements OnInit {

    currentOrder : any
    currentOrderId : number
    currentOrderUnitId : number

    orderUnitForm: FormGroup
    transferForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    unitList : any[] = []

    departmentList : any[] = []
    teamList : any[] = []
    partyList : any[] = []
    userList : any[] = []


    pagination: any = {
        pageSize : 20,
        pageNo : 1,
        total : 1
    }

    currentTabIndex : number = 1

    constructor(
        private route: ActivatedRoute,
        private httpService: HttpService,
        private fb: FormBuilder,
        private hsUserService: HSUserService,
        private hsOrderService: HSOrderService

    ) {

    }



    ngOnInit(): void {

        this.route.paramMap.switchMap( (params: ParamMap) => {
            this.currentOrderId = Number(params.get('orderId'))
            return this.hsOrderService.getOrderByID(this.currentOrderId)
        }).subscribe(
            data => {
                if (data) {
                    this.currentOrder = data.data
                }

                this.getOrderUnitList()

                // console.log('Order信息: ', data)
            },
            error => {this.httpService.errorHandler(error) }
        )


        this.getPartyList()
        this.getDepartmentList()
        this.getTeamList()
        this.getUserList()
        this.createOrderUnitForm()
        this.createTransferForm()
    }

    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }

    getOrder () {
        this.hsOrderService.getOrderByID(this.currentOrderId).subscribe(
            data => {
                this.currentOrder = data.data
            },
            error => {this.httpService.errorHandler(error) }
        )
    }
    getOrderUnitList () {
        this.hsOrderService.getOrderUnitListByID(this.currentOrderId).subscribe(
            data => {
                this.unitList = data.data.results

            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    getDepartmentList () {
        this.hsUserService.getDepartmentList().subscribe(
            data => {
                this.departmentList = data.data.results

            },
            error => {this.httpService.errorHandler(error) }
        )
    }
    getTeamList () {
        this.hsUserService.getTeamList().subscribe(
            data => {
                this.teamList = data.data.results

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


    getUserList () {
        this.hsUserService.getUserListDepartment().subscribe(
            data => {

                const tempResult : any[] = []

                if (data.data && Array.isArray(data.data)) {
                    data.data.forEach( user => {
                        tempResult.push ({
                            id : user.id,
                            name : user.phone
                        })
                    })
                }

                this.userList = tempResult

            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    orderUnitFormError : any = {}
    transferFormError : any = {}
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
        },
        'userId'  : {
            'required'      : '请选择财务人员!'
        }
    }

    orderUnitFormInputChange(formInputData : any) {
        this.orderUnitFormError = formErrorHandler(formInputData, this.orderUnitForm, this.orderUnitFormValidationMessages)
    }
    transferFormInputChange(formInputData : any) {
        this.transferFormError = formErrorHandler(formInputData, this.transferForm, this.orderUnitFormValidationMessages)
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
            this.hsOrderService.createNewOrderUnit(this.currentOrderId, postData).subscribe(
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
            this.hsOrderService.modifyOrderUnit(this.currentOrderId, this.currentOrderUnitId, postData).subscribe(
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



    changeTab (currentIndex : number) {
        this.currentTabIndex = currentIndex
    }





    createTransferForm(): void {

        this.transferForm = this.fb.group({
            'userId'    : ['', [Validators.required ] ]
        } )

        this.transferForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.transferFormInputChange(data)
        })
    }

    transferFormSubmit() {

        if (this.transferForm.invalid) {
            this.transferFormInputChange(this.transferForm.value)
            this.ignoreDirty = true

            console.log('当前信息: ', this.transferForm, this.transferFormError)
            return
        }


        this.hsOrderService.transferOrder(this.currentOrderId, this.transferForm.value.userId).subscribe(
            data => {
                console.log('保存成功: ', data)
                this.httpService.successHandler(data)
            },
            error => {this.httpService.errorHandler(error) }
        )

    }
}

