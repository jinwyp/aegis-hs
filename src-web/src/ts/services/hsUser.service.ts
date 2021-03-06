
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


    getUserListDepartment(): Observable<any> {
        return this.http.get(apiPath.getUserListInSameDepartment)
    }

    getUserList(query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
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


    getDepartmentList(query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetDepartmentList, {params: params})
    }
    createNewDepartment(department: any): Observable<any> {
        return this.http.post(apiPath.hsGetDepartmentList, department)
    }
    modifyDepartment(departmentId: number, department: any): Observable<any> {
        return this.http.put(apiPath.hsGetDepartmentList + '/' + departmentId, department)
    }
    delDepartment(departmentId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetDepartmentList + '/' + departmentId)
    }


    getTeamList(query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetTeamList, {params: params})
    }
    createNewTeam(team: any): Observable<any> {
        return this.http.post(apiPath.hsGetTeamList, team)
    }
    modifyTeam(teamId: number, team: any): Observable<any> {
        return this.http.put(apiPath.hsGetTeamList + '/' + teamId, team)
    }
    delTeam(teamId: number): Observable<any> {
        return this.http.delete(apiPath.hsGetTeamList + '/' + teamId)
    }

    getPartyList(query: any = {pageSize: 10000, pageNo: 1}): Observable<any> {
        const params = new HttpParams()
            .set('pageSize', query.pageSize)
            .set('pageNo', query.pageNo)

        return this.http.get(apiPath.hsGetPartyList, {params: params})
    }
    createNewParty(team: any): Observable<any> {

        return this.http.post(apiPath.hsGetPartyList, team)
    }
    modifyParty(teamId: number, team: any): Observable<any> {

        return this.http.put(apiPath.hsGetPartyList + '/' + teamId, team)
    }



}
