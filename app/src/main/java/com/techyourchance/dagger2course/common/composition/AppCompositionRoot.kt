package com.techyourchance.dagger2course.common.composition

import androidx.annotation.UiThread
import com.techyourchance.dagger2course.Constants
import com.techyourchance.dagger2course.networking.StackoverflowApi
import com.techyourchance.dagger2course.questions.FetchQuestionDetailsUseCase
import com.techyourchance.dagger2course.questions.FetchQuestionsUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
@UiThread
class AppCompositionRoot {

    private var _retrofit: Retrofit? = null

    private val retrofit: Retrofit = if (_retrofit == null) {
        _retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        _retrofit!!
    } else {
        _retrofit!!
    }


    private val stackoverflowApi: StackoverflowApi = retrofit.create(StackoverflowApi::class.java)

    val fetchQuestionDetailsUseCase: FetchQuestionDetailsUseCase
        get() = FetchQuestionDetailsUseCase(
            stackoverflowApi
        )
    val fetchQuestionsUseCase: FetchQuestionsUseCase
        get() = FetchQuestionsUseCase(
            stackoverflowApi
        )

}