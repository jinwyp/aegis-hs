import {Component, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'
import {ActivatedRoute, ParamMap} from "@angular/router"


import 'rxjs/add/operator/switchMap'


import { HttpService } from '../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../bs-form-module/validators/validator'
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
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    unitList : any[] = []
    teamList : any[] = []
    filterTeamList : any[] = []
    partyList : any[] = []


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

        this.getTeamList()
        this.createOrderUnitForm()
    }

    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }


    getOrderUnitList () {
        this.hsOrderService.getOrderUnitListByID(this.currentOrderId).subscribe(
            data => {
                this.unitList = data.data.results

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

    filterTeams (event : any) {
        this.filterTeamList = this.teamList.filter( team => {
            return team.deptId === event.id
        })
    }

    getPartyList () {

        this.hsUserService.getPartyList().subscribe(
            data => {
                this.partyList = data.data.results

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
            'required'      : '请填写天数!'
        },
        'tradeAddPrice'  : {
            'required'      : '请填写加价!'
        },
        'weightedPrice'  : {
            'required'      : '请填写价格!'
        }
    }

    orderUnitFormInputChange(formInputData : any, ignoreDirty : boolean = false) {
        this.orderUnitFormError = formErrorHandler(formInputData, this.orderUnitForm, this.orderUnitFormValidationMessages, ignoreDirty)
    }

    createOrderUnitForm(): void {

        this.orderUnitForm = this.fb.group({
            'hsMonth'    : ['', [Validators.required ] ],
            'maxPrepayRate'    : ['', [Validators.required ] ],
            'unInvoicedRate'    : ['', [Validators.required ] ],
            'contractBaseInterest'    : ['', [Validators.required ] ],

            'expectHKDays'    : ['', [Validators.required ] ],
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
            this.orderUnitFormInputChange(this.orderUnitForm.value, true)
            this.ignoreDirty = true

            console.log('当前信息: ', this.orderUnitForm, this.orderUnitFormError)
            return
        }

        const postData = this.orderUnitForm.value


        if (typeof this.orderUnitForm.value.hsMonth === "object" ) {
            if ( this.orderUnitForm.value.hsMonth.month.toString().length === 1) {
                postData.hsMonth = this.orderUnitForm.value.hsMonth.year.toString() + '0' + this.orderUnitForm.value.hsMonth.month.toString()
            } else {
                postData.hsMonth = this.orderUnitForm.value.hsMonth.year.toString() + this.orderUnitForm.value.hsMonth.month.toString()
            }
        }


        console.log(postData)

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

}

