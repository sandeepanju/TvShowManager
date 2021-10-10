package com.example.tvshowmanager

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.tvshowmanager.Constant.DD_MMM_YYYY
import com.example.tvshowmanager.Constant.SERVER_DATE_TIME_FORMAT
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.getInstance

@BindingAdapter("convertDateToRedableFormat")
fun formatDateTime(view: TextView, givenDate: String) {
    var formattedDate = ""
    // in some cases we are getting the date string itself as "null"
    // so we put the check to make it work
    if (givenDate.isNotEmpty() && givenDate != "null") {
        val format = SimpleDateFormat(SERVER_DATE_TIME_FORMAT, Locale.getDefault())
        val calendar: Calendar = Calendar.getInstance()
        format.timeZone = TimeZone.getTimeZone("UTC")
        calendar.time = format.parse(givenDate)
        val outputFormat = SimpleDateFormat(DD_MMM_YYYY, Locale.getDefault())
        outputFormat.timeZone = TimeZone.getDefault()
        formattedDate = outputFormat.format(calendar.time)
    }
    view.text = formattedDate
}

internal fun convertToDate(time: Long): String {
    val calendar: Calendar = getInstance()
    val format = SimpleDateFormat(DD_MMM_YYYY)
    format.timeZone = TimeZone.getDefault()
    calendar.timeInMillis = time
    return format.format(calendar.time)
}
