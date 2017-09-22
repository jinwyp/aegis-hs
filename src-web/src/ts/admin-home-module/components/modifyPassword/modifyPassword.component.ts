import {Component, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'

import { HttpService } from '../../../bs-form-module/services/http.service'


import { formErrorHandler, isMatched } from '../../../bs-form-module/validators/validator'
import {UserInfoService} from '../../../services/userInfo.service'



@Component({
  selector: 'app-admin-password',
  templateUrl: './modifyPassword.component.html',
  styleUrls: ['./modifyPassword.component.css']
})
export class ModifyPasswordComponent implements OnInit {

    passwordForm: FormGroup
    ignoreDirty: boolean = false

    currentUser : any


    constructor(
        private fb: FormBuilder,
        private userService: UserInfoService,
        private httpService: HttpService
    ) {

    }


    ngOnInit(): void {
        this.createUserInfoForm()
        this.getSessionUserInfo()
    }

    getSessionUserInfo () {
        this.userService.sessionUserInfo().subscribe(
            data => {
                this.currentUser = data

                // console.log('当前登陆的用户信息: ', data)
            },
            error => {this.httpService.errorHandler(error) }
        )
    }


    passwordFormError : any = {}
    passwordFormValidationMessages: any = {
        'oldPassword'  : {
            'required'      : '请填写密码!',
            'minlength'     : '密码长度6-30个字符!',
            'maxlength'     : '密码长度6-30个字符!'
        },
        'newPassword'  : {
            'required'      : '请填写密码!',
            'minlength'     : '密码长度6-30个字符!',
            'maxlength'     : '密码长度6-30个字符!'
        },
        'newPassword2'  : {
            'required'      : '请填写确认密码!',
            'minlength'     : '确认密码长度6-30个字符!',
            'maxlength'     : '确认密码长度6-30个字符!',
            'mismatched'    : '确认密码输入不一致!'
        }
    }

    passwordFormInputChange(formInputData : any, ignoreDirty : boolean = false) {
        this.passwordFormError = formErrorHandler(formInputData, this.passwordForm, this.passwordFormValidationMessages, ignoreDirty)
    }

    createUserInfoForm(user: any = {}): void {

        this.passwordForm = this.fb.group({
            'oldPassword'    : ['', [Validators.required, Validators.minLength(6), Validators.maxLength(30)] ],
            'newPassword'    : ['', [Validators.required, Validators.minLength(6), Validators.maxLength(30)] ],
            'newPassword2'    : ['', [Validators.required, Validators.minLength(6), Validators.maxLength(30), isMatched('newPassword')] ]
        } )

        this.passwordForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.passwordFormInputChange(data)
        })

    }


    passwordFormSubmit() {

        if (this.passwordForm.invalid) {
            this.passwordFormInputChange(this.passwordForm.value, true)
            this.ignoreDirty = true

            console.log('当前表单状态: ', this.passwordForm.invalid, this.passwordFormError, this.passwordForm)

            return
        }

        this.userService.modifyPassword(this.passwordForm.value).subscribe(
            data => {
                console.log('保存密码成功: ', data)
                this.httpService.successHandler(data)
            },
            error => {this.httpService.errorHandler(error) }
        )

    }




}
