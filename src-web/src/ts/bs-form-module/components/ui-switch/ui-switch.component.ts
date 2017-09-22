/*
 https://github.com/youmesoft/angular2-ui-switch

 https://github.com/yuyang041060120/angular2-ui-switch

 */


import { Component, Input, Output, EventEmitter, HostListener, forwardRef } from '@angular/core'
import { NG_VALUE_ACCESSOR, ControlValueAccessor, FormControl } from '@angular/forms'

const UI_SWITCH_CONTROL_VALUE_ACCESSOR: any = {
    provide    : NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => UiSwitchComponent),
    multi      : true
}

@Component({
    selector : 'ui-switch',
    templateUrl : './ui-switch.component.html',
    styleUrls: ['./ui-switch.component.css'],
    providers: [ UI_SWITCH_CONTROL_VALUE_ACCESSOR ]
})
export class UiSwitchComponent implements ControlValueAccessor {

    @Input('fc') currentFormControl: FormControl = new FormControl()
    @Input() label: string

    @Input() error: string = ''

    @Input('labelclass') labelClass: string = 'col-2'
    @Input('inputclass') inputClass: string = 'col-6'

    private onTouchedCallback = (v: any) => { return }
    private onChangeCallback  = (v: any) => { return }

    private _checked: boolean
    private _disabled: boolean
    private _reverse: boolean

    @Input() set checked(v: boolean) {
        this._checked = v !== false
    }

    get checked() {
        return this._checked
    }

    @Input() set disabled(v: boolean) {
        this._disabled = v !== false
    }

    get disabled() {
        return this._disabled
    }

    @Input() set reverse(v: boolean) {
        this._reverse = v !== false
    }

    get reverse() {
        return this._reverse
    }

    @Input() size: string           = 'medium'
    @Output() change: any                = new EventEmitter<boolean>()
    @Input() color: string          = 'rgb(100, 189, 99)'
    @Input() switchOffColor: string = ''
    @Input() switchColor: string    = '#fff'

             defaultBgColor: string = '#fff'
             defaultBoColor: string = '#dfdfdf'


    getColor(flag?: string) {
        if (flag === 'borderColor') { return this.defaultBoColor }

        if (flag === 'switchColor') {
            if (this.reverse) { return !this.checked ? this.switchColor : this.switchOffColor || this.switchColor}

            return this.checked ? this.switchColor : this.switchOffColor || this.switchColor
        }

        if (this.reverse) {
            return !this.checked ? this.color : this.defaultBgColor
        }
        return this.checked ? this.color : this.defaultBgColor
    }

    @HostListener('click')
    onToggle () {
        if (this.disabled) { return }
        this.checked = !this.checked
        this.change.emit(this.checked)
        this.onChangeCallback(this.checked)
        this.onTouchedCallback(this.checked)
    }

    writeValue(obj: any): void {
        if (obj !== this.checked) {
            this.checked = !!obj
        }
    }

    registerOnChange(fn: any) {
        this.onChangeCallback = fn
    }

    registerOnTouched(fn: any) {
        this.onTouchedCallback = fn
    }
}
