
/**
 * Created by jin on 8/31/17.
 */

import {Injectable} from '@angular/core'
import {HttpClient} from '@angular/common/http';

import {Observable} from 'rxjs/Observable'


const apiPath = {
    getFormModelList : '/api/form/models',
    createFormModel : '/api/form/models',
    getFormModelById : '/api/form/models',
    getFormDataByFormModelId : '/api/form/models'
}

@Injectable()
export class FormModelService {

    constructor(
        private http: HttpClient
    ) {
    }


    getFormModelList(): Observable<any> {

        return this.http.get(apiPath.getFormModelList)
    }

    getFormModelById(id : any): Observable<any> {

        return this.http.get(apiPath.getFormModelById + '/' + id)
    }

    createFormModel(schema : any): Observable<any> {

        return this.http.post(apiPath.createFormModel, schema)
    }


    getFormDataByFormModelId(id : any): Observable<any> {

        return this.http.get(apiPath.getFormDataByFormModelId + '/' + id + '/formdata')
    }

    createFormData(data : any): Observable<any> {

        return this.http.post(apiPath.getFormDataByFormModelId + '/' + data.modelSchemeId + '/formdata', data)
    }

}
