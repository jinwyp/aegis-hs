import {Component, OnInit} from '@angular/core'
import { FormBuilder, FormGroup, Validators} from '@angular/forms'

import { HttpService } from '../../../bs-form-module/services/http.service'


import {UserInfoService} from '../../../services/userInfo.service'





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
        private userService: UserInfoService
    ) {
        // this.getCurrentUserInfo()
    }



    ngOnInit(): void {
        this.getCurrentUserInfo()
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


}
