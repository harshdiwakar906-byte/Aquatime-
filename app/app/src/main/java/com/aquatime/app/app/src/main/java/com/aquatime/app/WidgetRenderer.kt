
package com.aquatime.app

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

object WidgetRenderer {

    fun formatElapsed(context: Context, startMillis: Long): String {
        if (startMillis <= 0L) return context.getString(R.string.not_set)

        val diff = maxOf(0L, System.currentTimeMillis() - startMillis)
        val minutes = diff / 60_000L
        val days = minutes / (60 * 24)
        val hours = (minutes / 60) % 24
        val mins = minutes % 60

        return "${days}d ${hours}h ${mins}m"
    }

    fun build(context: Context, kind: TimerKind): RemoteViews {
        val views = RemoteViews(context.packageName, R.layout.widget_glass)

        views.setTextViewText(
            R.id.widget_label,
            context.getString(kind.labelRes)
        )
        views.setTextViewText(
            R.id.widget_value,
            formatElapsed(context, TimeStore.getStart(context, kind))
        )

        val intent = Intent(context, MainActivity::class.java)
        val pending = PendingIntent.getActivity(
            context,
            kind.ordinal,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        views.setOnClickPendingIntent(R.id.widget_root, pending)
        return views
    }

    fun refreshAll(context: Context) {
        val manager = AppWidgetManager.getInstance(context)

        for (kind in TimerKind.values()) {
            val ids = manager.getAppWidgetIds(
                ComponentName(context, kind.provider)
            )

            if (ids.isNotEmpty()) {
                manager.updateAppWidget(ids, build(context, kind))
            }
        }
    }
}
