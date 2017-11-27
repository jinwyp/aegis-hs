import {Component, Input, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'



import { HttpService } from '../../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../../bs-form-module/validators/validator'

import { HSUserService } from '../../../../services/hsUser.service'
import { HSOrderService } from '../../../../services/hsOrder.service'


import {getEnum} from '../../../../services/localStorage'


// 还款
@Component({
  selector: 'app-huankuan',
  templateUrl: './repaymentHuanKuan.component.html',
  styleUrls: ['./repaymentHuanKuan.component.css']
})
export class RepaymentHuanKuanComponent implements OnInit {

    @Input() currentOrder : any
    @Input() businessType : string
    @Input() party : any = {}

    currentHuanKuanId : number = 1

    repaymentHKForm: FormGroup
    borrowForm: FormGroup
    repaymentHKSearchForm: FormGroup


    ignoreDirty: boolean = false
    ignoreDirtyBorrow: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    repaymentHKList : any[] = []

    unitList : any[] = []
    borrowDropDownList : any[] = []
    borrowPostList : any[] = []
    borrowListObject : any = {}

    totalPrincipal : number = 0
    totalInterest : number = 0
    totalFee : number = 0



    repaymentTypeList : any[] = [
        {id: false , name : '客户还服务费'},
        {id: true , name : 'CCS还服务费'}
    ]

    promiseStatusList : any[] = [
        {id: false , name : '未还款'},
        {id: true , name : '已还款'}
    ]

    pagination: any = {
        pageSize : 10000,
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

        this.createRepaymentHKSearchForm()
        this.createHKForm()
        this.createBorrowForm()


        this.getOrderUnitList()
        this.getRepaymentHKList()
        this.getBorrowList()



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
        let query : any = {
            pageSize: this.pagination.pageSize,
            pageNo: this.pagination.pageNo
        }

        query = (<any>Object).assign(query, this.repaymentHKSearchForm.value)

        console.log('Query: ', query)

        this.hsOrderService.getRepaymentHKListByID(this.businessType, this.currentOrder.id, query).subscribe(
            data => {
                this.repaymentHKList = data.data.results

                if (Array.isArray(data.data.results)) {
                    this.totalPrincipal = 0
                    this.totalInterest = 0
                    this.totalFee = 0

                    data.data.results.forEach( (repayment) => {
                        if (Array.isArray(repayment.huankuanMapList)) {

                            repayment.huankuanMapList.forEach( (borrow) => {

                                this.totalPrincipal =  this.totalPrincipal + borrow.principal
                                this.totalInterest =  this.totalInterest + borrow.interest
                                this.totalFee =  this.totalFee + borrow.fee

                            })
                        }
                    })
                }

            },
            error => {this.httpService.errorHandler(error) }
        )
    }



    getBorrowList () {
        this.hsOrderService.getBorrowListUnfinishedByID(this.businessType, this.currentOrder.id).subscribe(
            data => {

                if (Array.isArray(data.data)) {
                    this.borrowDropDownList = []
                    data.data.forEach( (borrow) => {
                        if (borrow) {
                            this.borrowDropDownList.push ({
                                id : borrow.id,
                                name : 'ID:' + borrow.id + ' 借款日期:' + borrow.jiekuanDate.slice(0, 10) + ' 资金方:' + this.party.object[borrow.capitalId].name + ' | 借款金额:' + borrow.amount + ' 已还金额:' + borrow.huankuanTotal
                            })
                        }

                        this.borrowListObject[borrow.id] = borrow
                    })
                }

            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    getOrderUnitList () {
        this.hsOrderService.getOrderUnitListByID(this.businessType, this.currentOrder.id).subscribe(
            data => {
                this.unitList = data.data.results

                if (Array.isArray(data.data.results)) {

                    const tempArray = []
                    data.data.results.forEach( unit => {
                        unit.name = unit.hsMonth
                        tempArray.push(unit)
                    })

                    this.unitList = tempArray
                }
            },
            error => {this.httpService.errorHandler(error) }
        )
    }


    createRepaymentHKSearchForm(): void {

        this.repaymentHKSearchForm = this.fb.group({
            'hsId'    : ['' ],
            'huankuanDateStart'    : [null ],
            'huankuanDateEnd'    : [null ],
            'promise'    : ['' ]
        } )
    }


    repaymentHKFormError : any = {}
    borrowFormError : any = {}
    repaymentHKFormValidationMessages: any = {
        'hsId'  : {
            'required'      : '请选择核算月!'
        },
        'promise'  : {
            'required'      : '请填写状态!'
        },
        'huankuanDate'  : {
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
        },
        'ccsPay'  : {
            'required'      : '请选择类型!'
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
            'promise'    : ['', [Validators.required ] ],
            // 'skCompanyId'    : ['', [Validators.required ] ],
            'huankuanDate'    : [null, [Validators.required ] ]
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
            'fee'    : ['', [Validators.required ] ],
            'ccsPay'    : ['', [Validators.required ] ]
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
            return { jiekuanId : borrow.jiekuanId, principal : Number(borrow.principal), interest : Number(borrow.interest), fee : Number(borrow.fee), ccsPay : borrow.ccsPay }
        })

        if (this.isAddNew) {
            this.hsOrderService.createNewRepaymentHK(this.businessType, this.currentOrder.id, postData).subscribe(
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

            this.hsOrderService.modifyRepaymentHK(this.businessType, this.currentOrder.id, this.currentHuanKuanId, postData).subscribe(
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
        this.borrowPostList = []

        this.getBorrowList()

        if (isAddNew) {
            this.isAddNew = true
            this.currentHuanKuanId = 0

            this.repaymentHKForm.patchValue({
                'hsId'    : '',
                'promise'    : '',
                'huankuanDate'    : null
            })


        } else {
            this.isAddNew = false
            this.currentHuanKuanId = repaymentHKOrder.id

            const tempArray : any[] = []

            if (Array.isArray(repaymentHKOrder.huankuanMapList)) {
                repaymentHKOrder.huankuanMapList.forEach( item => {
                    tempArray.push((<any>Object).assign( {}, this.borrowListObject[item.jiekuanId],
                        {jiekuanId : item.jiekuanId, principal : item.principal, interest : item.interest, fee : item.fee, ccsPay : item.ccsPay}) )
                })
            }
            this.borrowPostList = tempArray
            this.repaymentHKForm.patchValue(repaymentHKOrder)
        }


        this.isShowForm = !this.isShowForm
    }


    deleteItem (repaymentHK : any) {

        this.hsOrderService.delRepaymentHK(this.businessType, this.currentOrder.id, repaymentHK.id).subscribe(
            data => {
                console.log('保存成功: ', data)
                this.httpService.successHandler(data)

                this.getRepaymentHKList()
            },
            error => {this.httpService.errorHandler(error) }
        )
    }


    createNewBorrow () {
        if (this.borrowForm.invalid) {

            this.borrowFormInputChange(this.borrowForm.value)
            this.ignoreDirtyBorrow = true

            // console.log('this.ignoreDirtyBorrow2', this.ignoreDirtyBorrow, this.borrowForm.value)

            return
        }

        this.borrowPostList.push(
            (<any>Object).assign(
                {}, this.borrowListObject[this.borrowForm.value.jiekuanId],
                {
                    jiekuanId : this.borrowForm.value.jiekuanId,
                    principal : this.borrowForm.value.principal,
                    interest : this.borrowForm.value.interest,
                    fee : this.borrowForm.value.fee,
                    ccsPay : this.borrowForm.value.ccsPay
                }
            )
        )

        this.borrowForm.reset({
            'jiekuanId'    : '',
            'principal'    : '',
            'interest'    : '',
            'fee'    : '',
            'ccsPay'    : ''
        })

        this.ignoreDirtyBorrow = false
    }

    delBorrow (borrow: any) {
        const index = this.borrowPostList.indexOf(borrow)
        this.borrowPostList.splice(index, 1)
    }

}

