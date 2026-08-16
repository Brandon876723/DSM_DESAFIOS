package com.example.desafio1_dsm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import android.content.Context
import android.os.Vibrator
import android.os.Build
import android.os.VibrationEffect
import android.content.Intent

class SalarioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salario)


        val editEmpleado = findViewById<EditText>(R.id.editEmpleado)
        val editSalario = findViewById<EditText>(R.id.editSalario)
        val btnCalcular = findViewById<Button>(R.id.btnCalcularSalario)
        val txtResultado = findViewById<TextView>(R.id.txtResultadoSalario)

        btnCalcular.setOnClickListener(){
            if (editEmpleado.text.isEmpty() || editSalario.text.isEmpty()) {
                editSalario.error = getString(R.string.errorSalario)
                vibrar()
            }

            val salarioBase = editSalario.text.toString().toDoubleOrNull()
            if (salarioBase == null || salarioBase <= 0) {
                editSalario.error = getString(R.string.errorSalario)
                vibrar()
                return@setOnClickListener
            }

            val afp = salarioBase * 0.0725
            val isss = salarioBase * 0.03
            val renta = calcularRenta(salarioBase)
            val salarioNeto = salarioBase - (afp + isss + renta)

            txtResultado.text = getString(
                R.string.resultadoSalario,
                editEmpleado.text,
                salarioBase,
                afp,
                isss,
                renta,
                salarioNeto
            )
        }

        //boton para regresar
        val btnVolver = findViewById<Button>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    //la tabla en tramos iria asi
    private fun calcularRenta(salario: Double): Double {
        return when {
            salario <= 472.0 -> 0.0
            salario <= 895.24 -> (salario - 472.0) * 0.10 + 17.67
            salario <= 2038.10 -> (salario - 895.24) * 0.20 + 60.00
            else -> (salario - 2038.10) * 0.30 + 288.57
        }
    }

    //para que pueda vibrar
    private fun vibrar() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(500)
        }
    }
}