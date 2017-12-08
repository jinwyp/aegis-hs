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

    businessType : string = ''

    currentOrder : any
    currentOrderId : number


    transferForm: FormGroup
    ignoreDirty: boolean = false



    departmentList : any[] = []
    teamList : any[] = []


    partyObject : any = {
        normal : [],
        object : {},
        orderIncluded : [],
        capital : [],
        other : []
    }

    userList : any[] = []
    unitListStat : any[] = []
    partyCompanyStat : any[] = []


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
        this.route.data.subscribe( (data) => this.businessType = data.businessType)

        this.route.paramMap.switchMap( (params: ParamMap) => {
            this.currentOrderId = Number(params.get('orderId'))
            return this.hsOrderService.getOrderByID(this.businessType, this.currentOrderId)
        }).subscribe(
            data => {
                if (data) {
                    this.currentOrder = data.data
                }
                this.getPartyList()
                this.getOrderUnitStatisticsList()
                // console.log('Order信息: ', data)
            },
            error => {this.httpService.errorHandler(error) }
        )


        this.getDepartmentList()
        this.getTeamList()
        this.getUserList()

        this.createTransferForm()
    }

    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }

    getOrder () {
        this.hsOrderService.getOrderByID(this.businessType, this.currentOrderId).subscribe(
            data => {
                this.currentOrder = data.data
            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    getOrderUnitStatisticsList () {

        this.hsOrderService.getOrderStatisticsByID(this.businessType, this.currentOrderId).subscribe(
            data => {
                this.unitListStat = data.data

                const tempTotal : any = {
                    partyList : [],

                    hsMonth : '汇总',

                    totalInstorageNum : 0,
                    totalOutstorageNum : 0,
                    totalStockNum : 0,


                    totalFayunNum : 0,
                    totalArriveNum : 0,
                    totalUnarriveNum : 0,

                    purchaseIncludeTaxTotalAmount : 0,
                    saleIncludeTaxTotalAmount : 0,

                    purchaseCargoAmountofMoney : 0,
                    saleCargoAmountofMoney : 0,

                    tradingCompanyAddMoney : 0,
                    salesFeeAmount : 0,
                    dsddFee : 0,
                    ccsProfile : 0,


                    totalBuyerNums : 0,
                    totalBuyerMoney : 0,
                    unsettlerBuyerNumber : 0,
                    unsettlerBuyerMoneyAmount : 0,

                    upstreamCapitalPressure : 0,
                    downstreamCapitalPressure : 0,

                    totalStockMoney : 0,

                    tradingCompanyInTypeNum : 0,
                    tradingCompanyInTpeMoneyAmount : 0,
                    totalCSSIntypeNumber : 0,
                    totalCCSIntypeMoney : 0,
                    cssUninTypeNum : 0,
                    cssUninTypeMoney : 0,


                    externalCapitalPaymentAmount : 0,
                    ownerCapitalPaymentAmount : 0,
                    totalHuikuanPaymentMoney : 0
                }

                const tempObject : any = {}

                if (Array.isArray(data.data)) {

                    data.data.forEach( unit => {
                        console.log(unit)
                        tempTotal.totalInstorageNum = tempTotal.totalInstorageNum + unit.totalInstorageNum || 0
                        tempTotal.totalOutstorageNum = tempTotal.totalOutstorageNum + unit.totalOutstorageNum || 0
                        tempTotal.totalStockNum = tempTotal.totalStockNum + unit.totalStockNum || 0
                        tempTotal.totalStockMoney = tempTotal.totalStockMoney + unit.totalStockMoney || 0

                        tempTotal.totalFayunNum = tempTotal.totalFayunNum + unit.totalFayunNum || 0
                        tempTotal.totalArriveNum = tempTotal.totalArriveNum + unit.totalArriveNum || 0
                        tempTotal.totalUnarriveNum = tempTotal.totalUnarriveNum + unit.totalUnarriveNum || 0
                        tempTotal.purchaseIncludeTaxTotalAmount = tempTotal.purchaseIncludeTaxTotalAmount + unit.purchaseIncludeTaxTotalAmount || 0
                        tempTotal.saleIncludeTaxTotalAmount = tempTotal.saleIncludeTaxTotalAmount + unit.saleIncludeTaxTotalAmount || 0


                        tempTotal.purchaseCargoAmountofMoney = tempTotal.purchaseCargoAmountofMoney + unit.purchaseCargoAmountofMoney || 0
                        tempTotal.saleCargoAmountofMoney = tempTotal.saleCargoAmountofMoney + unit.saleCargoAmountofMoney || 0

                        tempTotal.tradingCompanyAddMoney = tempTotal.tradingCompanyAddMoney + unit.tradingCompanyAddMoney
                        tempTotal.salesFeeAmount = tempTotal.salesFeeAmount + unit.salesFeeAmount
                        tempTotal.dsddFee = tempTotal.dsddFee + unit.dsddFee
                        tempTotal.ccsProfile = tempTotal.ccsProfile + unit.ccsProfile


                        tempTotal.totalBuyerNums = tempTotal.totalBuyerNums + unit.totalBuyerNums
                        tempTotal.totalBuyerMoney = tempTotal.totalBuyerMoney + unit.totalBuyerMoney
                        tempTotal.unsettlerBuyerNumber = tempTotal.unsettlerBuyerNumber + unit.unsettlerBuyerNumber
                        tempTotal.unsettlerBuyerMoneyAmount = tempTotal.unsettlerBuyerMoneyAmount + unit.unsettlerBuyerMoneyAmount

                        tempTotal.upstreamCapitalPressure = tempTotal.upstreamCapitalPressure + unit.upstreamCapitalPressure
                        tempTotal.downstreamCapitalPressure = tempTotal.downstreamCapitalPressure + unit.downstreamCapitalPressure


                        tempTotal.tradingCompanyInTypeNum = tempTotal.tradingCompanyInTypeNum + unit.tradingCompanyInTypeNum
                        tempTotal.tradingCompanyInTpeMoneyAmount = tempTotal.tradingCompanyInTpeMoneyAmount + unit.tradingCompanyInTpeMoneyAmount
                        tempTotal.totalCSSIntypeNumber = tempTotal.totalCSSIntypeNumber + unit.totalCSSIntypeNumber || 0
                        tempTotal.totalCCSIntypeMoney = tempTotal.totalCCSIntypeMoney + unit.totalCCSIntypeMoney || 0
                        tempTotal.cssUninTypeNum = tempTotal.cssUninTypeNum + unit.cssUninTypeNum || 0
                        tempTotal.cssUninTypeMoney = tempTotal.cssUninTypeMoney + unit.cssUninTypeMoney || 0


                        tempTotal.externalCapitalPaymentAmount = tempTotal.externalCapitalPaymentAmount + unit.externalCapitalPaymentAmount
                        tempTotal.ownerCapitalPaymentAmount = tempTotal.ownerCapitalPaymentAmount + unit.ownerCapitalPaymentAmount
                        tempTotal.totalHuikuanPaymentMoney = tempTotal.totalHuikuanPaymentMoney + unit.totalHuikuanPaymentMoney


                        // 处理参与方

                        if (Array.isArray(unit.capitalPressureList)) {
                            unit.capitalPressureList.forEach( (company, index) => {

                                if (typeof tempObject['party' + company.receiveCompanyId] === 'undefined') {
                                    tempObject['party' + company.receiveCompanyId] = {
                                        id : company.receiveCompanyId,
                                        partiesCapitalPressure : Number(company.partiesCapitalPressure) || 0,
                                        partyList : [company]
                                    }


                                } else {
                                    tempObject['party' + company.receiveCompanyId].partiesCapitalPressure = tempObject['party' + company.receiveCompanyId].partiesCapitalPressure + Number(company.partiesCapitalPressure) || 0
                                    tempObject['party' + company.receiveCompanyId].partyList.push(company)
                                }
                            })
                        }


                    })

                }

                this.unitListStat.push(tempTotal)


                console.log('tempObject', tempObject)

                for (const prop in tempObject) {
                    if (tempObject.hasOwnProperty(prop)) {
                        tempObject[prop].partyList.push({
                            partiesCapitalPressure : tempObject[prop].partiesCapitalPressure
                        })
                        this.partyCompanyStat.push(tempObject[prop])

                    }
                }
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
                this.partyObject.normal = data.data.results

                if (Array.isArray(data.data.results)) {

                    const tempArray = []
                    const tempArrayCapital = []
                    const tempArrayOther = []
                    data.data.results.forEach( company => {

                        this.partyObject.object[company.id] = company

                        if ( company.id === this.currentOrder.upstreamId || company.id === this.currentOrder.mainAccounting || company.id === this.currentOrder.downstreamId) {
                            tempArray.push(company)
                        }

                        this.currentOrder.orderPartyList.forEach( company2 => {
                            if (company.id === company2.customerId) {
                                tempArray.push(company)
                            }
                        })

                        if (company.partyType === 2 || company.id === this.currentOrder.mainAccounting) {
                            tempArrayCapital.push(company)
                        }

                        if (company.partyType === 3) {
                            tempArrayOther.push(company)
                        }

                    })

                    this.partyObject.orderIncluded = tempArray
                    this.partyObject.capital = tempArrayCapital
                    this.partyObject.other = tempArrayOther
                }
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
                            name : user.phone + ' ' + user.username
                        })
                    })
                }

                this.userList = tempResult

            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    transferFormError : any = {}
    transferFormValidationMessages: any = {
        'userId'  : {
            'required'      : '请选择财务人员!'
        }
    }


    transferFormInputChange(formInputData : any) {
        this.transferFormError = formErrorHandler(formInputData, this.transferForm, this.transferFormValidationMessages)
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


        this.hsOrderService.transferOrder(this.businessType, this.currentOrderId, this.transferForm.value.userId).subscribe(
            data => {
                console.log('保存成功: ', data)
                this.httpService.successHandler(data)
            },
            error => {this.httpService.errorHandler(error) }
        )

    }


    finishedOrder () {
        this.hsOrderService.finishOrder(this.businessType, this.currentOrderId).subscribe(
            data => {
                console.log('保存成功: ', data)
                this.httpService.successHandler(data)
            },
            error => {this.httpService.errorHandler(error) }
        )
    }
}

