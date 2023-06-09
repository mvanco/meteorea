package cz.funtasty.meteorea.injection

import android.content.Context
import android.content.SharedPreferences

import cz.funtasty.meteorea.entity.DataHelper
import dagger.Component
import dagger.Module

@Module
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    val context: Context
    val sharedPreferences: SharedPreferences

    fun inject(dataHelper: DataHelper)
}
