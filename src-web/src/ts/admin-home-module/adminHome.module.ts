import { NgModule } from '@angular/core'
import { BrowserModule } from '@angular/platform-browser'
import { RouterModule, Routes } from '@angular/router'
import { HTTP_INTERCEPTORS } from '@angular/common/http'


import { BSFormModule } from '../bs-form-module/bs-form.module'
import { BSCommonModule } from '../bs-common-module/bs-common.module'


import { AdminHomeComponent } from './components/adminHome/adminHome.component'
import { BasicInfoComponent } from './components/basicInfo/basicInfo.component'
import { ModifyPasswordComponent } from './components/modifyPassword/modifyPassword.component'


import { UserManagementComponent } from './components/userManagement/userManagement.component'

import { UserInfoService } from '../services/userInfo.service'
import { HSUserService } from '../services/hsUser.service'
import { AuthInterceptor } from '../services/headerHttpInterceptor'


const userHomeRoutes: Routes = [
    {path : '', redirectTo : '/info/basic', pathMatch : 'full'},
    {path : 'info/basic', component : BasicInfoComponent},
    {path : 'info/password', component : ModifyPasswordComponent},

    {path : 'users', component : UserManagementComponent},
    {path : 'departments', component : UserManagementComponent},
    {path : 'teams', component : UserManagementComponent},
    {path : 'companies', component : UserManagementComponent},
    {path : '**', redirectTo : '/info/basic', pathMatch : 'full'},
]


@NgModule({
    declarations : [
        AdminHomeComponent,
        BasicInfoComponent,
        ModifyPasswordComponent,

        UserManagementComponent
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
        UserInfoService,
        HSUserService,

        {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
    ],
    bootstrap    : [AdminHomeComponent]
})
export class AdminHomeModule { }
