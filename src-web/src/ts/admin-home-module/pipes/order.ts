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

