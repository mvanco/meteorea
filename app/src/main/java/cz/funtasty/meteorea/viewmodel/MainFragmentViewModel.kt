package cz.funtasty.meteorea.viewmodel

import android.databinding.ObservableInt
import android.view.View

class MainFragmentViewModel : BaseFragmentViewModel() {
    val loaderVisibility = ObservableInt(View.GONE)
    val metCount = ObservableInt(0)

    fun setLoaderVisibility(visible: Boolean) {
        if (visible) {
            loaderVisibility.set(View.VISIBLE)
        }
        else {
            loaderVisibility.set(View.GONE)
        }
    }

}