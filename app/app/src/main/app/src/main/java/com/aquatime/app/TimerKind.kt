
package com.aquatime.app

enum class TimerKind(
    val key: String,
    val labelRes: Int,
    val provider: Class<*>
) {
    TOGETHER("together_start", R.string.together_label, TogetherTimeWidget::class.java),
    APART("apart_start", R.string.apart_label, TimeApartWidget::class.java)
}
