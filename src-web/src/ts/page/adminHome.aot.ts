import { enableProdMode } from '@angular/core'
import { platformBrowser } from '@angular/platform-browser'

import {AdminHomeModuleNgFactory} from '../../aotCompiled/ts/admin-home-module/adminHome.module.ngfactory'

if (process.env.NODE_ENV === 'production') {
    enableProdMode()
}

platformBrowser().bootstrapModuleFactory(AdminHomeModuleNgFactory)

