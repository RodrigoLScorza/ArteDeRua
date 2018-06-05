package br.com.rodrigoscorza.artederua.ui.fragments


import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.rodrigoscorza.artederua.R
import br.com.rodrigoscorza.artederua.entities.Arte
import br.com.rodrigoscorza.artederua.ui.PrincipalActivity
import br.com.rodrigoscorza.artederua.ui.adapter.ArteAdapterViewModel
import br.com.rodrigoscorza.artederua.util.Constantes
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MFragment : Fragment(), OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private lateinit var mMap: GoogleMap
    var mGoogleApiClient: GoogleApiClient? = null
    private var ok: Boolean? = null
    private lateinit var principalActivity: PrincipalActivity


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        principalActivity = (activity as PrincipalActivity)

        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this@MFragment)

        mGoogleApiClient = GoogleApiClient.Builder(principalActivity.applicationContext)
                .addConnectionCallbacks(this@MFragment)
                .addOnConnectionFailedListener(this@MFragment)
                .addApi(LocationServices.API)
                .build()


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        MapsInitializer.initialize(principalActivity.applicationContext)
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

    }

    override fun onStart() {
        super.onStart()
        mGoogleApiClient?.connect()
    }

    override fun onStop() {
        super.onStop()
        mGoogleApiClient?.disconnect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
    }


    override fun onConnected(p0: Bundle?) {
        if (!principalActivity.enableGPS()) {
            principalActivity.mandaToast(R.string.habiliteLocation)
            return
        }

        if (principalActivity.checkpermission()) {
            myLocation()
        }


    }

    override fun onConnectionSuspended(p0: Int) {

    }


    @SuppressLint("MissingPermission")
    private fun myLocation() {
        mMap.isMyLocationEnabled = true

        val l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
        if (mMap != null && l != null) {
            Constantes.latLng = LatLng(l.latitude, l.longitude)
            val updateCam = CameraUpdateFactory.newLatLngZoom(Constantes.latLng, 15f)
            mMap?.animateCamera(updateCam)
            pegaDados()
        }

    }

    private fun pegaDados() {
        ViewModelProviders.of(this@MFragment)
                .get(ArteAdapterViewModel::class.java)
                .artes
                .observe(this@MFragment, Observer<MutableList<Arte>> {
                    it?.forEach {
                        mMap.addMarker(MarkerOptions().position(LatLng(it.latitude, it.longitude)).title(it.nome).
                                snippet("${activity?.resources?.getString(R.string.nota)} ${it.nota}"))
                    }
                })
    }

}


