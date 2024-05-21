package com.example.datingapp

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.datingapp.activity.TermsConditionsActivity
import com.example.datingapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    //this is for navigation drawer
    var actionBarDrawerToggle : ActionBarDrawerToggle ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        var sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        actionBarDrawerToggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)

        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        binding.navigationView.setNavigationItemSelectedListener(this)

        val navController = findNavController(R.id.fragment)

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.rateUs -> {
                // Implement functionality to prompt user to rate the app
                // Example: navigate to Google Play Store
                val uri = Uri.parse("market://details?id=$packageName")
                val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                try {
                    startActivity(goToMarket)
                } catch (e: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=$packageName")))
                }




            } R.id.favourite -> {
            toggleFavorite()
            } R.id.shareApp -> {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome app!")
            sendIntent.type = "text/plain"
            startActivity(Intent.createChooser(sendIntent, "Share"))
            }

            R.id.termsCondition -> {
                val intent = Intent(this, TermsConditionsActivity::class.java)
                startActivity(intent)
            } R.id.Developer -> {
            showDeveloperInfoDialog()
            }
            R.id.feedback -> {
                Toast.makeText(this, "this is feedback", Toast.LENGTH_SHORT).show()
            }

        }
        return true
    }

    private fun toggleFavorite() {

//        val sharedPreferences
//        val isFavorite = sharedPreferences.getBoolean("isFavorite", false)
//
//        // Toggle favorite state
//        val newFavoriteState = !isFavorite
//
//        // Update SharedPreferences with the new favorite state
//        sharedPreferences.edit().putBoolean("isFavorite", newFavoriteState).apply()
//
//        // Show appropriate toast message based on the new state
//        if (newFavoriteState) {
//            Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(this, "Removed from Favorites", Toast.LENGTH_SHORT).show()
//        }

    }

    private fun showDeveloperInfoDialog() {



        // Find the ImageView in the dialog layout

        // Set the photo of the developer


        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Developer Information")
            .setMessage("Prasad Suresh Kirpan\nEmail: prasad.kirpan33@gmail.com"+"\nPhone no:7030799480")

            .setPositiveButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        alertDialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle!!.onOptionsItemSelected(item)){
            true
        }else

            super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.close()
        }else      super.onBackPressed()
    }
}