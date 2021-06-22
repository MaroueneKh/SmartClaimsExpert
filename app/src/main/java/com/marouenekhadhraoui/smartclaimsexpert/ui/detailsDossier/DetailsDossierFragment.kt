package com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier

import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import coil.load
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.databinding.FragmentDetailsDossierBinding
import com.marouenekhadhraoui.smartclaimsexpert.ui.home.DossierModel
import com.marouenekhadhraoui.smartclaimsexpert.utils.Status
import com.marouenekhadhraoui.smartclaimsexpert.utils.internetErr
import com.marouenekhadhraoui.smartclaimsexpert.utils.invisible
import com.marouenekhadhraoui.smartclaimsexpert.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details_dossier.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class DetailsDossierFragment : Fragment(), OnMapReadyCallback {


    private val viewModel: DetailsDossierViewModel by viewModels()

    private var _binding: FragmentDetailsDossierBinding? = null
    private val binding get() = _binding

    private lateinit var navDirections: NavDirections

    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment


    @Inject
    lateinit var logger: Logger
    private lateinit var location: Location
    private lateinit  var uriVideo: Uri

    val storage = Firebase.storage
    val storageRef = storage.reference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details_dossier, container, false)
        binding?.lifecycleOwner = this

        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return binding?.root
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViewModel()
        observeDossiers()
        observeButton()
        observeImage()
        viewModel.getSuivi(arguments?.get("id").toString().toInt())
        observeFacture()

    }
    fun bindViewModel() {
        binding?.viewModel = viewModel
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun observeDossiers() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.dossier.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        logger.log("success")
                        if (it.data!!.isEmpty()) {
                            logger.log("null")
                        } else {
                            val id: String? = arguments?.get("id").toString()
                           val  dossier: DossierModel? = it.data.find { it.idDossier.toString().equals(id) }
                            setRadioGroup(dossier?.type)
                            setImages(dossier?.idDossier.toString())
                            location = Location(LocationManager.NETWORK_PROVIDER) // OR GPS_PROVIDER based on the requirement
                            location.latitude = dossier?.lat?.toDouble()!!
                            location.longitude = dossier?.lang?.toDouble()!!
                            setMarker(location)
                            ProgressBarmap.invisible()

                        }
                    }
                    Status.LOADING -> {
                        logger.log("loading")
                    }
                    Status.ERROR -> {
                        logger.log("error")
                        if (it.message.equals(internetErr)) {
                            logger.log("internet error")
                            //  Snackbar.make(findViewById(R.id.constraint), "Verifier votre connexion", Snackbar.LENGTH_LONG).show()
                        }

                    }
                }
            }
        }
    }

  private fun setRadioGroup(type:String?)
  {
      personne1.isChecked = true
      if (!type!!.equals("collision"))
      {
          personne2.isChecked = true
      }

  }
    private fun setImages(id:String)
    {
        logger.log("id")
        logger.log(id)

        val scan1ref = storageRef.child("$id/images/scan1.jpg")
        val scan2ref = storageRef.child("$id/images/scan2.jpg")
        val vid1ref = storageRef.child("$id/videos/vid1.mp4")
        val degat1ref = storageRef.child("$id/images/degat1.jpg")
        val degat2ref = storageRef.child("$id/images/degat2.jpg")
        val degat3ref = storageRef.child("$id/images/degat3.jpg")
        val degat4ref = storageRef.child("$id/images/degat4.jpg")
        scan1ref.downloadUrl.addOnSuccessListener { url ->
            img_scan1.load(url)
            ProgressBarscan1.invisible()
        }.addOnFailureListener {
            // Handle any errors
        }
        scan2ref.downloadUrl.addOnSuccessListener { url ->
            img_scan2.load(url)
            ProgressBarscan2.invisible()
        }.addOnFailureListener {
            // Handle any errors
        }
        degat1ref.downloadUrl.addOnSuccessListener { url ->
            img_Degats1.load(url)
            ProgressBardegat1.invisible()
        }.addOnFailureListener {
            // Handle any errors
        }
        degat2ref.downloadUrl.addOnSuccessListener { url ->
            img_Degats2.load(url)
            ProgressBardegat2.invisible()
        }.addOnFailureListener {
            // Handle any errors
        }
        degat3ref.downloadUrl.addOnSuccessListener { url ->
            img_Degats3.load(url)
            ProgressBardegat3.invisible()
        }.addOnFailureListener {
            // Handle any errors
        }
        degat4ref.downloadUrl.addOnSuccessListener { url ->
            img_Degats4.load(url)
            ProgressBardegat4.invisible()
        }.addOnFailureListener {
            // Handle any errors
        }
        vid1ref.downloadUrl.addOnSuccessListener { url ->
            Glide.with(requireContext()).load(url).thumbnail(0.8f).fitCenter()
                    .into(videoImage)
            uriVideo=url
        }.addOnFailureListener {

            // Handle any errors
        }
    }

 fun observeButton()
 {
     viewModel.pressBtnPlanifierEvent.observe(viewLifecycleOwner, Observer {
         val bundle = bundleOf("id" to arguments?.get("id").toString())
         setNavDirections(bundle)
         val navController = findNavController()
         navController.navigate(navDirections)

     })
 }



    fun setNavDirections(bundle: Bundle) {

        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }

            override fun getActionId(): Int {
                return R.id.action_detailsDossierFragment_to_dialogTypeExpertise
            }
        }


    }
    fun setNavDirectionsToVideo(bundle: Bundle) {
        navDirections = object : NavDirections {
            override fun getArguments(): Bundle {
                return bundle
            }
            override fun getActionId(): Int {
                return R.id.action_detailsDossierFragment_to_videoLectureDialog
            }
        }
    }
    fun observeImage()
    { imagePlay.setOnClickListener(View.OnClickListener {
            val bundle = bundleOf("uriVideo" to uriVideo.toString())
            setNavDirectionsToVideo(bundle)
            val navController = findNavController()
            navController.navigate(navDirections)

        })
    }
    fun observeFacture()
    {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.suivi.collect {
                when (it.status) {
                    Status.SUCCESS -> {
                        logger.log("success")
                        if (it.data!!.isEmpty()) {
                            logger.log("no suivi")
                        } else {
                            if (it.data!![0].resultat.equals("Facture Envoye"))
                                img_facture.visible()
                                txt_facture.visible()
                                ProgressFacture.visible()
                         val  id:String = arguments?.get("id").toString()

                            val facture = storageRef.child("$id/images/facture.png")
                            facture.downloadUrl.addOnSuccessListener { url ->
                                factur_png.visible()
                                factur_png.load(url)
                                ProgressFacture.invisible()
                              //  ProgressBarscan1.invisible()
                            }.addOnFailureListener {
                                logger.log(it.message.toString())
                                // Handle any errors
                            }



                        }
                    }
                    Status.LOADING -> {
                        logger.log("loading")
                    }
                    Status.ERROR -> {
                        logger.log("error")
                        if (it.message.equals(internetErr)) {
                            logger.log("internet error")
                            //  Snackbar.make(findViewById(R.id.constraint), "Verifier votre connexion", Snackbar.LENGTH_LONG).show()
                        }

                    }
                }
            }
        }


    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        location = Location(LocationManager.NETWORK_PROVIDER) // OR GPS_PROVIDER based on the requirement
        location.latitude = 36.8488155
        location.longitude = 10.1968604
        setMarker(location)
        logger.log("on map ready")
    }
    fun setMarker(location: Location) {
        mMap.addMarker(
                MarkerOptions()
                        .position(LatLng(location.latitude, location.longitude))
                        .title("Emplacement de l'accident").draggable(true)
        )
        val latlng = LatLng(location.latitude, location.longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 18.0F))
    }


}