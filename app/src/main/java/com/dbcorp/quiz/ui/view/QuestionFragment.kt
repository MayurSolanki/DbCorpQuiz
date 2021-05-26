package com.dbcorp.quiz.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dbcorp.quiz.ui.utitility.GlobalBus
import com.dbcorp.quiz.ui.utitility.QuestionAttemptEvent
import com.dbcorp.quiz.databinding.FragQuestionBinding
import com.dbcorp.quiz.ui.viewmodel.QuestionModel
import com.dbcorp.quiz.ui.viewmodel.QuestionViewModel


class QuestionFragment : Fragment() {

    lateinit var questionViewModel: QuestionViewModel
    private var questionModel : QuestionModel? = null
    lateinit var fragQuestionBinding: FragQuestionBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        questionViewModel = ViewModelProvider(requireActivity()).get(QuestionViewModel::class.java)

    }


    fun newInstance(): QuestionFragment? {
        val args = Bundle()
        val fragment = QuestionFragment()
        fragment.arguments = args
        return fragment
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         fragQuestionBinding =  FragQuestionBinding.inflate(layoutInflater)

        val args = arguments
        if (args != null) {
            questionModel = args.getParcelable<QuestionModel>("questionModel")

        }

        fragQuestionBinding.apply {
            tvQuestion.text = questionModel!!.question
            rbOption1.text = questionModel!!.option1
            rbOption2.text = questionModel!!.option2
            rbOption3.text = questionModel!!.option3
            rbOption4.text = questionModel!!.option4
            rbOptionSkip.text = "Skip"

        }

        fragQuestionBinding.btnNext.setOnClickListener {

           val checkRbid =  fragQuestionBinding.rgOptions.checkedRadioButtonId

            fragQuestionBinding.root.findViewById<RadioButton>(checkRbid)?.let { rbSelected ->

                if(rbSelected.text.toString().equals("SKIP",ignoreCase = true)){
                    val quizAttemptEvent = QuestionAttemptEvent(false,true,false,false, false)
                    GlobalBus().getBus()!!.post(quizAttemptEvent)
                }else{
                    val result =   checkSelectedOptionIsRightOrWrong(questionModel,rbSelected.text.toString())

                    if(result){
                        val quizAttemptEvent = QuestionAttemptEvent(false,false,false,true, false)
                        GlobalBus().getBus()!!.post(quizAttemptEvent)

                    }else{
                        val quizAttemptEvent = QuestionAttemptEvent(false,false,false,false, true)
                        GlobalBus().getBus()!!.post(quizAttemptEvent)
                    }
                }


            }?:run{
                val quizAttemptEvent = QuestionAttemptEvent(false,false,true,false, false)
                GlobalBus().getBus()!!.post(quizAttemptEvent)
            }



        }


        return fragQuestionBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }




    fun checkSelectedOptionIsRightOrWrong(questionModel: QuestionModel?, text: String):Boolean{
        return text.equals(questionModel!!.answer,ignoreCase = false)

    }


}