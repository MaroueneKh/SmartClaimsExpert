package com.marouenekhadhraoui.smartclaimsexpert.ui.detailsDossier

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.extractor.ExtractorsFactory
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.marouenekhadhraoui.smartclaimsexpert.Logger
import com.marouenekhadhraoui.smartclaimsexpert.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_video_lecture.*
import kotlinx.android.synthetic.main.dialog_video_lecture.view.*
import javax.inject.Inject

@AndroidEntryPoint
class VideoLectureDialog : DialogFragment() {

    @Inject
    lateinit var logger: Logger

    private lateinit var viewPager: ViewPager2


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val myview: View = inflater.inflate(R.layout.dialog_video_lecture, container, false)

        // bandwisthmeter is used for
        // getting default bandwidth
        logger.log("uriVideo")
        logger.log(arguments?.getString("uriVideo").toString())
        myview.jz_video.setUp(arguments?.getString("uriVideo"),"Accident")

        return myview
    }

}