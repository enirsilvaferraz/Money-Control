package com.system.moneycontrol.infrastructure

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.widget.ArrayAdapter
import com.system.moneycontrol.R
import com.system.moneycontrol.model.entities.bases.DialogItem

object MyViewUtils {

    fun showListDialog(context: Context, title: String, list: List<DialogItem>, callback: (DialogItem) -> Unit) {

        val arrayAdapter = ArrayAdapter<String>(context, R.layout.item_string, list.map { it.getDescription() })

        val function: (DialogInterface, Int) -> Unit = { dialogInterface, selectedIndex ->
            callback(list[selectedIndex])
            dialogInterface.dismiss()
        }

        AlertDialog.Builder(context, R.style.AppTheme_Dialog)
                .setTitle(title)
                .setSingleChoiceItems(arrayAdapter, -1, function)
                .create().show()
    }

}