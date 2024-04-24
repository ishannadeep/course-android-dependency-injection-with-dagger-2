package com.techyourchance.dagger2course.screens.questiondetails

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.dagger2course.Constants
import com.techyourchance.dagger2course.MyApplication
import com.techyourchance.dagger2course.networking.StackoverflowApi
import com.techyourchance.dagger2course.questions.FetchQuestionDetailsUseCase
import com.techyourchance.dagger2course.screens.common.ScreensNavigator
import com.techyourchance.dagger2course.screens.common.dialogs.DialogsNavigator
import com.techyourchance.dagger2course.screens.common.viewsmvc.QuestionDetailsViewMVC
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class QuestionDetailsActivity : AppCompatActivity(), QuestionDetailsViewMVC.Listener {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)


    private lateinit var stackoverflowApi: StackoverflowApi

    private lateinit var questionId: String
    private lateinit var questionDetailsViewMVC: QuestionDetailsViewMVC
    private lateinit var dialogsNavigator: DialogsNavigator
    private lateinit var screensNavigator: ScreensNavigator
    private lateinit var fetchQuestionDetailsUseCase: FetchQuestionDetailsUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        questionDetailsViewMVC = QuestionDetailsViewMVC(LayoutInflater.from(this), null)
        setContentView(questionDetailsViewMVC.rootView)

        // init retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        stackoverflowApi = retrofit.create(StackoverflowApi::class.java)

        // retrieve question ID passed from outside
        questionId = intent.extras!!.getString(EXTRA_QUESTION_ID)!!
        dialogsNavigator = DialogsNavigator(supportFragmentManager)
        screensNavigator = ScreensNavigator(this)
        fetchQuestionDetailsUseCase =
            FetchQuestionDetailsUseCase((application as MyApplication).retrofit)
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