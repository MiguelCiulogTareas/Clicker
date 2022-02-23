package ciulog.miguel.clicker

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    var cuenta: Int = 0
    var cosa: String? = "Clicks"
    lateinit var tv_cuenta: TextView
    lateinit var et_what: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn_suma: Button = findViewById(R.id.btn_suma)
        val btn_resta: Button = findViewById(R.id.btn_minus)
        val btn_borrar: Button = findViewById(R.id.btn_clear)
        et_what= findViewById(R.id.et_what)
        tv_cuenta = findViewById(R.id.tv_cuenta)

        btn_suma.setOnClickListener{
            cuenta++
            tv_cuenta.setText("$cuenta")
        }
        btn_resta.setOnClickListener{
            cuenta--
            tv_cuenta.setText("$cuenta")
        }
        btn_borrar.setOnClickListener{
            val alertDialog: AlertDialog? = this?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton("Borrar",
                        DialogInterface.OnClickListener { dialog, id ->
                            // User clicked OK button
                            cuenta = 0
                            tv_cuenta.setText("$cuenta")
                        })
                    setNegativeButton("Cancelar",
                        DialogInterface.OnClickListener { dialog, id ->
                            // User cancelled the dialog
                        })
                }
                // Set other dialog properties
                builder?.setMessage("Seguro que desea eliminar la cuenta?")
                    .setTitle("Eliminar cuenta")

                // Create the AlertDialog
                builder.create()
            }
            alertDialog?.show()
        }
    }

    override fun onPause() {
        super.onPause()

        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putInt("contador", cuenta)
        editor.putString("cosa", cosa)
        editor.commit()
    }

    override fun onResume() {
        super.onResume()
        val sharedPref = this?.getPreferences(Context.MODE_PRIVATE)
        cuenta = sharedPref.getInt("contador", 0)
        cosa = sharedPref.getString("cosa", "Clicks")
        tv_cuenta.text = "$cuenta"
        et_what.setText(cosa)
    }
}