package com.tailors.doctoria.utils.dialogs

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.example.taskbiddex.R

class ProgressDialog {
    private var progressDialog: Dialog? = null
    fun showProgressDialog(activity: Activity) {
        progressDialog = Dialog(activity)
        val inflate = LayoutInflater.from(activity)
            .inflate(R.layout.loading_dialog, null)
        progressDialog!!.setContentView(inflate)
        progressDialog!!.setCancelable(false)
        progressDialog!!.window!!.setBackgroundDrawable(
            ColorDrawable(Color.TRANSPARENT)
        )
        if (activity.isFinishing.not())
            progressDialog!!.show()
    }
    fun dismissProgress(){
        if (progressDialog != null)
            if (progressDialog!!.isShowing)
                progressDialog!!.dismiss()
    }
}