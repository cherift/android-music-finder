package com.example.myapplication.presentation.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.myapplication.R

class InternetDialogFragment(val fragment: Fragment) : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.error)
            .setMessage(R.string.no_internet)
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                    if (fragment is MusicPlayerFragment)
                        activity!!.supportFragmentManager.popBackStack()
                })
        return builder.create()
    }
}