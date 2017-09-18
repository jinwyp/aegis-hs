import { enableProdMode } from '@angular/core'
import {platformBrowser} from '@angular/platform-browser'

import {RegisterModuleNgFactory} from '../../aotCompiled/ts/login-module/register.module.ngfactory'

if (process.env.NODE_ENV === 'production') {
    enableProdMode()
}

platformBrowser(
    [ {provide: 'moduleType', useValue: 'register' } ]
).bootstrapModuleFactory(RegisterModuleNgFactory)

