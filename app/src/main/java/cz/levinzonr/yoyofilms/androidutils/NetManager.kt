package cz.levinzonr.yoyofilms.androidutils

import android.content.Context
import android.net.NetworkInfo
import android.net.ConnectivityManager



class NetManager(val context: Context) {

    fun isConnected() : Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }


}