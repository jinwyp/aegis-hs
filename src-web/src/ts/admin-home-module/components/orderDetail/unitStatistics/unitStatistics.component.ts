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


    test : any =  {
        'success' : true,
        'data'    : {
            'hsId'                          : 1,
            'orderId'                       : 1,
            'totalpaymentAmount'            : 122.00,
            'totalLoadMoney'                : 100005599.00,
            'loadEstimateCost'              : 16800.000000,
            'totalUnrepaymentEstimateCost'  : 100011199.00,
            'totalRepaymentPrincipeAmount'  : 200.00,
            'totalUnpayFee'                 : 100.00,
            'totalUnpayInterest'            : 200.00,
            'totalUnpayPrincipal'           : 200.00,
            'outCapitalAmout'               : 100010899.00,
            'totalpaymentMoney'             : 4000.00,
            'unpaymentMoney'                : 0.00,
            'unpaymentEstimateProfile'      : 0E-10,
            'actualUtilizationRate'         : 0.0000,
            'rate'                          : 0E-10,
            'totalPaymentedRateMoney'       : 0E-10,
            'contractRateProfile'           : -5000.0000000000,
            'tiexianRate'                   : 0E-16,
            'tiexianRateAmount'             : 0E-16,
            'totalBuyerMoney'               : 5000.00,
            'totalBuyerNums'                : 2000.00,
            'totalBuyersettleGap'           : 330.00,
            'businessFee'                   : 0.00,
            'superviseFee'                  : 0.00,
            'serviceFee'                    : 0.00,
            'salesFeeAmount'                : 0.00,
            'finalSettleAmount'             : 1670.00,
            'saleCargoAmountofMoney'        : -292000.0000,
            'tradingCompanyAddMoney'        : 334000.0000,
            'unsettlerBuyerMoneyAmount'     : -297000.0000,
            'unsettlerBuyerNumber'          : -330.00,
            'purchaseCargoAmountofMoney'    : 99723899.0000000000000000,
            'externalCapitalPaymentAmount'  : 100005599.00,
            'ownerCapitalPaymentAmount'     : -99957077.00,
            'upstreamCapitalPressure'       : -99675377.0000000000000000,
            'downstreamCapitalPressure'     : -296000.0000,
            'yingPrePayment'                : 0.00,
            'settleGrossProfileNum'         : 1670.00,
            'purchaseIncludeTaxTotalAmount' : 99723899.0000000000000000,
            'saleIncludeTaxTotalAmount'     : -292000.0000,
            'tradeCompanyAddMoney'          : 334000.0000,
            'withoutTaxIncome'              : -17346438.46153846,
            'withoutTaxCost'                : 68422705.98290598290598290598,
            'ying_VAT'                      : 0E-22,
            'yingAdditionalTax'             : 0E-22,
            'stampDuty'                     : 51076267.52136752136752136752,
            'opreationCrossProfile'         : -136845411.9658119642735042735000,
            'crossProfileATon'              : -81943.36045857003848712830748503,
            'totalFayunNum'                 : 2000.00,
            'totalUnarriveNum'              : 2000.00,
            'totalarriveNum'                : 0.00,
            'totalPayDownBail'              : 0.00,
            'totalRefundDownBail'           : 0.00,
            'totalRefundUpBail'             : 48000.00,
            'totalUpstream'                 : 0.00,
            'balanceUpstreamBail'           : -48000.00,
            'balanceDownStreamBail'         : 0.00,
            'dsddfee'                       : 20003333.00,
            'hshyfee'                       : 0.00,
            'hsqyfee'                       : 0.00,
            'hssyfee'                       : 0.00,
            'ccsprofile'                    : -100015899.0000000000000000
        }

    }


}

