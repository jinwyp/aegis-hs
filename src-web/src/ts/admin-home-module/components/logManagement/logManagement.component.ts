import {Component, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'
import { ActivatedRoute } from '@angular/router'

import { HttpService } from '../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../bs-form-module/validators/validator'
import { UserInfoService } from '../../../services/userInfo.service'
import { HSUserService } from '../../../services/hsUser.service'
import { HSOrderService } from '../../../services/hsOrder.service'

import {getEnum} from '../../../services/localStorage'





@Component({
  selector: 'app-log',
  templateUrl: './logManagement.component.html',
  styleUrls: ['./logManagement.component.css']
})
export class LogManagementComponent implements OnInit {

    sessionUser : any
    currentOrderId : any

    logSearchForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    logList : any[] = []

    pagination: any = {
        pageSize : 20,
        pageNo : 1,
        total : 1
    }


    constructor(
        private route: ActivatedRoute,
        private httpService: HttpService,
        private fb: FormBuilder,
        private userService: UserInfoService,
        private hsUserService: HSUserService,
        private hsOrderService: HSOrderService

    ) {

    }



    ngOnInit(): void {

        this.createLogSearchForm()
        this.getSessionUserInfo()
        this.getLogList()

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

    getLogList () {

        let query : any = {
            pageSize: this.pagination.pageSize,
            pageNo: this.pagination.pageNo
        }

        query = (<any>Object).assign(query, this.logSearchForm.value)

        console.log('query: ', query)
        this.hsOrderService.getLogList( query).subscribe(
            data => {
                this.logList = data.data.results

                this.pagination.pageSize = data.data.pageSize
                this.pagination.pageNo = data.data.pageNo
                this.pagination.total = data.data.totalRecord

            },
            error => {this.httpService.errorHandler(error) }
        )
    }



    createLogSearchForm(): void {

        this.logSearchForm = this.fb.group({
            'orderId'    : ['' ]
        } )
    }


    orderFormError : any = {}
    orderOtherPartyFormError : any = {}
    orderFormValidationMessages: any = {
        'deptId'  : {
            'required'      : '请填写事业部门!'
        },
        'teamId'  : {
            'required'      : '请选择团队!'
        },
        'line'  : {
            'required'      : '请选择相关公司完成业务线名称!'
        },

        'cargoType'  : {
            'required'      : '请选择货物种类!'
        },
        'upstreamSettleMode'  : {
            'required'      : '请选择结算方式!'
        },
        'downstreamSettleMode'  : {
            'required'      : '请选择结算方式!'
        },
        'mainAccounting'  : {
            'required'      : '请选择公司!'
        },
        'upstreamId'  : {
            'required'      : '请选择公司!'
        },
        'downstreamId'  : {
            'required'      : '请选择公司!'
        },

        'custType'  : {
            'required'      : '请选择客户类型!'
        },
        'customerId'  : {
            'required'      : '请选择公司!'
        },
        'position'  : {
            'required'      : '请选择位置!'
        }
    }



}

