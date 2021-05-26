package com.dbcorp.quiz.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.dbcorp.quiz.ui.viewmodel.MainViewModel
import com.dbcorp.quiz.ui.viewmodel.QuestionModel
import com.dbcorp.quiz.ui.view.QuizActivity
import com.dbcorp.quiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)


        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.questionsOfQuiz.observe(this@MainActivity, { listOfQuestion ->

             val data =  listOfQuestion as ArrayList<QuestionModel>

            if(listOfQuestion.isNotEmpty()){

                 val intent = Intent(this, QuizActivity::class.java)
                 intent.putParcelableArrayListExtra("questionary", data)
                 startActivity(intent)
            }
        })

        mainBinding.btnStart.setOnClickListener {
            mainViewModel.fetchQuiz()
        }

    }
}