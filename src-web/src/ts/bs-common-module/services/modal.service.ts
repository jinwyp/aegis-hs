/**
 * Created by jin on 8/15/17.
 */
import { Injectable } from '@angular/core'

import {Observable} from 'rxjs/Observable'
import { Subject } from 'rxjs/Subject'


@Injectable()
export class ModalService {

    private modalSubject : any = new Subject<Object>()


    showModal(data?: any): Observable<any> {
        return this.modalSubject.next(data)
    }


    getModal(): Observable<any> {
        return this.modalSubject.asObservable()
    }

    clearMessage() {
        this.modalSubject.next(null)
    }

}



