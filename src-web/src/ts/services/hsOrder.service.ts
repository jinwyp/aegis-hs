
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
        let params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        if (query.teamId) { params = params.append('teamId', query.teamId)}
        if (query.mainAccounting) { params = params.append('mainAccounting', query.mainAccounting)}
        if (query.createDateStart) { params = params.append('createDateStart', query.createDateStart)}
        if (query.createDateEnd) { params = params.append('createDateEnd', query.createDateEnd)}
        if (query.status) { params = params.append('status', query.status)}

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


    getOrderUnitListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderConfig + '/' + orderId + '/units', {params: params} )
    }
    createNewOrderUnit(orderId: number, unit: any): Observable<any> {

        return this.http.post(apiPath.hsGetOrderConfig + '/' + orderId + '/units', unit )
    }
    modifyOrderUnit(orderId: number, unitId: number, unit: any ): Observable<any> {

        return this.http.put(apiPath.hsGetOrderConfig + '/' + orderId + '/units/' + unitId.toString() , unit)
    }


    getShippingListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderConfig + '/' + orderId + '/fayuns', {params: params} )
    }
    createNewShipping(orderId: number, shipping: any): Observable<any> {

        return this.http.post(apiPath.hsGetOrderConfig + '/' + orderId + '/fayuns', shipping )
    }
    modifyShipping(orderId: number, shippingId: number, shipping: any ): Observable<any> {

        return this.http.put(apiPath.hsGetOrderConfig + '/' + orderId + '/fayuns/' + shippingId.toString() , shipping)
    }

    getPaymentListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderConfig + '/' + orderId + '/fukuans', {params: params} )
    }
    createNewPayment(orderId: number, payment: any): Observable<any> {

        return this.http.post(apiPath.hsGetOrderConfig + '/' + orderId + '/fukuans', payment )
    }
    modifyPayment(orderId: number, paymentId: number, payment: any ): Observable<any> {

        return this.http.put(apiPath.hsGetOrderConfig + '/' + orderId + '/fukuans/' + paymentId.toString() , payment)
    }


    getRepaymentListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderConfig + '/' + orderId + '/huikuans', {params: params} )
    }
    createNewRepayment(orderId: number, repayment: any): Observable<any> {

        return this.http.post(apiPath.hsGetOrderConfig + '/' + orderId + '/huikuans', repayment )
    }
    modifyRepayment(orderId: number, repaymentId: number, repayment: any ): Observable<any> {

        return this.http.put(apiPath.hsGetOrderConfig + '/' + orderId + '/huikuans/' + repaymentId.toString() , repayment)
    }


    getRepaymentHKListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderConfig + '/' + orderId + '/huankuans', {params: params} )
    }
    createNewRepaymentHK(orderId: number, repayment: any): Observable<any> {

        return this.http.post(apiPath.hsGetOrderConfig + '/' + orderId + '/huankuans', repayment )
    }
    modifyRepaymentHK(orderId: number, repaymentId: number, repayment: any ): Observable<any> {

        return this.http.put(apiPath.hsGetOrderConfig + '/' + orderId + '/huankuans/' + repaymentId.toString() , repayment)
    }





    getSettleUpstreamListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderConfig + '/' + orderId + '/settleupstream', {params: params} )
    }
    createNewSettleUpstream(orderId: number, settle: any): Observable<any> {

        return this.http.post(apiPath.hsGetOrderConfig + '/' + orderId + '/settleupstream', settle )
    }
    modifySettleUpstream(orderId: number, settleId: number, settle: any ): Observable<any> {

        return this.http.put(apiPath.hsGetOrderConfig + '/' + orderId + '/settleupstream/' + settleId.toString() , settle)
    }


    getSettleDownstreamListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderConfig + '/' + orderId + '/settledownstream', {params: params} )
    }
    createNewSettleDownstream(orderId: number, settle: any): Observable<any> {

        return this.http.post(apiPath.hsGetOrderConfig + '/' + orderId + '/settledownstream', settle )
    }
    modifySettleDownstream(orderId: number, settleId: number, settle: any ): Observable<any> {

        return this.http.put(apiPath.hsGetOrderConfig + '/' + orderId + '/settledownstream/' + settleId.toString() , settle)
    }


    getSettleTrafficListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderConfig + '/' + orderId + '/settletraffic', {params: params} )
    }
    createNewSettleTraffic(orderId: number, settle: any): Observable<any> {

        return this.http.post(apiPath.hsGetOrderConfig + '/' + orderId + '/settletraffic', settle )
    }
    modifySettleTraffic(orderId: number, settleId: number, settle: any ): Observable<any> {

        return this.http.put(apiPath.hsGetOrderConfig + '/' + orderId + '/settletraffic/' + settleId.toString() , settle)
    }





    getExpenseListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderConfig + '/' + orderId + '/fees', {params: params} )
    }
    createNewExpense(orderId: number, expense: any): Observable<any> {

        return this.http.post(apiPath.hsGetOrderConfig + '/' + orderId + '/fees', expense )
    }
    modifyExpense(orderId: number, expenseId: number, expense: any ): Observable<any> {

        return this.http.put(apiPath.hsGetOrderConfig + '/' + orderId + '/fees/' + expenseId.toString() , expense)
    }


    getInvoiceListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderConfig + '/' + orderId + '/invoices', {params: params} )
    }
    createNewInvoice(orderId: number, invoice: any): Observable<any> {

        return this.http.post(apiPath.hsGetOrderConfig + '/' + orderId + '/invoices', invoice )
    }
    modifyInvoice(orderId: number, invoiceId: number, invoice: any ): Observable<any> {

        return this.http.put(apiPath.hsGetOrderConfig + '/' + orderId + '/invoices/' + invoiceId.toString() , invoice)
    }


}

