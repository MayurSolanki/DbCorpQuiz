package com.dbcorp.quiz.ui.viewmodel

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.dbcorp.quiz.ui.view.QuestionFragment


class QuizViewModel() : ViewModel() {


    fun buildFragments(context: Context ,questionary: ArrayList<QuestionModel>): MutableList<Fragment>{
        val frgs = mutableListOf<Fragment>()

        questionary.forEach {
            val bundle = Bundle()
            bundle.putParcelable("questionModel",it)
            frgs.add(Fragment.instantiate(context, QuestionFragment::class.java.name,bundle))

        }


        return frgs
    }
}