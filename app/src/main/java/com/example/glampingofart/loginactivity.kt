package com.example.glampingofart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

//Inicio de la clase

class loginactivity : AppCompatActivity() {

    //Inicializar Firebase Authentication

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loginactivity)

        /*
        Extraer datos de las cajas de texto hola x2

        box_usuario guarda el valor ingresado en usuario
        box_contraseña guarda el valor ingresado en contraseña
        boton_ingresar permite ingresar a la aplicación
         */

        val box_usuario = findViewById<AppCompatEditText>(R.id.Username)
        val box_contraseña = findViewById<AppCompatEditText>(R.id.Password)
        val boton_ingresar = findViewById<AppCompatButton>(R.id.Boton1)

        //  Conexión a firebase Authentication

        firebaseAuth = Firebase.auth

        /*
        Botón ingresar: al presionarlo verifica que el usuario y la contraseña ingresados en la app coincidan con las credenciales almacenadas en firebase authentication
        box_usuario.text.toString(): Convertir lo ingresado en box_usuario a string
        box_contraseña.text.toString(): Convertir lo ingresado en box_contraseña a string
         */

        boton_ingresar.setOnClickListener {
            signIN(box_usuario.text.toString(), box_contraseña.text.toString())
        }

        /*
        Boton crear_cuenta

        Al presionarlo redirige a la activity Menu_registrarse
         */

        val crear_cuenta = findViewById<AppCompatButton>(R.id.reg_cuenta)
        crear_cuenta.setOnClickListener {
            val reg_users = Intent(this, Menu_registrarse::class.java)
            startActivity(reg_users)
        }
    }

    //Metodo de conexion entre la app y firebase authentication

    private fun signIN(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val ingreso = Intent(this, DesktopActivity::class.java)
                    Toast.makeText(this, "Bienvenido", Toast.LENGTH_LONG).show()
                    startActivity(ingreso)
                } else {
                    println("Usuario o contraseña incorrecta")
                }
            }
    }
}


