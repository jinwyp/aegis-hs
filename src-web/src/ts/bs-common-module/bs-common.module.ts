/**
 * Created by jin on 8/16/17.
 */


import { NgModule} from '@angular/core'
import { CommonModule } from '@angular/common'
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import { BSModalComponent } from '../bs-common-module/components/bs-modal/bs-modal.component'
import { BSPaginationComponent } from '../bs-common-module/components/bs-pagination/bs-pagination.component'
import { SimpleNotificationsComponent } from '../bs-common-module/components/simple-notifications/simple-notifications.component'
import { SimpleNotificationComponent } from '../bs-common-module/components/simple-notification/simple-notification.component'


import {MaxPipe} from './pipes/max/max.pipe'



import {ModalService} from './services/modal.service'
import {NotificationService} from './services/notification.service'

@NgModule({
    declarations : [
        MaxPipe,

        BSPaginationComponent,

        BSModalComponent,
        SimpleNotificationComponent,
        SimpleNotificationsComponent
    ],
    imports      : [
        CommonModule,
        BrowserAnimationsModule
    ],
    providers    : [
        ModalService,
        NotificationService
    ],
    exports:      [
        CommonModule,
        BrowserAnimationsModule,

        BSModalComponent,
        BSPaginationComponent,
        SimpleNotificationsComponent

    ],
    bootstrap    : []
})
export class BSCommonModule {}
