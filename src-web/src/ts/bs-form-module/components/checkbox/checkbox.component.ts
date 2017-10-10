import {Component, OnInit, Input, forwardRef, ElementRef, ViewChild, OnChanges, SimpleChange} from '@angular/core'
import { ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR } from '@angular/forms'

@Component({
    selector: 'bs-checkbox',
    templateUrl: './checkbox.component.html',
    styleUrls: ['./checkbox.component.css'],
    providers   : [
        {
            provide     : NG_VALUE_ACCESSOR,
            useExisting : forwardRef(() => CheckboxComponent),
            multi       : true
        }
    ]
})
export class CheckboxComponent implements OnInit, OnChanges, ControlValueAccessor {

    @Input('fc') currentFormControl: FormControl = new FormControl()
    @Input() label: string

    @Input('options') optionList: any[]

    @Input() error: string = ''

    @Input('labelclass') labelClass: string = 'col-2'
    @Input('inputclass') inputClass: string = 'col-6'

    @Input() key: string = ''


    interValueCurrentSelected : any = []
    optionListCopy : any = []


    constructor(
        private el: ElementRef
    ) {
        // console.log('constructor')
    }

    ngOnInit(): void {
        // console.log('ngOnInit')
    }


    ngOnChanges (changes: {[propKey: string]: SimpleChange}) {
        // console.log('ngOnChanges')

        for (const propertyName in changes) {

            if (changes.hasOwnProperty(propertyName)) {
                const currentChangeObject = changes[propertyName]

                if (propertyName === 'optionList' && currentChangeObject.currentValue && Array.isArray(currentChangeObject.currentValue)) {

                    this.optionListCopy = this.optionList.slice()
                    this.writeValue(this.interValueCurrentSelected)
                }
            }

        }
    }


    getCurrentOption(currentOption: any) {
        if (this.interValueCurrentSelected.indexOf(currentOption) === -1) {
            this.interValueCurrentSelected.push(currentOption)
            currentOption.checked = true
        }else {
            this.interValueCurrentSelected.splice(this.interValueCurrentSelected.indexOf(currentOption), 1)
            currentOption.checked = false
        }

        this.value = this.interValueCurrentSelected

    }




    onChange (value : any) { return }
    onTouched () { return }

    get value() {
        // console.log('getter: ', this.interValueCurrentSelected)
        return this.interValueCurrentSelected
    }

    set value(val: any) {
        // console.log('Setter: ', val)
        this.interValueCurrentSelected = val

        if (this.key) {
            const tempResult = this.interValueCurrentSelected.map( item => item[this.key])
            this.onChange(tempResult)

        } else {
            this.onChange(val)
        }
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

        if (value && Array.isArray(value) && Array.isArray(this.optionListCopy) && this.optionListCopy.length > 0 ) {

            // console.log('WriteValue optionList: ', value)

            const tempValueObject : any = {}

            value.forEach( id => {
                tempValueObject [id] = id
            })

            const tempValue : any[] = []

            this.optionListCopy.forEach( option => {

                value.forEach( (id) => {
                    if (option.id === id) {
                        tempValue.push(option)
                        option.checked = true
                    }
                })
            })

            this.value = tempValue


        }else if (value && Array.isArray(value)) {

            // console.log('WriteValue: ', value)

            this.value = value
        }
    }

}
