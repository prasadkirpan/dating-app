package com.example.datingapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.datingapp.R
import EditProfileActivity
import com.example.datingapp.activity.profileEdit
import com.example.datingapp.auth.LoginActivity
import com.example.datingapp.databinding.FragmentDatingBinding
import com.example.datingapp.databinding.FragmentProfileBinding
import com.example.datingapp.model.UserModel
import com.example.datingapp.utils.Config
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User




class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        Config.showDialog(requireContext())
        binding = FragmentProfileBinding.inflate(layoutInflater)


        FirebaseDatabase.getInstance().getReference("users")
            .child(FirebaseAuth.getInstance().currentUser!!.phoneNumber!!).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val data = it.getValue(UserModel::class.java)
                    binding.name.setText(data!!.name.toString())
                    binding.city.setText(data.city.toString())
                    binding.email.setText(data.email.toString())
                    binding.number.setText(data.number.toString())

                    Glide.with(requireContext()).load(data.image).placeholder(R.drawable.profile)
                        .into(binding.userImage)
                    Config.hideDialog()
                }
            }


        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

//        binding.editProfile.setOnClickListener {
//            val intent = Intent(requireContext(), profileEdit::class.java)
//
//            startActivity(intent)
//            requireActivity().finish()
//        }




        return binding.root
    }
    }



