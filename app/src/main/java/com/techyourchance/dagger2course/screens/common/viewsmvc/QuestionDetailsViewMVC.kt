package com.techyourchance.dagger2course.screens.common.viewsmvc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.techyourchance.dagger2course.R
import com.techyourchance.dagger2course.screens.common.toolbar.MyToolbar

class QuestionDetailsViewMVC(
    private val inflater: LayoutInflater,
    private val viewGroup: ViewGroup?
) {
    private var toolbar: MyToolbar
    private var swipeRefresh: SwipeRefreshLayout
    private var txtQuestionBody: TextView

    val rootView: View = inflater.inflate(R.layout.layout_question_details, viewGroup)
    private val context: Context = rootView.context
    private lateinit var listener: Listener
    private val listeners = HashSet<Listener>()

    interface Listener {
        fun onBackPress()
    }

    init {
        txtQuestionBody = findViewById(R.id.txt_question_body)

        // init toolbar
        toolbar = findViewById(R.id.toolbar)
        toolbar.setNavigateUpListener { listener.onBackPress() }

        // init pull-down-to-refresh (used as a progress indicator)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        swipeRefresh.isEnabled = false
    }

    fun <T : View?> findViewById(@IdRes id: Int): T {
        return rootView.findViewById<T>(id)
    }

    fun showProgressIndication() {
        swipeRefresh.isRefreshing = true
    }

    fun hideProgressIndication() {
        swipeRefresh.isRefreshing = false
    }

    fun bindResult(text: CharSequence) {
        txtQuestionBody.text = text
    }

    fun registerListeners(listener: Listener) {
        listeners.add(listener)
    }

    fun unregisterListeners(listener: Listener) {
        listeners.remove(listener)
    }
}