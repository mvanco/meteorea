package cz.funtasty.meteorea.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.funtasty.meteorea.databinding.FragmentDetailBinding
import cz.funtasty.meteorea.entity.Meteorite
import cz.funtasty.meteorea.viewmodel.DetailFragmentViewModel
import kotlinx.android.synthetic.main.fragment_detail.view.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.CameraUpdate
import org.osmdroid.api.IGeoPoint
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import android.view.MotionEvent
import android.view.View.OnTouchListener
import cz.funtasty.meteorea.Config


class DetailFragment : BaseFragment() {
    private lateinit var mViewModel: DetailFragmentViewModel
    private lateinit var mBinding: FragmentDetailBinding
    private var mListener: OnFragmentInteractionListener? = null

    interface OnFragmentInteractionListener

    companion object {
        const val KEY_METEORITE = "movie_key"

        fun newInstance(meteorite: Meteorite): DetailFragment {
            val fragment =  DetailFragment()
            val args = Bundle()
            args.putParcelable(KEY_METEORITE, meteorite)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(DetailFragmentViewModel::class.java)
        lifecycle.addObserver(mViewModel)
        mViewModel.meteorite = arguments?.getParcelable(KEY_METEORITE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = FragmentDetailBinding.inflate(inflater, container, false)
        mBinding.viewModel = mViewModel
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initMap()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        }
        else {
            throw RuntimeException("${context.toString()} must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    private fun initMap() {
        mBinding.mapView.apply {
            controller. apply {
                setCenter(mViewModel.meteorite) // Set map to current location.
                setZoom(Config.DEFAULT_ZOOM)
            }
            isTilesScaledToDpi = true

            setOnTouchListener(OnTouchListener { _, _ ->  // Disable movement of map and zooming
                return@OnTouchListener true
            })
        }
    }
}