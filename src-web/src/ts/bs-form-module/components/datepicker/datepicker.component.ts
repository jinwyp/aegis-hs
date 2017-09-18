import {Component, OnInit, Input, forwardRef, ElementRef, ViewChild} from '@angular/core'
import { ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR } from '@angular/forms'
import {isUndefined} from 'util'

@Component({
    selector: 'bs-datepicker',
    templateUrl: './datepicker.component.html',
    styleUrls: ['./datepicker.component.css'],
    host: {
        '(document:click)': 'onClickHideSelect($event)'
    },
    providers   : [
        {
            provide     : NG_VALUE_ACCESSOR,
            useExisting : forwardRef(() => DatePickerComponent),
            multi       : true
        }
    ]
})
export class DatePickerComponent implements OnInit, ControlValueAccessor {

    @Input('fc') currentFormControl: FormControl = new FormControl()
    @Input() label: string

    @Input() error: string = ''

    @Input() showLabel: boolean = true

    @Input('labelclass') labelClass: string = 'col-2'
    @Input('inputclass') inputClass: string = 'col-3'

    @ViewChild('datepicker') datePickerEl: ElementRef

    interValueDate : any = {year: 2017, month: 1}
    inputDisplayValue : string = ''

    isShowDatePicker: boolean = false

    errorFormatInfo: string = ''

    constructor(
        private el: ElementRef
    ) {
        // console.log('constructor')
    }

    ngOnInit(): void {
        // console.log('ngOnInit', this.el.nativeElement)

    }

    showDatePicker() {
        this.isShowDatePicker = !this.isShowDatePicker
    }

    getDate(event : any) {

        this.value = event
        this.inputDisplayValue = event.year + '-' + event.month + '-' + event.day
    }

    onKeyChange(textValue : string) {

        // const isDateString = new RegExp('^[1-4][0-9]{3}-(0?[1-9]|1[0-2])-(0?[1-9]|[1-2][0-9]|3[0-1])$')

        /**
         * @isDateString {RegExp}
         *
         * 考虑闰年的情况
         * http://blog.sina.com.cn/s/blog_672f8c780100ni4j.html
         */
        const isDateString = new RegExp('^(?:(?!0000)[0-9]{4}-(?:(?:0?[1-9]|1[0-2])-(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])-(?:29|30)|(?:0?[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$')

        if (isDateString.test(textValue)) {
            this.errorFormatInfo = ''

            const tempArrayDate : string[] = textValue.split('-')
            const tempDate = new Date( Number(tempArrayDate[0]), Number(tempArrayDate[1]) - 1, Number(tempArrayDate[2]) )

            this.value = {year: tempDate.getFullYear(), month: tempDate.getMonth() + 1, day: tempDate.getDate()}
            // this.inputDisplayValue = this.interValueDate.year + '-' + this.interValueDate.month + '-' + this.interValueDate.day
        }else {
            this.errorFormatInfo = '日期格式不正确!'
        }
    }

    //点击选择框以外区域,隐藏datepicker
    onClickHideSelect(event: any) {

        if (!this.datePickerEl.nativeElement.contains(event.target)) {
            this.isShowDatePicker = false
        }
    }



    onChange (value : any) { return }
    onTouched () { return }

    get value() {
        // console.log('getter: ', this.interValueCurrentSelected)
        return this.interValueDate
    }

    set value(val: any) {
        // console.log('Setter: ', val)
        this.interValueDate = val

        this.onChange(val)
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

        let now = new Date()

        let isValid = false

        if (value) {

            if (typeof value === 'string' || value instanceof String) {

                const tempArray : string[] = value.split('-')

                if (tempArray.length === 3) {

                    if ( Number(tempArray[0]) > 1900 && Number(tempArray[0]) < 4000
                        && Number(tempArray[1]) >= 1 && Number(tempArray[1]) <= 12
                        && Number(tempArray[2]) >= 1 && Number(tempArray[2]) <= 31
                    ) {
                        isValid = true

                        now = new Date( Number(tempArray[0]), Number(tempArray[1]) - 1, Number(tempArray[2]) )

                        this.value = {year: now.getFullYear(), month: now.getMonth() + 1, day: now.getDate()}
                        this.inputDisplayValue = this.interValueDate.year + '-' + this.interValueDate.month + '-' + this.interValueDate.day
                    }
                }

                if (tempArray.length === 2) {

                    if ( Number(tempArray[0]) > 1900 && Number(tempArray[0]) < 4000
                        && Number(tempArray[1]) >= 1 && Number(tempArray[1]) <= 12
                    ) {
                        isValid = true

                        now = new Date( Number(tempArray[0]), Number(tempArray[1]) - 1, 1 )

                        this.value = {year: now.getFullYear(), month: now.getMonth() + 1, day: now.getDate()}
                        this.inputDisplayValue = this.interValueDate.year + '-' + this.interValueDate.month
                    }
                }
            }

            if (typeof value === 'object' && value.year && value.month && value.day ) {

                isValid = true
                this.value = value
                this.inputDisplayValue = this.interValueDate.year + '-' + this.interValueDate.month + '-' + this.interValueDate.day
            }

        }

        if (!isValid) {
            this.value = null
            this.inputDisplayValue = ''
        }

    }

}
