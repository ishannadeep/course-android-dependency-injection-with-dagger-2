package com.techyourchance.dagger2course.common.composition

import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.dagger2course.questions.FetchQuestionDetailsUseCase
import com.techyourchance.dagger2course.questions.FetchQuestionsUseCase
import com.techyourchance.dagger2course.screens.common.ScreensNavigator
import com.techyourchance.dagger2course.screens.common.dialogs.DialogsNavigator

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val appCompositionRoot: AppCompositionRoot
) {


    val screensNavigator: ScreensNavigator by lazy {
        ScreensNavigator(activity)
    }

    private val supportFragmentManager get() = activity.supportFragmentManager//create extra variable because if we want to change where we get supportFragmentManager we only need to change here

    val dialogsNavigator get() = DialogsNavigator(supportFragmentManager)//by lazy is not needed because we don't keep any state in this obj


    private val stackoverflowApi get() = appCompositionRoot.stackoverflowApi

    val fetchQuestionDetailsUseCase: FetchQuestionDetailsUseCase
        get() = FetchQuestionDetailsUseCase(
            stackoverflowApi
        )
    val fetchQuestionsUseCase: FetchQuestionsUseCase
        get() = FetchQuestionsUseCase(
            stackoverflowApi
        )

}