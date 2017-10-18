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

    currentInvoiceId : number = 1

    invoiceForm: FormGroup
    invoiceDetailForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true


    invoiceDetailList : any[] = []


    invoiceList : any[] = []
    partyList : any[] = []

    unitList : any[] = []

    invoiceDirectionList : any[] = getEnum('InvoiceDirection')
    invoiceTypeList : any[] = getEnum('InvoiceType')


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

        this.getOrderUnitList()
        this.getPartyList()
        this.getInvoiceList()
        this.createInvoiceForm()
        this.createInvoiceDetailForm()

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


    getInvoiceList () {
        this.hsOrderService.getInvoiceListByID(this.businessType, this.currentOrder.id).subscribe(
            data => {
                this.invoiceList = data.data.results

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
            'required'      : '请填写价税合计!'
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
            'invoiceNumber'    : ['', [Validators.required ] ],
            'cargoAmount'    : ['', [Validators.required ] ],
            'taxRate'        : ['', [Validators.required ] ],
            'priceAndTax'    : ['', [Validators.required ] ]
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

            console.log('当前信息: ', this.invoiceForm, this.invoiceFormError)
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
        if (this.invoiceDetailForm.invalid) {
            this.invoiceDetailFormInputChange(this.invoiceDetailForm.value)
            this.ignoreDirty = true

            return
        }

        this.invoiceDetailList.push(this.invoiceDetailForm.value)
    }
    delInvoiceDetail (detailInvoice: any) {

        const index = this.invoiceDetailList.indexOf(detailInvoice)
        this.invoiceDetailList.splice(index, 1)
    }

}

