
package com.aquatime.app

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    private val valueViews = mutableMapOf<TimerKind, TextView>()

    private fun dp(value: Int): Int =
        (value * resources.displayMetrics.density).toInt()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(24), dp(48), dp(24), dp(24))
            setBackgroundColor(Color.parseColor("#06202B"))
        }

        val title = TextView(this).apply {
            text = getString(R.string.app_name)
            textSize = 28f
            setTextColor(Color.parseColor("#7FFFEF"))
            gravity = Gravity.CENTER_HORIZONTAL
        }
        root.addView(title)

        for (kind in TimerKind.values()) {
            val label = TextView(this).apply {
                text = getString(kind.labelRes)
                textSize = 18f
                setTextColor(Color.WHITE)
                setPadding(0, dp(32), 0, dp(4))
            }

            val value = TextView(this).apply {
                textSize = 24f
                setTextColor(Color.parseColor("#7FFFEF"))
            }

            val button = Button(this).apply {
                text = getString(R.string.start_now)
                setOnClickListener {
                    TimeStore.startNow(this@MainActivity, kind)
                    WidgetRenderer.refreshAll(this@MainActivity)
                    refresh()
                }
            }

            valueViews[kind] = value
            root.addView(label)
            root.addView(value)
            root.addView(button)
        }

        val hint = TextView(this).apply {
            text = getString(R.string.add_widget_hint)
            textSize = 14f
            setTextColor(Color.parseColor("#99FFFFFF"))
            setPadding(0, dp(40), 0, 0)
        }
        root.addView(hint)

        setContentView(root)
    }

    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        for ((kind, view) in valueViews) {
            view.text = WidgetRenderer.formatElapsed(
                this,
                TimeStore.getStart(this, kind)
            )
        }
    }
}
