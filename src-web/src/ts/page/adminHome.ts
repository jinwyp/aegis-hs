import { enableProdMode } from '@angular/core'
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic'

import { AdminHomeModule } from '../admin-home-module/adminHome.module'

if (process.env.NODE_ENV === 'production') {
    enableProdMode()
}

platformBrowserDynamic().bootstrapModule(AdminHomeModule)
