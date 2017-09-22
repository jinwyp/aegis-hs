
/**
 * Created by jin on 8/10/17.
 */

import {Injectable} from '@angular/core'
import {HttpClient, HttpParams} from '@angular/common/http'

import { Observable } from 'rxjs/Observable'
import { BehaviorSubject } from 'rxjs/BehaviorSubject'


import {apiPath} from './apiPath'


@Injectable()
export class UserInfoService {

    constructor(
        private http: HttpClient
    ) {
    }


    private behaviorSubject : any = new BehaviorSubject(null)


    sendUserInfoMessage(user: any): Observable<any> {
        return this.behaviorSubject.next(user)
    }


    sessionUserInfo(): Observable<any> {
        return this.behaviorSubject.asObservable()
    }

    clearUserInfo() {
        this.behaviorSubject.next(null)
    }


    getSessionUserInfoHttp(): Observable<any> {

        return this.http.get(apiPath.getUserInfo)
    }



    saveUserInfo(user : any): Observable<any> {

        return this.http.post(apiPath.saveUserInfo, user)
    }

    modifyPassword(user : any): Observable<any> {

        return this.http.post(apiPath.modifyUserPassword, user)
    }

    getUserAddressList(query: any): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.getUserAddressList, {params: params})
    }

    createUserAddress(address : any): Observable<any> {

        return this.http.post(apiPath.saveUserAddressList, address)
    }

    updateUserAddress(addressId : string, address : any): Observable<any> {

        return this.http.put(apiPath.saveUserAddressList + '/' + addressId, address)
    }




}
