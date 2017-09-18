
/**
 * Created by jin on 8/10/17.
 */

import {Injectable} from '@angular/core'
import {HttpClient} from '@angular/common/http';

import {Observable} from 'rxjs/Observable'

import {apiPath} from './apiPath'


@Injectable()
export class UserLoginService {

    constructor(
        private http: HttpClient
    ) {
    }

    checkUsername(username : string): Observable<any> {

        return this.http.post(apiPath.signUpCheckUsername, {username : username})
    }

    registerNewUser(user : any): Observable<any> {

        return this.http.post(apiPath.signUp, user)
    }

    registerNewUserByMobile(user : any): Observable<any> {

        return this.http.post(apiPath.signUpByMobile, user)
    }

    registerSendSMS(mobilePhone : any): Observable<any> {

        return this.http.get(apiPath.signUpGetSMSCode + '/' + mobilePhone)
    }


    login(user : any): Observable<any> {

        return this.http.post(apiPath.login, user)
    }

}
