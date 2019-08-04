package com.george.flightcenter.internal

import kotlinx.coroutines.*

/**
 * Util method to deferred the instantiation of variables when are really needed by the view
 *
 * High order suspending function as a parameter
 */
fun<T> lazyDeferred(block: suspend CoroutineScope.() -> T) : Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            //only when deferred is called and lazy is called this will execute
            block.invoke(this)
        }
    }
}