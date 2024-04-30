package com.techyourchance.dagger2course.screens.common.viewsmvc

import android.view.LayoutInflater
import android.view.ViewGroup

class ViewMVCFactory(val layoutInflater: LayoutInflater) {

    fun newQuestionListViewMvc(parent: ViewGroup?): QuestionsListViewMVC {
        return QuestionsListViewMVC(layoutInflater, parent)
    }

    fun newQuestionDetailsViewMVC(parent: ViewGroup?): QuestionDetailsViewMVC {
        return QuestionDetailsViewMVC(layoutInflater, parent)
    }
}