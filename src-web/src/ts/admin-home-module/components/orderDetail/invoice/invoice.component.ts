import {Component, Input, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'



import { HttpService } from '../../../../bs-form-module/services/http.service'

import { formErrorHandler, isMobilePhone, isMatched, checkFieldIsExist } from '../../../../bs-form-module/validators/validator'

import { HSUserService } from '../../../../services/hsUser.service'
import { HSOrderService } from '../../../../services/hsOrder.service'


import {getEnum} from '../../../../services/localStorage'


@Component({
    selector: 'app-invoice',
    templateUrl: './invoice.component.html',
    styleUrls: ['./invoice.component.css']
})
export class InvoiceComponent implements OnInit {

    @Input() currentOrder : any
    @Input() businessType : string
    @Input() party : any = {}

    currentInvoiceId : number = 1

    invoiceForm: FormGroup
    invoiceDetailForm: FormGroup
    invoiceSearchForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

    isShowDeletePrompt: boolean = false
    isShowDeletePromptIndex: number = -1

    invoiceList : any[] = []
    invoiceDetailList : any[] = []


    unitList : any[] = []

    billingCompany : any
    billToCompany : any
    invoiceDetailFormType : number = 1

    totalAllPriceAndTax : number = 0
    totalAllCargoAmount : number = 0

    invoiceDirectionList : any[] = getEnum('InvoiceDirection')
    invoiceTypeList : any[] = getEnum('InvoiceType')



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
        this.createInvoiceSearchForm()
        this.createInvoiceForm()
        this.createInvoiceDetailForm()

        this.getOrderUnitList()
        this.getInvoiceList()

    }


    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }


    getInvoiceList () {

        let query : any = {
            pageSize: this.pagination.pageSize,
            pageNo: this.pagination.pageNo
        }

        query = (<any>Object).assign(query, this.invoiceSearchForm.value)

        console.log('Query: ', query)

        this.hsOrderService.getInvoiceListByID(this.businessType, this.currentOrder.id, query).subscribe(
            data => {
                this.invoiceList = data.data.results

                if (Array.isArray(data.data.results)) {

                    this.totalAllPriceAndTax  = 0
                    this.totalAllCargoAmount  = 0

                    data.data.results.forEach( invoice => {
                        this.totalAllPriceAndTax  = this.totalAllPriceAndTax + invoice.totalPriceAndTax
                        this.totalAllCargoAmount  = this.totalAllCargoAmount + invoice.totalCargoAmount

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


    createInvoiceSearchForm(): void {

        this.invoiceSearchForm = this.fb.group({
            'hsId'    : ['' ],
            'invoiceDirection'    : ['' ],
            'invoiceType'    : ['' ],
            'openDateStart'    : [null ],
            'openDateEnd'    : [null ],
            'openCompanyId'    : ['' ],
            'receiverId'    : ['' ]
        } )
    }


    invoiceFormError : any = {}
    invoiceFormValidationMessages: any = {
        'hsId'  : {
            'required'      : '请选择核算月!'
        },

        'invoiceDirection'  : {
            'required'      : '请填写进项或销项!'
        },
        'invoiceType'  : {
            'required'      : '请填写发票类型!'
        },
        'openDate'  : {
            'required'      : '请填写开票日期!'
        },
        'openCompanyId'  : {
            'required'      : '请填写开票单位!'
        },
        'receiverId'  : {
            'required'      : '请填写收票单位!'
        }
    }

    invoiceFormInputChange(formInputData : any) {
        this.invoiceFormError = formErrorHandler(formInputData, this.invoiceForm, this.invoiceFormValidationMessages)
    }

    invoiceDetailFormError : any = {}
    invoiceDetailFormValidationMessages: any = {
        'invoiceNumber'  : {
            'required'      : '请填写发票号!'
        },
        'cargoAmount'  : {
            'required'      : '请填写发票对应的货物数量(吨)!'
        },
        'taxRate'  : {
            'required'      : '请填写税率!'
        },
        'priceAndTax'  : {
            'required'      : '请填写价税合计!',
            'wrong'      : '金额与税额相加不等于价税合计!'
        },


        'amount'  : {
            'required'      : '请填写金额!'
        },
        'taxAmount'  : {
            'required'      : '请填写税额!'
        },
        'sheetAmount'  : {
            'required'      : '请填写张数!'
        },
        'cargoType'  : {
            'required'      : '请填写货物种类!'
        }
    }
    invoiceDetailFormInputChange(formInputData : any) {
        this.invoiceDetailFormError = formErrorHandler(formInputData, this.invoiceDetailForm, this.invoiceDetailFormValidationMessages)
    }

    createInvoiceForm(): void {
        this.invoiceForm = this.fb.group({
            'hsId'    : ['', [Validators.required ] ],
            'invoiceDirection'    : ['', [Validators.required ] ],
            'invoiceType'    : ['', [Validators.required ] ],
            'openDate'    : [null, [Validators.required ] ],

            'openCompanyId'    : ['', [Validators.required ] ],
            'receiverId'    : ['', [Validators.required ] ]
        } )


        this.invoiceForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.invoiceFormInputChange(data)
        })
    }

    createInvoiceDetailForm(): void {

        this.invoiceDetailForm = this.fb.group({
            'invoiceNumber'    : ['' ],
            'cargoAmount'    : ['', [Validators.required ] ],
            'taxRate'        : ['', [Validators.required ] ],
            'priceAndTax'    : ['', [Validators.required ] ],

            'amount'    : ['' ],
            'taxAmount'    : ['' ],
            'sheetAmount'    : ['' ],
            'cargoType'    : ['' ]

        } )


        this.invoiceDetailForm.valueChanges.subscribe(data => {
            this.ignoreDirty = false
            this.invoiceDetailFormInputChange(data)
        })
    }




    invoiceFormSubmit() {

        if (this.invoiceForm.invalid) {
            this.invoiceFormInputChange(this.invoiceForm.value)
            this.ignoreDirty = true

            // console.log('当前信息: ', this.invoiceForm, this.invoiceFormError)
            return
        }

        const postData = this.invoiceForm.value
        postData.orderId = this.currentOrder.id

        postData.details = this.invoiceDetailList



        if (this.isAddNew) {
            this.hsOrderService.createNewInvoice(this.businessType, this.currentOrder.id, postData).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.httpService.successHandler(data)

                    this.getInvoiceList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        } else {
            postData.id = this.currentInvoiceId
            // delete postData.huikuanAmount

            if (Array.isArray(postData.details)) {
                postData.details.forEach( (detail) => {
                    delete detail.tsc
                })
            }

            this.hsOrderService.modifyInvoice(this.businessType, this.currentOrder.id, this.currentInvoiceId, postData).subscribe(
                data => {
                    console.log('修改成功: ', data)
                    this.httpService.successHandler(data)

                    this.getInvoiceList()
                    this.showForm()

                },
                error => {this.httpService.errorHandler(error) }
            )
        }

    }


    showForm(isAddNew : boolean = true, invoice?: any ) {

        this.ignoreDirty = false

        if (isAddNew) {
            this.isAddNew = true
            this.currentInvoiceId = 0
            this.invoiceDetailList = []

            this.invoiceForm.patchValue({
                'hsId'    : '',

                'invoiceDirection'    : '',
                'invoiceType'    : '',
                'openDate'    : null,

                'openCompanyId'    : '',
                'receiverId'    : ''
            })

        } else {
            this.isAddNew = false
            this.currentInvoiceId = invoice.id

            this.invoiceForm.patchValue(invoice)
            this.invoiceDetailList = invoice.details

            this.getInvoiceList()
        }


        this.isShowForm = !this.isShowForm


    }


    deleteItem (invoice : any) {

        this.hsOrderService.delInvoice(this.businessType, this.currentOrder.id, invoice.id).subscribe(
            data => {
                console.log('保存成功: ', data)
                this.httpService.successHandler(data)

                this.getInvoiceList()
            },
            error => {this.httpService.errorHandler(error) }
        )

    }


    createInvoiceDetail () {

        console.log('invoiceDetailForm Value : ', this.invoiceDetailForm.value)

        let formValid : boolean = true

        if (this.invoiceDetailForm.invalid) {
            this.invoiceDetailFormInputChange(this.invoiceDetailForm.value)

            formValid = false
        }


        if (this.invoiceDetailFormType === 2) {
            if (!this.invoiceDetailForm.value.invoiceNumber) {
                this.invoiceDetailFormError.invoiceNumber = this.invoiceDetailFormValidationMessages.invoiceNumber.required
                formValid = false
            }

            if (!this.invoiceDetailForm.value.amount) {
                this.invoiceDetailFormError.amount = this.invoiceDetailFormValidationMessages.amount.required
                formValid = false
            }

            if (!this.invoiceDetailForm.value.taxAmount) {
                this.invoiceDetailFormError.taxAmount = this.invoiceDetailFormValidationMessages.taxAmount.required
                formValid = false
            }

            if (!this.invoiceDetailForm.value.sheetAmount) {
                this.invoiceDetailFormError.sheetAmount = this.invoiceDetailFormValidationMessages.sheetAmount.required
                formValid = false
            }

            if (this.accAdd(Number(this.invoiceDetailForm.value.amount), Number(this.invoiceDetailForm.value.taxAmount)) !== Number(this.invoiceDetailForm.value.priceAndTax)) {
                this.invoiceDetailFormError.priceAndTax = this.invoiceDetailFormValidationMessages.priceAndTax.wrong
                formValid = false
            }
        }


        if (!formValid) {
            this.ignoreDirty = true
            return
        }

        this.invoiceDetailList.push(this.invoiceDetailForm.value)

        this.invoiceDetailForm.reset({
            'invoiceNumber'    : '',
            'cargoAmount'    : '',
            'taxRate'        : '',
            'priceAndTax'    : '',

            'amount'    : '',
            'taxAmount'    : '',
            'sheetAmount'    : '',
            'cargoType'    : ''

        } )

    }


    showDeletePrompt (index : number) {
        this.isShowDeletePromptIndex = index
        this.isShowDeletePrompt = !this.isShowDeletePrompt
    }

    delInvoiceDetail (detailInvoice: any) {
        console.log(detailInvoice)

        const index = this.invoiceDetailList.indexOf(detailInvoice)

        if (detailInvoice.id) {

            this.hsOrderService.delInvoiceDetail(this.businessType, this.currentOrder.id, detailInvoice.id).subscribe(
                data => {
                    console.log('保存成功: ', data)
                    this.invoiceDetailList.splice(index, 1)

                    this.httpService.successHandler(data)

                    this.getInvoiceList()
                },
                error => {this.httpService.errorHandler(error) }
            )



        } else {

            this.invoiceDetailList.splice(index, 1)

        }

        this.isShowDeletePrompt = false
        this.isShowDeletePromptIndex = -1
    }



    getBillingCompany (event : any) {
        // console.log(event)
        this.billingCompany = event
        this.changInvoiceDetailForm()
    }

    getBillToCompany (event : any) {
        // console.log(event)
        this.billToCompany = event
        this.changInvoiceDetailForm()
    }

    changInvoiceDetailForm () {
        this.invoiceDetailFormType = 1 // 2、当开票单位是或收票单位都不是css账务公司1：

        // 1 当开票单位是或收票单位有一个是css账务公司1：


        if (this.billingCompany && this.billingCompany.partyType === 1) {
            this.invoiceDetailFormType = 2
        }

        if (this.billToCompany && this.billToCompany.partyType === 1) {
            this.invoiceDetailFormType = 2
        }


        console.log('invoiceDetailFormType: ', this.invoiceDetailFormType, this.billingCompany, this.billToCompany)

    }



    // 加法函数，用来得到精确的加法结果 在运算前我们把参加运算的数先升级(10的X的次方)到整数，等运算完后再降级(0.1的X的次方)。

    accAdd (arg1 : number, arg2 : number) {
        let r1
        let r2
        let m

        try {
            r1 = arg1.toString().split('.')[1].length
        } catch (e) { r1 = 0 }

        try {
            r2 = arg2.toString().split('.')[1].length
        } catch ( e ) { r2 = 0 }

        m = Math.pow(10, Math.max(r1, r2))
        return (arg1 * m + arg2 * m) / m
    }
}

