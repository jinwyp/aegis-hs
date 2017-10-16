import {Component, Input, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'



import { HttpService } from '../../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../../bs-form-module/validators/validator'

import { HSUserService } from '../../../../services/hsUser.service'
import { HSOrderService } from '../../../../services/hsOrder.service'


import {getEnum} from '../../../../services/localStorage'


@Component({
  selector: 'app-huankuan',
  templateUrl: './repaymentHuanKuan.component.html',
  styleUrls: ['./repaymentHuanKuan.component.css']
})
export class RepaymentHuanKuanComponent implements OnInit {

    @Input() currentOrder : any
    currentHuanKuanId : number = 1

    repaymentHKForm: FormGroup
    borrowForm: FormGroup

    ignoreDirty: boolean = false
    ignoreDirtyBorrow: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    shippingList : any[] = []
    partyList : any[] = []
    partyListObject : any = {}

    unitList : any[] = []
    borrowDropDownList : any[] = []
    borrowPostList : any[] = []
    borrowListObject : any = {}


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
        this.getRepaymentHKList()

        this.createHKForm()
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


    getRepaymentHKList () {
        this.hsOrderService.getRepaymentHKListByID(this.currentOrder.id).subscribe(
            data => {
                this.shippingList = data.data.results

            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    getPartyList () {

        this.hsUserService.getPartyList().subscribe(
            data => {
                this.partyList = data.data.results

                if (Array.isArray(data.data.results)) {
                    data.data.results.forEach( (company) => {
                        this.partyListObject[company.id] = company
                    })
                }

                this.getBorrowList()

            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    getBorrowList () {
        this.hsOrderService.getBorrowListByID(this.currentOrder.id).subscribe(
            data => {

                if (Array.isArray(data.data.results)) {
                    data.data.results.forEach( (borrow) => {
                        if (borrow) {
                            this.borrowDropDownList.push ({
                                id : borrow.id,
                                name : 'ID:' + borrow.id + ' 日期:' + borrow.jiekuanDate + ' 资金方:' + this.partyListObject[borrow.capitalId].name + ' 金额:' + borrow.amount
                            })
                        }

                        this.borrowListObject[borrow.id] = borrow
                    })
                }

            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    repaymentHKFormError : any = {}
    borrowFormError : any = {}
    repaymentHKFormValidationMessages: any = {
        'hsId'  : {
            'required'      : '请选择核算月!'
        },
        'skCompanyId'  : {
            'required'      : '请填写资金方!'
        },
        'huankuankDate'  : {
            'required'      : '请填写还款日期!'
        },
        'huankuanPrincipal'  : {
            'required'      : '请填写还款总额!'
        },
        'huankuanInterest'  : {
            'required'      : '请填写还款利息!'
        },
        'huankuanFee'  : {
            'required'      : '请填写还款服务费!'
        },


        'jiekuanId'  : {
            'required'      : '请选择借款!'
        },
        'principal'  : {
            'required'      : '请填写本金!'
        },
        'interest'  : {
            'required'      : '请填写利息!'
        },
        'fee'  : {
            'required'      : '请填写服务费!'
        }
    }


    repaymentHKFormInputChange(formInputData : any) {
        this.repaymentHKFormError = formErrorHandler(formInputData, this.repaymentHKForm, this.repaymentHKFormValidationMessages)
    }
    borrowFormInputChange(formInputData : any) {
        this.borrowFormError = formErrorHandler(formInputData, this.borrowForm, this.repaymentHKFormValidationMessages)
    }

    createHKForm(): void {

        this.repaymentHKForm = this.fb.group({
            'hsId'    : ['', [Validators.required ] ],
            'skCompanyId'    : ['', [Validators.required ] ],
            'huankuankDate'    : [null, [Validators.required ] ]
            // 'huankuanPrincipal'    : ['', [Validators.required ] ],
            // 'huankuanInterest'    : ['', [Validators.required ] ],
            // 'huankuanFee'    : ['', [Validators.required ] ]
        } )


        this.repaymentHKForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.repaymentHKFormInputChange(data)
        })
    }


    createBorrowForm(): void {

        this.borrowForm = this.fb.group({
            'jiekuanId'    : ['', [Validators.required ] ],
            'principal'    : ['', [Validators.required ] ],
            'interest'    : ['', [Validators.required ] ],
            'fee'    : ['', [Validators.required ] ]
        } )

        this.borrowForm.valueChanges.subscribe(data => {
            // console.log('this.ignoreDirtyBorrow', this.ignoreDirtyBorrow, data)
            this.ignoreDirtyBorrow = false
            this.borrowFormInputChange(data)
        })
    }



    repaymentHKFormSubmit() {

        if (this.repaymentHKForm.invalid) {
            this.ignoreDirty = true
            this.repaymentHKFormInputChange(this.repaymentHKForm.value)

            console.log('当前信息: ', this.repaymentHKForm, this.repaymentHKFormError)
            return
        }

        const postData = this.repaymentHKForm.value
        postData.orderId = this.currentOrder.id
        postData.huankuanMapList = this.borrowPostList.map( borrow => {
            return { jiekuanId : borrow.id, principal : Number(borrow.principal), interest : Number(borrow.interest), fee : Number(borrow.fee) }
        })

        if (this.isAddNew) {
            this.hsOrderService.createNewRepaymentHK(this.currentOrder.id, postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getRepaymentHKList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            postData.id = this.currentHuanKuanId
            delete postData.huankuanPrincipal

            this.hsOrderService.modifyRepaymentHK(this.currentOrder.id, this.currentHuanKuanId, postData).subscribe(
                data => {
                    console.log('修改成功: ', data)
                    this.httpService.successHandler(data)

                    this.getRepaymentHKList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }


    showForm(isAddNew : boolean = true, repaymentHKOrder?: any ) {

        this.ignoreDirty = false

        if (isAddNew) {
            this.isAddNew = true
            this.currentHuanKuanId = 0

            this.repaymentHKForm.patchValue({
                'hsId'    : '',
                'skCompanyId'    : '',
                'huankuankDate'    : null
            })


        } else {
            this.isAddNew = false
            this.currentHuanKuanId = repaymentHKOrder.id

            this.borrowPostList = []
            this.repaymentHKForm.patchValue(repaymentHKOrder)
        }


        this.isShowForm = !this.isShowForm
    }


    createNewBorrow () {
        if (this.borrowForm.invalid) {

            this.borrowFormInputChange(this.borrowForm.value)
            this.ignoreDirtyBorrow = true

            // console.log('this.ignoreDirtyBorrow2', this.ignoreDirtyBorrow, this.borrowForm.value)

            return
        }

        this.borrowPostList.push((<any>Object).assign( {}, this.borrowListObject[this.borrowForm.value.id],
            {jiekuanId : this.borrowForm.value.id, principal : this.borrowForm.value.principal, interest : this.borrowForm.value.interest, fee : this.borrowForm.value.fee}) )

        this.ignoreDirtyBorrow = false
    }

    delBorrow (borrow: any) {
        const index = this.borrowPostList.indexOf(borrow)
        this.borrowPostList.splice(index, 1)
    }

}

