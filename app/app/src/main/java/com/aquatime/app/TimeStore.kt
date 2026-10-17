
package com.aquatime.app

import android.content.Context

object TimeStore {
    private const val PREFS = "aquatime_prefs"

    private fun prefs(context: Context) =
        context.applicationContext.getSharedPreferences(PREFS, Context.MODE_PRIVATE)

    fun getStart(context: Context, kind: TimerKind): Long =
        prefs(context).getLong(kind.key, 0L)

    fun setStart(context: Context, kind: TimerKind, millis: Long) {
        prefs(context).edit().putLong(kind.key, millis).apply()
    }

    fun startNow(context: Context, kind: TimerKind) {
        setStart(context, kind, System.currentTimeMillis())
    }
}
