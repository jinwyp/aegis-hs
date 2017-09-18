import {Component, Input, forwardRef, OnInit, OnChanges, SimpleChange} from '@angular/core'
import {ControlValueAccessor, NG_VALUE_ACCESSOR} from '@angular/forms'

@Component({
    selector    : 'bs-switch',
    templateUrl : './switch.component.html',
    providers   : [
        {
            provide     : NG_VALUE_ACCESSOR,
            useExisting : forwardRef(() => SwitchComponent),
            multi       : true
        }
    ]
})
export class SwitchComponent implements ControlValueAccessor, OnInit, OnChanges {

    @Input() label: string = 'label switch'
    interValue: boolean = false

    onChange: any  = () => {}
    onTouched: any = () => {}

    constructor () {
        // console.log('constructor')
    }

    ngOnInit () {
        // console.log('ngOnInit')
    }

    ngOnChanges (changes: {[propKey: string]: SimpleChange}) {
        // console.log('ngOnChanges')

        for (const propertyName in changes) {

            if (changes.hasOwnProperty(propertyName)) {
                const currentChange = changes[propertyName]
                // console.log('currentChange: ', currentChange)
            }

        }
    }

    get value() {
        // console.log('getter: ', this.interValue)
        return this.interValue
    }

    set value(val: any) {
        // console.log('setter: ', val)
        this.interValue = val
        this.onChange(val)
        this.onTouched()
    }


    registerOnChange(fn: any): void {
        // console.log('registerOnChange: ', fn)
        this.onChange = fn
    }

    registerOnTouched(fn: any): void {
        // console.log('registerOnTouched: ', fn)
        this.onTouched = fn
    }

    writeValue(value: any): void {
        // console.log('writeValue: ', value)
        if (value) {
            this.value = value
        }
    }


    switch() {
        this.value = !this.value
    }


}
