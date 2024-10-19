package com.example.taskbiddex.common.core

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView.ScaleType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.example.taskbiddex.MyApplication
import com.example.taskbiddex.R
import com.example.taskbiddex.common.utils.dialogs.AppDialog
import com.google.android.material.snackbar.Snackbar
import com.tailors.doctoria.application.core.BaseViewModel
import com.tailors.doctoria.utils.dialogs.ProgressDialog
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType

/*
    created at 01/01/2024
    by Abdallah Marwad
    abdallahshehata311as@gmail.com
 */
abstract class BaseActivityMVVM<VB : ViewBinding, VM : BaseViewModel> : AppCompatActivity() {
    protected val binding by lazy { initBinding() }
    protected lateinit var viewModel: VM
    private val progressDialog by lazy { ProgressDialog() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[getViewModelClass()]
        noInternetCallBack()
        enterAnimation()
        exitAnimation()
    }

    protected abstract fun getViewModelClass(): Class<VM>
    protected fun showProgressDialog() {
        progressDialog.showProgressDialog(this)
    }

    protected fun hideProgressDialog() {
        progressDialog.dismissProgress()
    }

    @Suppress("UNCHECKED_CAST")
    private fun initBinding(): VB {
        val superclass = javaClass.genericSuperclass as ParameterizedType
        val method = (superclass.actualTypeArguments[0] as Class<Any>)
            .getDeclaredMethod("inflate", LayoutInflater::class.java)
        return method.invoke(null, layoutInflater) as VB
    }

    protected fun showLongToast(msg: String) {
        Toast.makeText(
            this,
            msg,
            Toast.LENGTH_LONG
        ).show()
    }

    protected fun showShortToast(msg: String) {
        Toast.makeText(
            this,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    protected fun showLongSnackBar(msg: String) {
        Snackbar.make(
            binding.root,
            msg,
            Snackbar.LENGTH_LONG
        ).show()
    }

    protected fun showShortSnackBar(msg: String) {
        Snackbar.make(
            binding.root,
            msg,
            Snackbar.LENGTH_SHORT
        ).show()
    }

    protected open fun noInternetCallBack() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.noInternet.collect {
                    showShortSnackBar(resources.getString(R.string.check_internet_connection))
                }
            }
        }
    }

    protected fun showDialogWithMsg(
        message: String?, imgId: Int = R.drawable.err_img,
        scaleType: ScaleType = ScaleType.CENTER_INSIDE,
        cancelable: Boolean = true, onClick: () -> Unit = {}

    ) {
        val dialog = AppDialog()
        dialog.showDialog(
            this,
            "",
            message,
            MyApplication.myAppContext.resources.getString(R.string.cancel),
            "",
            imgId,
            {
                dialog.dismiss()
                onClick()
            },
            {
                dialog.dismiss()
            },
            showNegativeBtn = false,
            cancelable = cancelable,
            scaleType
        )
    }

    private fun enterAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            overrideActivityTransition(OVERRIDE_TRANSITION_OPEN, R.anim.from_left, R.anim.to_right)
    }

    private fun exitAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            overrideActivityTransition(OVERRIDE_TRANSITION_CLOSE, R.anim.from_right, R.anim.to_left)
    }

    protected fun enterAnimationDeprecated() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            overridePendingTransition(R.anim.from_left, R.anim.to_right)
    }

    override fun finish() {
        super.finish()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
            overridePendingTransition(R.anim.from_right, R.anim.to_left)
    }

    fun openFiles(reqCode: Int) {
        var chooseFile = Intent(Intent.ACTION_GET_CONTENT)
        chooseFile.type = "*/*"
        chooseFile.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        chooseFile = Intent.createChooser(chooseFile, "Choose a file")
        startActivityForResult(chooseFile, reqCode)
    }

    protected fun getResourceString(id : Int) =
        MyApplication.myAppContext.resources.getString(id)
    protected fun getResourceDrawable(id : Int) =
        MyApplication.myAppContext.resources.getDrawable(id)

}