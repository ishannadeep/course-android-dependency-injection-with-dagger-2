package com.techyourchance.dagger2course.screens.questionslist

import android.os.Bundle
import com.techyourchance.dagger2course.R
import com.techyourchance.dagger2course.screens.common.activities.BaseActivity

class QuestionsListActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questionlist_activity_layout)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.questionList_activity_frame_layout, QuestionsListFragment())
                .commit()
        }
    }
}