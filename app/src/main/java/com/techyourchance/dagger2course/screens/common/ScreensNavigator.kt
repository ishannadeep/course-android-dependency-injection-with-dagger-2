package com.techyourchance.dagger2course.screens.common

import android.app.Activity
import com.techyourchance.dagger2course.questions.Question
import com.techyourchance.dagger2course.screens.questiondetails.QuestionDetailsActivity

class ScreensNavigator(private val activity: Activity) {

    fun navigateBack() {
        activity.onBackPressed()
    }

    fun navigateToDetail(question: Question) {
        QuestionDetailsActivity.start(activity, question.id)
    }
}