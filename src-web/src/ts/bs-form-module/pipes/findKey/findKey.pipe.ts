/**
 * Created by jin on 8/16/17.
 */


import {Pipe, PipeTransform} from '@angular/core'


/*
 * 从数组中查找指定的key的值
 * Takes an exponent argument that defaults to 1.
 * Usage:
 *   value | findKey: sourceData : keyName
 * Example:
 *   {{ 1 | findKey : [{id : 1, name : '上海'}] 'name' }}
 *   formats to: 上海
 */


@Pipe({name: 'findKey'})
export class FindKeyPipe implements PipeTransform {
    transform(value: string | number, ...args: any[]): any {

        const sourceData = args[0]
        const key = args[1]

        if (!value || !key || !sourceData || !Array.isArray(sourceData)) { return value }


        let result : string | number = '';

        sourceData.forEach( item => {
            if (item.id === value) {
                result = item[key]
            }
        })

        return result

    }
}
