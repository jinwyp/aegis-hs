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


import { OrderListComponent } from './components/orderList/orderList.component'
import { OrderDetailComponent } from './components/orderDetail/orderDetail.component'
import { UnitComponent } from './components/orderDetail/unit/unit.component'
import { ShippingOrderComponent } from './components/orderDetail/shippingOrder/shippingOrder.component'

import { BorrowComponent } from './components/orderDetail/borrow/borrow.component'
import { PaymentComponent } from './components/orderDetail/payment/payment.component'
import { RepaymentComponent } from './components/orderDetail/repayment/repayment.component'
import { RepaymentHuanKuanComponent } from './components/orderDetail/repaymentHuanKuan/repaymentHuanKuan.component'

import { SettleOrderComponent } from './components/orderDetail/settleOrder/settleOrder.component'
import { ExpenseComponent } from './components/orderDetail/expense/expense.component'
import { InvoiceComponent } from './components/orderDetail/invoice/invoice.component'
import { WarehouseOrderComponent } from './components/orderDetail/warehouseOrder/warehouseOrder.component'

import { DepositComponent } from './components/orderDetail/deposit/deposit.component'




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

    {path : 'yingorders', component : OrderListComponent, data: { businessType: 'ying' }},
    {path : 'yingorders/:orderId', component : OrderDetailComponent, data: { businessType: 'ying' }},

    {path : 'cangorders', component : OrderListComponent, data: { businessType: 'cang' }},
    {path : 'cangorders/:orderId', component : OrderDetailComponent, data: { businessType: 'cang' }},


    {path : '**', redirectTo : '/info/basic', pathMatch : 'full'}
]


@NgModule({
    declarations : [
        PipeSubstringId,
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

        OrderListComponent,
        OrderDetailComponent,
        UnitComponent,
        ShippingOrderComponent,

        BorrowComponent,
        PaymentComponent,
        RepaymentComponent,
        RepaymentHuanKuanComponent,
        SettleOrderComponent,
        ExpenseComponent,
        InvoiceComponent,
        WarehouseOrderComponent,

        DepositComponent

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
