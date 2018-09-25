package cz.funtasty.meteorea

import cz.funtasty.meteorea.injection.ApplicationComponent
import cz.funtasty.meteorea.injection.ApplicationModule
import cz.funtasty.meteorea.injection.DaggerApplicationComponent

class Application: android.app.Application {

    companion object {
        lateinit var sInstance: Application
    }

    constructor() {
        sInstance = this
    }

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}
