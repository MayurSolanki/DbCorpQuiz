package com.dbcorp.quiz.ui.utitility

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow


class BroadcastEventBus {
    private val _events = MutableSharedFlow<String>()
    val events = _events.asSharedFlow() // read-only public view

    suspend fun postEvent(quizEvents: String) {
        _events.emit(quizEvents) // suspends until subscribers receive it
    }
}