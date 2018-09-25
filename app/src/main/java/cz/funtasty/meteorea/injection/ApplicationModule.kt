package cz.funtasty.meteorea.injection

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(val mApplication: Application) {

    @Provides
    internal fun provideSharedPreferences(): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(mApplication)
    }

    @Provides
    internal fun provideContext(): Context {
        return mApplication.applicationContext
    }
}