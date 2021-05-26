package com.dbcorp.quiz.ui.utitility

import org.greenrobot.eventbus.EventBus


class GlobalBus {
    private var sBus: EventBus? = null
    fun getBus(): EventBus? {
        if (sBus == null) sBus = EventBus.getDefault()
        return sBus
    }
}