package com.example.testfirebasestackmobile.ui.dialog

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import com.example.testfirebasestackmobile.R
import com.example.testfirebasestackmobile.databinding.DialogLoadingBinding

class LoadingDialog(private val activity: Activity) {

    private lateinit var dialog: AlertDialog

    fun showAlertDialog() {
        val builder = AlertDialog.Builder(activity)
        val layInf = activity.layoutInflater

        with(builder) {
            setView(layInf.inflate(R.layout.dialog_loading, null))
            setCancelable(false)
        }

        dialog = builder.create()
        dialog.show()
    }

    fun dismissAlertDialog() {
        dialog.dismiss()
    }

}