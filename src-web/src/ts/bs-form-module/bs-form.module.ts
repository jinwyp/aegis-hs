import { NgModule} from '@angular/core'
import { CommonModule } from '@angular/common'
import { HttpClientModule } from '@angular/common/http'
import { FormsModule, ReactiveFormsModule} from '@angular/forms'


import { BSCommonModule } from '../bs-common-module/bs-common.module'
import { NgbDatepickerModule } from './components/ngb-datepicker/datepicker.module';



import { SwitchComponent } from './components/switch/switch.component'
import { TextInputComponent } from './components/text-input/text-input.component'
import { SelectDropdownComponent } from './components/selectDropdown/selectDropdown.component'
import { RadioComponent } from './components/radio/radio.component'
import { DatePickerComponent } from './components/datepicker/datepicker.component'
import { AddressDropdownComponent } from './components/addressDropdown/addressDropdown.component'


import {ForbiddenValidatorDirective} from './validators/custom-validator'



import {FilterArrayPipe} from './pipes/filterArray/filterArray.pipe'


import {HttpService} from './services/http.service'

@NgModule({
    declarations : [
        FilterArrayPipe,


        SwitchComponent,
        TextInputComponent,
        SelectDropdownComponent,
        RadioComponent,
        DatePickerComponent,
        AddressDropdownComponent,


        ForbiddenValidatorDirective
    ],
    imports      : [
        CommonModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule,

        BSCommonModule,
        NgbDatepickerModule.forRoot()
    ],
    providers    : [
        HttpService
    ],
    exports:      [
        CommonModule,
        ReactiveFormsModule,
        HttpClientModule,

        NgbDatepickerModule,

        TextInputComponent,
        SwitchComponent,
        SelectDropdownComponent,
        RadioComponent,
        DatePickerComponent,
        RadioComponent,
        AddressDropdownComponent
    ],
    bootstrap    : []
})
export class BSFormModule {}
