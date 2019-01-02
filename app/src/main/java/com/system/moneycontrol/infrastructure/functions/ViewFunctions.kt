package com.system.moneycontrol.infrastructure.functions

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.system.moneycontrol.R
import com.system.moneycontrol.model.entities.DialogItem
import java.util.*

object ViewFunctions {

    fun showListDialog(context: Context, title: String, list: List<DialogItem>, checkedItem: Int = -1, callback: (DialogItem) -> Unit) {

        val arrayAdapter = ArrayAdapter<String>(context, R.layout.item_list_single, android.R.id.text1, list.map { it.getDescription() })

        val function: (DialogInterface, Int) -> Unit = { dialogInterface, selectedIndex ->
            callback(list[selectedIndex])
            dialogInterface.dismiss()
        }

        AlertDialog.Builder(context, R.style.AppTheme_Dialog)
                .setTitle(title)
                .setSingleChoiceItems(arrayAdapter, checkedItem, function)
                .create().show()
    }

    fun showDatePicker(context: Context, date: Date?, onResult: (Date) -> Unit) {

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

    fun hideKeyboard(activity: FragmentActivity?, view: View?) {

        if (activity != null && view != null) {
            (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun showConfirmDialog(context: Context, title: String, content: String, calback: () -> Unit) {
        AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.system_yes) { _: DialogInterface, _: Int -> calback() }
                .setNegativeButton(R.string.system_no) { _: DialogInterface, _: Int -> }
                .create().show()
    }
}