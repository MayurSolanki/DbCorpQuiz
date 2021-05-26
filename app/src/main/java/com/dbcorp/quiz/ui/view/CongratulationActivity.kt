package com.dbcorp.quiz.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dbcorp.quiz.databinding.ActivityCongratulationBinding
import com.dbcorp.quiz.databinding.ActivityMainBinding


class CongratulationActivity : AppCompatActivity() {

    var bundle : Bundle? = null
    var total : Int = 0
    var obtain : Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityCongratulationBinding = ActivityCongratulationBinding.inflate(layoutInflater)
        setContentView(activityCongratulationBinding.root)

        bundle =  intent.extras

        if(bundle != null){
            total =    bundle!!.getInt("total_score")
            obtain =    bundle!!.getInt("obtain_score")
        }

        activityCongratulationBinding.tvScore.text = "Congratulations \n Your score is $obtain/$total "

        activityCongratulationBinding.btnPlayAgain.setOnClickListener {
            finish()
        }



    }

}