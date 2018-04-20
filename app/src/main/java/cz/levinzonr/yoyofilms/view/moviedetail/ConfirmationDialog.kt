package cz.levinzonr.yoyofilms.view.moviedetail

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AlertDialog
import cz.levinzonr.yoyofilms.R

class ConfirmationDialog : DialogFragment() {

    private lateinit var callback: (Boolean) -> Unit


    companion object {

        private const val TAG = "ConfirmationDialog"

        fun show(fragmentManager: FragmentManager, callback: (Boolean) -> Unit) {
            var fragment = fragmentManager.findFragmentByTag(TAG) as? ConfirmationDialog
            if (fragment == null) fragment = ConfirmationDialog()
            fragment.callback = callback
            fragment.show(fragmentManager, TAG)
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
                .setTitle(getString(R.string.confirmation_title))
                .setMessage(getString(R.string.confirmation_msg))
                .setNegativeButton(android.R.string.no,{_,_ ->callback(false)})
                .setPositiveButton(android.R.string.yes, {_,_ -> callback(true)})
                .create()
    }

    override fun onPause() {
        super.onPause()
        dismiss()
    }

}