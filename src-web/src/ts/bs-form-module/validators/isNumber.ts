/**
 * Created by jin on 10/9/17.
 */

import { OnChanges, SimpleChanges } from '@angular/core'
import { AbstractControl, NG_VALIDATORS, Validator, ValidatorFn, Validators } from '@angular/forms'




// 验证是否是整数
export function isInt(param: any = {}): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} => {

        const val: number = control.value

        if (val) {

            if (isNaN(val)) {
                console.log('已验证是NaN:', val, isNaN(val))
                return { 'int': true }
            }

            if (/^[1-9]\d*$/.test(val.toString())) {

                if (!isNaN(param.min) && !isNaN(param.max)) {
                    return val < param.min || val > param.max ? {'int': { 'min': param.min, 'max': param.max, 'actual': val }} : null

                } else if (!isNaN(param.min)) {
                    return val < param.min ? {'int': { 'min': param.min, 'actual': val }} : null

                } else if (!isNaN(param.max)) {
                    return val > param.max ? {'int': { 'max': param.max, 'actual': val }} : null
                }

            }else {
                console.log('已验证不是正整数:', val)
                return { 'int': true }
            }

        }

        return null

    }
}



// 验证是否是带1或2位小数
export function isNumberWithTwoDecimal(param: any = {}): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} => {

        const val: number = control.value

        if (val) {

            if (isNaN(val)) {
                console.log('已验证是NaN:', val, isNaN(val))
                return { 'number': true }
            }

            if (/^[0-9]*(\.[0-9]{1,2})?$/.test(val.toString())) {

                if (!isNaN(param.min) && !isNaN(param.max)) {
                    return val < param.min || val > param.max ? {'number': { 'min': param.min, 'max': param.max, 'actual': val }} : null

                } else if (!isNaN(param.min)) {
                    return val < param.min ? {'number': { 'min': param.min, 'actual': val }} : null

                } else if (!isNaN(param.max)) {
                    return val > param.max ? {'number': { 'max': param.max, 'actual': val }} : null
                }

            }else {
                console.log('已验证不是整数，也不是一位或两位小数:', val)
                return { 'number': true }
            }
        }

        return null

    }
}
