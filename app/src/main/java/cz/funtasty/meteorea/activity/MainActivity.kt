package cz.funtasty.meteorea.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.util.Log
import cz.funtasty.meteorea.R
import cz.funtasty.meteorea.databinding.ActivityMainBinding
import cz.funtasty.meteorea.local.DataHelper
import cz.funtasty.meteorea.viewmodel.MainActivityViewModel

class MainActivity : BaseActivity() {

    private lateinit var mViewModel: MainActivityViewModel
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        lifecycle.addObserver(mViewModel)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mViewModel.onLoadMeteorites.subscribe {
            val iha = if (it.isNotEmpty()) it[0].fall else "nenajdene"
            Log.d("wow", "$iha, vratilo sa mi ${it.size} radku")
        }
    }
}
