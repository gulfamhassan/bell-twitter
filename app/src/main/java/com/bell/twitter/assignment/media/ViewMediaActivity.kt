package com.bell.twitter.assignment.media

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import com.bell.twitter.assignment.R
import com.bell.twitter.assignment.utils.Constants.Companion.MEDIA_TYPE_GIF
import com.bell.twitter.assignment.utils.Constants.Companion.MEDIA_TYPE_IMAGE
import com.bell.twitter.assignment.utils.Constants.Companion.MEDIA_TYPE_VIDEO
import com.bell.twitter.assignment.utils.loadImageFromUrl
import kotlinx.android.synthetic.main.activity_view_media.*

class ViewMediaActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_media)

        intent.extras?.let {
            when (intent.getStringExtra(MEDIA_TYPE)) {
                MEDIA_TYPE_IMAGE -> {
                    imageView.apply {
                        visibility = View.VISIBLE
                        loadImageFromUrl(intent.getStringExtra(MEDIA_URL))
                    }
                }
                MEDIA_TYPE_VIDEO -> {
                    videoView.apply {
                        visibility = View.VISIBLE
                        val mc = MediaController(this@ViewMediaActivity)
                        mc.setAnchorView(this)
                        mc.setMediaPlayer(this)

                        if (intent.getStringExtra(MEDIA_TYPE_CONTENT) == MEDIA_TYPE_GIF)
                            this.setOnCompletionListener { this.start() }

                        this.setMediaController(mc)
                        this.setVideoURI(Uri.parse(intent.getStringExtra(MEDIA_URL)))
                        this.requestFocus()
                        this.start()
                    }
                }
                else -> {
                }
            }
        }
    }


    companion object {

        const val MEDIA_TYPE: String = "media_type"
        const val MEDIA_URL: String = "url"
        const val MEDIA_TYPE_CONTENT: String = "content"

        @JvmStatic
        fun getStartIntent(
            context: Context,
            mediaType: String,
            mediaUrl: String,
            mediaContentTye: String
        ): Intent {
            return Intent(context, ViewMediaActivity::class.java)
                .apply {
                    putExtra(MEDIA_TYPE, mediaType)
                    putExtra(MEDIA_URL, mediaUrl)
                    putExtra(MEDIA_TYPE_CONTENT, mediaContentTye)
                }
        }
    }
}