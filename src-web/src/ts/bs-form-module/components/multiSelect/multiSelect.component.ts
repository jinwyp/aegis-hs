import {Component, OnInit, Input, Output, forwardRef, ElementRef, ViewChild, OnChanges, SimpleChange, EventEmitter} from '@angular/core'
import { ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR } from '@angular/forms'

@Component({
    selector: 'bs-multi-select',
    templateUrl: './multiSelect.component.html',
    styleUrls: ['./multiSelect.component.css'],
    host: {
        '(document:click)': 'onClickHideSelect($event)',
        '(document:keydown)': 'onKeyboardSelectOption($event)'
    },
    providers   : [
        {
            provide     : NG_VALUE_ACCESSOR,
            useExisting : forwardRef(() => MultiSelectComponent),
            multi       : true
        }
    ]
})
export class MultiSelectComponent implements OnInit, OnChanges, ControlValueAccessor {

    @Input('fc') currentFormControl: FormControl = new FormControl()
    @Input() label: string
    @Input() addAllOptions: boolean = false


    @Input('options') optionList: any[]

    @Input() editable: boolean = false

    @Input() error: string = ''
    @Input() dirty: boolean = false


    @Input('labelclass') labelClass: string = 'col-2'
    @Input('inputclass') inputClass: string = 'col-6'

    @Input() key: string = ''

    @Output() change: any = new EventEmitter<any>()

    @ViewChild('optionsListEl') optionsListEl: ElementRef
    @ViewChild('inputEl') inputEl: ElementRef

    interValueCurrentSelected : any = []

    isShowSelectOptionList: boolean = false
    currentSelectIndexByKeyboard: number =  -1

    filterOptionList: any = []


    constructor(
        private el: ElementRef
    ) {
        // console.log('constructor')
    }

    ngOnInit(): void {
        // console.log('ngOnInit', this.el.nativeElement)
    }


    ngOnChanges (changes: {[propKey: string]: SimpleChange}) {
        // console.log('ngOnChanges')

        for (const propertyName in changes) {

            if (changes.hasOwnProperty(propertyName)) {
                const currentChangeObject = changes[propertyName]


                if (propertyName === 'optionList' && currentChangeObject.currentValue && Array.isArray(currentChangeObject.currentValue)) {

                    this.filterOptionList = this.optionList.slice()
                    this.writeValue(this.interValueCurrentSelected)
                }
            }
        }
    }


    showOptions() {
        this.isShowSelectOptionList = !this.isShowSelectOptionList
        this.inputEl.nativeElement.focus()
    }


    filterOptions(event: KeyboardEvent) {

        if (event.keyCode !== 13 && event.keyCode !== 38 && event.keyCode !== 40 && event.keyCode !== 8)  {
            this.isShowSelectOptionList = true

            const inputText: string = (<HTMLInputElement>event.target).value
            const tempOptions: any[] = this.optionList.slice()

            if (inputText.trim()) {

                for (let i = this.optionList.length - 1; i >= 0; i --) {

                    if ( this.optionList[i].name.indexOf(inputText) === -1 ) {
                        tempOptions.splice(i, 1)
                    }
                }
            }

            this.filterOptionList = tempOptions

            if (tempOptions.length === 0) {
                this.currentSelectIndexByKeyboard = -1
            } else {
                this.currentSelectIndexByKeyboard = 0
            }

        }

    }


    clickOption(currentOption: any) {

        if ( this.interValueCurrentSelected.indexOf(currentOption) === -1) {
            this.interValueCurrentSelected.push(currentOption)

            const tempIndex = this.filterOptionList.indexOf(currentOption)
            this.filterOptionList[tempIndex].selected = true
        }

        this.value = this.interValueCurrentSelected

        this.isShowSelectOptionList = false
        this.currentSelectIndexByKeyboard = this.filterOptionList.indexOf(currentOption)

        if (this.currentSelectIndexByKeyboard === -1) {
            this.filterOptionList = this.optionList.slice()
        }

        this.change.emit(currentOption)

    }
    deleteSelected(currentOption: any) {

        this.interValueCurrentSelected.splice(this.interValueCurrentSelected.indexOf(currentOption), 1)

        this.value = this.interValueCurrentSelected
    }


    //点击选择框以外区域,select选择框隐藏, 并重置数据
    onClickHideSelect(event: any) {

        if (!this.optionsListEl.nativeElement.contains(event.target)) {
            this.isShowSelectOptionList = false
        }
    }

    //select 键盘事件
    onKeyboardSelectOption(event: any) {

        if ( this.isShowSelectOptionList) {

            const optionsLength = this.filterOptionList.length

            if ( event.keyCode === 40) {
                //下

                if ( this.currentSelectIndexByKeyboard < optionsLength - 1) {
                    this.currentSelectIndexByKeyboard ++

                }else {
                    this.currentSelectIndexByKeyboard = 0
                }
            }else if (event.keyCode === 38) {
                //上

                if ( this.currentSelectIndexByKeyboard < 1) {
                    this.currentSelectIndexByKeyboard = optionsLength - 1
                }else {
                    this.currentSelectIndexByKeyboard --
                }

            }else if (event.keyCode === 13) {
                //enter

                if (this.currentSelectIndexByKeyboard === -1 ) {
                    this.clickOption({ id : -1 , name : ''})

                } else {
                    this.clickOption(this.filterOptionList[this.currentSelectIndexByKeyboard])
                }
                this.inputEl.nativeElement.value = ''
            }

        }

        if ( event.keyCode === 8 && this.inputEl.nativeElement.value === '' && this.inputEl.nativeElement === document.activeElement) {
            const tempOptions = this.interValueCurrentSelected.splice(this.interValueCurrentSelected.length - 1, 1)

            this.filterOptionList.forEach( option => {
                if ( option.id === tempOptions[0].id) {
                    option.selected = false
                }
            })

            console.log('index: ', tempOptions, this.currentSelectIndexByKeyboard)
        }
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
        console.log('WriteValue: ', value)

        if ( value && Array.isArray(value) && Array.isArray(this.filterOptionList) && this.filterOptionList.length > 0) {

            const tempValue : any[] = []

            this.filterOptionList.forEach( (option) => {
                value.forEach( (id) => {
                    if ( option.id === id) {
                        tempValue.push(option)
                        option.selected = true
                    }
                })
            })

            this.value = tempValue
            this.interValueCurrentSelected = tempValue

        }
    }

}
