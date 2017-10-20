import {Component, Input, forwardRef, OnInit, OnChanges, SimpleChange} from '@angular/core'
import {ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR} from '@angular/forms'

@Component({
    selector    : 'bs-text-input',
    templateUrl : './text-input.component.html',
    providers   : [
        {
            provide     : NG_VALUE_ACCESSOR,
            useExisting : forwardRef(() => TextInputComponent),
            multi       : true
        }
    ]
})
export class TextInputComponent implements ControlValueAccessor, OnInit, OnChanges {

    @Input('fc') currentFormControl: FormControl = new FormControl()
    @Input() type: string = 'text'
    @Input() label: string = '标签文字:'
    @Input() prompt: string = ''
    @Input() hint: string = ''

    @Input() error: string = ''
    @Input() dirty: boolean = false
    @Input() readOnly: boolean = false
    @Input() percent: boolean = false
    @Input() passwordTip: boolean = false

    @Input('labelclass') labelClass: string = 'col-2'
    @Input('inputclass') inputClass: string = 'col-6'
    @Input() additionalClass: string = 'col-4'
    @Input() additionalContent : boolean = false


    interValue: string | number = ''
    labelId : string | number = ''
    showTip : boolean = false
    showPasswordValidation1 : boolean = false
    showPasswordValidation2 : boolean = false
    showPasswordValidation3 : boolean = false
    showPasswordValidation4 : boolean = false

    onChange: any  = () => { return undefined }
    onTouched: any = () => { return undefined }

    constructor() {
        // console.log('constructor')
    }

    ngOnInit() {
        // console.log('ngOnInit', this.additionalContent)
        this.labelId = 'id_' + this.getRandomInt()
    }

    ngOnChanges(changes: { [propKey: string]: SimpleChange }) {
        // console.log('ngOnChanges')

        for (const propertyName in changes) {

            if (changes.hasOwnProperty(propertyName)) {
                const currentChangeObject = changes[propertyName]

                // if (currentChangeObject.currentValue && currentChangeObject.isFirstChange) {
                //     console.log('currentChangeObject firstChange: ', propertyName, currentChangeObject)
                // }else {
                //     console.log('currentChangeObject secondChange: ', propertyName, currentChangeObject)
                // }

            }
        }
    }

    get value() {
        // console.log('getter: ', this.interValue)

        if (this.percent && this.interValue) {
            return Math.floor(Number(this.interValue) * 10000) / 100
        }
        return this.interValue
    }

    set value(val: any) {
        // console.log('Setter: ', val)

        if (this.percent) {
            this.interValue = Math.floor(Number(val) * 100) / 10000
        } else {
            this.interValue = val
        }

        this.onChange(this.interValue)
        this.onTouched()
    }


    registerOnChange(fn: any): void {
        // console.log('RegisterOnChange: ', fn)
        this.onChange = fn
    }

    registerOnTouched(fn: any): void {
        // console.log('RegisterOnTouched: ', fn)
        this.onTouched = fn
    }

    writeValue(value: any): void {
        // console.log('WriteValue: ', value)
        if (value) {
            this.interValue = value
        }
    }


    inputOnChange(inputValue: any) {
        this.showTip = true
        this.value = inputValue

        if (inputValue && inputValue.length >= 6) { this.showPasswordValidation1 = true} else {this.showPasswordValidation1 = false}
        if (/[A-Z]/.test(inputValue)) { this.showPasswordValidation2 = true} else {this.showPasswordValidation2 = false}
        if (/[a-z]/.test(inputValue)) { this.showPasswordValidation3 = true} else {this.showPasswordValidation3 = false}
        if (/[0-9]/.test(inputValue)) { this.showPasswordValidation4 = true} else {this.showPasswordValidation4 = false}

    }

    inputOnBlue(inputValue: any) {
        this.showTip = false
    }


    getRandomInt(min: number = 1000, max: number = 9999) {

        // https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Math/random

        min = Math.ceil(min)
        max = Math.floor(max)
        return Math.floor(Math.random() * (max - min)) + min  //The maximum is exclusive and the minimum is inclusive
    }



}
