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
    currentOrderId : any

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
            this.currentOrderId = params.get('orderId')
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
                this.unitList = data.data

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
        'name'  : {
            'required'      : '请填写名称!'
        },
        'deptId'  : {
            'required'      : '请选择事业部门!'
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

        if (this.isAddNew) {
            this.hsOrderService.createNewOrder(postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            this.hsOrderService.modifyOrder(this.currentOrderId, postData).subscribe(
                data => {
                    console.log('修改成功: ', data)
                    this.httpService.successHandler(data)

                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }


    showForm(isAddNew : boolean = true, team?: any ) {

        if (isAddNew) {
            this.isAddNew = true

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

            this.orderUnitForm.patchValue(team)
        }


        this.isShowForm = !this.isShowForm
    }



    changeTab (currentIndex : number) {
        this.currentTabIndex = currentIndex
    }

}

