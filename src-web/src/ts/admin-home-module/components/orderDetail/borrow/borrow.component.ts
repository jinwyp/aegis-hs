import {Component, Input, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'



import { HttpService } from '../../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../../bs-form-module/validators/validator'

import { HSUserService } from '../../../../services/hsUser.service'
import { HSOrderService } from '../../../../services/hsOrder.service'


import {getEnum} from '../../../../services/localStorage'


@Component({
  selector: 'app-borrow',
  templateUrl: './borrow.component.html',
  styleUrls: ['./borrow.component.css']
})
export class BorrowComponent implements OnInit {

    @Input() currentOrder : any
    @Input() businessType : string
    currentBorrowId : number = 0

    borrowForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    borrowList : any[] = []
    partyList : any[] = []

    unitList : any[] = []

    purposeList : any[] = getEnum('PaymentPurpose')
    payModeList : any[] = getEnum('PayMode')


    pagination: any = {
        pageSize : 20,
        pageNo : 1,
        total : 1
    }



    constructor(
        private httpService: HttpService,
        private fb: FormBuilder,
        private hsUserService: HSUserService,
        private hsOrderService: HSOrderService

    ) {

    }



    ngOnInit(): void {

        this.getPartyList()
        this.getBorrowList()
        this.createBorrowForm()

        if (this.currentOrder) {
            if (Array.isArray(this.currentOrder.orderConfigList)) {

                const tempArray = []
                this.currentOrder.orderConfigList.forEach( unit => {
                    unit.name = unit.hsMonth
                    tempArray.push(unit)
                })

                this.unitList = tempArray
            }
        }
    }


    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }


    getBorrowList () {
        this.hsOrderService.getBorrowListByID(this.currentOrder.id).subscribe(
            data => {
                this.borrowList = data.data.results

            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    getPartyList () {

        this.hsUserService.getPartyList().subscribe(
            data => {
                this.partyList = data.data.results

            },
            error => {this.httpService.errorHandler(error) }
        )
    }



    borrowFormError : any = {}
    borrowFormValidationMessages: any = {
        'hsId'  : {
            'required'      : '请选择核算月!'
        },
        'jiekuanDate'  : {
            'required'      : '请填写借款日期!'
        },
        'amount'  : {
            'required'      : '请填写借款金额!'
        },
        'capitalId'  : {
            'required'      : '请填写资金方!'
        }
    }

    borrowFormInputChange(formInputData : any) {
        this.borrowFormError = formErrorHandler(formInputData, this.borrowForm, this.borrowFormValidationMessages)
    }

    createBorrowForm(): void {

        this.borrowForm = this.fb.group({
            'hsId'    : ['', [Validators.required ] ],
            'jiekuanDate'    : [null, [Validators.required ] ],
            'amount'    : ['', [Validators.required ] ],

            'capitalId'    : ['', [Validators.required ] ],
            'useInterest'    : ['', [] ],
            'useDays'    : ['', [ ] ]
        } )

        this.borrowForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.borrowFormInputChange(data)
        })
    }





    borrowFormSubmit() {

        if (this.borrowForm.invalid) {
            this.borrowFormInputChange(this.borrowForm.value)
            this.ignoreDirty = true

            console.log('当前信息: ', this.borrowForm, this.borrowFormError)
            return
        }

        const postData = this.borrowForm.value
        postData.orderId = this.currentOrder.id


        if (this.isAddNew) {
            this.hsOrderService.createNewBorrow(this.currentOrder.id, postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getBorrowList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            postData.id = this.currentBorrowId
            delete postData.payDate
            delete postData.payAmount

            this.hsOrderService.modifyBorrow(this.currentOrder.id, this.currentBorrowId, postData).subscribe(
                data => {
                    console.log('修改成功: ', data)
                    this.httpService.successHandler(data)

                    this.getBorrowList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }
    }


    showForm(isAddNew : boolean = true, borrowOrder?: any ) {

        if (isAddNew) {
            this.isAddNew = true
            this.currentBorrowId = 0

            this.borrowForm.patchValue({
                'hsId'    : '',
                'jiekuanDate'    : null,
                'amount'    : '',

                'capitalId'    : '',
                'useInterest'    : '',
                'useDays'    : ''
            })

        } else {
            this.isAddNew = false
            this.currentBorrowId = borrowOrder.id

            this.borrowForm.patchValue(borrowOrder)
        }

        this.isShowForm = !this.isShowForm
    }


    deleteItem (borrow : any) {

        this.hsOrderService.delBorrow(this.currentOrder.id, borrow.id).subscribe(
            data => {
                console.log('保存成功: ', data)
                this.httpService.successHandler(data)

                this.getBorrowList()
            },
            error => {this.httpService.errorHandler(error) }
        )
    }


}
