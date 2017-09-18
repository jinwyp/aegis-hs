/**
 * Created by daisy on 2017/6/1.
 */

import { Injectable } from '@angular/core'
import { Observable } from 'rxjs/Observable'
import { BehaviorSubject } from 'rxjs/BehaviorSubject'





const sideBarItems  = [
    {
        'name' : '控制台',
        'status'     : true,
        'icon'     : 'fa-tachometer',
        'routerLink': '/accountHome',
        'subMenu' : [
            {
                'name': '统计图标1',
                'routerLink': '/accountHome'
            },
            {
                'name': '统计图标2',
                'routerLink': '/accountPhone'
            },
            {
                'name': '统计图标3',
                'routerLink': '/accountPassword'
            }
        ]
    },

    {
        'name' : '用户管理',
        'status'     : true,
        'icon'     : 'ico_2',
        'routerLink': '/company',
        'subMenu' : [{
            'name': '企业信息审核',
            'routerLink': '/company'
        }]
    }
]




@Injectable()
class SideBarService {
    private behaviorSubject : any = new BehaviorSubject('')


    sendCompanyStatusMessage(companyStatus: string): Observable<any> {
        return this.behaviorSubject.next(companyStatus)
    }


    getCompanyStatusMessage(): Observable<any> {
        return this.behaviorSubject.asObservable()
    }

    clearMessage() {
        this.behaviorSubject.next(null)
    }

}




