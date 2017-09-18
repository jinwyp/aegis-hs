import {Component, OnInit} from '@angular/core'

import { HttpService } from '../../../bs-form-module/services/http.service'


import {UserInfoService} from '../../../services/userInfo.service'





@Component({
  selector: 'app-user-home',
  templateUrl: './indexHome.component.html',
  styleUrls: ['./indexHome.component.css']
})
export class IndexHomeComponent implements OnInit {

    currentUser : any


    constructor(
        private httpService: HttpService,
        private userService: UserInfoService
    ) {

    }


    ngOnInit(): void {
        this.getCurrentUserInfo()
    }


    getCurrentUserInfo () {
        this.userService.getUserInfoHttp().subscribe(
            data => {
                this.currentUser = data.data

                this.userService.sendUserInfoMessage(data.data)

                // console.log('当前登陆的用户信息: ', data)
            },
            error => {this.httpService.errorHandler(error) }
        )
    }

}
