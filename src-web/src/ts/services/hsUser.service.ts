
/**
 * Created by jin on 8/10/17.
 */

import {Injectable} from '@angular/core'
import {HttpClient, HttpParams} from '@angular/common/http'

import { Observable } from 'rxjs/Observable'
import { BehaviorSubject } from 'rxjs/BehaviorSubject'


import {apiPath} from './apiPath'


@Injectable()
export class HSUserService {

    constructor(
        private http: HttpClient
    ) {
    }



    getUserList(query: any = {pageSize: 100, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetUserList, {params: params})
    }
    createNewUser(user: any): Observable<any> {

        return this.http.post(apiPath.hsGetUserList, user)
    }
    modifyUser(userId: number, user: any): Observable<any> {

        return this.http.put(apiPath.hsGetUserList + '/' + userId, user)
    }


    getDepartmentList(): Observable<any> {

        return this.http.get(apiPath.hsGetDepartmentList)
    }


    getTeamList(): Observable<any> {

        return this.http.get(apiPath.hsGetTeamList)
    }
}
