package com.example.routerplusdata.utils

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(date: String?): String {
    if (date.isNullOrEmpty()) return "N/A"

    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))

        val parsedDate = inputFormat.parse(date)

        outputFormat.format(parsedDate!!)
    } catch (e: Exception) {
        "N/A"
    }
}