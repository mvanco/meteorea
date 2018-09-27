package cz.funtasty.meteorea

import android.databinding.BindingAdapter
import android.widget.ImageView

object DataBindingAdapter {
    @BindingAdapter("mass")
    @JvmStatic
    fun setImageMass(imageView: ImageView, mass: Int) {
        var size =
            when (mass) {
                in 0..2000 -> 30
                in 2001..10000 -> 70
                else -> 120
            }

        val scale = imageView.resources.displayMetrics.density
        val sizeInDp = (size * scale + 0.5).toInt()

        imageView.layoutParams.apply {
            width = sizeInDp
            height = sizeInDp
        }
    }
}