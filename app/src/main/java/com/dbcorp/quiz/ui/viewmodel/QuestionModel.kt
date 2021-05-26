package com.dbcorp.quiz.ui.viewmodel

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize



@Parcelize
data class QuestionModel  (
     var question: String = "",
     var option1: String= "",
     var option2: String= "",
     var option3: String= "",
     var option4: String= "",
     var answer: String= "",
    ) : Parcelable
