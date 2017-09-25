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


        // Clone the request to add the new header.
        const authReq = req.clone({headers: req.headers.set('Authorization', getAccessToken)})

        // Pass on the cloned request instead of the original request.
        return next.handle(authReq)

    }

}
