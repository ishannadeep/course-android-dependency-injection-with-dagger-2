package com.techyourchance.dagger2course.screens.questiondetails

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import com.techyourchance.dagger2course.questions.FetchQuestionDetailsUseCase
import com.techyourchance.dagger2course.screens.common.ScreensNavigator
import com.techyourchance.dagger2course.screens.common.activities.BaseActivity
import com.techyourchance.dagger2course.screens.common.dialogs.DialogsNavigator
import com.techyourchance.dagger2course.screens.common.viewsmvc.QuestionDetailsViewMVC
import kotlinx.coroutines.*

class QuestionDetailsActivity : BaseActivity(), QuestionDetailsViewMVC.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private lateinit var questionId: String
    private lateinit var questionDetailsViewMVC: QuestionDetailsViewMVC
    private lateinit var dialogsNavigator: DialogsNavigator
    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var fetchQuestionDetailsUseCase: FetchQuestionDetailsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        questionDetailsViewMVC = compositionRoot.viewMvcFactory.newQuestionDetailsViewMVC(null)
        setContentView(questionDetailsViewMVC.rootView)

        // retrieve question ID passed from outside
        questionId = intent.extras!!.getString(EXTRA_QUESTION_ID)!!
        dialogsNavigator = compositionRoot.dialogsNavigator
        screensNavigator = compositionRoot.screensNavigator
        fetchQuestionDetailsUseCase = compositionRoot.fetchQuestionDetailsUseCase
    }

    override fun onStart() {
        super.onStart()
        questionDetailsViewMVC.registerListeners(this)
        fetchQuestionDetails()
    }

    override fun onStop() {
        super.onStop()
        coroutineScope.coroutineContext.cancelChildren()
        questionDetailsViewMVC.unregisterListeners(this)
    }

    private fun fetchQuestionDetails() {
        coroutineScope.launch {
            questionDetailsViewMVC.showProgressIndication()
            try {
                when (val result = fetchQuestionDetailsUseCase.fetchQuestionDetails(questionId)) {
                    is FetchQuestionDetailsUseCase.Result.Success -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            questionDetailsViewMVC.bindResult(
                                Html.fromHtml(
                                    result.question,
                                    Html.FROM_HTML_MODE_LEGACY
                                )
                            )
                        } else {
                            @Suppress("DEPRECATION")
                            questionDetailsViewMVC.bindResult(Html.fromHtml(result.question))
                        }
                    }

                    is FetchQuestionDetailsUseCase.Result.Failure -> onFetchFailed()
                }
            } finally {
                questionDetailsViewMVC.hideProgressIndication()
            }
        }
    }

    private fun onFetchFailed() {
        dialogsNavigator.showServerError()
    }

    companion object {
        const val EXTRA_QUESTION_ID = "EXTRA_QUESTION_ID"
        fun start(context: Context, questionId: String) {
            val intent = Intent(context, QuestionDetailsActivity::class.java)
            intent.putExtra(EXTRA_QUESTION_ID, questionId)
            context.startActivity(intent)
        }
    }

    override fun onBackPress() {
        screensNavigator.navigateBack()
    }
}