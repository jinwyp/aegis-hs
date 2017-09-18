
/**
 * Created by jin on 8/10/17.
 */

import {ReflectiveInjector, Type} from '@angular/core'
import {AbstractControl, AsyncValidatorFn, ValidationErrors, FormControl} from '@angular/forms'
import { HttpClient, HttpClientModule } from '@angular/common/http'

import {Observable} from 'rxjs/Observable'
import 'rxjs/add/observable/timer'

import 'rxjs/add/operator/map'
import 'rxjs/add/operator/switchMap'



declare let Reflect: any
function getAnnotations(typeOrFunc: Type<any>): any[]|null {
    // Prefer the direct API.
    if ((<any>typeOrFunc).annotations) {
        let annotations = (<any>typeOrFunc).annotations
        if (typeof annotations === 'function' && annotations.annotations) {
            annotations = annotations.annotations
        }
        return annotations
    }

    // API of tsickle for lowering decorators to properties on the class.
    if ((<any>typeOrFunc).decorators) {
        return convertTsickleDecoratorIntoMetadata((<any>typeOrFunc).decorators)
    }

    // API for metadata created by invoking the decorators.
    if (Reflect && Reflect.getOwnMetadata) {
        return Reflect.getOwnMetadata('annotations', typeOrFunc)
    }
    return null
}

function convertTsickleDecoratorIntoMetadata(decoratorInvocations: any[]): any[] {
    if (!decoratorInvocations) {
        return []
    }
    return decoratorInvocations.map(decoratorInvocation => {
        const decoratorType = decoratorInvocation.type
        const annotationCls = decoratorType.annotationCls
        const annotationArgs = decoratorInvocation.args ? decoratorInvocation.args : []
        return new annotationCls(...annotationArgs)
    })
}



export function checkFieldIsExist (url: string, postDataKey: string = 'username', mobilePhoneKey?: string ): AsyncValidatorFn {

    return (control : AbstractControl) : Observable<ValidationErrors| null> => {

        const injector = ReflectiveInjector.resolveAndCreate(getAnnotations(HttpClientModule)[0].providers)
        const http = injector.get(HttpClient)

        const postData : any = {}
        postData[postDataKey] = control.value

        if (postDataKey === 'smsCode' && mobilePhoneKey) {

            if (!control.parent) {
                return null
            }

            const mobilePhoneField : FormControl = control.parent.get(mobilePhoneKey) as FormControl

            if (!mobilePhoneField) {
                throw new Error('checkFieldIsExistValidator(): mobilePhone field control not found')
            }

            postData[mobilePhoneKey] = mobilePhoneField.value
        }

        return Observable.timer(500).switchMap(() => {
            return http.post(url, postData).map( data => {

                if (postDataKey === 'captcha' || postDataKey === 'smsCode') {
                    if (data.data[postDataKey + 'Wrong']) {
                        return {wrong : true}
                    }
                    return null
                }else {
                    if (data.data[postDataKey + 'IsExist']) {
                        return {isExist : true}
                    }
                    return null
                }

            })
        })

    }

}





