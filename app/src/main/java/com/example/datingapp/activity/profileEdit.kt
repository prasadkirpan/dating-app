package com.example.datingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.example.datingapp.MainActivity
import com.example.datingapp.R

class profileEdit : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextNumber: EditText
    private lateinit var editTextCity: EditText
    private lateinit var buttonSave: Button


    private lateinit var backPressButton: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_edit)


        name = findViewById(R.id.editTextFullName)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextNumber = findViewById(R.id.editTextNumber)
        editTextCity = findViewById(R.id.editTextCity)
        buttonSave = findViewById(R.id.buttonSave)




        buttonSave.setOnClickListener {
            saveChanges()
        }






        backPressButton = findViewById(R.id.backPress)

        // Set click listener for the back press button
        backPressButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }

    private fun saveChanges() {
      //  val fullName = name.text.toString().trim()
       // val email = editTextEmail.text.toString().trim()
     //   val number = editTextNumber.text.toString().trim()
       // val city = editTextCity.text.toString().trim()

        val fullName = intent.getStringExtra("fullName")
        val email = intent.getStringExtra("email")
        val number = intent.getStringExtra("number")
        val city = intent.getStringExtra("city")

        // Here you can perform any validation on the input data

        // You can also perform other operations like saving the data to a database
        // or sending it to a server

        // For now, let's just print the values to the logcat
        println("Full Name: $fullName")
        println("Email: $email")
        println("Number: $number")
        println("City: $city")
    }


}




