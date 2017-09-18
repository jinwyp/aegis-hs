/**
 * Created by jin on 7/25/17.
 */


import { BrowserModule} from '@angular/platform-browser'
import { NgModule} from '@angular/core'


import { BSFormModule } from '../bs-form-module/bs-form.module'
import { BSCommonModule } from '../bs-common-module/bs-common.module'

import { LoginComponent } from './components/login/login.component'

import { UserLoginService } from '../services/userLogin.service'



@NgModule({
    declarations : [
        LoginComponent
    ],
    imports      : [
        BrowserModule,

        BSFormModule,
        BSCommonModule

    ],
    providers    : [
        UserLoginService
    ],
    bootstrap    : [LoginComponent]
})
export class LoginModule {}


