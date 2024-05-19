package com.techyourchance.dagger2course.common.dependencyInjection

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.dagger2course.screens.common.ScreensNavigator

class ActivityCompositionRoot(
    private val activity: AppCompatActivity,
    private val appCompositionRoot: AppCompositionRoot
) {
    val screensNavigator: ScreensNavigator by lazy {
        ScreensNavigator(activity)
    }
    val stackoverflowApi get() = appCompositionRoot.stackoverflowApi

    val layoutInflater get() = LayoutInflater.from(activity)

    val supportFragmentManager get() = activity.supportFragmentManager//create extra variable because if we want to change where we get supportFragmentManager we only need to change here
}