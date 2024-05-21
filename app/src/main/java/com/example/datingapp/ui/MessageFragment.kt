package com.example.datingapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.datingapp.adapter.MessageUserAdapter
import com.example.datingapp.databinding.FragmentMessageBinding
import com.example.datingapp.model.UserModel
import com.example.datingapp.utils.Config
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
class MessageFragment : Fragment() {

    private lateinit var binding: FragmentMessageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        getData()
        return binding.root
    }

    private fun getData() {
        Config.showDialog(requireContext())

        val currentId = FirebaseAuth.getInstance().currentUser?.phoneNumber

        // Fetch user data
        FirebaseDatabase.getInstance().getReference("users").child(currentId!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userModel = snapshot.getValue(UserModel::class.java)
                    userModel?.let { user ->
                        // Update username
                        val userName = user.name // Assuming name is a property of UserModel

                        FirebaseDatabase.getInstance().getReference("chats")
                            .addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val list = arrayListOf<String>()
                                    val newList = arrayListOf<String>()

                                    for (data in snapshot.children) {
                                        if (data.key!!.contains(currentId)) {
                                            list.add(data.key!!.replace(currentId, ""))
                                            newList.add(data.key!!)
                                        }
                                    }

                                    try {
                                        binding.recyclerView.adapter =
                                            MessageUserAdapter(
                                                requireContext(),
                                                list,
                                                newList,
                                                userName // Pass the userName directly
                                            )
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }

                                    Config.hideDialog()
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Toast.makeText(
                                        requireContext(),
                                        error.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    Config.hideDialog()
                                }
                            })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                    Config.hideDialog()
                }
            })
    }
}


//class MessageFragment : Fragment() {
//
//    private lateinit var binding: FragmentMessageBinding
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = FragmentMessageBinding.inflate(inflater, container, false)
//        getData()
//        return binding.root
//    }
//    private fun getData() {
//        Config.showDialog(requireContext())
//
//        val currentId = FirebaseAuth.getInstance().currentUser!!.phoneNumber
//
//        // Fetch user data
//        FirebaseDatabase.getInstance().getReference("users").child(currentId!!)
//
//            .addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val userModel = snapshot.getValue(UserModel::class.java)
//                    userModel?.let { user ->
//                        // Update username
//                        val database_name = FirebaseDatabase.getInstance().getReference("users")
//
//                        val userName =database_name.child("name")
//
//                        FirebaseDatabase.getInstance().getReference("chats")
//                            .addValueEventListener(object : ValueEventListener {
//                                override fun onDataChange(snapshot: DataSnapshot) {
//                                    val list = arrayListOf<String>()
//                                    val newList = arrayListOf<String>()
//
//                                    for (data in snapshot.children) {
//                                        if (data.key!!.contains(currentId!!)) {
//                                            list.add(data.key!!.replace(currentId, ""))
//                                            newList.add(data.key!!)
//                                        }
//                                    }
//
//                                    try {
//                                        binding.recyclerView.adapter =
//                                            MessageUserAdapter(requireContext(), list, newList,
//                                                userName.toString()
//                                            )
//                                    } catch (e: Exception) {
//                                        e.printStackTrace()
//                                    }
//
//                                    Config.hideDialog()
//                                }
//
//                                override fun onCancelled(error: DatabaseError) {
//                                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
//                                    Config.hideDialog()
//                                }
//                            })
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
//                    Config.hideDialog()
//                }
//            })
//    }
//





