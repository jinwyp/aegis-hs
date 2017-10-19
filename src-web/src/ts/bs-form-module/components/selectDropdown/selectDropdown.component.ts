import {Component, OnInit, Input, Output, forwardRef, ElementRef, ViewChild, OnChanges, SimpleChange, EventEmitter} from '@angular/core'
import { ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR } from '@angular/forms'

@Component({
    selector: 'bs-select',
    templateUrl: './selectDropdown.component.html',
    styleUrls: ['./selectDropdown.component.css'],
    host: {
        '(document:click)': 'onClickHideSelect($event)',
        '(document:keydown)': 'onKeyboardSelectOption($event)'
    },
    providers   : [
        {
            provide     : NG_VALUE_ACCESSOR,
            useExisting : forwardRef(() => SelectDropdownComponent),
            multi       : true
        }
    ]
})
export class SelectDropdownComponent implements OnInit, OnChanges, ControlValueAccessor {

    @Input('fc') currentFormControl: FormControl = new FormControl()
    @Input() label: string
    @Input() prompt: string = '请选择'
    @Input() addAllOptions: boolean = false


    @Input('options') optionList: any[]

    @Input() editable: boolean = false

    @Input() error: string = ''
    @Input() dirty: boolean = false

    @Input('labelclass') labelClass: string = 'col-2'
    @Input('inputclass') inputClass: string = 'col-6'


    @Output() change: any = new EventEmitter<any>()

    @ViewChild('optionsListEl') optionsListEl: ElementRef

    interValueCurrentSelected : any = { id : -1 , name : '请选择'}

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

                // if (currentChangeObject.currentValue && currentChangeObject.isFirstChange) {
                //     console.log('currentChangeObject firstChange: ', propertyName, currentChangeObject)
                // }else {
                //     console.log('currentChangeObject secondChange: ', propertyName, currentChangeObject)
                // }

                if (propertyName === 'optionList' && currentChangeObject.currentValue && Array.isArray(currentChangeObject.currentValue)) {

                    this.filterOptionList = this.optionList.slice()
                    // console.log('optionList', currentChangeObject.currentValue)
                    if (this.addAllOptions === true) {
                        this.filterOptionList.unshift({ id : '' , name : '全部' })
                    }

                    this.writeValue(this.interValueCurrentSelected.id)
                }
            }
        }
    }


    showOptions() {
        this.isShowSelectOptionList = !this.isShowSelectOptionList
    }

    filterOptions(event: KeyboardEvent) {

        if (event.keyCode !== 13 && event.keyCode !== 38 && event.keyCode !== 40)  {
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
        this.value = currentOption
        this.isShowSelectOptionList = false
        this.currentSelectIndexByKeyboard = this.filterOptionList.indexOf(currentOption)

        if (this.currentSelectIndexByKeyboard === -1) {
            this.filterOptionList = this.optionList.slice()
        }

        this.change.emit(currentOption)

        // console.log('clickCurrentOption', currentOption, this.currentSelectIndexByKeyboard)
    }


    //点击选择框以外区域,select选择框隐藏, 并重置数据
    onClickHideSelect(event: any) {

        if (!this.optionsListEl.nativeElement.contains(event.target)) {

            if (this.isShowSelectOptionList === true) {
                if (this.currentSelectIndexByKeyboard === -1 ) {
                    this.clickOption({ id : -1 , name : ''})

                } else {
                    this.clickOption(this.filterOptionList[this.currentSelectIndexByKeyboard])
                }
            }
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
            }

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

        if (this.interValueCurrentSelected.id === -1 && val.id !== -1  || this.interValueCurrentSelected.id !== -1) {
            this.onChange(val.id)
        }

        this.interValueCurrentSelected = val


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

        if (Array.isArray(this.filterOptionList)) {

            let tempValue = { id : -1 , name : ''}

            this.filterOptionList.forEach( option => {
                if (option.id === value) {
                    tempValue = option
                }
            })

            this.value = tempValue
            this.currentSelectIndexByKeyboard = this.optionList.indexOf(this.value)
        }
    }

}
