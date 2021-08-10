package com.cassiolucianodasilva.teste_solutios.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.cassiolucianodasilva.teste_solutios.service.constants.StatmentsConstants
import com.google.gson.Gson


open class BaseRepository(context: Context) {

    /**
     * Verifica se existe conexão com internet
     */

    val mContext: Context = context

    fun fail(code: Int) = code != StatmentsConstants.HTTP.SUCCESS

    fun failRespose(respose: String): String {
        return Gson().fromJson(respose, String::class.java)
    }


    fun isConnectionAvailable(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = cm.activeNetwork ?: return false
            val actNw = cm.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            cm.run {
                cm.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }

}