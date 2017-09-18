/**
 * Created by jin on 8/10/17.
 */


import {AbstractControl, FormControl, FormGroup, ValidatorFn} from '@angular/forms'
import {Subscription} from 'rxjs/Subscription'



// 密码与确认密码是否相符
export function passwordMatch(passwordKey: string, confirmPasswordKey: string): ValidatorFn {

    return (group: FormGroup): {[key: string]: any} => {
        const password = group.controls[passwordKey]
        const confirmPassword = group.controls[confirmPasswordKey]

        if (password.value !== confirmPassword.value) {
            return {
                passwordMismatched: true
            }
        }

        return null
    }
}



// 判断与某个字段相符
export function isMatched(comparedFieldKey: string): ValidatorFn {

    let valueChangeListener: Subscription

    return (control: AbstractControl): {[key: string]: any} => {

        if (!control.parent) {
            return null
        }

        const comparedField : FormControl = control.parent.get(comparedFieldKey) as FormControl

        if (!comparedField) {
            throw new Error('isMatchedValidator(): compared field control not found')
        }

        if (!valueChangeListener) {
            valueChangeListener = comparedField.valueChanges.subscribe(() => {
                control.updateValueAndValidity()
            })
        }


        if (control.value !== comparedField.value) {
            return {
                mismatched: true
            }
        }

        return null
    }
}
