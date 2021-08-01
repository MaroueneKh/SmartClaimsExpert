package com.marouenekhadhraoui.smartclaimsexpert.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.data.local.Datapreferences
import com.marouenekhadhraoui.smartclaimsexpert.utils.Status
import com.marouenekhadhraoui.smartclaimsexpert.utils.TO_SIGNIN_OR_SIGNUP
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_visio2.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.SessionDescription
import javax.inject.Inject

@AndroidEntryPoint
class VisioActivity2 : AppCompatActivity()  {
    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1
        private const val CAMERA_PERMISSION = Manifest.permission.CAMERA
    }

    private lateinit var rtcClient: RTCClient
    private lateinit var signallingClient: SignallingClient
    @Inject
    lateinit var logger: Logger

    @Inject
    lateinit var dataPref: Datapreferences

    private val viewModel: VisioViewModel by viewModels()


     var id:Int = 0


    private val sdpObserver = object : AppSdpObserver() {
        override fun onCreateSuccess(p0: SessionDescription?) {
            super.onCreateSuccess(p0)
            signallingClient.send(p0)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visio2)


        checkCameraPermission()


    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this,
                CAMERA_PERMISSION
            ) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission()
        } else {
            onCameraPermissionGranted()
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    private fun onCameraPermissionGranted() {
        rtcClient = RTCClient(
            application,
            object : PeerConnectionObserver() {
                override fun onIceCandidate(p0: IceCandidate?) {
                    super.onIceCandidate(p0)
                    signallingClient.send(p0)
                    rtcClient.addIceCandidate(p0)
                }

                override fun onAddStream(p0: MediaStream?) {
                    super.onAddStream(p0)
                    p0?.videoTracks?.get(0)?.addSink(remote_view)
                }
            }
        )
        rtcClient.initSurfaceView(remote_view)
        rtcClient.initSurfaceView(local_view)


        rtcClient.startLocalVideoCapture(local_view)
        signallingClient =
            SignallingClient(
                createSignallingClientListener()
            )
        Handler().postDelayed(
            {
                rtcClient.call(sdpObserver)
            },
            2000 // value in milliseconds
        )

       call_button.setOnClickListener {
        rtcClient.call(sdpObserver)
         }


        cancel_button.setOnClickListener {
            //
            rtcClient.endCall(sdpObserver)
            viewModel.callAssure(1,"off")



            val bundle = intent.extras
            logger.log("idDossier")
            logger.log(bundle?.get("id").toString())

            CoroutineScope(IO).launch {
                dataPref.id.collect {
                    id=it.toInt()
                    viewModel.getSuivi(id)
                    lifecycleScope.launchWhenStarted {
                        viewModel.suivi.collect {
                            when (it.status) {
                                Status.SUCCESS -> {
                                    if (it.data!!.isEmpty()) {
                                        logger.log("here")

                                        viewModel.modifierVisio(id,1,"en attente d'avis")
                                        viewModel.modifierDossier(id,"En attente de facture")

                                        val intent = Intent(applicationContext, MainActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                        intent.action = TO_SIGNIN_OR_SIGNUP
                                        startActivity(intent)

                                    }
                                    else{
                                        logger.log("not here")
                                        viewModel.modifierSuivi(id,1,"en attente d'avis")

                                        val intent = Intent(applicationContext, MainActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                                        intent.action = TO_SIGNIN_OR_SIGNUP
                                        startActivity(intent)
                                    }

                                }
                                Status.LOADING -> {

                                }
                                Status.ERROR -> {

                                }
                            }
                        }
                    }

                }
                // Your Stuff here
            }




            //local_view.clearImage()
            //local_view.release()
            // intent.action = TO_VISIO_FRAGMENT
            // startActivity(intenttoFRagment,bundle)
            //finish()



        }
    }
    private fun createSignallingClientListener() = object :
        SignallingClientListener {
        override fun onConnectionEstablished() {
            call_button.isClickable = true
        }

        override fun onOfferReceived(description: SessionDescription) {
            rtcClient.onRemoteSessionReceived(description)
            rtcClient.answer(sdpObserver)
            remote_view_loading.isGone = true
        }

        override fun onAnswerReceived(description: SessionDescription) {
            rtcClient.onRemoteSessionReceived(description)
            remote_view_loading.isGone = true

        }

        override fun onIceCandidateReceived(iceCandidate: IceCandidate) {
            rtcClient.addIceCandidate(iceCandidate)
        }
    }


    private fun requestCameraPermission(dialogShown: Boolean = false) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                CAMERA_PERMISSION
            ) && !dialogShown) {
            showPermissionRationaleDialog()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(CAMERA_PERMISSION),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun showPermissionRationaleDialog() {
        AlertDialog.Builder(this)
            .setTitle("Camera Permission Required")
            .setMessage("This app need the camera to function")
            .setPositiveButton("Grant") { dialog, _ ->
                dialog.dismiss()
                requestCameraPermission(true)
            }
            .setNegativeButton("Deny") { dialog, _ ->
                dialog.dismiss()
                onCameraPermissionDenied()
            }
            .show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            onCameraPermissionGranted()
        } else {
            onCameraPermissionDenied()
        }
    }

    private fun onCameraPermissionDenied() {
        Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_LONG).show()
    }


    override fun onDestroy() {
        signallingClient.destroy()
        super.onDestroy()
    }




}