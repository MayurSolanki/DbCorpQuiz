package com.dbcorp.quiz.ui.view

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter


class QuizAdapter(
    context : Context,
    fm: FragmentManager,
    fragmentsList: MutableList<Fragment>
) : FragmentPagerAdapter(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){

var list = mutableListOf<Fragment>()

    init {
        this.list = fragmentsList
    }



    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Fragment {
        return list.get(position)
    }
}