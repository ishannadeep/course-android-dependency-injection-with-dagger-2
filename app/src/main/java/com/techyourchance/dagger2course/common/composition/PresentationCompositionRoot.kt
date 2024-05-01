package com.techyourchance.dagger2course.common.composition

import com.techyourchance.dagger2course.questions.FetchQuestionDetailsUseCase
import com.techyourchance.dagger2course.questions.FetchQuestionsUseCase
import com.techyourchance.dagger2course.screens.common.dialogs.DialogsNavigator
import com.techyourchance.dagger2course.screens.common.viewsmvc.ViewMVCFactory

class PresentationCompositionRoot(private val activityCompositionRoot: ActivityCompositionRoot) {

    private val layoutInflater get() = activityCompositionRoot.layoutInflater
    private val supportFragmentManager get() = activityCompositionRoot.supportFragmentManager
    private val stackoverflowApi get() = activityCompositionRoot.stackoverflowApi

    val viewMvcFactory: ViewMVCFactory get() = ViewMVCFactory(layoutInflater)
    val dialogsNavigator get() = DialogsNavigator(supportFragmentManager)//by lazy is not needed because we don't keep any state in this obj
    val screensNavigator get() = activityCompositionRoot.screensNavigator

    val fetchQuestionDetailsUseCase: FetchQuestionDetailsUseCase
        get() = FetchQuestionDetailsUseCase(
            stackoverflowApi
        )
    val fetchQuestionsUseCase: FetchQuestionsUseCase
        get() = FetchQuestionsUseCase(
            stackoverflowApi
        )
}