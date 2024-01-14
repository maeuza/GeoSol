package com.mezsoft.geosol.common.extensions


import android.content.Context
import android.widget.Toast
import java.text.NumberFormat
import java.util.Locale

class Extensions(private val context: Context) {


    fun showToast(text: String, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, text, length).show()

    }

    /*  fun formatDouble(number: Double): String {
          val format = NumberFormat.getInstance(Locale.getDefault())
          return format.format(number)
      }*/

}