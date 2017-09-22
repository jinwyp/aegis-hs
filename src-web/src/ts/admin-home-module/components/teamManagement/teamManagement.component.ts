import {Component, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'

import { HttpService } from '../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../bs-form-module/validators/validator'
import { UserInfoService } from '../../../services/userInfo.service'
import { HSUserService } from '../../../services/hsUser.service'




@Component({
  selector: 'app-team-management',
  templateUrl: './teamManagement.component.html',
  styleUrls: ['./teamManagement.component.css']
})
export class TeamManagementComponent implements OnInit {

    sessionUser : any
    currentTeamId : any

    teamForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    teamList : any[] = []
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
        this.createteamForm()
        this.getTeamList()
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

    getTeamList () {

        const query : any = {
            pageSize: this.pagination.pageSize,
            pageNo: this.pagination.pageNo
        }

        this.hsUserService.getTeamList(query).subscribe(
            data => {
                this.teamList = data.data.results

                this.pagination.pageSize = data.data.pageSize
                this.pagination.pageNo = data.data.pageNo
                this.pagination.total = data.data.totalRecord

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


    teamFormError : any = {}
    teamFormValidationMessages: any = {
        'name'  : {
            'required'      : '请填写名称!'
        },
        'deptId'  : {
            'required'      : '请选择事业部门!'
        }
    }

    teamFormInputChange(formInputData : any, ignoreDirty : boolean = false) {
        this.teamFormError = formErrorHandler(formInputData, this.teamForm, this.teamFormValidationMessages, ignoreDirty)
    }

    createteamForm(user: any = {}): void {

        this.teamForm = this.fb.group({
            'name'    : ['', [Validators.required] ],
            'deptId'    : ['', [Validators.required ] ]
        } )

        this.teamForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.teamFormInputChange(data)
        })
    }


    teamFormSubmit() {

        if (this.teamForm.invalid) {
            this.teamFormInputChange(this.teamForm.value, true)
            this.ignoreDirty = true

            console.log('当前信息: ', this.teamForm, this.teamFormError)
            return
        }

        const postData = this.teamForm.value

        if (this.isAddNew) {
            this.hsUserService.createNewTeam(postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getTeamList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            this.hsUserService.modifyTeam(this.currentTeamId, postData).subscribe(
                data => {
                    console.log('修改成功: ', data)
                    this.httpService.successHandler(data)

                    this.getTeamList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }


    showForm(isAddNew : boolean = true, user?: any ) {

        if (isAddNew) {
            this.isAddNew = true

            this.teamForm.patchValue({
                'name'    : '',
                'deptId'    : ''
            })

        } else {
            this.isAddNew = false
            this.currentTeamId = user.id

            this.teamForm.patchValue(user)
        }


        this.isShowForm = !this.isShowForm
    }



}
