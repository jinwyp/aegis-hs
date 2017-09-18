import {Component, Inject, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'

import { HttpService } from '../../../bs-form-module/services/http.service'


import { formErrorHandler } from '../../../bs-form-module/validators/validator'
import {UserLoginService} from '../../../services/userLogin.service'



@Component({
    selector    : 'app-login',
    templateUrl : './login.component.html'
})
export class LoginComponent implements OnInit {

    loginForm: FormGroup
    ignoreDirty: boolean = false

    constructor(
        @Inject('moduleType') public pageType: string,
        private fb: FormBuilder,
        public userService: UserLoginService,
        private httpService: HttpService
    ) {

    }

    ngOnInit(): void {
        this.createLoginForm()
    }

    loginFormError : any = {}
    loginFormValidationMessages: any = {
        'username'  : {
            'required'      : '请填写用户名!',
            'minlength'     : '用户名长度4-30个字符!',
            'maxlength'     : '用户名长度4-30个字符!',
            'pattern'       : '用户名必须字母开头!',
            'isExist'       : '用户名已经存在!'
        },
        'mobilePhone' : {
            'required'    : '请填写手机号!',
            'mobilePhone' : '手机号格式不正确!',
            'isExist'     : '手机号已经存在!'
        },
        'password'  : {
            'required'      : '请填写密码!',
            'minlength'     : '密码长度6-30个字符!',
            'maxlength'     : '密码长度6-30个字符!'
        }
    }

    loginFormInputChange(formInputData : any, ignoreDirty : boolean = false) {
        this.loginFormError = formErrorHandler(formInputData, this.loginForm, this.loginFormValidationMessages, ignoreDirty)
    }

    createLoginForm(): void {
        this.loginForm = this.fb.group({
            'username'    : ['', [Validators.required, Validators.minLength(4), Validators.maxLength(30), Validators.pattern(/^[a-zA-Z][a-zA-Z0-9_]*$/)] ],
            'password'    : ['', [Validators.required, Validators.minLength(6), Validators.maxLength(30)] ]
        } )
        this.loginForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.loginFormInputChange(data)
        })
    }


    loginFormSubmit() {

        if (this.loginForm.invalid) {
            this.loginFormInputChange(this.loginForm.value, true)
            this.ignoreDirty = true
            return
        }

        this.userService.login(this.loginForm.value).subscribe(
            data => {
                console.log('登陆成功: ', data)
                window.location.href = '/web/home'
                this.httpService.successHandler(data)
            },
            error => {this.httpService.errorHandler(error) }
        )

    }


}
