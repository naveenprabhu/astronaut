package com.sapient.astronaut.base

import java.lang.ref.WeakReference

open class BasePreseneter<V> {

    lateinit var view : WeakReference<V>

    fun getView() : V {
        return view.get()!!
    }

    fun setView(view : V){
        this.view = WeakReference(view)
    }

}