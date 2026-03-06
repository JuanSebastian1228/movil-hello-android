package com.ropero.helloandroid

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        // Referenciar elementos de la interfaz
        val textView = findViewById<TextView>(R.id.textView)
        val btnSaludar = findViewById<Button>(R.id.btnSaludar)
        // Variable contador
        var contador = 0
        // Asignar acción al botón
        btnSaludar.setOnClickListener {
            contador++
            textView.text = "Has hecho clic $contador veces"

            // Mostrar mensaje emergente
            Toast.makeText(
                this,
                "¡Botón presionado!",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}