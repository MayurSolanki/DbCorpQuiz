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
             // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                val quizList = mutableListOf<QuestionModel>()

                for (postSnapshot in dataSnapshot.children) {
                    val post: QuestionModel? = postSnapshot.getValue(QuestionModel::class.java)
                    Log.d(MY_TAG, "Value : " + post)
                    quizList.add(post!!)
                }

                _questionsLiveData.value = quizList

            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(MY_TAG, "Failed to read value.", error.toException())
            }
        })
    }
}