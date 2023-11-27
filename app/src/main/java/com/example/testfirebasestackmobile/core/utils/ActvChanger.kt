package com.example.testfirebasestackmobile.core.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.testfirebasestackmobile.ui.dialog.LoadingDialog

class ActvChanger {

    companion object {
        private fun goToAnotherView(activity: Activity, destiny: Class<*>) {
            val intent = Intent(activity, destiny)
            activity.startActivity(intent)
        }
        fun use(activity: Activity, destiny: Class<*>, loginOrLogout: Boolean) {
            if (loginOrLogout) {
                val dialog = LoadingDialog(activity)
                dialog.showAlertDialog()

                Handler(Looper.getMainLooper()).postDelayed({
                    dialog.dismissAlertDialog()

                    goToAnotherView(activity, destiny)
                    activity.finish()
                }, 500)
            }
            else {
                goToAnotherView(activity, destiny)
            }
        }
    }
}