package com.kkbox.yunwenlin.sample

import android.os.Build
import android.support.annotation.RequiresApi

class Recorder {
    lateinit var transcript: String

    @RequiresApi(Build.VERSION_CODES.M)
    internal fun afterRecord() {
        var searchClient = HTTPRequestToKKbox()
        searchClient.execute(MainActivity.recorder.transcript, "track", "TW")
    }
}