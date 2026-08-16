package com.example.desafio1_dsm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class PromedioActivity : AppCompatActivity(){
    //llamamos a el xml y lo cargamos para que lo muestre
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promedio)

        //variables de los campos del xml para ocuparlos en el codigo
        val editNombre = findViewById<EditText>(R.id.editNombre)
        val editNota1 = findViewById<EditText>(R.id.editNota1)
        val editNota2 = findViewById<EditText>(R.id.editNota2)
        val editNota3 = findViewById<EditText>(R.id.editNota3)
        val editNota4 = findViewById<EditText>(R.id.editNota4)
        val editNota5 = findViewById<EditText>(R.id.editNota5)
        val btnCalcular = findViewById<Button>(R.id.btnCalcular)
        val txtResultado = findViewById<TextView>(R.id.txtResultado)

        //para que el boton ejecute lo que este adentro
        btnCalcular.setOnClickListener {
            //validacion para los campos vacios
            /*si alguna de las notas esta vacia mostrara el mensaje de que los complete
            * y asi no seguira con el calculo*/
            if (editNombre.text.isEmpty() || editNota1.text.isEmpty() || editNota2.text.isEmpty()
                || editNota3.text.isEmpty() || editNota4.text.isEmpty() || editNota5.text.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            /*Convertimos las notas que ingresamos de texto
            * a double osea numeros*/
            val notas = listOf(
                editNota1.text.toString().toDouble(),
                editNota2.text.toString().toDouble(),
                editNota3.text.toString().toDouble(),
                editNota4.text.toString().toDouble(),
                editNota5.text.toString().toDouble()
            )

            //para validar el rango de las notas
            if (notas.any { it < 0 || it > 10 }) /*si alguna nota no se encuentra entre
            0 y 10 mostrara un error*/{
                Toast.makeText(this, "Las notas no deben pasar de 10 o ser menor que 0",
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //cacular el promedio
            val promedio = calcularPromedio(notas)
            //si la val promedio es igual o mayor a 6
            //mostrara que aprobo y si no el else
            val estado = if (promedio >= 6) {
                "Aprobado"
            }
            else {
                "Reprobado"
            }

            //Mostrar resultado de el promedio
            //mostrara la nota con 2 decimales
            txtResultado.text = "El promedio del estudiante ${editNombre.text } es %.2f → ${estado}".format(promedio)
        }

        //boton para regresar
        val btnVolver = findViewById<Button>(R.id.btnVolver)
        btnVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    //la funcion de calcular promedio para que este separado
    private fun calcularPromedio(notas: List<Double>): Double {
        return notas.average()
    }

}