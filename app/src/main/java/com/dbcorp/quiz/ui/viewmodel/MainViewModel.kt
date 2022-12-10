package com.dbcorp.quiz.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dbcorp.quiz.ui.utitility.Util.Companion.MY_TAG
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainViewModel : ViewModel() {

    private val database = Firebase.database
    private val myRef = database.getReference("quiz")

    private val _questionsLiveData = MutableLiveData<List<QuestionModel>>()
    val questionsOfQuiz : LiveData<List<QuestionModel>> = _questionsLiveData

    init {
    }

     fun fetchQuiz(){
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("log...", ""+dataSnapshot)

                val quizList = mutableListOf<QuestionModel>()

                for (postSnapshot in dataSnapshot.children) {
                    val post: QuestionModel? = postSnapshot.getValue(QuestionModel::class.java)
                    quizList.add(post!!)
                }

                _questionsLiveData.value = quizList

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(MY_TAG, "Failed to read value.", error.toException())
            }
        })
    }
}