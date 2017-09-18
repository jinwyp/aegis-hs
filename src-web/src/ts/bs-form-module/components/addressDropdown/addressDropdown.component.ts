import {Component, OnInit, Input, forwardRef, ElementRef, ViewChild} from '@angular/core'
import { ControlValueAccessor, FormControl, NG_VALUE_ACCESSOR } from '@angular/forms'


import { provinceList } from './provinceData'

@Component({
    selector: 'bs-address',
    templateUrl: './addressDropdown.component.html',
    styleUrls: ['./addressDropdown.component.css'],
    host: {
        '(document:click)': 'onClickHideSelect($event)'
    },
    providers   : [
        {
            provide     : NG_VALUE_ACCESSOR,
            useExisting : forwardRef(() => AddressDropdownComponent),
            multi       : true
        }
    ]
})
export class AddressDropdownComponent implements OnInit, ControlValueAccessor {

    @Input('fc') currentFormControl: FormControl = new FormControl()
    @Input() label: string

    @Input('dataSource') dataSource: any[]

    @Input() error: string = ''

    @Input('labelclass') labelClass: string = 'col-2'
    @Input('inputclass') inputClass: string = 'col-6'


    @ViewChild('optionsListEl') optionsListEl: ElementRef

    currentProvince: any = {
        id : -1,
        name: '请选择省'
    }
    currentCity: any = {
        id : -1,
        name: '市'
    }
    currentDistrict: any = {
        id : -1,
        name: '区'
    }

    currentTab: string = 'province'

    cityList: any = []
    districtList: any = []

    interValueCurrentSelected : any = {
        province: '请选择省',
        city: '市',
        district: '区',
        provinceId: '',
        cityId: '',
        districtId: ''
    }

    isShowDropdownList: boolean = false

    constructor(
        private el: ElementRef
    ) {
        // console.log('constructor')
    }

    ngOnInit(): void {
        // console.log('ngOnInit', this.el.nativeElement)
    }

    showOptions() {
        this.isShowDropdownList = !this.isShowDropdownList
    }




    tabChange(tab: any) {
        this.currentTab = tab
    }


    getCurrentProvince(province: any) {

        this.currentProvince = province
        this.cityList = this.currentProvince.cities

        if ( this.currentProvince.name === '北京' || this.currentProvince.name === '天津'
            || this.currentProvince.name === '上海' || this.currentProvince.name === '重庆'
            || this.currentProvince.name === '香港' || this.currentProvince.name === '澳门' ) {


            this.currentCity = province.cities[0]
            this.districtList = this.currentCity.counties

            this.currentTab = 'district'

        }else {
            this.currentTab = 'city'
            this.currentCity = {
                name: '市'
            }
        }

        this.currentDistrict = {
            name: '区'
        }

    }

    getCurrentCity(city: any) {

        this.currentCity = city
        this.districtList = city.counties

        this.currentDistrict = {
            name: '区'
        }

        this.currentTab = 'district'
    }

    getCurrentDistrict(district: any) {
        this.currentDistrict = district

        this.value = {
            province: this.currentProvince.name,
            provinceId: this.currentProvince.id,
            city: this.currentCity.name,
            cityId: this.currentCity.id,
            district: this.currentDistrict.name,
            districtId: this.currentDistrict.id
        }

        this.isShowDropdownList = false
    }


    //点击选择框以外区域,select选择框隐藏
    onClickHideSelect(event: any) {

        if (!this.optionsListEl.nativeElement.contains(event.target)) {
            this.isShowDropdownList = false
            this.currentTab = 'province'
        }
    }


    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }



    onChange (value : any) {return}
    onTouched () {return}

    get value() {
        // console.log('getter: ', this.value)
        return this.interValueCurrentSelected
    }

    set value(val: any) {
        // console.log('Setter: ', val)
        this.interValueCurrentSelected = val

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
        // console.log('WriteValue: ', value, this.dataSource)


        if (!this.dataSource) {
            this.dataSource = provinceList
        }


        if (Array.isArray(this.dataSource) && this.dataSource.length > 0) {

            if (value && value.provinceId && value.cityId && value.districtId) {
                //通过 省市区 ID查找
                let tempProvince = {id : -1, name: '请选择省', cities : []}
                let tempCity = {id : -1, name: '市', counties : [] }
                let tempDistrict = {id : -1, name: '区'}

                this.dataSource.forEach( province => {
                    if (province.id === Number(value.provinceId)) { tempProvince = province }
                })
                this.currentProvince = tempProvince

                this.currentProvince.cities.forEach(city => {
                    if (city.id === Number(value.cityId)) { tempCity = city}
                })
                this.currentCity = tempCity

                this.currentCity.counties.forEach(district => {
                    if ( district.id === Number(value.districtId) ) { tempDistrict = district }
                })
                this.currentDistrict = tempDistrict

                this.value = {
                    province: this.currentProvince.name,
                    provinceId: value.provinceId,
                    city: this.currentCity.name,
                    cityId: value.cityId,
                    district: this.currentDistrict.name,
                    districtId: value.districtId
                }

            }else if (value && value.province && value.city && value.district) {
                //通过省市名称字符串 查找

                let tempProvince = {id : -1, name: '请选择省', cities : []}
                let tempCity = {id : -1, name: '市', counties : [] }
                let tempDistrict = {id : -1, name: '区'}

                this.dataSource.forEach( province => {
                    if (province.name === Number(value.province)) { tempProvince = province }
                })
                this.currentProvince = tempProvince

                this.currentProvince.cities.forEach(city => {
                    if (city.name === Number(value.city)) { tempCity = city}
                })
                this.currentCity = tempCity

                this.currentCity.counties.forEach(district => {
                    if ( district.name === Number(value.district) ) { tempDistrict = district }
                })
                this.currentDistrict = tempDistrict

                this.value = {
                    province: this.currentProvince.name,
                    provinceId: this.currentProvince.id,
                    city: this.currentCity.name,
                    cityId: this.currentCity.id,
                    district: this.currentDistrict.name,
                    districtId: this.currentDistrict.id
                }
            }

        }
    }

}
