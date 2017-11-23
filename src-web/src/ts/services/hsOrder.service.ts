
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


    getLogList(query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        let params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        if (query.orderId) { params = params.append('orderId', query.orderId)}
        return this.http.get(apiPath.hsGetOrderList + '/logs', {params: params})
    }



    getOrderList(businessType : string, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        let params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        if (query.teamId) { params = params.append('teamId', query.teamId)}
        if (query.mainAccounting) { params = params.append('mainAccounting', query.mainAccounting)}
        if (query.createDateStart) { params = params.append('createDateStart', query.createDateStart)}
        if (query.createDateEnd) { params = params.append('createDateEnd', query.createDateEnd)}
        if (query.status) { params = params.append('status', query.status)}

        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + 's', {params: params})
    }
    getOrderByID(businessType : string, orderId: any): Observable<any> {
        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + 's/' + orderId )
    }

    createNewOrder(businessType : string, order: any): Observable<any> {
        return this.http.post(apiPath.hsGetOrderList + '/' + businessType + 's', order)
    }
    modifyOrder(businessType : string, orderId: number, user: any): Observable<any> {
        return this.http.put(apiPath.hsGetOrderList + '/' + businessType + 's/' + orderId, user)
    }
    delOrder(businessType : string, orderId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetOrderList + '/' + businessType + 's/' + orderId)
    }

    transferOrder(businessType : string, orderId: number, userId: number): Observable<any> {
        return this.http.post(apiPath.hsGetOrderList + '/' + businessType + 's/' + orderId + '/to/' + userId, {} )
    }


    getOrderUnitListByID(businessType : string, orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/units', {params: params} )
    }
    createNewOrderUnit(businessType : string, orderId: number, unit: any): Observable<any> {
        return this.http.post(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/units', unit )
    }
    modifyOrderUnit(businessType : string, orderId: number, unitId: number, unit: any ): Observable<any> {
        return this.http.put(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/units/' + unitId.toString() , unit)
    }
    delOrderUnit(businessType : string, orderId: number, unitId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/units/' + unitId.toString())
    }
    getOrderUnitStatisticsByID(businessType : string, orderId: number, unitId: number): Observable<any> {
        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/analysis/' + unitId.toString() )
    }
    getOrderStatisticsByID(businessType : string, orderId: number): Observable<any> {
        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/analysis' )
    }

    getShippingListByID(businessType : string, orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/fayuns', {params: params} )
    }
    createNewShipping(businessType : string, orderId: number, shipping: any): Observable<any> {
        return this.http.post(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/fayuns', shipping )
    }
    modifyShipping(businessType : string, orderId: number, shippingId: number, shipping: any ): Observable<any> {
        return this.http.put(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/fayuns/' + shippingId.toString() , shipping)
    }
    delShipping(businessType : string, orderId: number, shippingId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/fayuns/' + shippingId.toString())
    }


    getBorrowListUnfinishedByID(businessType : string, orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/jiekuansUnfinished', {params: params} )
    }
    getBorrowListByID(businessType : string, orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        let params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        if (query.hsId) { params = params.append('hsId', query.hsId)}
        if (query.loanStatus) { params = params.append('loanStatus', query.loanStatus)}
        if (query.jiekuanDateStart) { params = params.append('jiekuanDateStart', query.jiekuanDateStart)}
        if (query.jiekuanDateEnd) { params = params.append('jiekuanDateEnd', query.jiekuanDateEnd)}
        if (query.amount) { params = params.append('amount', query.amount)}
        if (query.capitalId) { params = params.append('capitalId', query.capitalId)}
        if (query.useInterest) { params = params.append('useInterest', query.useInterest)}
        if (query.useDays) { params = params.append('useDays', query.useDays)}

        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/jiekuans', {params: params} )
    }
    createNewBorrow(businessType : string, orderId: number, borrow: any): Observable<any> {
        return this.http.post(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/jiekuans', borrow )
    }
    modifyBorrow(businessType : string, orderId: number, borrowId: number, borrow: any ): Observable<any> {
        return this.http.put(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/jiekuans/' + borrowId.toString() , borrow)
    }
    delBorrow(businessType : string, orderId: number, borrowId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/jiekuans/' + borrowId.toString())
    }


    getPaymentListByID(businessType : string, orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        let params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        if (query.hsId) { params = params.append('hsId', query.hsId)}
        if (query.payDateStart) { params = params.append('payDateStart', query.payDateStart)}
        if (query.payDateEnd) { params = params.append('payDateEnd', query.payDateEnd)}
        if (query.receiveCompanyId) { params = params.append('receiveCompanyId', query.receiveCompanyId)}
        if (query.payUsage) { params = params.append('payUsage', query.payUsage)}
        if (query.payAmount) { params = params.append('payAmount', query.payAmount)}

        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/fukuans', {params: params} )
    }
    getPaymentByID(businessType : string, orderId: number, paymentId: number): Observable<any> {
        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/fukuans/' + paymentId.toString())
    }
    createNewPayment(businessType : string, orderId: number, payment: any): Observable<any> {
        return this.http.post(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/fukuans', payment )
    }
    modifyPayment(businessType : string, orderId: number, paymentId: number, payment: any ): Observable<any> {
        return this.http.put(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/fukuans/' + paymentId.toString() , payment)
    }
    delPayment(businessType : string, orderId: number, paymentId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/fukuans/' + paymentId.toString())
    }

    // 回款
    getRepaymentListByID(businessType : string, orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/huikuans', {params: params} )
    }
    createNewRepayment(businessType : string, orderId: number, repayment: any): Observable<any> {
        return this.http.post(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/huikuans', repayment )
    }
    modifyRepayment(businessType : string, orderId: number, repaymentId: number, repayment: any ): Observable<any> {
        return this.http.put(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/huikuans/' + repaymentId.toString() , repayment)
    }
    delRepayment(businessType : string, orderId: number, repaymentId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/huikuans/' + repaymentId.toString())
    }

    // 还款
    getRepaymentHKListByID(businessType : string, orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        let params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        if (query.hsId) { params = params.append('hsId', query.hsId)}
        if (query.huankuankDateStart) { params = params.append('huankuankDateStart', query.huankuankDateStart)}
        if (query.huankuankDateEnd) { params = params.append('huankuankDateEnd', query.huankuankDateEnd)}
        if (query.promise || query.promise === false) { params = params.append('promise', query.promise)}

        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/huankuans', {params: params} )
    }
    createNewRepaymentHK(businessType : string, orderId: number, repayment: any): Observable<any> {
        return this.http.post(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/huankuans', repayment )
    }
    modifyRepaymentHK(businessType : string, orderId: number, repaymentId: number, repayment: any ): Observable<any> {
        return this.http.put(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/huankuans/' + repaymentId.toString() , repayment)
    }
    delRepaymentHK(businessType : string, orderId: number, repaymentId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/huankuans/' + repaymentId.toString())
    }



    getSettleUpstreamAdditionalInfo(businessType : string, orderId: number): Observable<any> {
        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/settleseller/allinfo')
    }
    getSettleUpstreamListByID(businessType : string, settleType : string, orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/' + settleType, {params: params} )
    }
    createNewSettleUpstream(businessType : string, settleType : string, orderId: number, settle: any): Observable<any> {
        return this.http.post(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/' + settleType, settle )
    }
    modifySettleUpstream(businessType : string, settleType : string, orderId: number, settleId: number, settle: any ): Observable<any> {
        return this.http.put(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/' + settleType + '/' + settleId.toString() , settle)
    }
    delSettleUpstream(businessType : string, settleType : string, orderId: number, settleId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/' + settleType + '/' + settleId.toString())
    }

    getSettleDownstreamListByID(businessType : string, settleType : string, orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/' + settleType, {params: params} )
    }
    createNewSettleDownstream(businessType : string, settleType : string, orderId: number, settle: any): Observable<any> {
        return this.http.post(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/' + settleType, settle )
    }
    modifySettleDownstream(businessType : string, settleType : string, orderId: number, settleId: number, settle: any ): Observable<any> {
        return this.http.put(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/' + settleType + '/' + settleId.toString() , settle)
    }
    delSettleDownstream(businessType : string, settleType : string, orderId: number, settleId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/' + settleType + '/' + settleId.toString())
    }

    getSettleTrafficListByID(businessType : string, orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/settletraffic', {params: params} )
    }
    createNewSettleTraffic(businessType : string, orderId: number, settle: any): Observable<any> {
        return this.http.post(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/settletraffic', settle )
    }
    modifySettleTraffic(businessType : string, orderId: number, settleId: number, settle: any ): Observable<any> {
        return this.http.put(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/settletraffic/' + settleId.toString() , settle)
    }
    delSettleTraffic(businessType : string, orderId: number, settleId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/settletraffic/' + settleId.toString())
    }




    getDepositListByID(businessType : string, orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/bails', {params: params} )
    }
    createNewDeposit(businessType : string, orderId: number, invoice: any): Observable<any> {
        return this.http.post(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/bails', invoice )
    }
    modifyDeposit(businessType : string, orderId: number, invoiceId: number, invoice: any ): Observable<any> {
        return this.http.put(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/bails/' + invoiceId.toString() , invoice)
    }
    delDeposit(businessType : string, orderId: number, invoiceId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/bails/' + invoiceId.toString())
    }





    getExpenseListByID(businessType : string, orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/fees', {params: params} )
    }
    createNewExpense(businessType : string, orderId: number, expense: any): Observable<any> {
        return this.http.post(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/fees', expense )
    }
    modifyExpense(businessType : string, orderId: number, expenseId: number, expense: any ): Observable<any> {
        return this.http.put(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/fees/' + expenseId.toString() , expense)
    }
    delExpense(businessType : string, orderId: number, expenseId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/fees/' + expenseId.toString())
    }



    getInvoiceListByID(businessType : string, orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        let params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        if (query.hsId) { params = params.append('hsId', query.hsId)}
        if (query.invoiceDirection) { params = params.append('invoiceDirection', query.invoiceDirection)}
        if (query.invoiceType) { params = params.append('invoiceType', query.invoiceType)}
        if (query.openDateStart) { params = params.append('openDateStart', query.openDateStart)}
        if (query.openDateEnd) { params = params.append('openDateEnd', query.openDateEnd)}
        if (query.openCompanyId) { params = params.append('openCompanyId', query.openCompanyId)}
        if (query.receiverId) { params = params.append('receiverId', query.receiverId)}

        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/invoices', {params: params} )
    }
    createNewInvoice(businessType : string, orderId: number, invoice: any): Observable<any> {
        return this.http.post(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/invoices', invoice )
    }
    modifyInvoice(businessType : string, orderId: number, invoiceId: number, invoice: any ): Observable<any> {
        return this.http.put(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/invoices/' + invoiceId.toString() , invoice)
    }
    delInvoice(businessType : string, orderId: number, invoiceId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/invoices/' + invoiceId.toString())
    }



    getWarehouseInListByID(businessType : string, orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/rukus', {params: params} )
    }
    createNewWareInhouse(businessType : string, orderId: number, warehouse: any): Observable<any> {
        return this.http.post(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/rukus', warehouse )
    }
    modifyWarehouseIn(businessType : string, orderId: number, warehouseId: number, warehouse: any ): Observable<any> {
        return this.http.put(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/rukus/' + warehouseId.toString() , warehouse)
    }
    delWarehouseIn(businessType : string, orderId: number, warehouseId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/rukus/' + warehouseId.toString())
    }


    getWarehouseOutListByID(businessType : string, orderId: number, query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/chukus', {params: params} )
    }
    createNewWarehouseOut(businessType : string, orderId: number, warehouse: any): Observable<any> {
        return this.http.post(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/chukus', warehouse )
    }
    modifyWarehouseOut(businessType : string, orderId: number, warehouseId: number, warehouse: any ): Observable<any> {
        return this.http.put(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/chukus/' + warehouseId.toString() , warehouse)
    }
    delWarehouseOut(businessType : string, orderId: number, warehouseId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetOrderList + '/' + businessType + '/' + orderId + '/chukus/' + warehouseId.toString())
    }


}

