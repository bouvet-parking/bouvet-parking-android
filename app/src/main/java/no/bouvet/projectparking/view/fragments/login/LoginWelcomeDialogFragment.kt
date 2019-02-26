package no.bouvet.projectparking.view.fragments.login

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import no.bouvet.projectparking.view.activities.LoginActivity

class LoginWelcomeDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Du logger deg enkelt inn via din Bouvet-konto!")
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