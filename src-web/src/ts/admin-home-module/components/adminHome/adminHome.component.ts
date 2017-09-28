import {Component, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'

import { HttpService } from '../../../bs-form-module/services/http.service'


import {UserInfoService} from '../../../services/userInfo.service'
import {HSOrderService} from '../../../services/hsOrder.service'

import {saveEnum} from '../../../services/localStorage'





@Component({
  selector: 'app-admin-home',
  templateUrl: './adminHome.component.html',
  styleUrls: ['./adminHome.component.css']
})
export class AdminHomeComponent implements OnInit {

    currentUser : any

    isLeftMenuCollapsed : boolean = false
    currentMenu : string = ''



    constructor(
        private httpService: HttpService,
        private userService: UserInfoService,
        private orderService: HSOrderService
    ) {
        // this.getCurrentUserInfo()
    }



    ngOnInit(): void {
        this.getCurrentUserInfo()
        this.getDictionary()
    }


    toggleLeftMenu () {
        this.isLeftMenuCollapsed = !this.isLeftMenuCollapsed
    }

    clickMenu (menu : string) {
        this.currentMenu = menu
    }


    getCurrentUserInfo () {
        this.userService.getSessionUserInfoHttp().subscribe(
            data => {
                this.currentUser = data.data

                this.userService.sendUserInfoMessage(data.data)

                // console.log('当前登陆的用户信息: ', data)
            },
            error => {this.httpService.errorHandler(error) }
        )
    }


    getDictionary () {


        this.orderService.getEnumList('SettleMode').subscribe(
            data => {
                saveEnum('SettleMode', data.data)
            },
            error => {this.httpService.errorHandler(error) }
        )


        this.orderService.getEnumList('CustomerType').subscribe(
            data => {
                saveEnum('CustomerType', data.data)
            },
            error => {this.httpService.errorHandler(error) }
        )

        this.orderService.getEnumList('CargoType').subscribe(
            data => {
                saveEnum('CargoType', data.data)
            },
            error => {this.httpService.errorHandler(error) }
        )

        this.orderService.getEnumList('CargoArriveStatus').subscribe(
            data => {
                saveEnum('CargoArriveStatus', data.data)
            },
            error => {this.httpService.errorHandler(error) }
        )

        this.orderService.getEnumList('TrafficMode').subscribe(
            data => {
                saveEnum('TrafficMode', data.data)
            },
            error => {this.httpService.errorHandler(error) }
        )


        this.orderService.getEnumList('PaymentPurpose').subscribe(
            data => {
                saveEnum('PaymentPurpose', data.data)
            },
            error => {this.httpService.errorHandler(error) }
        )

        this.orderService.getEnumList('ReceivePaymentPurpose').subscribe(
            data => {
                saveEnum('ReceivePaymentPurpose', data.data)
            },
            error => {this.httpService.errorHandler(error) }
        )

        this.orderService.getEnumList('PayMode').subscribe(
            data => {
                saveEnum('PayMode', data.data)
            },
            error => {this.httpService.errorHandler(error) }
        )
    }

}
