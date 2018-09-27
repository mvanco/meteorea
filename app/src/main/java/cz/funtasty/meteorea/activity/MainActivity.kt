package cz.funtasty.meteorea.activity

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import cz.funtasty.meteorea.BuildConfig
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
        val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        lifecycle.addObserver(mViewModel)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(mBinding.toolbar)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MainFragment.newInstance(), TAG_MAIN_FRAGMENT).commit()

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
//                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    val fragment = supportFragmentManager.findFragmentByTag(MAIN_FRAGMENT_TAG) as MainFragment
//                    val transaction = supportFragmentManager.beginTransaction()
//
//                    if (fragment != null) {
//                        transaction.remove(fragment)
//                    }
//
//                    transaction.add(R.id.activityMain_fragmentContainer, MainFragment.newInstance(), MAIN_FRAGMENT_TAG).commit()
//
//                    supportFragmentManager.executePendingTransactions()
//                } else {
//                    val builder = AlertDialog.Builder(this)
//                    builder.setMessage(R.string.message_application_termination)
//                    builder.setPositiveButton(R.string.button_confirm) { dialog, which -> this.finish() }
//                    builder.create().show()
//                }
                return
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.onLoadMeteorites.observeOn(AndroidSchedulers.mainThread()).subscribe {
            var fragment = supportFragmentManager.findFragmentByTag(TAG_MAIN_FRAGMENT)
            if (fragment != null) {
                (fragment as MainFragment).setMeteorites(it)
            }
        }
        mViewModel.onLoaderVisibilityChanged.observe(this, Observer {
            (supportFragmentManager.findFragmentByTag(TAG_MAIN_FRAGMENT) as MainFragment).setLoaderVisibility(it!!)
        })
    }

    override fun onItemClicked(meteorite: Meteorite) {
        startActivity(DetailActivity.newIntent(this, meteorite))
    }
}
