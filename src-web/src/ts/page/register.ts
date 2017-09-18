import { enableProdMode } from '@angular/core'
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic'

import { RegisterModule } from '../login-module/register.module'

if (process.env.NODE_ENV === 'production') {
    enableProdMode()
}

platformBrowserDynamic(
    [ {provide: 'moduleType', useValue: 'register' } ]
).bootstrapModule(RegisterModule)
