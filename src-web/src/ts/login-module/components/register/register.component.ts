import {Component, Inject, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'

import { HttpService } from '../../../bs-form-module/services/http.service'


import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../bs-form-module/validators/validator'
import {UserLoginService} from '../../../services/userLogin.service'



import {apiPath} from '../../../services/apiPath'


@Component({
    selector    : 'app-register',
    templateUrl : './register.component.html'
})
export class RegisterComponent implements OnInit {

    formType: string = 'mobilePhone' // 注册有三种，一种是填入手机号通过短信验证码注册 mobilePhone，一种是填入邮箱注册 email，另一种是直接不验证手机和邮箱直接注册normal

    registerForm: FormGroup
    registerFormWithMobilePhone: FormGroup
    registerFormWithMobilePhoneStep2: FormGroup
    ignoreDirty: boolean = false

    imageSrcCaptcha : string = apiPath.getSignUpCaptcha

    sendSMSFirstTime: boolean = true
    sendSMSTimer: number = 0
    registerProcessStep: string = 'step1'



    constructor(
        @Inject('moduleType') public pageType: string,
        private fb: FormBuilder,
        public userService: UserLoginService,
        private httpService: HttpService
    ) {

    }

    ngOnInit(): void {
        this.createRegisterForm()
    }

    registerFormError : any = {}
    registerFormValidationMessages: any = {
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
        },
        'password2'  : {
            'required'      : '请填写确认密码!',
            'minlength'     : '确认密码长度6-30个字符!',
            'maxlength'     : '确认密码长度6-30个字符!',
            'mismatched'    : '确认密码输入不一致!'
        },
        'captcha'  : {
            'required'      : '请填写验证码!',
            'minlength'     : '验证码长度4-4个字符!',
            'maxlength'     : '验证码长度4-4个字符!',
            'wrong'         : '验证码错误!'
        },
        'smsCode'  : {
            'required'      : '请填写短信验证码!',
            'minlength'     : '验证码长度6-6个字符!',
            'maxlength'     : '验证码长度6-6个字符!',
            'wrong'         : '验证码错误!'
        }
    }

    registerFormInputChange(formInputData : any, ignoreDirty : boolean = false) {
        this.registerFormError = formErrorHandler(formInputData, this.registerForm, this.registerFormValidationMessages, ignoreDirty)
    }
    registerFormInputChangeWithMobilePhone(formInputData : any, ignoreDirty : boolean = false) {
        this.registerFormError = formErrorHandler(formInputData, this.registerFormWithMobilePhone, this.registerFormValidationMessages, ignoreDirty)
    }
    registerFormInputChangeWithMobilePhoneStep2(formInputData : any, ignoreDirty : boolean = false) {
        this.registerFormError = formErrorHandler(formInputData, this.registerFormWithMobilePhoneStep2, this.registerFormValidationMessages, ignoreDirty)
    }

    createRegisterForm(): void {

        this.registerForm = this.fb.group({
            'username'    : ['', [Validators.required, Validators.minLength(4), Validators.maxLength(30), Validators.pattern(/^[a-zA-Z][a-zA-Z0-9_]*$/)], [checkFieldIsExist(apiPath.signUpCheckUsername)] ],
            'mobilePhone' : ['', [Validators.required, isMobilePhone() ], [checkFieldIsExist(apiPath.signUpCheckMobilePhone, 'mobilePhone')]],
            'password'    : ['', [Validators.required, Validators.minLength(6), Validators.maxLength(30)] ],
            'password2'   : ['', [Validators.required, Validators.minLength(6), Validators.maxLength(30), isMatched('password')] ],
            'captcha'     : ['', [Validators.required, Validators.minLength(4), Validators.maxLength(4)], [checkFieldIsExist(apiPath.signUpCheckCaptcha, 'captcha')] ]
        } )
        this.registerForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.registerFormInputChange(data)
        })


        this.registerFormWithMobilePhone = this.fb.group({
            'mobilePhone' : ['', [Validators.required, isMobilePhone() ], [checkFieldIsExist(apiPath.signUpCheckMobilePhone, 'mobilePhone')]],
            'captcha'     : ['', [Validators.required, Validators.minLength(4), Validators.maxLength(4)], [checkFieldIsExist(apiPath.signUpCheckCaptcha, 'captcha')] ]
        } )
        this.registerFormWithMobilePhone.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.registerFormInputChangeWithMobilePhone(data)
        })

        this.registerFormWithMobilePhoneStep2 = this.fb.group({
            'mobilePhone' : ['', [Validators.required, isMobilePhone() ], [checkFieldIsExist(apiPath.signUpCheckMobilePhone, 'mobilePhone')]],
            'smsCode'     : ['', [Validators.required, Validators.minLength(6), Validators.maxLength(6)], [checkFieldIsExist(apiPath.signUpCheckSMSCode, 'smsCode', 'mobilePhone')] ],
            'username'    : ['', [Validators.required, Validators.minLength(4), Validators.maxLength(30), Validators.pattern(/^[a-zA-Z][a-zA-Z0-9_]*$/)], [checkFieldIsExist(apiPath.signUpCheckUsername)] ],
            'password'    : ['', [Validators.required, Validators.minLength(6), Validators.maxLength(30)] ],
            'password2'   : ['', [Validators.required, Validators.minLength(6), Validators.maxLength(30), isMatched('password')] ]
        } )
        this.registerFormWithMobilePhoneStep2.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.registerFormInputChangeWithMobilePhoneStep2(data)
        })
    }


    registerFormSubmit() {

        if (this.registerForm.invalid) {
            this.registerFormInputChange(this.registerForm.value, true)
            this.ignoreDirty = true
            return
        }

        this.userService.registerNewUser(this.registerForm.value).subscribe(
            data => {
                console.log('注册成功: ', data)
                this.httpService.successHandler(data)
            },
            error => {this.httpService.errorHandler(error) }
        )
    }


    registerFormWithMobilePhoneSubmitStep1() {
        console.log('error : ', this.registerFormWithMobilePhone.invalid, this.registerFormWithMobilePhone.errors)


        if (this.registerFormWithMobilePhone.invalid) {
            this.registerFormInputChangeWithMobilePhone(this.registerFormWithMobilePhone.value, true)
            this.ignoreDirty = true
            return
        }

        this.userService.registerSendSMS(this.registerFormWithMobilePhone.value.mobilePhone).subscribe(
            data => {
                console.log('发送短信成功: ', data)
                this.httpService.successHandler(data, '发送短信成功!')

                this.registerProcessStep = 'step2'
                this.registerFormWithMobilePhoneStep2.patchValue({ mobilePhone : this.registerFormWithMobilePhone.value.mobilePhone})


                this.sendSMSFirstTime = false
                this.sendSMSTimer = 90

                const intervalTimer : any  = setInterval( () => {
                    this.sendSMSTimer = this.sendSMSTimer - 1

                    if ( this.sendSMSTimer === 0) {
                        clearInterval(intervalTimer)

                        this.registerFormWithMobilePhoneStep2.patchValue({ smsCode : ''})
                        this.changeCaptchaImage()
                    }

                }, 1000 )
            },
            error => {this.httpService.errorHandler(error) }
        )
    }


    registerFormWithMobilePhoneSubmitStep2() {

        console.log('registerFormOnSubmit', this.registerForm.value)
        if (this.registerFormWithMobilePhoneStep2.invalid) {
            this.registerFormInputChangeWithMobilePhoneStep2(this.registerFormWithMobilePhoneStep2.value, true)
            this.ignoreDirty = true
            return
        }

        this.userService.registerNewUserByMobile(this.registerFormWithMobilePhoneStep2.value).subscribe(
            data => {
                console.log('注册成功: ', data)
                this.httpService.successHandler(data)
            },
            error => {this.httpService.errorHandler(error) }
        )
    }


    // 点击图片更换验证码
    changeCaptchaImage() {

        this.imageSrcCaptcha = apiPath.getSignUpCaptcha + '?' + new Date().getTime().toString()

    }


}
