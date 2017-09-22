import {Component, Input, forwardRef, OnInit, OnChanges, SimpleChange} from '@angular/core'
import {ControlValueAccessor, NG_VALUE_ACCESSOR, FormControl} from '@angular/forms'

@Component({
    selector    : 'bs-switch',
    templateUrl : './switch.component.html',
    styleUrls: ['./switch.component.css'],
    providers   : [
        {
            provide     : NG_VALUE_ACCESSOR,
            useExisting : forwardRef(() => SwitchComponent),
            multi       : true
        }
    ]
})
export class SwitchComponent implements ControlValueAccessor, OnInit, OnChanges {

    @Input('fc') currentFormControl: FormControl = new FormControl()
    @Input() label: string
    @Input() prompt: string = 'Checkbox'

    @Input() error: string = ''


    @Input() checkbox: boolean = false

    @Input('labelclass') labelClass: string = 'col-2'
    @Input('inputclass') inputClass: string = 'col-6'


    interValue: boolean = false

    onChange: any  = () => { return }
    onTouched: any = () => { return }

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
                const currentChangeObject = changes[propertyName]

                if (currentChangeObject.currentValue && currentChangeObject.isFirstChange) {
                    // console.log('currentChangeObject firstChange: ', propertyName, currentChangeObject)
                }else {
                    // console.log('currentChangeObject secondChange: ', propertyName, currentChangeObject)
                }
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

        if ( typeof value === 'boolean') {
            this.value = value
        } else {
            this.value = false
        }

    }


    switch() {
        this.value = !this.value
    }


}
