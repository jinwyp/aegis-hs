import {Component, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'

import { HttpService } from '../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../bs-form-module/validators/validator'
import { UserInfoService } from '../../../services/userInfo.service'
import { HSUserService } from '../../../services/hsUser.service'




@Component({
  selector: 'app-department-management',
  templateUrl: './departmentManagement.component.html',
  styleUrls: ['./departmentManagement.component.css']
})
export class DepartmentManagementComponent implements OnInit {

    sessionUser : any
    currentDepartmentId : any

    departmentForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    departmentList : any[] = []

    pagination: any = {
        pageSize : 20,
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


    getDepartmentList () {
        const query : any = {
            pageSize: this.pagination.pageSize,
            pageNo: this.pagination.pageNo
        }

        this.hsUserService.getDepartmentList(query).subscribe(
            data => {
                this.departmentList = data.data.results

                this.pagination.pageSize = data.data.pageSize
                this.pagination.pageNo = data.data.pageNo
                this.pagination.total = data.data.totalRecord

            },
            error => {this.httpService.errorHandler(error) }
        )
    }


    departmentFormError : any = {}
    departmentFormValidationMessages: any = {
        'name'  : {
            'required'      : '请填写部门名称!'
        }
    }

    departmentFormInputChange(formInputData : any) {
        this.departmentFormError = formErrorHandler(formInputData, this.departmentForm, this.departmentFormValidationMessages)
    }

    createUserForm(user: any = {}): void {

        this.departmentForm = this.fb.group({
            'name'    : ['', [Validators.required] ]
        } )

        this.departmentForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.departmentFormInputChange(data)
        })
    }


    departmentFormSubmit() {

        if (this.departmentForm.invalid) {
            this.departmentFormInputChange(this.departmentForm.value)
            this.ignoreDirty = true

            console.log('当前信息: ', this.departmentForm, this.departmentFormError)
            return
        }

        const postData = this.departmentForm.value

        if (this.isAddNew) {
            this.hsUserService.createNewDepartment(postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getDepartmentList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            this.hsUserService.modifyDepartment(this.currentDepartmentId, postData).subscribe(
                data => {
                    console.log('修改成功: ', data)
                    this.httpService.successHandler(data)

                    this.getDepartmentList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }


    showForm(isAddNew : boolean = true, department?: any ) {

        if (isAddNew) {
            this.isAddNew = true

            this.departmentForm.patchValue({
                'name'    : ''
            })

        } else {
            this.isAddNew = false
            this.currentDepartmentId = department.id

            this.departmentForm.patchValue(department)
        }


        this.isShowForm = !this.isShowForm
    }


    deleteItem (department : any) {

        this.hsUserService.delDepartment(department.id).subscribe(
            data => {
                console.log('保存成功: ', data)
                this.httpService.successHandler(data)

                this.getDepartmentList()
            },
            error => {this.httpService.errorHandler(error) }
        )

    }


}
