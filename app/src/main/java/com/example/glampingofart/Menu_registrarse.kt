package com.example.glampingofart

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Menu_registrarse : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_registrarse)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registrarse)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Conexion a la base de datos FireBase

        val database = FirebaseDatabase.getInstance().reference
        lateinit var reg_login: FirebaseAuth

        // Contenedor de datos a guardar en firebase

        data class Usuarios(

            val nombre: String,
            val apellido: String,
            val identificacion: String,
            val correo: String,
            val usuario: String,
            val contraseña: String
        )


        // Asignación de los elementos visuales (activity_menu_registrarse.xml)

        val ins_nombre = findViewById<AppCompatEditText>(R.id.Edittext_Nombre)
        val ins_apellido = findViewById<AppCompatEditText>(R.id.Edittext_Apellido)
        val ins_id = findViewById<AppCompatEditText>(R.id.Edittext_ID)
        val ins_correo = findViewById<AppCompatEditText>(R.id.Edittext_correo)
        val ins_usuario = findViewById<AppCompatEditText>(R.id.Edittext_Usuario)
        val ins_contraseña = findViewById<AppCompatEditText>(R.id.Edittext_Contraseña)
        val ins_val_contraseña = findViewById<AppCompatEditText>(R.id.Edittext_Contraseñaconf)
        val btn_conf = findViewById<AppCompatButton>(R.id.btn_registrar)


        // Conversion  de los Edittext a String

        btn_conf.setOnClickListener {

            val nombre = ins_nombre.text.toString()
            val apellido = ins_apellido.text.toString()
            val identificacion = ins_id.text.toString()
            val correo = ins_correo.text.toString()
            val usuario = ins_usuario.text.toString()
            val contraseña = ins_contraseña.text.toString()
            val val_contraseña = ins_val_contraseña.text.toString()


            /* Validación de campos:
            is.Empty: Validar que las cajas de texto no se encuentren vacias
            .length < 6: el campo debe contener al menos 6 caracteres
             */

            if (nombre.isEmpty()){
                ins_nombre.setError("Requerido")
            }

            else if (apellido.isEmpty()){
                ins_apellido.setError("Requerido")
            }

            else if (identificacion.isEmpty()){
                ins_id.setError("Requerido")
            }

            else if (correo.isEmpty()){
                ins_correo.setError("Requerido")
            }

            else if (usuario.isEmpty()){
                ins_usuario.setError("Requerido")
            }

            else if (contraseña.isEmpty()){
                ins_contraseña.setError("Requerido")
            }

            else if (contraseña.length<6){
                ins_contraseña.setError("La contraseña debe tener al menos 6 caracteres")
            }

            else if (val_contraseña.isEmpty()){
                ins_val_contraseña.setError("Requerido")
            }

            /* Condicional

            Si algunos de los campos se encuentran vacios entonces imprimir el mensaje "ingrese los campos faltantes"

            Sino entonces
                Si al verificar que la contraseña ingresada en el campo "contraseña" es igual a "val_contraseña" entonces el contenedor "Ingresar_usuario" toma los datos asignados y guardarlos en la base de datos

                Sino entonces presenta un error con el mensaje "Las contraseñas no coinciden"
             */

            if (nombre.isEmpty() || apellido.isEmpty() || identificacion.isEmpty() || correo.isEmpty() || usuario.isEmpty() || contraseña.isEmpty() || contraseña.length<6 || val_contraseña.isEmpty()){
                Toast.makeText(this,"Ingrese los campos faltante", Toast.LENGTH_LONG).show()
            }

            else{

                if (contraseña == val_contraseña) {

                    val Ingresar_usuario = Usuarios(
                        nombre = nombre,
                        apellido = apellido,
                        identificacion = identificacion,
                        correo = correo,
                        usuario = usuario,
                        contraseña = contraseña
                    )

                    //Ingreso de los valores a la base de datos de realtime firebase bajo la raiz /Usuarios/#identificacion

                    database.child("Usuarios").child(identificacion).setValue(Ingresar_usuario).addOnSuccessListener {

                        Toast.makeText(this,"Datos ingresados correctamente",Toast.LENGTH_LONG).show()

                        //Limpiar los campos al presionar el boton "Registrar"

                        ins_nombre.setText("")
                        ins_apellido.setText("")
                        ins_id.setText("")
                        ins_correo.setText("")
                        ins_usuario.setText("")
                        ins_contraseña.setText("")
                        ins_val_contraseña.setText("")

                        /* Ingreso de los valores (correo y contraseña) a la base de datos de firebase Authentication para el inicio de sesión

                        Si los datos son correctos imprimir el mensaje "Usuario registrado con éxito" y volver a la activity LoginActivity

                        Sino imprimir el mensaje "Error al guardar: #Error
                         */

                        reg_login = FirebaseAuth.getInstance()
                        reg_login.createUserWithEmailAndPassword(correo, contraseña).addOnCompleteListener { task ->

                                if (task.isSuccessful) {
                                    Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
                                }

                                else {
                                    Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                            }

                        val volver = Intent(this, loginactivity::class.java)
                        startActivity(volver)
                    }

                        .addOnFailureListener { error ->
                            Toast.makeText(this, "Error al guardar: ${error.message}", Toast.LENGTH_LONG).show()}

                } else {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
                }
            }
        }

        // Botón que permite volver a la activity LoginActivity

        val btn_atras = findViewById<AppCompatButton>(R.id.btn_volver)

        btn_atras.setOnClickListener{
            val atras = Intent(this, loginactivity::class.java)
            startActivity(atras)
        }
    }
}
