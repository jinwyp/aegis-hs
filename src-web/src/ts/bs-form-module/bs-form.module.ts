import { NgModule} from '@angular/core'
import { CommonModule } from '@angular/common'
import { HttpClientModule } from '@angular/common/http'
import { FormsModule, ReactiveFormsModule} from '@angular/forms'


import { BSCommonModule } from '../bs-common-module/bs-common.module'
import { NgbDatepickerModule } from './components/ngb-datepicker/datepicker.module'



import { UiSwitchComponent } from './components/ui-switch/ui-switch.component'
import { SwitchComponent } from './components/switch/switch.component'
import { TextInputComponent } from './components/text-input/text-input.component'
import { SelectDropdownComponent } from './components/selectDropdown/selectDropdown.component'
import { MultiSelectComponent } from './components/multiSelect/multiSelect.component'
import { RadioComponent } from './components/radio/radio.component'
import { DatePickerComponent } from './components/datepicker/datepicker.component'
import { AddressDropdownComponent } from './components/addressDropdown/addressDropdown.component'
import { CheckboxComponent } from './components/checkbox/checkbox.component'


import {ForbiddenValidatorDirective} from './validators/custom-validator'



import {FilterArrayPipe} from './pipes/filterArray/filterArray.pipe'
import {FindKeyPipe} from './pipes/findKey/findKey.pipe'


import {HttpService} from './services/http.service'

@NgModule({
    declarations : [
        FilterArrayPipe,
        FindKeyPipe,

        UiSwitchComponent,
        SwitchComponent,
        TextInputComponent,
        SelectDropdownComponent,
        MultiSelectComponent,
        RadioComponent,
        DatePickerComponent,
        AddressDropdownComponent,
        CheckboxComponent,

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


        FilterArrayPipe,
        FindKeyPipe,

        TextInputComponent,
        UiSwitchComponent,
        SwitchComponent,
        SelectDropdownComponent,
        MultiSelectComponent,
        RadioComponent,
        DatePickerComponent,
        AddressDropdownComponent,
        CheckboxComponent
    ],
    bootstrap    : []
})
export class BSFormModule {}
