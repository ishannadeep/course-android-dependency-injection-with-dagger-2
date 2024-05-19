package com.techyourchance.dagger2course.common.dependencyInjection;


import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;


@Target(allowedTargets = AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
public @interface Service {
}
