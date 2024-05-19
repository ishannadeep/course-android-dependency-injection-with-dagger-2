package com.techyourchance.dagger2course.screens.common.activities

import androidx.appcompat.app.AppCompatActivity
import com.techyourchance.dagger2course.MyApplication
import com.techyourchance.dagger2course.common.dependencyInjection.ActivityCompositionRoot
import com.techyourchance.dagger2course.common.dependencyInjection.AppCompositionRoot
import com.techyourchance.dagger2course.common.dependencyInjection.PresentationCompositionRoot
import com.techyourchance.dagger2course.common.dependnecyinjection.Injector

open class BaseActivity : AppCompatActivity() {

    private val appCompositionRoot: AppCompositionRoot get() = (application as MyApplication).appCompositionRoot
    val activityCompositionRoot: ActivityCompositionRoot by lazy {
        ActivityCompositionRoot(this, appCompositionRoot)
    }
    private val compositionRoot: PresentationCompositionRoot by lazy {
        PresentationCompositionRoot(activityCompositionRoot)
    }

    protected val injector get() = Injector(compositionRoot)

}