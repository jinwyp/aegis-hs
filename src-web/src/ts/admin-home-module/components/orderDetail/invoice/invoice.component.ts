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

    currentInvoiceId : number = 1

    invoiceForm: FormGroup
    ignoreDirty: boolean = false

    isShowForm: boolean = false
    isAddNew: boolean = true

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

        this.getPartyList()
        this.getInvoiceList()
        this.createInvoiceForm()

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
        this.hsOrderService.getInvoiceListByID(this.currentOrder.id).subscribe(
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

    invoiceFormInputChange(formInputData : any, ignoreDirty : boolean = false) {
        this.invoiceFormError = formErrorHandler(formInputData, this.invoiceForm, this.invoiceFormValidationMessages, ignoreDirty)
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





    invoiceFormSubmit() {

        if (this.invoiceForm.invalid) {
            this.invoiceFormInputChange(this.invoiceForm.value, true)
            this.ignoreDirty = true

            console.log('当前信息: ', this.invoiceForm, this.invoiceFormError)
            return
        }

        const postData = this.invoiceForm.value
        postData.orderId = this.currentOrder.id


        if (this.invoiceForm.value.openDate && typeof this.invoiceForm.value.openDate === "object" ) {
            postData.openDate = this.hsOrderService.formatDateTime(this.invoiceForm.value.openDate)
        }


        if (this.isAddNew) {
            this.hsOrderService.createNewInvoice(this.currentOrder.id, postData).subscribe(
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
            delete postData.huikuanAmount

            this.hsOrderService.modifyInvoice(this.currentOrder.id, this.currentInvoiceId, postData).subscribe(
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
        }


        this.isShowForm = !this.isShowForm
    }



}
