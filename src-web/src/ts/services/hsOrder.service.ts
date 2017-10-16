
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

        return this.http.get(apiPath.hsGetYingOrderList, {params: params})
    }
    getOrderByID(orderId: any): Observable<any> {
        return this.http.get(apiPath.hsGetYingOrderList + '/' + orderId )
    }

    createNewOrder(order: any): Observable<any> {
        return this.http.post(apiPath.hsGetYingOrderList, order)
    }
    modifyOrder(orderId: number, user: any): Observable<any> {
        return this.http.put(apiPath.hsGetYingOrderList + '/' + orderId, user)
    }
    delOrder(orderId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetYingOrderList + '/' + orderId)
    }

    transferOrder(orderId: number, userId: number): Observable<any> {
        return this.http.post(apiPath.hsGetYingOrderList + '/' + orderId + '/to/' + userId, {} )
    }


    getOrderUnitListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetYingOrderConfig + '/' + orderId + '/units', {params: params} )
    }
    createNewOrderUnit(orderId: number, unit: any): Observable<any> {
        return this.http.post(apiPath.hsGetYingOrderConfig + '/' + orderId + '/units', unit )
    }
    modifyOrderUnit(orderId: number, unitId: number, unit: any ): Observable<any> {
        return this.http.put(apiPath.hsGetYingOrderConfig + '/' + orderId + '/units/' + unitId.toString() , unit)
    }
    delOrderUnit(orderId: number, unitId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetYingOrderConfig + '/' + orderId + '/units/' + unitId.toString())
    }

    getShippingListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetYingOrderConfig + '/' + orderId + '/fayuns', {params: params} )
    }
    createNewShipping(orderId: number, shipping: any): Observable<any> {
        return this.http.post(apiPath.hsGetYingOrderConfig + '/' + orderId + '/fayuns', shipping )
    }
    modifyShipping(orderId: number, shippingId: number, shipping: any ): Observable<any> {
        return this.http.put(apiPath.hsGetYingOrderConfig + '/' + orderId + '/fayuns/' + shippingId.toString() , shipping)
    }
    delShipping(orderId: number, shippingId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetYingOrderConfig + '/' + orderId + '/fayuns/' + shippingId.toString())
    }


    getPaymentListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetYingOrderConfig + '/' + orderId + '/fukuans', {params: params} )
    }
    createNewPayment(orderId: number, payment: any): Observable<any> {

        return this.http.post(apiPath.hsGetYingOrderConfig + '/' + orderId + '/fukuans', payment )
    }
    modifyPayment(orderId: number, paymentId: number, payment: any ): Observable<any> {

        return this.http.put(apiPath.hsGetYingOrderConfig + '/' + orderId + '/fukuans/' + paymentId.toString() , payment)
    }


    getRepaymentListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetYingOrderConfig + '/' + orderId + '/huikuans', {params: params} )
    }
    createNewRepayment(orderId: number, repayment: any): Observable<any> {

        return this.http.post(apiPath.hsGetYingOrderConfig + '/' + orderId + '/huikuans', repayment )
    }
    modifyRepayment(orderId: number, repaymentId: number, repayment: any ): Observable<any> {

        return this.http.put(apiPath.hsGetYingOrderConfig + '/' + orderId + '/huikuans/' + repaymentId.toString() , repayment)
    }


    getRepaymentHKListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetYingOrderConfig + '/' + orderId + '/huankuans', {params: params} )
    }
    createNewRepaymentHK(orderId: number, repayment: any): Observable<any> {

        return this.http.post(apiPath.hsGetYingOrderConfig + '/' + orderId + '/huankuans', repayment )
    }
    modifyRepaymentHK(orderId: number, repaymentId: number, repayment: any ): Observable<any> {

        return this.http.put(apiPath.hsGetYingOrderConfig + '/' + orderId + '/huankuans/' + repaymentId.toString() , repayment)
    }





    getSettleUpstreamListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetYingOrderConfig + '/' + orderId + '/settleupstream', {params: params} )
    }
    createNewSettleUpstream(orderId: number, settle: any): Observable<any> {

        return this.http.post(apiPath.hsGetYingOrderConfig + '/' + orderId + '/settleupstream', settle )
    }
    modifySettleUpstream(orderId: number, settleId: number, settle: any ): Observable<any> {

        return this.http.put(apiPath.hsGetYingOrderConfig + '/' + orderId + '/settleupstream/' + settleId.toString() , settle)
    }


    getSettleDownstreamListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetYingOrderConfig + '/' + orderId + '/settledownstream', {params: params} )
    }
    createNewSettleDownstream(orderId: number, settle: any): Observable<any> {

        return this.http.post(apiPath.hsGetYingOrderConfig + '/' + orderId + '/settledownstream', settle )
    }
    modifySettleDownstream(orderId: number, settleId: number, settle: any ): Observable<any> {

        return this.http.put(apiPath.hsGetYingOrderConfig + '/' + orderId + '/settledownstream/' + settleId.toString() , settle)
    }


    getSettleTrafficListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetYingOrderConfig + '/' + orderId + '/settletraffic', {params: params} )
    }
    createNewSettleTraffic(orderId: number, settle: any): Observable<any> {

        return this.http.post(apiPath.hsGetYingOrderConfig + '/' + orderId + '/settletraffic', settle )
    }
    modifySettleTraffic(orderId: number, settleId: number, settle: any ): Observable<any> {

        return this.http.put(apiPath.hsGetYingOrderConfig + '/' + orderId + '/settletraffic/' + settleId.toString() , settle)
    }





    getExpenseListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetYingOrderConfig + '/' + orderId + '/fees', {params: params} )
    }
    createNewExpense(orderId: number, expense: any): Observable<any> {
        return this.http.post(apiPath.hsGetYingOrderConfig + '/' + orderId + '/fees', expense )
    }
    modifyExpense(orderId: number, expenseId: number, expense: any ): Observable<any> {
        return this.http.put(apiPath.hsGetYingOrderConfig + '/' + orderId + '/fees/' + expenseId.toString() , expense)
    }
    delExpense(orderId: number, expenseId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetYingOrderConfig + '/' + orderId + '/fees/' + expenseId.toString())
    }



    getInvoiceListByID(orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetYingOrderConfig + '/' + orderId + '/invoices', {params: params} )
    }
    createNewInvoice(orderId: number, invoice: any): Observable<any> {
        return this.http.post(apiPath.hsGetYingOrderConfig + '/' + orderId + '/invoices', invoice )
    }
    modifyInvoice(orderId: number, invoiceId: number, invoice: any ): Observable<any> {
        return this.http.put(apiPath.hsGetYingOrderConfig + '/' + orderId + '/invoices/' + invoiceId.toString() , invoice)
    }
    delInvoice(orderId: number, invoiceId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetYingOrderConfig + '/' + orderId + '/invoices/' + invoiceId.toString())
    }

}

