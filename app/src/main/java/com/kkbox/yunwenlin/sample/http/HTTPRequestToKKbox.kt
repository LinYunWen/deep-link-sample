package com.kkbox.yunwenlin.sample

import android.os.AsyncTask
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import org.json.JSONArray

class HTTPRequestToKKbox: AsyncTask<String, Unit, JSONArray>() {

    // @p[0]: query
    // @p[1]: type
    // @p[2]: territory
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun doInBackground(vararg p: String): JSONArray {
        val result = MainActivity.kkboxClient.doSearch(p[0], p[1], p[2])
        val info = MainActivity.kkboxClient.parser(result, p[0])

        // on error
        if (info.getJSONObject(0).has("error")) {
            val error = info.getJSONObject(0).getString("error")
            Log.e(MainActivity.LOG_TAG, "error: $error")
            return info
        }

        MainActivity.player.musicURL = MainActivity.kkboxClient.getUrl(info.getJSONObject(0))

        return info
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onPostExecute(result: JSONArray) {
        MainActivity.player.dataInfo = result
        MainActivity.player.startPlaying()
    }
}