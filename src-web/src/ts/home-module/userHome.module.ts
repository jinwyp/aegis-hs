import { NgModule } from '@angular/core'
import { BrowserModule } from '@angular/platform-browser'
import { RouterModule, Routes } from '@angular/router'

import { BSFormModule } from '../bs-form-module/bs-form.module'
import { BSCommonModule } from '../bs-common-module/bs-common.module'


import { IndexHomeComponent } from './components/indexHome/indexHome.component'
import { BasicInfoComponent } from './components/basicInfo/basicInfo.component'
import { ModifyPasswordComponent } from './components/modifyPassword/modifyPassword.component'
import { AddressComponent } from './components/address/address.component'

import { UserInfoService } from '../services/userInfo.service'


const userHomeRoutes: Routes = [
    {path : '', redirectTo : '/info/basic', pathMatch : 'full'},
    {path : 'info/basic', component : BasicInfoComponent},
    {path : 'info/password', component : ModifyPasswordComponent},
    {path : 'info/address', component : AddressComponent},
    {path : '**', redirectTo : '/info/basic', pathMatch : 'full'},
]


@NgModule({
    declarations : [
        IndexHomeComponent,
        BasicInfoComponent,
        ModifyPasswordComponent,
        AddressComponent
    ],
    imports      : [
        BrowserModule,

        BSFormModule,
        BSCommonModule,

        RouterModule.forRoot( userHomeRoutes,
            { enableTracing: false } // <-- debugging purposes only
        )

    ],
    providers    : [
        UserInfoService
    ],
    bootstrap    : [IndexHomeComponent]
})
export class UserHomeModule { }
