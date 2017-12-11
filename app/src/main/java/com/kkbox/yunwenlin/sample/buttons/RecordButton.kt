package com.kkbox.yunwenlin.sample.buttons

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import com.kkbox.yunwenlin.sample.MainActivity

class RecordButton : Button {
    // ----- constructor
    internal val context: Context

    constructor(context: Context) : super(context) {
        this.context = context
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context = context
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        this.context = context
    }
    // ------ constructor

    var mStartRecording = false
    @RequiresApi(Build.VERSION_CODES.M)
    var clicker: View.OnClickListener = OnClickListener {
        onRecord(!mStartRecording)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun onRecord(start: Boolean) {
        mStartRecording = start
        if (mStartRecording) {
            MainActivity.mRecordButton!!.text = "Stop Recording"
            MainActivity.speech.startListening(MainActivity.recognizerIntent)
        } else {
            MainActivity.mRecordButton!!.text = "Start Recording"
            MainActivity.speech.stopListening()
        }
    }

    init {
        text = "Start Recording"
        setOnClickListener(clicker)
    }
}