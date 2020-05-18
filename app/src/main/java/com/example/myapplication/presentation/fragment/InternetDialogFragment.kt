package com.example.myapplication.presentation.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import com.example.myapplication.R

class InternetDialogFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.error)
            .setMessage(R.string.no_internet)
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                    activity!!.supportFragmentManager!!.popBackStack()
                })
        return builder.create()
    }
}