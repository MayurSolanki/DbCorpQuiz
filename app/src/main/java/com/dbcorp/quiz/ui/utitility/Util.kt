package com.dbcorp.quiz.ui.utitility

import android.content.Context
import android.widget.Toast


class Util {

    companion object{
        public const val MY_TAG : String = "DbCorp"
    }

    fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, text, duration).show()
    }
}