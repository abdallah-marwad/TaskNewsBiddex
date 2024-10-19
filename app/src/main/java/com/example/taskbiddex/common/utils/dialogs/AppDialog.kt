package com.example.taskbiddex.common.utils.dialogs

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import android.widget.TextView
import com.example.taskbiddex.R

class AppDialog {
    private lateinit var customDialog: AlertDialog

    fun showDialog(
        activity : Activity,
        title: String,
        msg: String?,
        btnPosTxt: String?,
        btnNegTxt: String?,
        imgID: Int = -1,
        posListener: View.OnClickListener?,
        negListener: View.OnClickListener?,
        showNegativeBtn: Boolean = true,
        cancelable: Boolean = false,
        scaleType: ScaleType = ScaleType.CENTER_INSIDE,
    ) {
        val dialogView: View = LayoutInflater.from(activity)
            .inflate(R.layout.app_dialog, null)
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setView(dialogView)
        customDialogConfugrations(dialogBuilder, cancelable)
        val btnPos = dialogView.findViewById<Button>(R.id.dialog_btn_pos)
        val btnNeg = dialogView.findViewById<Button>(R.id.dialog_btn_neg)
        val dialogTxtFail = dialogView.findViewById<TextView>(R.id.dialogTxt)
        val dialogTxtHint = dialogView.findViewById<TextView>(R.id.dialogTxtHint)
        val imgDialog = dialogView.findViewById<ImageView>(R.id.imgDialog)
        if (title == "") dialogTxtFail.visibility = View.GONE
        if (imgID == -1) imgDialog.visibility = View.GONE
        if (showNegativeBtn.not()) btnNeg.visibility = View.GONE

        dialogTxtFail.text = title
        dialogTxtHint.text = msg
        btnPos.text = btnPosTxt
        btnNeg.text = btnNegTxt
        imgDialog.setImageResource(imgID)
        btnPos.setOnClickListener(posListener)
        btnNeg.setOnClickListener(negListener)
        imgDialog.scaleType = scaleType
        customDialog.show()
    }

    private fun customDialogConfugrations(dialogBuilder: AlertDialog.Builder, cancelable: Boolean) {
        customDialog = dialogBuilder.create()
        customDialog.setCancelable(cancelable)
        customDialog.setCanceledOnTouchOutside(cancelable)
        customDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        customDialog.window?.attributes?.windowAnimations = R.style.dialogAnimationVertically
    }

    fun dismiss() {
        if (customDialog.isShowing)
            customDialog.dismiss()
    }

}

