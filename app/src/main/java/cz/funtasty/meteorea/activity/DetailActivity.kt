package cz.funtasty.meteorea.activity

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import cz.funtasty.meteorea.R
import cz.funtasty.meteorea.databinding.ActivityDetailBinding
import cz.funtasty.meteorea.fragment.DetailFragment
import cz.funtasty.meteorea.entity.Meteorite
import cz.funtasty.meteorea.viewmodel.DetailActivityViewModel

class DetailActivity : BaseActivity(), DetailFragment.OnFragmentInteractionListener {

    private lateinit var mViewModel: DetailActivityViewModel
    private lateinit var mBinding: ActivityDetailBinding

    companion object {
        private const val KEY_METEORITE = "meteorite_key"
        private const val TAG_DETAIL_FRAGMENT = "detail_fragment_tag"

        fun newIntent(context: Context, meteorite: Meteorite): Intent {
            return  Intent(context, DetailActivity::class.java).apply {
                putExtra(DetailActivity.KEY_METEORITE, meteorite)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(DetailActivityViewModel::class.java)
        if (mViewModel.meteorite == null) {
            mViewModel.meteorite = intent.getParcelableExtra(KEY_METEORITE)
        }
        lifecycle.addObserver(mViewModel)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        setSupportActionBar(mBinding.toolbar)
        supportActionBar ?: throw RuntimeException("you dont have access to action bar")
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = mViewModel.meteorite!!.name
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, mViewModel.meteorite?.let {DetailFragment.newInstance(mViewModel.meteorite!!)}, TAG_DETAIL_FRAGMENT).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var isHandled = super.onOptionsItemSelected(item)
        when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                isHandled = true
            }
        }
        return isHandled
    }
}
