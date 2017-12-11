package com.kkbox.yunwenlin.sample

import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.kkbox.openapi.api.HttpClient
import com.kkbox.openapi.api.SearchFetcher
import java.io.IOException

import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

class KKboxOpenAPIClient {

    @Throws(IOException::class)
    internal fun doSearch(query: String, type: String, territory: String): JSONObject {
        val searchFetcher = SearchFetcher(HttpClient(MainActivity.accessToken, MainActivity.mainContext), territory)
        Log.i("query: ", query)
        val data = searchFetcher.setSearchCriteria(query, type).fetchSearchResult().get(10, TimeUnit.SECONDS).toString()
        return JSONObject(data)
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    internal fun parser(data: JSONObject, keyword: String): JSONArray {

        if (data.has("error")) {
            val errorMessage = data.getJSONObject("error").getString("message")
            return JSONArray("[{\"error\": \"$errorMessage\"}]")
        }

        if (data.has("tracks") && !data.isNull("tracks")) {
            val collection = data.getJSONObject("tracks").getJSONArray("data")
            val length = collection.length()
            for (i in 0 until length - 1) {
                // Log.i(MainActivity.LOG_TAG, "name: " + getName(collection.getJSONObject(i)))
                if (doMatchName(getName(collection.getJSONObject(i)), keyword)) {
                    val item = collection[i]
                    collection.remove(i)
                    collection.put(0, item)
                    return collection
                }
            }
            return collection
        }
        return JSONArray("[{\"error\": \"no result\"}]")
    }

    internal fun getName(data: JSONObject): String {
        if (data.has("name")) {
            return data.getString("name")
        }
        return "Error: There is no \"name\" key"
    }

    internal fun getUrl(data: JSONObject): String {
        if (data.has("name")) {
            return data.getString("url")
        }
        return "Error: There is no \"url\" key"
    }

    internal fun doMatchName(name: String, expect: String): Boolean {
        var index = name.indexOf("(")
        if (index <= 0) {
            if (name.equals(expect, true)) {
                return true
            }
            return false
        }

        var substring = name.substring(0, index - 1)
        if (substring == expect) {
            return true
        }
        return false
    }
}