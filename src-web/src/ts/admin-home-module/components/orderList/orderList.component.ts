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
    isShowEditOrderButton: boolean = true

    orderList : any[] = []
    departmentList : any[] = []
    teamList : any[] = []
    filterTeamList : any[] = []

    partyList : any[] = []
    partyListSearchZhangWu : any[] = []
    partyListUpstreamAndDownstream : any[] = []

    partyListZhangWu : any[] = []
    partyListOther : any[] = []
    partyListFilterOther : any[] = []
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

                this.getTeamList()
                // console.log('当前登陆的用户信息: ', data)
            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    getOrderList (event? : any) {

        let query : any = {
            pageSize: this.pagination.pageSize,
            pageNo: this.pagination.pageNo
        }

        query = (<any>Object).assign(query, this.orderSearchForm.value)

        console.log('Query: ', query)
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
                        if (company.partyType === 3) {
                            tempArray2.push(company)
                        }
                    })

                    this.partyListSearchZhangWu = tempArray
                    this.partyListUpstreamAndDownstream = tempArray2
                    this.partyListZhangWu = tempArray.slice()
                    this.partyListOther = tempArray2.slice()

                }
            },
            error => {this.httpService.errorHandler(error) }
        )
    }


    getPaymentList () {
        this.hsOrderService.getPaymentListByID(this.businessType, this.currentOrderId).subscribe(
            data => {

                if (data.data && data.data.results) {

                    if (data.data.results.length > 0) {
                        this.isShowEditOrderButton = false
                    }
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
        },
        'customerPosition'  : {
            'required'      : '请选择位置!'
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
            'downstreamId'    : ['', [Validators.required ] ],
            'terminalClientId'    : ['' ]
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
        postData.orderPartyList = this.otherParty1List.concat(this.otherParty2List)
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

        this.isShowEditOrderButton = true


        if (isAddNew) {
            this.isAddNew = true

            this.otherParty1List = []
            this.otherParty2List = []
            this.orderForm.reset({
                'deptId'    : '',
                'teamId'    : '',
                'line'    : '',
                'cargoType'    : '',

                'upstreamSettleMode'    : '',
                'downstreamSettleMode'    : '',

                'mainAccounting'    : '',
                'upstreamId'    : '',
                'downstreamId'    : '',
                'terminalClientId'    : ''

            })

            this.orderOtherPartyForm.reset({
                'custType'    : '',
                'customerId'    : '',
                'customerPosition'    : ''
            })

        } else {
            this.isAddNew = false
            this.currentOrderId = order.id

            this.orderForm.patchValue(order)

            if (Array.isArray(order.orderPartyList)) {
                order.orderPartyList.forEach( (party) => {

                    delete party.tsc
                    if (party.customerPosition === 1) {
                        this.otherParty1List.push(party)
                    }
                    if (party.customerPosition === 2) {
                        this.otherParty2List.push(party)
                    }
                })
            }

            this.getPaymentList()
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
            'customerPosition'    : ['', [Validators.required ] ]
        } )

        this.orderOtherPartyForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.orderOtherPartyFormInputChange(data)
        })
    }


    getFilterCompanyList (event : any) {
        this.filterParty()
    }


    createNewOtherParty () {

        if (this.orderOtherPartyForm.invalid) {
            this.orderOtherPartyFormInputChange(this.orderOtherPartyForm.value)
            this.ignoreDirty = true

            return
        }

        if (this.orderOtherPartyForm.value.custType === -1 || this.orderOtherPartyForm.value.customerId === -1 || this.orderOtherPartyForm.value.customerPosition === -1) {

            if (this.orderOtherPartyForm.value.custType === -1) {
                this.orderOtherPartyFormError['custType'] = '请选择客户类型!'
            }

            if (this.orderOtherPartyForm.value.customerId === -1) {
                this.orderOtherPartyFormError['customerId'] = '请选择公司!'
            }

            if (this.orderOtherPartyForm.value.customerPosition === -1) {
                this.orderOtherPartyFormError['customerPosition'] = '请选择位置!'
            }
            // console.log(this.orderOtherPartyFormError)
            this.ignoreDirty = true
            return
        }


        if (this.orderOtherPartyForm.value.customerPosition === 1) {
            this.otherParty1List.push(this.orderOtherPartyForm.value)
        } else {
            this.otherParty2List.push(this.orderOtherPartyForm.value)
        }

        this.filterParty()

        this.lineName()

        this.orderOtherPartyForm.reset({
            'custType'    : '',
            'customerId'    : '',
            'customerPosition'    : ''
        })

        this.partyListFilterOther = []
    }

    delOtherParty (company: any, customerPosition : number) {

        if (customerPosition === 1) {
            const index = this.otherParty1List.indexOf(company)
            this.otherParty1List.splice(index, 1)
        } else {
            const index = this.otherParty2List.indexOf(company)
            this.otherParty2List.splice(index, 1)
        }

        this.filterParty()

        this.lineName()

    }

    filterParty () {


        if (this.orderOtherPartyForm.value.custType === 'ACCOUNTING_COMPANY') {
            this.partyListFilterOther = this.partyListZhangWu.slice()
        }

        if (this.orderOtherPartyForm.value.custType === 'TRAFFICKCER') {
            this.partyListFilterOther = this.partyListOther.slice()
        }

        this.partyList.forEach( (company => {

            this.otherParty1List.forEach( (company2 => {
                if (company2.customerId === company.id) {
                    const index1 = this.partyListFilterOther.indexOf(company)
                    this.partyListFilterOther.splice(index1, 1)
                }
            }))

            this.otherParty2List.forEach( (company3 => {
                if (company3.customerId === company.id) {
                    const index2 = this.partyListFilterOther.indexOf(company)
                    this.partyListFilterOther.splice(index2, 1)
                }
            }))

        }))

    }

    lineName() {
        let lineName = ''

        if (this.orderForm.value.upstreamId && this.orderForm.value.mainAccounting && this.orderForm.value.downstreamId ) {

            lineName = this.partyListObject[this.orderForm.value.upstreamId].shortName + ' - '

            this.otherParty1List.forEach( company => {
                lineName = lineName + this.partyListObject[company.customerId].shortName + ' - '
            })

            lineName = lineName + this.partyListObject[this.orderForm.value.mainAccounting].shortName + ' - '

            this.otherParty2List.forEach( company => {
                lineName = lineName + this.partyListObject[company.customerId].shortName + ' - '
            })

            lineName = lineName + this.partyListObject[this.orderForm.value.downstreamId].shortName

            if (this.orderForm.value.terminalClientId) {
                lineName = lineName + this.partyListObject[this.orderForm.value.terminalClientId].shortName
            }


            this.orderForm.patchValue({line : lineName})
        }

    }

}

