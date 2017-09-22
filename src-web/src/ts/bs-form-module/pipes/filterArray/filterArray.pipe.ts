/**
 * Created by jin on 8/16/17.
 */


import {Pipe, PipeTransform} from '@angular/core'


/*
 * 过滤数组
 *
 * Usage:
 *   value | filterArray:exponent
 * Example:
 *   {{ [{isCity : true , name : 'Shanghai'}] | filterArray: {isCity : true} }}
 *   formats to: 1024
 */


@Pipe({
    name: 'filterArray'
})
export class FilterArrayPipe implements PipeTransform {
    transform(value: any[], ...args: any[]): any {

        const filter = args[0]

        if (!value || !filter || !Array.isArray(value)) { return value }

        const keys = Object.keys(filter)

        return value.filter( item => {
            let flag = true
            keys.forEach( key => {
                if (item[key] !== filter[key] ) {
                    flag = false
                }
            })
            return flag
        })

    }
}
