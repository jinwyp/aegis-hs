/**
 * Created by jin on 8/16/17.
 */


import {Pipe, PipeTransform} from '@angular/core'


/*
 * 截取字符串长度
 * Takes an exponent argument that defaults to 1.
 * Usage:
 *   value | exponentialStrength:exponent
 * Example:
 *   {{ 2 | exponentialStrength:10 }}
 *   formats to: 1024
 */


@Pipe({name: 'max'})
export class MaxPipe implements PipeTransform {
    transform(value: string, ...args: any[]): any {
        if (!value) { return value }

        const allowed = args[0]
        const received = value.length

        if (received > allowed && allowed !== 0) {
            const toCut = allowed - received
            return value.slice(0, toCut)
        }

        return value
    }
}
