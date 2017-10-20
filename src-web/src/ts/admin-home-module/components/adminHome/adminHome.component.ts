import {Component, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'

import { HttpService } from '../../../bs-form-module/services/http.service'


import {UserInfoService} from '../../../services/userInfo.service'
import {HSOrderService} from '../../../services/hsOrder.service'

import {saveAccessToken, saveEnum} from '../../../services/localStorage'





@Component({
  selector: 'app-admin-home',
  templateUrl: './adminHome.component.html',
  styleUrls: ['./adminHome.component.css']
})
export class AdminHomeComponent implements OnInit {

    currentUser : any

    isLeftMenuCollapsed : boolean = false
    currentMenu : number = 2
    currentSubMenu : number = 21

    menuList : any [] = [
        true,
        true,
        true,
        true,
        true
    ]



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


    clickMenu (event: any, menu : number) {
        event.preventDefault()
        this.currentMenu = Number(menu)
        this.menuList[this.currentMenu] = !this.menuList[this.currentMenu]
    }

    clickSubMenu (menu : number) {
        this.currentMenu = Number(menu.toString().substr(0, 1))
        this.currentSubMenu = Number(menu)
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


        this.orderService.getEnumList('OrderStatus').subscribe(
            data => {
                saveEnum('OrderStatus', data.data)
            },
            error => {this.httpService.errorHandler(error) }
        )

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

        this.orderService.getEnumList('FeeClass').subscribe(
            data => {
                saveEnum('FeeClass', data.data)
            },
            error => {this.httpService.errorHandler(error) }
        )


        this.orderService.getEnumList('InvoiceDirection').subscribe(
            data => {
                saveEnum('InvoiceDirection', data.data)
            },
            error => {this.httpService.errorHandler(error) }
        )
        this.orderService.getEnumList('InvoiceType').subscribe(
            data => {
                saveEnum('InvoiceType', data.data)
            },
            error => {this.httpService.errorHandler(error) }
        )

        this.orderService.getEnumList('DiscountMode').subscribe(
            data => {
                saveEnum('DiscountMode', data.data)
            },
            error => {this.httpService.errorHandler(error) }
        )

        this.orderService.getEnumList('InStorageStatus').subscribe(
            data => {
                saveEnum('InStorageStatus', data.data)
            },
            error => {this.httpService.errorHandler(error) }
        )

        this.orderService.getEnumList('BailType').subscribe(
            data => {
                saveEnum('BailType', data.data)
            },
            error => {this.httpService.errorHandler(error) }
        )
    }

    logout () {
        this.userService.userLogout().subscribe(
            data => {
                console.log('退出登陆成功: ', data)
                saveAccessToken('')

                setTimeout(function() {
                    window.location.href = '/web/login'
                }, 500)

            },
            error => {this.httpService.errorHandler(error) }
        )
    }

}
