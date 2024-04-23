package com.techyourchance.dagger2course.screens.common.dialogs

import androidx.fragment.app.FragmentManager

class DialogsNavigator(private val supportFragmentManager: FragmentManager) {
    fun showServerError() {
        supportFragmentManager.beginTransaction()
            .add(ServerErrorDialogFragment.newInstance(), null)
            .commitAllowingStateLoss()
    }
}