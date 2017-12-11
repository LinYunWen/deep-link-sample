package com.kkbox.yunwenlin.sample

import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import org.json.JSONArray

class Player {
    var curPlayIndex = 0
    lateinit var dataInfo: JSONArray
    var musicURL = "https://event.kkbox.com/content/song/WpYWY-V7aMOw-E1BUu"

    @RequiresApi(Build.VERSION_CODES.M)
    internal fun startPlaying() {
        MainActivity.webView.post(Runnable {
            MainActivity.webView.loadUrl(musicURL)
        })
        Log.i("widget load url: ", musicURL)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    internal fun playOther(lambda: Int) {
        curPlayIndex += lambda
        if (curPlayIndex >= 0) {
            curPlayIndex %= dataInfo.length()
        } else {
            curPlayIndex += dataInfo.length()
        }
        Log.i("cur play index: ", curPlayIndex.toString())
        musicURL = dataInfo.getJSONObject(curPlayIndex).getString("url")
    }
}