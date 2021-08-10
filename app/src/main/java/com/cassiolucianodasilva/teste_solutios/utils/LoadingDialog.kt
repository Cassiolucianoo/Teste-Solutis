package com.cassiolucianodasilva.teste_solutios.utils

import android.app.Activity
import android.app.AlertDialog
import com.cassiolucianodasilva.teste_solutios.R

class LoadingDialog(val mActivity: Activity) {
    private lateinit var isdialog: AlertDialog
    fun startLoading() {

        /**set View*/
        val inflater = mActivity.layoutInflater
        val dialogView = inflater.inflate(R.layout.loading_item, null)

        /**set   val  handler = Handler()
        handler.postDelayed(object : Runnable {
        override fun run() {
        loading.isDismiss()
        }

        },5000) Dialog*/
        val bulider = AlertDialog.Builder(mActivity)
        bulider.setView(dialogView)
        bulider.setCancelable(false)
        isdialog = bulider.create()
        isdialog.show()
    }

    fun isDismiss() {
        isdialog.dismiss()
    }


}