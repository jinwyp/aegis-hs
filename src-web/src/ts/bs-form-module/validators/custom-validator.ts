import { Directive, Input, OnChanges, SimpleChanges } from '@angular/core'
import { AbstractControl, NG_VALIDATORS, Validator, ValidatorFn, Validators } from '@angular/forms'

/** A hero's name can't match the given regular expression */
export function forbiddenNameValidator(nameRe: RegExp): ValidatorFn {
    return (control: AbstractControl): {[key: string]: any} => {
        const name = control.value
        const no = nameRe.test(name)
        return no ? {'forbiddenName': {name}} : null
    }
}

@Directive({
    selector: '[forbiddenName]',
    providers: [{provide: NG_VALIDATORS, useExisting: ForbiddenValidatorDirective, multi: true}]
})
export class ForbiddenValidatorDirective implements Validator, OnChanges {
    @Input() forbiddenName: string
    private valFn: any = Validators.nullValidator

    ngOnChanges(changes: SimpleChanges): void {
        const change = changes['forbiddenName']
        console.log('change: ', change, this.forbiddenName)

        if (change) {
            const val: string | RegExp = change.currentValue
            const re = val instanceof RegExp ? val : new RegExp(val, 'i')
            this.valFn = forbiddenNameValidator(re)
        }
    }

    validate(control: AbstractControl): {[key: string]: any} {
        return this.valFn(control)
    }
}



/*
 Copyright 2017 Google Inc. All Rights Reserved.
 Use of this source code is governed by an MIT-style license that
 can be found in the LICENSE file at http://angular.io/license
 */
