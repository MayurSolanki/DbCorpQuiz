package com.dbcorp.quiz.ui.utitility


data class QuestionAttemptEvent(
     var isTimeForQuestionOver :Boolean,
     var isSkipped : Boolean,
     var isNotAttempted : Boolean,
     var isRight : Boolean,
     var isWrong : Boolean)
