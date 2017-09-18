/**
 * Created by jin on 8/7/17.
 */


import {AbstractControl, ValidatorFn} from '@angular/forms'




export const mobilePattern = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
export const mobilePattern2 = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/




/** A hero's name can't match the given regular expression */
export function isMobilePhone(): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} => {
        const mobilePhone = control.value
        if (mobilePattern.test(mobilePhone)) {
            return null
        }

        // console.log('手机号格式不正确!')
        return {'mobilePhone': true}
    }
}




