import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'



import { HttpService } from '../../../../bs-form-module/services/http.service'

import { formErrorHandler, isInt } from '../../../../bs-form-module/validators/validator'

import { HSUserService } from '../../../../services/hsUser.service'
import { HSOrderService } from '../../../../services/hsOrder.service'


import {getEnum} from '../../../../services/localStorage'


@Component({
    selector: 'app-statistics',
    templateUrl: './unitStatistics.component.html',
    styleUrls: ['./unitStatistics.component.css']
})
export class UnitStatisticsComponent implements OnInit {

    @Input() currentOrder : any
    @Input() businessType : string
    @Input() currentUnit : any
    @Output() back: any = new EventEmitter<boolean>()


    unitStatistics : any = {}
    isShowDetail : boolean = false

    pagination: any = {
        pageSize : 20,
        pageNo : 1,
        total : 1
    }


    constructor(
        private httpService: HttpService,
        private fb: FormBuilder,
        private hsUserService: HSUserService,
        private hsOrderService: HSOrderService

    ) {

    }



    ngOnInit(): void {
        this.getOrderUnitStat()
    }


    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }


    getOrderUnitStat () {
        this.hsOrderService.getOrderUnitStatisticsByID(this.businessType, this.currentOrder.id, this.currentUnit.id).subscribe(
            data => {
                console.log('currentUnitStatistics: ', data.data)
                this.unitStatistics = data.data
            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    backTo() {
        this.back.emit(false)
    }



    test2 : any = {
        'success' : true,
        'data'    : {
            'hsId'                           : 6,
            'orderId'                        : 12,
            'totalPaymentAmount'             : 23816677.57,
            'totalLoadMoney'                 : 16690000.00,
            'totalRepaymentPrincipeAmount'   : 16690000.00,
            'totalUnpayFee'                  : 19828.00,
            'totalUnpayInterest'             : 91256.90,
            'totalServiceCharge'             : 19828.00,
            'totalUnpayPrincipal'            : 16690000.00,
            'totalRepaymentInterest'         : 91256.90,
            'outCapitalAmout'                : 0E-10,
            'totalHuikuanPaymentMoney'       : 24166823.10,
            'payCargoAmount'                 : 23816677.57,
            'unpaymentMoney'                 : 0.00,
            'unpaymentEstimateProfile'       : 0E-10,
            'interestDays'                   : 56,
            'rate'                           : 0E-10,
            'totalPaymentedRateMoney'        : 0E-10,
            'contractRateProfile'            : 0E-10,
            'tiexianRate'                    : 0E-16,
            'tiexianRateAmount'              : 0E-16,
            'totalBuyerMoney'                : 24166823.10,
            'totalBuyerNums'                 : 40530.00,
            'totalBuyersettleGap'            : 0.00,
            'invoicedMoneyAmount'            : 23816677.57,
            'finalSettleAmount'              : 40530.00,
            'saleCargoAmountofMoney'         : 24166823.1000,
            'tradingCompanyAddMoney'         : 40530.0000,
            'unsettlerBuyerMoneyAmount'      : 0.0000,
            'unsettlerBuyerNumber'           : 0.00,
            'ccsProfile'                     : 0E-16,
            'purchaseCargoAmountofMoney'     : 24166823.1000000000000000,
            'externalCapitalPaymentAmount'   : 16690000.00,
            'ownerCapitalPaymentAmount'      : 7309191.37,
            'downstreamCapitalPressure'      : 0.0000,
            'cssUninTypeNum'                 : 40530.00,
            'cssUninTypeMoney'               : 24207353.1000000000000000,
            'unInvoicedAmountofMoney'        : 390675.5300000000000000,
            'yingPrePayment'                 : 0.00,
            'settleGrossProfileNum'          : 40530.00,
            'purchaseIncludeTaxTotalAmount'  : 24166823.1000000000000000,
            'saleIncludeTaxTotalAmount'      : 24166823.1000,
            'tradeCompanyAddMoney'           : 40530.0000,
            'withoutTaxIncome'               : 20655404.35897436,
            'withoutTaxCost'                 : 20690045.38461538461538461538,
            'vat'                            : 0E-22,
            'additionalTax'                  : 0E-22,
            'stampDuty'                      : 41345449.74358974358974358974,
            'opreationCrossProfile'          : -41380090.7692307682051282051200,
            'crossProfileATon'               : -1020.97435897435894905324957118,
            'totalFayunNum'                  : 40530.00,
            'totalUnarriveNum'               : 0.00,
            'totalArriveNum'                 : 40530.00,
            'tradingCompanyInTypeNum'        : 40530.00,
            'tradingCompanyInTpeMoneyAmount' : 23816677.57,
            'settledDownstreamHuikuanMoneny' : 0.0000,
            'ownerCapitalPressure'           : -16857631.7300000000000000,
            'invoicedMoneyNum'               : 40530.00
        }
    }


}

