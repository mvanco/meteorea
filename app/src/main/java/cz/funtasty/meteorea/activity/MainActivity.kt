package cz.funtasty.meteorea.activity

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import cz.funtasty.meteorea.R
import cz.funtasty.meteorea.databinding.ActivityMainBinding
import cz.funtasty.meteorea.fragment.MainFragment
import cz.funtasty.meteorea.entity.Meteorite
import cz.funtasty.meteorea.viewmodel.MainActivityViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class MainActivity : BaseActivity(), MainFragment.OnFragmentInteractionListener {
    private lateinit var mViewModel: MainActivityViewModel
    private lateinit var mBinding: ActivityMainBinding

    companion object {
        private val TAG_MAIN_FRAGMENT = "tag_main_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        lifecycle.addObserver(mViewModel)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(mBinding.toolbar)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MainFragment.newInstance(), TAG_MAIN_FRAGMENT).commit()

    }

    override fun onResume() {
        super.onResume()
        mViewModel.onLoadMeteorites.observeOn(AndroidSchedulers.mainThread()).subscribe {
            var fragment = supportFragmentManager.findFragmentByTag(TAG_MAIN_FRAGMENT)
            if (fragment != null) {
                (fragment as MainFragment).setMeteorites(it)
            }
        }
    }

    override fun onItemClicked(meteorite: Meteorite) {
        startActivity(DetailActivity.newIntent(this, meteorite))
    }
}
