/**
 * Created by jin on 9/27/17.
 */


/**
 * Created by jin on 6/7/17.
 */

import { Pipe, PipeTransform } from '@angular/core';
/*
 * Raise the value exponentially
 * Takes an exponent argument that defaults to 1.
 * Usage:
 *   company.companyStatus | companyInfoStatus
 * Example:
 *   {{ company.companyStatus |  companyInfoStatus}}
 *
 */



@Pipe({name: 'substringId'})
export class PipeSubstringId implements PipeTransform {
    transform(value: string, exponent: string): string {

        let text: string = '';
        text = value.substring(12);
        return text
    }
}



@Pipe({name: 'orderStatus'})
export class PipeOrderStatus implements PipeTransform {
    transform(value: string, exponent: string): string {

        let text: string = '无状态'

        if (value === 'UNCOMPLETED') { text = '未完成' }
        if (value === 'COMPLETED') { text = '已完成' }

        return text

    }
}


@Pipe({name: 'settleMode'})
export class PipePaymentSettleMode implements PipeTransform {
    transform(value: string, exponent: string): string {

        let text: string = '无状态'

        if (value === 'ONE_PAPER_SETTLE') { text = '一票结算' }
        if (value === 'TWO_PAPER_SETTLE') { text = '两票结算' }

        return text

    }
}


@Pipe({name: 'cargoType'})
export class PipeCargoType implements PipeTransform {
    transform(value: string, exponent: string): string {

        let text: string = '无状态'

        if (value === 'COAL') { text = '煤炭' }
        if (value === 'STEELS') { text = '钢材' }

        return text

    }
}

@Pipe({name: 'arriveStatus'})
export class PipeCargoArriveStatus implements PipeTransform {
    transform(value: string, exponent: string): string {

        let text: string = '无状态'

        if (value === 'ARRIVE') { text = '已到场' }
        if (value === 'UNARRIVE') { text = '未到场' }

        return text

    }
}


@Pipe({name: 'customerType'})
export class PipeCustomerType implements PipeTransform {
    transform(value: string, exponent: string): string {

        let text: string = '无状态'

        if (value === 'UPSTREAM') { text = '上游' }
        if (value === 'DOWNSTREAM') { text = '下游' }
        if (value === 'TRAFFICKCER') { text = '贸易公司' }
        if (value === 'FUNDER') { text = '资金方' }
        if (value === 'TRANSPORT_COMPANY') { text = '运输方' }
        if (value === 'ACCOUNTING_COMPANY') { text = '账务公司' }

        return text

    }
}

@Pipe({name: 'trafficMode'})
export class PipeTrafficMode implements PipeTransform {
    transform(value: string, exponent: string): string {

        let text: string = '无状态'

        if (value === 'MOTOR') { text = '汽运' }
        if (value === 'SHIP') { text = '船运' }
        if (value === 'RAIL') { text = '火运' }

        return text

    }
}

