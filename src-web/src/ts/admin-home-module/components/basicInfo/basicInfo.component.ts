import {Component, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'

import { HttpService } from '../../../bs-form-module/services/http.service'


import { formErrorHandler } from '../../../bs-form-module/validators/validator'
import { UserInfoService } from '../../../services/userInfo.service'
import { HSUserService } from '../../../services/hsUser.service'




@Component({
  selector: 'app-admin-basic',
  templateUrl: './basicInfo.component.html',
  styleUrls: ['./basicInfo.component.css']
})
export class BasicInfoComponent implements OnInit {

    userInfoForm: FormGroup
    ignoreDirty: boolean = false

    currentUser : any

    departmentList : any[] = []

    constructor(
        private fb: FormBuilder,
        private userService: UserInfoService,
        private httpService: HttpService,
        private hsUserService: HSUserService
    ) {

    }




    ngOnInit(): void {
        this.getDepartmentList()
        this.createUserInfoForm()
        this.getCurrentUserInfo()
    }

    getCurrentUserInfo () {
        this.userService.sessionUserInfo().subscribe(
            data => {
                this.currentUser = data

/*

                if (data && data.gender) {
                    this.userInfoForm.patchValue({ 'gender'    : data.gender})
                }
*/


                // console.log('当前登陆的用户信息: ', data)
            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    getDepartmentList () {
        this.hsUserService.getDepartmentList().subscribe(
            data => {
                this.departmentList = data.data.results

            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    userInfoFormError : any = {}
    userInfoFormValidationMessages: any = {
        'deptId'   : {
            'required'  : '请填写名字!',
            'minlength' : '名字长度1-100个字符!',
            'maxlength' : '名字长度1-100个字符!',
            'pattern'   : '名字必须字母开头!',
            'isExist'   : '名字已经存在!'
        },
        'phone' : {
            'required'    : '请填写手机号!',
            'mobilePhone' : '手机号格式不正确!',
            'isExist'     : '手机号已经存在!'
        }
    }

    userInfoFormInputChange(formInputData : any) {
        this.userInfoFormError = formErrorHandler(formInputData, this.userInfoForm, this.userInfoFormValidationMessages)
    }

    createUserInfoForm(user: any = {}): void {

        this.userInfoForm = this.fb.group({
            'deptId'    : [user.deptId, [Validators.required] ]
        } )

        this.userInfoForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.userInfoFormInputChange(data)
        })

    }


    userInfoFormSubmit() {

        if (this.userInfoForm.invalid) {
            this.userInfoFormInputChange(this.userInfoForm.value)
            this.ignoreDirty = true

            console.log('当前表单状态: ', this.userInfoForm.invalid, this.userInfoFormError, this.userInfoForm)

            return
        }

        this.userService.saveUserInfo(this.userInfoForm.value).subscribe(
            data => {
                console.log('保存用户信息成功: ', data)
                this.httpService.successHandler(data)
            },
            error => {this.httpService.errorHandler(error) }
        )

    }



}
