package no.bouvet.projectparking.fragments

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import no.bouvet.projectparking.LoginActivity
import no.bouvet.projectparking.R

class LoginWelcomeDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Du logger deg enkelt inn ved hjelp av mobilen din!")
                    .setTitle("Velkommen til Project Parking")
                    .setPositiveButton("Logg Inn", DialogInterface.OnClickListener { dialog, id ->
                        context?.let {
                            if (it is LoginActivity) {
                                it.performLogin()
                            }
                        }
                    }

                    ).setCancelable(false)
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }


}