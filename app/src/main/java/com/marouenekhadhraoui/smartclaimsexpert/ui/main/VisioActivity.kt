package com.marouenekhadhraoui.smartclaimsexpert.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle

import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.lifecycle.lifecycleScope
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import com.marouenekhadhraoui.smartclaimsexpert.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_visio.*
import kotlinx.coroutines.flow.collect
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.SessionDescription
import java.io.File
import javax.inject.Inject


@AndroidEntryPoint
class VisioActivity : AppCompatActivity()  {
    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 1
        private const val CAMERA_PERMISSION = Manifest.permission.CAMERA
    }

    private lateinit var rtcClient: RTCClient
    private lateinit var signallingClient: SignallingClient
    @Inject
    lateinit var logger: Logger


  private var fois:Int = 0


    private val viewModel: VisioViewModel by viewModels()


    private val sdpObserver = object : AppSdpObserver() {
        override fun onCreateSuccess(p0: SessionDescription?) {
            super.onCreateSuccess(p0)
            signallingClient.send(p0)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visio)
logger.log("fi visio activity")


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

        fois++
        rtcClient.initSurfaceView(remote_view)
        rtcClient.initSurfaceView(local_view)


        rtcClient.startLocalVideoCapture(local_view)


        if (fois == 1)
      {
       logger.log("djdjjdjdjd")
       viewModel.callAssure(1,"on")
          rtcClient.endCall(sdpObserver)
       }




        cancel_button.setOnClickListener {
            //

            rtcClient.endCall(sdpObserver)

            viewModel.callAssure(1,"off")



           val bundle = intent.extras
            logger.log("idDossier")
            logger.log(bundle?.get("id").toString())
            viewModel.getSuivi(bundle?.get("id").toString().toInt())
            lifecycleScope.launchWhenStarted {
                viewModel.suivi.collect {
                    when (it.status) {
                        Status.SUCCESS -> {
                            if (it.data!!.isEmpty()) {
                                logger.log("here")
                             //   viewModel.modifierVisio(bundle?.get("id").toString().toInt(),1,"en attente d'avis")
                                finish()

                            }
                            else{
                                logger.log("not here")
                               // viewModel.modifierSuivi(bundle?.get("id").toString().toInt(),1,"en attente d'avis")
                                finish()
                            }

                        }
                        Status.LOADING -> {

                        }
                        Status.ERROR -> {

                        }
                    }
                }
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
//       signallingClient.destroy()

        super.onDestroy()
    }




}