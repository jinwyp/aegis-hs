import {Component, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'

import { HttpService } from '../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../bs-form-module/validators/validator'
import { UserInfoService } from '../../../services/userInfo.service'
import { HSUserService } from '../../../services/hsUser.service'




@Component({
  selector: 'app-user-management',
  templateUrl: './userManagement.component.html',
  styleUrls: ['./userManagement.component.css']
})
export class UserManagementComponent implements OnInit {

    sessionUser : any
    currentUserId : any

    userForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    userList : any[] = []
    departmentList : any[] = []

    dataIsAdmin : any [] = [
        { id : 2 , name : '是'},
        { id : 1 , name : '否'}
    ]

    pagination: any = {
        pageSize : 10,
        pageNo : 1,
        total : 1
    }


    constructor(
        private httpService: HttpService,
        private fb: FormBuilder,
        private userService: UserInfoService,
        private hsUserService: HSUserService

    ) {

    }



    ngOnInit(): void {
        this.getDepartmentList()
        this.getSessionUserInfo()
        this.createUserForm()
        this.getUserList()
    }

    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }

    getSessionUserInfo () {
        this.userService.sessionUserInfo().subscribe(
            data => {
                if (data) {
                    this.sessionUser = data
                }
                // console.log('当前登陆的用户信息: ', data)
            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    getUserList () {

        const query : any = {
            pageSize: this.pagination.pageSize,
            pageNo: this.pagination.pageNo
        }


        this.hsUserService.getUserList(query).subscribe(
            data => {
                this.userList = data.data.results

                // this.pagination.total = data.meta.total
                // this.pagination.pageNo = data.meta.pageNo

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


    userFormError : any = {}
    userFormValidationMessages: any = {
        'phone'  : {
            'required'      : '请填写手机号!',
            'mobilePhone' : '手机号格式不正确!',
            'isExist'     : '手机号已经存在!'
        },
        'password'  : {
            'required'      : '请填写密码!'
        },
        'username'  : {
            'required'      : '请填写姓名!'
        },
        'deptId'  : {
            'required'      : '请填写事业部门!'
        },
        'isAdmin' : {
            'required'    : '必填项!'
        },
        'isActive' : {
            'required'    : '必填项!'
        }
    }

    userFormInputChange(formInputData : any) {
        this.userFormError = formErrorHandler(formInputData, this.userForm, this.userFormValidationMessages)
    }

    createUserForm(user: any = {}): void {

        this.userForm = this.fb.group({
            'phone'    : ['', [Validators.required, isMobilePhone()] ],
            'password'    : ['', [Validators.required] ],
            'username'    : ['', [Validators.required] ],
            'deptId'    : ['', [Validators.required ] ],
            'isAdmin'    : [1, [Validators.required] ],
            'isActive'    : [2, [Validators.required ]]
        } )

        this.userForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.userFormInputChange(data)
        })
    }


    userFormSubmit() {

        if (this.userForm.invalid) {
            this.userFormInputChange(this.userForm.value)
            this.ignoreDirty = true

            console.log('当前信息: ', this.userForm, this.userFormError)
            return
        }

        const postData = this.userForm.value

        if (postData.isAdmin === 2) {
            postData.isAdmin = true
        } else {
            postData.isAdmin = false
        }

        if (postData.isActive === 2) {
            postData.isActive = true
        } else {
            postData.isActive = false
        }

        if (this.isAddNew) {
            this.hsUserService.createNewUser(postData).subscribe(
                data => {
                    console.log('保存用户地址成功: ', data)
                    this.httpService.successHandler(data)

                    this.getUserList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            this.hsUserService.modifyUser(this.currentUserId, postData).subscribe(
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


    showForm(isAddNew : boolean = true, user?: any ) {

        if (isAddNew) {
            this.isAddNew = true

            this.userForm.patchValue({
                'phone'    : '',
                'password'    : '',
                'username'    : '',
                'deptId'    : '',
                'isAdmin'    : 1,
                'isActive'    : 2
            })

        } else {
            this.isAddNew = false
            this.currentUserId = user.id

            if (user.isAdmin === true) {
                user.isAdmin = 2
            } else {
                user.isAdmin = 1
            }

            if (user.isActive === true) {
                user.isActive = 2
            } else {
                user.isActive = 1
            }
            this.userForm.patchValue(user)
        }


        this.isShowForm = !this.isShowForm
    }



}
