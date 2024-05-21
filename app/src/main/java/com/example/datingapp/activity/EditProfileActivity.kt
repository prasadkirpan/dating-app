import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.datingapp.R
import com.example.datingapp.databinding.ActivityEditProfileBinding
import com.example.datingapp.databinding.ActivityRegisterBinding

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var editTextFullName: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding= ActivityEditProfileBinding.inflate(layoutInflater)

        // Initialize views
        editTextFullName = findViewById(R.id.editTextFullName)
        editTextEmail = findViewById(R.id.editTextEmail)
        buttonSave = findViewById(R.id.buttonSave)

        // Set click listener for the Save button
        binding.buttonSave.setOnClickListener {
            // Get text from EditText fields
            val fullName = editTextFullName.text.toString()
            val email = editTextEmail.text.toString()

            // Perform validation if needed

            // Save changes (e.g., update user profile in the database)
            // Replace this with your actual logic to save changes

            // Show a toast indicating that changes are saved
            Toast.makeText(this, "Changes saved successfully!", Toast.LENGTH_SHORT).show()

            // Finish the activity
            finish()
        }
    }
}


//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.example.datingapp.R
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.firestore.FirebaseFirestore
//
//class EditProfileActivity : AppCompatActivity() {
//
//    private lateinit var fullNameEditText: EditText
//    private lateinit var emailEditText: EditText
//    private lateinit var saveButton: Button
//
//    private lateinit var firebaseAuth: FirebaseAuth
//    private lateinit var firestore: FirebaseFirestore
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_edit_profile)
//
////        fullNameEditText = findViewById(R.id.editTextFullName)
////        emailEditText = findViewById(R.id.editTextEmail)
////        saveButton = findViewById(R.id.buttonSave)
////
////        firebaseAuth = FirebaseAuth.getInstance()
////        firestore = FirebaseFirestore.getInstance()
//
//        // Fetch current user data and populate the EditText fields
////        fetchCurrentUser()
////
////        saveButton.setOnClickListener {
////            val newFullName = fullNameEditText.text.toString()
////            val newEmail = emailEditText.text.toString()
////
////            if (validateInput(newFullName, newEmail)) {
////                // Save changes to Firestore
////                saveChanges(newFullName, newEmail)
////            }
////        }
////    }
//
////    private fun fetchCurrentUser() {
////        // Fetch current user data from Firestore
////        val currentUser = firebaseAuth.currentUser
////        val currentUserDocRef = firestore.collection("users").document(currentUser!!.uid)
////
////        currentUserDocRef.get()
////            .addOnSuccessListener { documentSnapshot ->
////                val fullName = documentSnapshot.getString("fullName")
////                val email = documentSnapshot.getString("email")
////
////                fullNameEditText.setText(fullName)
////                emailEditText.setText(email)
////            }
////            .addOnFailureListener { e ->
////                Toast.makeText(this, "Failed to fetch user data: ${e.message}", Toast.LENGTH_SHORT).show()
////            }
////    }
////
////    private fun saveChanges(newFullName: String, newEmail: String) {
////        val currentUser = firebaseAuth.currentUser
////        val currentUserDocRef = firestore.collection("users").document(currentUser!!.uid)
////
////        val updatedData = hashMapOf(
////            "fullName" to newFullName,
////            "email" to newEmail
////        )
////
////        currentUserDocRef.update(updatedData as Map<String, Any>)
////            .addOnSuccessListener {
////                Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
////                finish() // Close this activity
////            }
////            .addOnFailureListener { e ->
////                Toast.makeText(this, "Failed to update profile: ${e.message}", Toast.LENGTH_SHORT).show()
////            }
//    }
//
//    private fun validateInput(fullName: String, email: String): Boolean {
//        if (fullName.isEmpty() || email.isEmpty()) {
//            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
//            return false
//        }
//
//        // You can add more validation rules here if needed (e.g., email format validation)
//
//        return true
//    }
//}
//
//
