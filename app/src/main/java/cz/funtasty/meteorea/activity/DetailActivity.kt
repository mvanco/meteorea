package cz.funtasty.meteorea.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import cz.funtasty.meteorea.R
import cz.funtasty.meteorea.databinding.ActivityDetailBinding
import cz.funtasty.meteorea.viewmodel.DetailActivityViewModel

class DetailActivity : BaseActivity() {

    private lateinit var mViewModel: DetailActivityViewModel
    private lateinit var mBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(DetailActivityViewModel::class.java)
        lifecycle.addObserver(mViewModel)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
    }
}
