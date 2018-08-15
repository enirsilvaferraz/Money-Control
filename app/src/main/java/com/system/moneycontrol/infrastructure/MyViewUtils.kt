package com.system.moneycontrol.infrastructure

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.widget.ArrayAdapter
import android.widget.DatePicker
import com.system.moneycontrol.R
import com.system.moneycontrol.model.entities.bases.DialogItem
import java.util.*
import javax.inject.Inject

class MyViewUtils @Inject constructor(val context: Context) {

    fun showListDialog(title: String, list: List<DialogItem>, callback: (DialogItem) -> Unit) {

        val arrayAdapter = ArrayAdapter<String>(context, R.layout.item_list_single, list.map { it.getDescription() })

        val function: (DialogInterface, Int) -> Unit = { dialogInterface, selectedIndex ->
            callback(list[selectedIndex])
            dialogInterface.dismiss()
        }

        AlertDialog.Builder(context, R.style.AppTheme_Dialog)
                .setTitle(title)
                .setSingleChoiceItems(arrayAdapter, -1, function)
                .create().show()
    }

    fun showDatePicker(date: Date?, onResult: (Date) -> Unit) {

        val onDateSetListener: DatePickerDialog.OnDateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

                val calendar = Calendar.getInstance()
                calendar.set(year, month, dayOfMonth, 0, 0)

                onResult(calendar.time)
            }
        }

        val c = Calendar.getInstance()

        if (date != null) {
            c.time = date
        }

        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(context, onDateSetListener, year, month, day).show()
    }
}