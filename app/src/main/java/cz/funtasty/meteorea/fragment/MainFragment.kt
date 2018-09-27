package cz.funtasty.meteorea.fragment

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.funtasty.meteorea.adapter.MeteoriteAdapter
import cz.funtasty.meteorea.databinding.FragmentMainBinding
import cz.funtasty.meteorea.entity.Meteorite
import cz.funtasty.meteorea.viewmodel.MainFragmentViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {
    private lateinit var mViewModel: MainFragmentViewModel
    private lateinit var mBinding: FragmentMainBinding
    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var mAdapter: MeteoriteAdapter

    interface OnFragmentInteractionListener : MeteoriteAdapter.OnItemClickListener

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel::class.java)
        lifecycle.addObserver(mViewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = FragmentMainBinding.inflate(inflater, container, false)
        mBinding.viewModel = mViewModel
        Handler().postDelayed({
        }, 5000)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAdapter = MeteoriteAdapter(mListener)
        mBinding.recycler.adapter = mAdapter
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException("${context.toString()} must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    fun setMeteorites(meteorites: List<Meteorite>) {
        mAdapter.setData(meteorites)
        mViewModel.metCount.set(meteorites.size)
    }

    fun setLoaderVisibility(visible: Boolean) {
        mViewModel.setLoaderVisibility(visible)
    }
}