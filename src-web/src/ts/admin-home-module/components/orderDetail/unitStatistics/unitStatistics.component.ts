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
    @Input() party : any = {}

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


    test2: any = {
        'success' : true,
        'data'    : {
            'hsId'                           : 9,
            'orderId'                        : 15,
            'totalTradeGapFee'               : 20546.00,
            'totalPaymentAmount'             : 1405585.78,
            'totalLoadMoney'                 : 1150000.00,
            'totalRepaymentPrincipeAmount'   : 1150000.00,
            'totalServiceCharge'             : 2013.00,
            'totalRepaymentInterest'         : 7958.55,
            'outCapitalAmout'                : 9971.55,
            'totalHuikuanPaymentMoney'       : 1438220.00,
            'payCargoAmount'                 : 1385039.78,
            'unpaymentMoney'                 : 0.00,
            'unpaymentEstimateProfile'       : 0.00,
            'interestDays'                   : 0,
            'actualUtilizationRate'          : 0.20,
            'rate'                           : 2722.2222222222,
            'totalPaymentedRateMoney'        : 29772.22,
            'contractRateProfile'            : 29772.22,
            'tiexianRate'                    : 4875.00,
            'tiexianRateAmount'              : 4875.00,
            'totalBuyerMoney'                : 1438220.00,
            'totalBuyerNums'                 : 10273.00,
            'totalBuyersettleGap'            : 0.00,
            'totalCCSIntypeMoney'            : 1308900.00,
            'totalCSSIntypeNumber'           : 9450.00,
            'invoicedMoneyAmount'            : 1290000.00,
            'finalSettleAmount'              : 10273.00,
            'saleCargoAmountofMoney'         : 1438220.00,
            'tradingCompanyAddMoney'         : 20546.00,
            'unsettlerBuyerMoneyAmount'      : 0.00,
            'unsettlerBuyerNumber'           : 0.00,
            'ccsProfile'                     : 24675.67,
            'purchaseCargoAmountOfMoney'     : 1413544.33,
            'externalCapitalPaymentAmount'   : 0.00,
            'ownerCapitalPaymentAmount'      : 1213544.33,
            'upstreamCapitalPressure'        : -220546.00,
            'downstreamCapitalPressure'      : 0.00,
            'cssUninTypeNum'                 : 823.00,
            'cssUninTypeMoney'               : 125190.33,
            'unInvoicedAmountofMoney'        : 123544.33,
            'yingPrePayment'                 : 0.00,
            'settleGrossProfileNum'          : 10273.00,
            'purchaseIncludeTaxTotalAmount'  : 1413544.33,
            'saleIncludeTaxTotalAmount'      : 1438220.00,
            'tradeCompanyAddMoney'           : 20546.00,
            'withoutTaxIncome'               : 1229247.86,
            'withoutTaxCost'                 : 1225718.23,
            'vat'                            : 600.04,
            'additionalTax'                  : 72.00,
            'stampDuty'                      : 861.69,
            'opreationCrossProfile'          : 2595.94,
            'crossProfileATon'               : 0.25,
            'totalFayunNum'                  : 10273.00,
            'totalUnarriveNum'               : 0.00,
            'totalArriveNum'                 : 10273.00,
            'totalPayDownBail'               : 0.00,
            'totalRefundDownBail'            : 0.00,
            'totalRefundUpBail'              : 0.00,
            'totalUpstreamBail'              : 200000.00,
            'balanceUpstreamBail'            : 200000,
            'balanceDownStreamBail'          : 0,
            'tradingCompanyInTypeNum'        : 9450.00,
            'tradingCompanyInTpeMoneyAmount' : 1290000.00,
            'settledDownstreamHuikuanMoneny' : 0.00,
            'ownerCapitalPressure'           : -220546.00,
            'invoicedMoneyNum'               : 9450.00
        }
    }


}

