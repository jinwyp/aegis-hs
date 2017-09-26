
/**
 * Created by jin on 8/10/17.
 */

import {Injectable} from '@angular/core'
import {HttpClient, HttpParams} from '@angular/common/http'

import { Observable } from 'rxjs/Observable'
import { BehaviorSubject } from 'rxjs/BehaviorSubject'


import {apiPath} from './apiPath'


@Injectable()
export class HSOrderService {

    constructor(
        private http: HttpClient
    ) {
    }

    getEnumList(path: string): Observable<any> {
        return this.http.get(apiPath.dictionary + '/' + path)
    }

    getOrderList(query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderList, {params: params})
    }
    getOrderByID(orderId: any): Observable<any> {

        return this.http.get(apiPath.hsGetOrderList + '/' + orderId )
    }

    createNewOrder(user: any): Observable<any> {

        return this.http.post(apiPath.hsGetOrderList, user)
    }
    modifyOrder(userId: number, user: any): Observable<any> {

        return this.http.put(apiPath.hsGetOrderList + '/' + userId, user)
    }


    getOrderUnitListByID(orderId: any): Observable<any> {

        return this.http.get(apiPath.hsGetOrderList + '/' + orderId + '/units' )
    }

}
