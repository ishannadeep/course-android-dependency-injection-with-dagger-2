package com.techyourchance.dagger2course.screens.questionslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.dagger2course.common.dependencyInjection.Service
import com.techyourchance.dagger2course.questions.FetchQuestionsUseCase
import com.techyourchance.dagger2course.questions.Question
import com.techyourchance.dagger2course.screens.common.ScreensNavigator
import com.techyourchance.dagger2course.screens.common.dialogs.DialogsNavigator
import com.techyourchance.dagger2course.screens.common.fragments.BaseFragment
import com.techyourchance.dagger2course.screens.common.viewsmvc.QuestionsListViewMVC
import com.techyourchance.dagger2course.screens.common.viewsmvc.ViewMVCFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

class QuestionsListFragment : BaseFragment(), QuestionsListViewMVC.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    @field:Service
    private lateinit var fetchQuestionsUseCase: FetchQuestionsUseCase

    @field:Service
    private lateinit var dialogsNavigator: DialogsNavigator

    @field:Service
    private lateinit var screensNavigator: ScreensNavigator

    @field:Service
    private lateinit var viewMVCFactory: ViewMVCFactory

    private lateinit var viewMVC: QuestionsListViewMVC

    private var isDataLoaded = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injector.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewMVC = viewMVCFactory.newQuestionListViewMvc(container)
        return viewMVC.rootView
    }

    override fun onStart() {
        super.onStart()
        viewMVC.registerListeners(this)
        if (!isDataLoaded) {
            fetchQuestions()
        }
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
        viewMVC.unregisterListeners(this)
    }

    private fun fetchQuestions() {
        coroutineScope.launch {
            viewMVC.showProgressIndication()
            try {
                val result = fetchQuestionsUseCase.fetchQuestions()
                when (result) {
                    is FetchQuestionsUseCase.Result.Success -> {
                        viewMVC.bindQuestions(result.questions)
                        isDataLoaded = true
                    }

                    is FetchQuestionsUseCase.Result.Failure -> onFetchFailed()
                }
            } finally {
                viewMVC.hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed() {
        dialogsNavigator.showServerError();
    }

    override fun onClickRefresh() {
        fetchQuestions()
    }

    override fun onClickItem(question: Question) {
        screensNavigator.navigateToDetail(question)
    }
}