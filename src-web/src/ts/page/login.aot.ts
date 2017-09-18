import { enableProdMode } from '@angular/core'
import {platformBrowser} from '@angular/platform-browser'

import {LoginModuleNgFactory} from '../../aotCompiled/ts/login-module/login.module.ngfactory'

if (process.env.NODE_ENV === 'production') {
    enableProdMode()
}

platformBrowser(
    [ {provide: 'moduleType', useValue: 'login' } ]
).bootstrapModuleFactory(LoginModuleNgFactory)

