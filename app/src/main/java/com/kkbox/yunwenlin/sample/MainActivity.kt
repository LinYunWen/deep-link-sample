package com.kkbox.yunwenlin.sample

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log

import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.kkbox.yunwenlin.sample.buttons.RecordButton
import android.os.Handler


class MainActivity : AppCompatActivity() {

    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private val permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_RECORD_AUDIO_PERMISSION -> permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
        if (!permissionToRecordAccepted) finish()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        mainContext = this
        mainActivity = this

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)

        // speech recognizer
        var listener = AndroidSpeechRecognizer()
        speech = SpeechRecognizer.createSpeechRecognizer(this)
        recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speech.setRecognitionListener(listener)
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "zh-TW")
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.packageName)
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)

        // elements
        setContentView(R.layout.activity_main)
        mRecordButton = findViewById(R.id.record_button)
        webView = findViewById(R.id.webView)

        // webView
        var webSetting = webView.settings
        webSetting.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val url = request.url.toString()
                if (url.startsWith("intent://")) {
                    try {
                        val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                        startActivity(intent)

                        val handler = Handler()
                        handler.postDelayed({
                            // change the song after 5 seconds
                            player.playOther(1)
                        }, 10000)

                    } catch (error: ActivityNotFoundException) {
                        return false
                    }
                    return true
                }
                return false
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webView.settings.mediaPlaybackRequiresUserGesture = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onPause() {
        super.onPause()
        Log.i("app state: ", "on pause")

        if (mRecordButton!!.mStartRecording) {
            mRecordButton!!.onRecord(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("app state: ", "on destory")
        speech.cancel()
        speech.destroy()
    }

    companion object {
        val LOG_TAG = "Smart Player"
        private val REQUEST_RECORD_AUDIO_PERMISSION = 200
        lateinit var mainContext: Context
        lateinit var mainActivity: Activity
        lateinit var speech: SpeechRecognizer
        lateinit var recognizerIntent: Intent
        lateinit var webView: WebView
        var mRecordButton: RecordButton? = null

        val accessToken = ""
        var recorder = Recorder()
        var player = Player()
        val kkboxClient = KKboxOpenAPIClient()
    }
}