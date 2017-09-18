/**
 * Created by jin on 7/25/17.
 */


import { BrowserModule} from '@angular/platform-browser'
import { NgModule} from '@angular/core'


import { BSFormModule } from '../bs-form-module/bs-form.module'
import { BSCommonModule } from '../bs-common-module/bs-common.module'

import { RegisterComponent } from './components/register/register.component'

import { UserLoginService } from '../services/userLogin.service'



@NgModule({
    declarations : [
        RegisterComponent
    ],
    imports      : [
        BrowserModule,

        BSFormModule,
        BSCommonModule

    ],
    providers    : [
        UserLoginService
    ],
    bootstrap    : [RegisterComponent]
})
export class RegisterModule {}


