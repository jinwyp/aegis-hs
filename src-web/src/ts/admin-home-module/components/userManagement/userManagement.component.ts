import {Component, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'

import { HttpService } from '../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../bs-form-module/validators/validator'
import { UserInfoService } from '../../../services/userInfo.service'




@Component({
  selector: 'app-user-management',
  templateUrl: './userManagement.component.html',
  styleUrls: ['./userManagement.component.css']
})
export class UserManagementComponent implements OnInit {

    currentUser : any

    userAddressForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    userList : any[] = []
    currentAddressId : string = ''

    constructor(
        private fb: FormBuilder,
        private userService: UserInfoService,
        private httpService: HttpService
    ) {

    }



    ngOnInit(): void {
        this.createUserAddressForm()
        this.getCurrentUserInfo()
        this.getUserList()
    }

    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }

    getCurrentUserInfo () {
        this.userService.getUserInfo().subscribe(
            data => {
                if (data) {
                    this.currentUser = data
                }
                // console.log('当前登陆的用户信息: ', data)
            },
            error => {this.httpService.errorHandler(error) }
        )
    }


    getUserList () {
        this.userService.getUserList().subscribe(
            data => {
                this.userList = data.data

                // console.log('当前用户的收货地址: ', data)
            },
            error => {this.httpService.errorHandler(error) }
        )
    }


    userAddressFormError : any = {}
    userAddressFormValidationMessages: any = {
        'contactPerson'  : {
            'required'      : '请填写收货人!',
            'minlength'     : '名字长度1-100个字符!',
            'maxlength'     : '名字长度1-100个字符!',
            'pattern'       : '名字必须字母开头!',
            'isExist'       : '名字已经存在!'
        },
        'addressObj'  : {
            'required'      : '请填写地区!'
        },
        'detailAddress'  : {
            'required'      : '请填写详细地址!',
            'minlength'     : '详细地址长度4-500个字符!',
            'maxlength'     : '详细地址长度4-500个字符!',
            'pattern'       : '昵称必须字母开头!',
            'isExist'       : '昵称已经存在!'
        },
        'contactPersonMobilePhone' : {
            'required'    : '请填写手机号!',
            'mobilePhone' : '手机号格式不正确!',
            'isExist'     : '手机号已经存在!'
        },
        'contactPersonFixedPhone' : {
            'required'    : '请填写固定电话!',
            'mobilePhone' : '固定电话格式不正确!',
            'isExist'     : '固定电话已经存在!'
        },
        'contactPersonEmail' : {
            'required'    : '请填写邮箱!',
            'email' : '邮箱格式不正确!'
        },
        'postalCode' : {
            'required'    : '请填写邮编!'
        },
        'addressCodeName' : {
            'required'    : '请填写地址别名!'
        }
    }

    userAddressFormInputChange(formInputData : any, ignoreDirty : boolean = false) {
        this.userAddressFormError = formErrorHandler(formInputData, this.userAddressForm, this.userAddressFormValidationMessages, ignoreDirty)
    }

    createUserAddressForm(address: any = {}): void {

        if (!address.contactPerson) {address.contactPerson = ''}
        if (!address.addressObj) {address.addressObj = {
            province: '',
            city: '',
            district: ''
        }}

        if (!address.detailAddress) {address.detailAddress = ''}
        if (!address.contactPersonMobilePhone) {address.contactPersonMobilePhone = ''}
        if (!address.contactPersonFixedPhone) {address.contactPersonFixedPhone = ''}
        if (!address.contactPersonEmail) {address.contactPersonEmail = ''}
        if (!address.postalCode) {address.postalCode = ''}
        if (!address.addressCodeName) {address.addressCodeName = ''}

        this.userAddressForm = this.fb.group({
            'contactPerson'    : [address.contactPerson, [Validators.required] ],
            'addressObj'    : [address.addressObj, [Validators.required] ],
            'detailAddress'    : [address.detailAddress, [Validators.required, Validators.minLength(4), Validators.maxLength(500)] ],
            'contactPersonMobilePhone'    : [address.contactPersonMobilePhone, [Validators.required, isMobilePhone()] ],
            'contactPersonFixedPhone'    : [address.contactPersonFixedPhone ],
            'contactPersonEmail'    : [address.contactPersonEmail, [] ],
            'postalCode'    : [address.postalCode ],
            'addressCodeName'    : [address.addressCodeName ]
        } )

        this.userAddressForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.userAddressFormInputChange(data)
        })
    }


    userAddressFormSubmit() {
        console.log('当前信息: ', this.userAddressForm, this.userAddressFormError)

        if (this.userAddressForm.invalid) {
            this.userAddressFormInputChange(this.userAddressForm.value, true)
            this.ignoreDirty = true

            console.log('当前信息: ', this.userAddressForm, this.userAddressFormError)
            return
        }

        const postData = this.userAddressForm.value

        postData.province = this.userAddressForm.value.addressObj.province
        postData.provinceId = this.userAddressForm.value.addressObj.provinceId

        postData.city = this.userAddressForm.value.addressObj.city
        postData.cityId = this.userAddressForm.value.addressObj.cityId

        postData.district = this.userAddressForm.value.addressObj.district
        postData.districtId = this.userAddressForm.value.addressObj.districtId


        if (this.isAddNew) {
            this.userService.createUserAddress(postData).subscribe(
                data => {
                    console.log('保存用户地址成功: ', data)
                    this.httpService.successHandler(data)

                    this.getUserList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            this.userService.updateUserAddress(this.currentAddressId, postData).subscribe(
                data => {
                    console.log('修改用户地址成功: ', data)
                    this.httpService.successHandler(data)

                    this.getUserList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }


    showForm(isAddNew : boolean = true, address?: any ) {

        if (isAddNew) {
            this.isAddNew = true

            this.userAddressForm.patchValue({
                'contactPerson'    : '',
                'addressObj'    : {
                    province: '',
                    city: '',
                    district: ''
                },
                'detailAddress'    : '',
                'contactPersonMobilePhone'    : '',
                'contactPersonFixedPhone'    : '',
                'contactPersonEmail'    : '',
                'postalCode'    : '',
                'addressCodeName'    : ''
            })

        } else {
            this.isAddNew = false
            this.currentAddressId = address._id
            const tempAddress = address

            tempAddress.addressObj = {
                provinceId : address.provinceId,
                cityId : address.cityId,
                districtId : address.districtId
            }
            this.userAddressForm.patchValue(tempAddress)
        }


        this.isShowForm = !this.isShowForm
    }



}
