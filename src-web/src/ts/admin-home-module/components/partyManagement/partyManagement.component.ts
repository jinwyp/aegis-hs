import { Component, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'

import { HttpService } from '../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../bs-form-module/validators/validator'
import { UserInfoService } from '../../../services/userInfo.service'
import { HSUserService } from '../../../services/hsUser.service'




@Component({
  selector: 'app-party-management',
  templateUrl: './partyManagement.component.html',
  styleUrls: ['./partyManagement.component.css']
})
export class PartyManagementComponent implements OnInit {

    sessionUser : any
    currentPartyId : any

    partyForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    partyList : any[] = []

    partyType : any [] = [
        { id : 1 , name : 'ccs账务公司'},
        { id : 2 , name : '资金方'},
        { id : 3 , name : '外部'},
        { id : 4 , name : '贸易商公司'}
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
        this.getSessionUserInfo()
        this.createPartyForm()
        this.getPartyList()
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

    getPartyList () {

        const query : any = {
            pageSize: this.pagination.pageSize,
            pageNo: this.pagination.pageNo
        }

        this.hsUserService.getPartyList(query).subscribe(
            data => {
                this.partyList = data.data.results

                this.pagination.pageSize = data.data.pageSize
                this.pagination.pageNo = data.data.pageNo
                this.pagination.total = data.data.totalRecord

            },
            error => {this.httpService.errorHandler(error) }
        )
    }


    partyFormError : any = {}
    partyFormValidationMessages: any = {
        'name'  : {
            'required'      : '请填写名称!'
        },
        'shortName'  : {
            'required'      : '请填写简称!'
        },
        'partyType'  : {
            'required'      : '请填写参与方类型!'
        }
    }

    partyFormInputChange(formInputData : any) {
        this.partyFormError = formErrorHandler(formInputData, this.partyForm, this.partyFormValidationMessages)
    }

    createPartyForm(user: any = {}): void {

        this.partyForm = this.fb.group({
            'name'    : ['', [Validators.required] ],
            'shortName'    : ['', [Validators.required] ],
            'partyType'    : ['', [Validators.required ] ]
        } )

        this.partyForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.partyFormInputChange(data)
        })
    }


    partyFormSubmit() {

        if (this.partyForm.invalid) {
            this.partyFormInputChange(this.partyForm.value)
            this.ignoreDirty = true

            console.log('当前信息: ', this.partyForm, this.partyFormError)
            return
        }

        const postData = this.partyForm.value

        if (this.isAddNew) {
            this.hsUserService.createNewParty(postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getPartyList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            this.hsUserService.modifyParty(this.currentPartyId, postData).subscribe(
                data => {
                    console.log('修改成功: ', data)
                    this.httpService.successHandler(data)

                    this.getPartyList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }


    showForm(isAddNew : boolean = true, party?: any ) {

        if (isAddNew) {
            this.isAddNew = true

            this.partyForm.patchValue({
                'name'    : '',
                'shortName'    : '',
                'partyType'    : ''
            })

        } else {
            this.isAddNew = false
            this.currentPartyId = party.id

            this.partyForm.patchValue(party)
        }


        this.isShowForm = !this.isShowForm
    }



}
