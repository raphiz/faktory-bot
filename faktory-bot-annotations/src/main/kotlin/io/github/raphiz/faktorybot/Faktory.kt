package io.github.raphiz.faktorybot

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Faktory(val withInsert: Boolean = false)