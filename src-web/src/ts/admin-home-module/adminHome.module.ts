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
import { DepartmentManagementComponent } from './components/departmentManagement/departmentManagement.component'
import { TeamManagementComponent } from './components/teamManagement/teamManagement.component'
import { PartyManagementComponent } from './components/partyManagement/partyManagement.component'


import { OrderComponent } from './components/order/order.component'
import { OrderDetailComponent } from './components/orderDetail/orderDetail.component'
import { ShippingOrderComponent } from './components/orderDetail/shippingOrder/shippingOrder.component'




import { AuthInterceptor } from '../services/headerHttpInterceptor'

import { UserInfoService } from '../services/userInfo.service'
import { HSUserService } from '../services/hsUser.service'
import { HSOrderService } from '../services/hsOrder.service'


import { PipeSubstringId, PipeOrderStatus, PipePaymentSettleMode, PipeCargoType, PipeCustomerType, PipeCargoArriveStatus, PipeTrafficMode} from './pipes/order'




const userHomeRoutes: Routes = [
    {path : '', redirectTo : '/info/basic', pathMatch : 'full'},
    {path : 'info/basic', component : BasicInfoComponent},
    {path : 'info/password', component : ModifyPasswordComponent},

    {path : 'users', component : UserManagementComponent},
    {path : 'departments', component : DepartmentManagementComponent},
    {path : 'teams', component : TeamManagementComponent},
    {path : 'companies', component : PartyManagementComponent},

    {path : 'orders', component : OrderComponent},
    {path : 'orders/:orderId', component : OrderDetailComponent},
    {path : '**', redirectTo : '/info/basic', pathMatch : 'full'},
]


@NgModule({
    declarations : [
        PipeOrderStatus,
        PipePaymentSettleMode,
        PipeCargoType,
        PipeCustomerType,
        PipeCargoArriveStatus,
        PipeTrafficMode,


        AdminHomeComponent,
        BasicInfoComponent,
        ModifyPasswordComponent,

        UserManagementComponent,
        DepartmentManagementComponent,
        TeamManagementComponent,
        PartyManagementComponent,

        OrderComponent,
        OrderDetailComponent,
        ShippingOrderComponent
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
        HSOrderService,

        {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
    ],
    bootstrap    : [AdminHomeComponent]
})
export class AdminHomeModule { }
