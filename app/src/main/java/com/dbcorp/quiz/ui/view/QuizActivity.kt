package com.dbcorp.quiz.ui.view

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dbcorp.quiz.ui.utitility.GlobalBus
import com.dbcorp.quiz.ui.utitility.QuestionAttemptEvent
import com.dbcorp.quiz.databinding.ActivityQuizBinding
import com.dbcorp.quiz.ui.viewmodel.QuestionModel
import com.dbcorp.quiz.ui.viewmodel.QuizViewModel
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList



class QuizActivity : AppCompatActivity() {

    lateinit var quizViewModel: QuizViewModel
    lateinit var quizAdapter: QuizAdapter
    lateinit  var quizBinding : ActivityQuizBinding
    var questionary = ArrayList<QuestionModel>()
    var totalScore : Int  = 0
    var obtainScore : Int  = 0
    private var timer : CountDownTimer? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         quizBinding =   ActivityQuizBinding.inflate(layoutInflater)
        setContentView(quizBinding.root)

        val bundle  : Bundle? = intent.extras
        if(bundle != null){
            questionary  = bundle.getParcelableArrayList<QuestionModel>("questionary") as ArrayList<QuestionModel>
        }

        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

       quizAdapter = QuizAdapter(this,supportFragmentManager,quizViewModel.buildFragments(this,questionary))

        quizBinding.apply {
            this.viewPager.offscreenPageLimit = 0
            this.viewPager.adapter = quizAdapter
            quizAdapter.notifyDataSetChanged()
        }
        
        quizBinding.viewPager.setOnTouchListener(View.OnTouchListener { v, event ->
            return@OnTouchListener true
        })

        totalScore = questionary.size  * 20


    }

    override fun onStart() {
        super.onStart()
        reverseTimer(30,quizBinding.tvTimer)

        GlobalBus().getBus()!!.register(this)

    }

    override fun onStop() {
        super.onStop()
        GlobalBus().getBus()!!.unregister(this)

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public fun getMessage( questionAttemptEvent: QuestionAttemptEvent) {
        if( questionAttemptEvent.isTimeForQuestionOver || questionAttemptEvent.isSkipped || questionAttemptEvent.isNotAttempted || questionAttemptEvent.isRight || questionAttemptEvent.isWrong){
            calculateScore(questionAttemptEvent)

        }

    }

    fun calculateScore(questionAttemptEvent: QuestionAttemptEvent) {


        if(questionAttemptEvent.isRight){
            obtainScore += 20
        }else if(questionAttemptEvent.isWrong){
            obtainScore -= 10
        }else if(questionAttemptEvent.isSkipped){
            obtainScore -= 5
        }
        moveViewPager()
    }

    fun reverseTimer(Seconds: Int, tv: TextView) {
        timer = object : CountDownTimer((Seconds * 1000 + 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val progress = (millisUntilFinished / 1000).toInt()

                val timeInFormat = String.format(
                    Locale.getDefault(), "%02d : %02d ",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60
                )

                tv.text =  "${quizBinding.viewPager.currentItem +1}/ ${questionary.size}      Time Remain $timeInFormat"

            }

            override fun onFinish() {
                obtainScore -= 5
                moveViewPager()
            }
        }

        timer!!.start()
    }

     fun moveViewPager(){
         cancelTimer()

         if(quizBinding.viewPager.currentItem +1 ==  questionary.size ){
            val intent = Intent(this, CongratulationActivity::class.java)
            intent.putExtra("total_score", totalScore)
            intent.putExtra("obtain_score", obtainScore)
            startActivity(intent)
            finish()

        }else{
            quizBinding.viewPager.setCurrentItem(quizBinding.viewPager.currentItem + 1)
            reverseTimer(30,quizBinding.tvTimer)
        }
    }

    fun cancelTimer(){
        if(timer != null){
            timer!!.cancel()
        }
    }




}



