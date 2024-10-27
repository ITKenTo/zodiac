package com.example.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

abstract class CoreApplication : Application(), Application.ActivityLifecycleCallbacks,
    LifecycleEventObserver {

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    open fun onCreateLifecycleOwner() {}

    open fun onStartLifecycleOwner() {}

    open fun onResumeLifecycleOwner() {}

    open fun onPauseLifecycleOwner() {}

    open fun onStopLifecycleOwner() {}

    open fun onDestroyLifecycleOwner() {}

    open fun onAnyLifecycleOwner() {}

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                onCreateLifecycleOwner()
            }

            Lifecycle.Event.ON_START -> {
                onStartLifecycleOwner()
            }

            Lifecycle.Event.ON_RESUME -> {
                onResumeLifecycleOwner()
            }

            Lifecycle.Event.ON_PAUSE -> {
                onPauseLifecycleOwner()
            }

            Lifecycle.Event.ON_STOP -> {
                onStopLifecycleOwner()
            }

            Lifecycle.Event.ON_DESTROY -> {
                onDestroyLifecycleOwner()
            }

            Lifecycle.Event.ON_ANY -> {
                onAnyLifecycleOwner()
            }
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}