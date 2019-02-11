package no.bouvet.projectparking.view.fragments

import android.support.v7.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import no.bouvet.projectparking.view.activities.LoginActivity

class LoginFailedDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Innloggin feilet")
                    .setPositiveButton("PRØV IGJEN", DialogInterface.OnClickListener { dialog, id ->
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