import {Component, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'
import { ActivatedRoute } from '@angular/router'

import { HttpService } from '../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../bs-form-module/validators/validator'
import { UserInfoService } from '../../../services/userInfo.service'
import { HSUserService } from '../../../services/hsUser.service'
import { HSOrderService } from '../../../services/hsOrder.service'

import {getEnum} from '../../../services/localStorage'





@Component({
  selector: 'app-order',
  templateUrl: './orderList.component.html',
  styleUrls: ['./orderList.component.css']
})
export class OrderListComponent implements OnInit {

    businessType : string = ''

    sessionUser : any
    currentOrderId : any

    orderForm: FormGroup
    orderOtherPartyForm: FormGroup
    orderSearchForm: FormGroup
    ignoreDirty: boolean = false

    otherParty1List : any[] = []
    otherParty2List : any[] = []

    isShowForm: boolean = false
    isAddNew: boolean = true

    orderList : any[] = []
    departmentList : any[] = []
    teamList : any[] = []
    filterTeamList : any[] = []
    partyList : any[] = []
    partyListZhangWu : any[] = []
    partyListOther : any[] = []
    partyListObject : any = {}




    orderStatusList : any[] = getEnum('OrderStatus')
    payModeList : any[] = getEnum('SettleMode')

    cargoTypeList : any[] = getEnum('CargoType')
    // customerType : any[] = getEnum('CustomerType')
    customerType : any[] = [
        {id: 'TRAFFICKCER', name: '贸易公司'},
        {id: 'ACCOUNTING_COMPANY', name: '账务公司'}
    ]

    positionList : any[] = [
        {id: 1, name: '上游与主账户公司之间'},
        {id: 2, name: '主账户与下游公司之间'}
    ]

    pagination: any = {
        pageSize : 20,
        pageNo : 1,
        total : 1
    }


    constructor(
        private route: ActivatedRoute,
        private httpService: HttpService,
        private fb: FormBuilder,
        private userService: UserInfoService,
        private hsUserService: HSUserService,
        private hsOrderService: HSOrderService

    ) {

    }



    ngOnInit(): void {

        this.route.data.subscribe( (data) => this.businessType = data.businessType)

        this.createOrderForm()
        this.createOrderOtherPartyForm()
        this.createOrderSearchForm()

        this.getPartyList()
        this.getDepartmentList()
        this.getTeamList()
        this.getOrderList()
        this.getSessionUserInfo()

    }

    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }

    getSessionUserInfo () {
        this.userService.sessionUserInfo().subscribe(
            data => {
                if (data) {
                    this.sessionUser = data
                }
                // console.log('当前登陆的用户信息: ', data)
            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    getOrderList () {

        let query : any = {
            pageSize: this.pagination.pageSize,
            pageNo: this.pagination.pageNo
        }

        query = (<any>Object).assign(query, this.orderSearchForm.value)

        console.log('query: ', query)
        this.hsOrderService.getOrderList(this.businessType, query).subscribe(
            data => {
                this.orderList = data.data.results

                this.pagination.pageSize = data.data.pageSize
                this.pagination.pageNo = data.data.pageNo
                this.pagination.total = data.data.totalRecord

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

                this.filterTeamList = this.teamList.filter( team => {
                    return team.deptId === this.sessionUser.deptId
                })

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

                if (Array.isArray(data.data.results)) {
                    const tempArray : any[] = []
                    const tempArray2 : any[] = []

                    data.data.results.forEach( company => {
                        this.partyListObject[company.id] = company

                        if (company.partyType === 1) {
                            tempArray.push(company)
                        }
                        if (company.partyType !== 2) {
                            tempArray2.push(company)
                        }
                        this.partyListZhangWu = tempArray
                        this.partyListOther = tempArray2
                    })
                }
            },
            error => {this.httpService.errorHandler(error) }
        )
    }


    createOrderSearchForm(): void {

        this.orderSearchForm = this.fb.group({
            'teamId'    : ['' ],
            'mainAccounting'    : ['' ],
            'createDateStart'    : [null ],
            'createDateEnd'    : [null ],
            'status'    : ['' ]
        } )
    }


    orderFormError : any = {}
    orderOtherPartyFormError : any = {}
    orderFormValidationMessages: any = {
        'deptId'  : {
            'required'      : '请填写事业部门!'
        },
        'teamId'  : {
            'required'      : '请选择团队!'
        },
        'line'  : {
            'required'      : '请选择相关公司完成业务线名称!'
        },

        'cargoType'  : {
            'required'      : '请选择货物种类!'
        },
        'upstreamSettleMode'  : {
            'required'      : '请选择结算方式!'
        },
        'downstreamSettleMode'  : {
            'required'      : '请选择结算方式!'
        },
        'mainAccounting'  : {
            'required'      : '请选择公司!'
        },
        'upstreamId'  : {
            'required'      : '请选择公司!'
        },
        'downstreamId'  : {
            'required'      : '请选择公司!'
        },

        'custType'  : {
            'required'      : '请选择客户类型!'
        },
        'customerId'  : {
            'required'      : '请选择公司!'
        }
    }

    orderFormInputChange(formInputData : any) {
        this.orderFormError = formErrorHandler(formInputData, this.orderForm, this.orderFormValidationMessages)
    }
    orderOtherPartyFormInputChange(formInputData : any) {
        this.orderOtherPartyFormError = formErrorHandler(formInputData, this.orderOtherPartyForm, this.orderFormValidationMessages)
    }


    createOrderForm(): void {

        this.orderForm = this.fb.group({
            // 'deptId'    : ['', [Validators.required ] ],
            'teamId'    : ['', [Validators.required ] ],
            'line'    : ['', [Validators.required ] ],
            'cargoType'    : ['', [Validators.required ] ],

            'upstreamSettleMode'    : ['', [Validators.required ] ],
            'downstreamSettleMode'    : ['', [Validators.required ] ],

            'mainAccounting'    : ['', [Validators.required ] ],
            'upstreamId'    : ['', [Validators.required ] ],
            'downstreamId'    : ['', [Validators.required ] ]
        } )

        this.orderForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.orderFormInputChange(data)
        })
    }

    orderFormSubmit() {

        if (this.orderForm.invalid) {
            this.orderFormInputChange(this.orderForm.value)
            this.ignoreDirty = true

            console.log('当前信息: ', this.orderForm, this.orderFormError)
            return
        }

        const postData = this.orderForm.value

        postData.businessType = this.businessType
        postData.orderPartyList = this.otherParty1List
        delete postData.deptId

        if (this.isAddNew) {
            this.hsOrderService.createNewOrder(this.businessType, postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getOrderList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            postData.id = this.currentOrderId
            this.hsOrderService.modifyOrder(this.businessType, this.currentOrderId, postData).subscribe(
                data => {
                    console.log('修改成功: ', data)
                    this.httpService.successHandler(data)

                    this.getOrderList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }


    showForm(isAddNew : boolean = true, order?: any ) {

        if (isAddNew) {
            this.isAddNew = true

            this.otherParty1List = []
            this.orderForm.patchValue({
                'deptId'    : '',
                'teamId'    : '',
                'line'    : '',
                'cargoType'    : '',

                'upstreamSettleMode'    : '',
                'downstreamSettleMode'    : '',

                'mainAccounting'    : '',
                'upstreamId'    : '',
                'downstreamId'    : ''

            })

        } else {
            this.isAddNew = false
            this.currentOrderId = order.id

            this.orderForm.patchValue(order)

            this.otherParty1List = order.orderPartyList
        }


        this.isShowForm = !this.isShowForm
    }

    deleteItem (order : any) {

        this.hsOrderService.delOrder(this.businessType, order.id).subscribe(
            data => {
                console.log('保存成功: ', data)
                this.httpService.successHandler(data)

                this.getOrderList()
            },
            error => {this.httpService.errorHandler(error) }
        )
    }


    createOrderOtherPartyForm(): void {

        this.orderOtherPartyForm = this.fb.group({
            'custType'    : ['', [Validators.required ] ],
            'customerId'    : ['', [Validators.required ] ],
            'position'    : ['', [Validators.required ] ]
        } )

        this.orderOtherPartyForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.orderOtherPartyFormInputChange(data)
        })
    }

    createNewOtherParty () {
        if (this.orderOtherPartyForm.invalid) {
            this.orderOtherPartyFormInputChange(this.orderOtherPartyForm.value)
            this.ignoreDirty = true

            return
        }

        if (this.orderOtherPartyForm.value.position === 1) {
            this.otherParty1List.push(this.orderOtherPartyForm.value)
        } else {
            this.otherParty2List.push(this.orderOtherPartyForm.value)
        }

        this.lineName()

    }

    delOtherParty (company: any, position : number) {

        if (position === 1) {
            const index = this.otherParty1List.indexOf(company)
            this.otherParty1List.splice(index, 1)
        } else {
            const index = this.otherParty2List.indexOf(company)
            this.otherParty2List.splice(index, 1)
        }

    }


    lineName() {
        let lineName = ''

        if (this.orderForm.value.upstreamId && this.orderForm.value.mainAccounting && this.orderForm.value.downstreamId ) {

            lineName = this.partyListObject[this.orderForm.value.upstreamId].shortName + ' - '

            this.otherParty1List.forEach( company => {
                lineName = lineName + this.partyListObject[this.orderOtherPartyForm.value.customerId].shortName + ' - '
            })

            lineName = lineName + this.partyListObject[this.orderForm.value.mainAccounting].shortName + ' - '

            this.otherParty2List.forEach( company => {
                lineName = lineName + this.partyListObject[this.orderOtherPartyForm.value.customerId].shortName + ' - '
            })

            lineName = lineName + this.partyListObject[this.orderForm.value.downstreamId].shortName

            this.orderForm.patchValue({line : lineName})
        }

    }

}

