import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'



import { HttpService } from '../../../../bs-form-module/services/http.service'

import { formErrorHandler, isInt } from '../../../../bs-form-module/validators/validator'

import { HSUserService } from '../../../../services/hsUser.service'
import { HSOrderService } from '../../../../services/hsOrder.service'


import {getEnum} from '../../../../services/localStorage'


@Component({
    selector: 'app-statistics',
    templateUrl: './unitStatistics.component.html',
    styleUrls: ['./unitStatistics.component.css']
})
export class UnitStatisticsComponent implements OnInit {

    @Input() currentOrder : any
    @Input() businessType : string
    @Input() currentUnit : any
    @Output() back: any = new EventEmitter<boolean>()


    unitStatistics : any = {}

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
        this.getOrderUnitStat()
        console.log('currentUnit: ', this.currentUnit)
    }


    trackByFn(index: any, item: any) {
        return item ? item.id : undefined
    }


    getOrderUnitStat () {
        this.hsOrderService.getOrderUnitStatisticsByID(this.businessType, this.currentOrder.id, this.currentUnit.id).subscribe(
            data => {
                this.unitStatistics = data.data.results
            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    backTo() {
        this.back.emit(false)
    }




}

