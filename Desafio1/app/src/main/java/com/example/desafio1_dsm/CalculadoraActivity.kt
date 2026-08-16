package com.example.desafio1_dsm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.ImageButton
import android.widget.Toast
class CalculadoraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora)

        val num1 = findViewById<EditText>(R.id.editNumero1)
        val num2 = findViewById<EditText>(R.id.editNumero2)
        val txtResultado = findViewById<TextView>(R.id.txtResultadoCalc)

        val btnSuma = findViewById<ImageButton>(R.id.btnSuma)
        val btnResta = findViewById<ImageButton>(R.id.btnResta)
        val btnMultiplicacion = findViewById<ImageButton>(R.id.btnMultiplicacion)
        val btnDivision = findViewById<ImageButton>(R.id.btnDivision)
        val btnExponente = findViewById<ImageButton>(R.id.btnExponente)
        val btnRaiz = findViewById<ImageButton>(R.id.btnRaiz)

        fun obtenerNumero(edit: EditText): Double? {
            return edit.text.toString().toDoubleOrNull()
        }

        btnSuma.setOnClickListener {
            val a = obtenerNumero(num1)
            val b = obtenerNumero(num2)
            if (a != null && b != null) {
                mostrarResultado(txtResultado, a + b)
                guardarHistorial("Suma: $a + $b = ${a+b}")
            }
        }

        btnResta.setOnClickListener {
            val a = obtenerNumero(num1)
            val b = obtenerNumero(num2)
            if (a != null && b != null) {
                mostrarResultado(txtResultado, a - b)
                guardarHistorial("Resta: $a - $b = ${a-b}")
            }
        }

        btnMultiplicacion.setOnClickListener {
            val a = obtenerNumero(num1)
            val b = obtenerNumero(num2)
            if (a != null && b != null) {
                mostrarResultado(txtResultado, a * b)
                guardarHistorial("Multiplicación: $a * $b = ${a*b}")
            }
        }

        btnDivision.setOnClickListener {
            val a = obtenerNumero(num1)
            val b = obtenerNumero(num2)
            if (a != null && b != null) {
                if (b == 0.0) {
                    Toast.makeText(this, getString(R.string.errorDivision), Toast.LENGTH_SHORT).show()
                } else {
                    mostrarResultado(txtResultado, a / b)
                    guardarHistorial("División: $a / $b = ${a/b}")
                }
            }
        }

        btnExponente.setOnClickListener {
            val a = obtenerNumero(num1)
            val b = obtenerNumero(num2)
            if (a != null && b != null) {
                mostrarResultado(txtResultado, Math.pow(a, b))
                guardarHistorial("Exponente: $a ^ $b = ${Math.pow(a,b)}")
            }
        }

        btnRaiz.setOnClickListener {
            val a = obtenerNumero(num1)
            if (a != null) {
                mostrarResultado(txtResultado, Math.sqrt(a))
                guardarHistorial("Raíz cuadrada: √$a = ${Math.sqrt(a)}")
            }
        }
        //boton para regresar
        val btnVolver = findViewById<Button>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun mostrarResultado(txt: TextView, valor: Double) {
        txt.text = getString(R.string.resultadoCalc, valor)
    }

    // Guardar historial en almacenamiento interno
    private fun guardarHistorial(operacion: String) {
        openFileOutput("historial.txt", MODE_APPEND).use {
            it.write((operacion + "\n").toByteArray())
        }
    }
}