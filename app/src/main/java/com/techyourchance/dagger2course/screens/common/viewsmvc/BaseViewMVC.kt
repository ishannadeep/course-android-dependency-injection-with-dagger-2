package com.techyourchance.dagger2course.screens.common.viewsmvc

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes

open class BaseViewMVC<LISTENER_TYPE>(
    inflater: LayoutInflater,
    viewGroup: ViewGroup?,
    @LayoutRes layoutID: Int
) {

    val listeners = HashSet<LISTENER_TYPE>()
    var rootView: View = inflater.inflate(layoutID, viewGroup)
    protected val context: Context get() =  rootView.context

    fun registerListeners(listener: LISTENER_TYPE) {
        listeners.add(listener)
    }

    fun unregisterListeners(listener: LISTENER_TYPE) {
        listeners.remove(listener)
    }

    protected fun <T : View?> findViewById(@IdRes id: Int): T {
        return rootView.findViewById<T>(id)
    }
}