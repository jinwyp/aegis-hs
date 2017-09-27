/**
 * Created by jin on 9/19/17.
 */


import { Injectable } from '@angular/core'
import {HttpEvent, HttpInterceptor, HttpHandler,  HttpRequest} from '@angular/common/http'
import {Observable} from 'rxjs/Observable'

import { getAccessToken } from './localStorage'

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    intercept (req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        // get the token from a service
        const token: string = getAccessToken

        const header : any = {
            'Accept': 'application/json',
            'X-Requested-With': 'XMLHttpRequest'
        }

        // add it if we have one. Clone the request to add the new header.
        if (token) {
            header.Authorization = token
        }


        // if this is a login-request the header is
        // already set to x/www/formurl/encoded.
        // so if we already have a content-type, do not
        // set it, but if we don't have one, set it to
        // default --> json
        if (!req.headers.has('Content-Type')) {
            header['Content-Type'] = 'application/json'
        }


        let authReq : HttpRequest<any> = req.clone({setHeaders: header})


        // Pass on the cloned request instead of the original request.
        return next.handle(authReq)

    }

}
