package com.krishna.task2app

import android.app.AlertDialog
import android.content.Context


object Components {
    fun showDialogBox(context: Context, msg: String) {
        val builder1 = AlertDialog.Builder(context)
        builder1.setMessage(msg)
        builder1.setCancelable(false)

        builder1.setPositiveButton(
            "OK"
        ) { dialog, id -> dialog.cancel() }

        val alert11 = builder1.create()
        alert11.show()
    }
}