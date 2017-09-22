import {Component, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'

import { HttpService } from '../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../bs-form-module/validators/validator'
import { UserInfoService } from '../../../services/userInfo.service'
import { HSUserService } from '../../../services/hsUser.service'




@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent implements OnInit {

    sessionUser : any
    currentTeamId : any

    orderForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    orderList : any[] = []

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
        this.getOrderList()
        this.getSessionUserInfo()
        this.createOrderForm()
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

    getOrderList () {

        const query : any = {
            pageSize: this.pagination.pageSize,
            pageNo: this.pagination.pageNo
        }

        this.hsUserService.getTeamList(query).subscribe(
            data => {
                this.orderList = data.data.results

                this.pagination.pageSize = data.data.pageSize
                this.pagination.pageNo = data.data.pageNo
                this.pagination.total = data.data.totalRecord

            },
            error => {this.httpService.errorHandler(error) }
        )
    }



    orderFormError : any = {}
    orderFormValidationMessages: any = {
        'name'  : {
            'required'      : '请填写名称!'
        },
        'deptId'  : {
            'required'      : '请选择事业部门!'
        }
    }

    orderFormInputChange(formInputData : any, ignoreDirty : boolean = false) {
        this.orderFormError = formErrorHandler(formInputData, this.orderForm, this.orderFormValidationMessages, ignoreDirty)
    }

    createOrderForm(): void {

        this.orderForm = this.fb.group({
            'name'    : ['', [Validators.required] ],
            'deptId'    : ['', [Validators.required ] ]
        } )

        this.orderForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.orderFormInputChange(data)
        })
    }


    orderFormSubmit() {

        if (this.orderForm.invalid) {
            this.orderFormInputChange(this.orderForm.value, true)
            this.ignoreDirty = true

            console.log('当前信息: ', this.orderForm, this.orderFormError)
            return
        }

        const postData = this.orderForm.value

        if (this.isAddNew) {
            this.hsUserService.createNewTeam(postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getOrderList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            this.hsUserService.modifyTeam(this.currentTeamId, postData).subscribe(
                data => {
                    console.log('修改成功: ', data)
                    this.httpService.successHandler(data)

                    this.getOrderList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }


    showForm(isAddNew : boolean = true, team?: any ) {

        if (isAddNew) {
            this.isAddNew = true

            this.orderForm.patchValue({
                'name'    : '',
                'deptId'    : ''
            })

        } else {
            this.isAddNew = false
            this.currentTeamId = team.id

            this.orderForm.patchValue(team)
        }


        this.isShowForm = !this.isShowForm
    }



}
