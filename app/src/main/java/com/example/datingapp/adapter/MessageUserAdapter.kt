package com.example.datingapp.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.datingapp.activity.MessageActivity
import com.example.datingapp.databinding.UserItemLayoutBinding
import com.example.datingapp.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MessageUserAdapter(
    val context: Context,
    val userIds: List<String>,
    val chatKey: List<String>,
    val userName: String?
) : RecyclerView.Adapter<MessageUserAdapter.MessageUserViewHolder>() {

    inner class MessageUserViewHolder(val binding: UserItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageUserViewHolder {
        return MessageUserViewHolder(
            UserItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return userIds.size
    }

    override fun onBindViewHolder(holder: MessageUserViewHolder, position: Int) {
        val userId = userIds[position]

        // Fetch user data from Firebase
        FirebaseDatabase.getInstance().getReference("users")
            .child(userId).addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val data = snapshot.getValue(UserModel::class.java)
                            // Null check for data
                            if (data != null) {
                                // Set username to TextView
                                holder.binding.userName.text = data.name ?: "Unknown"
                                // Load user image
                                Glide.with(context).load(data.image).into(holder.binding.userImage)
                            } else {
                                Log.d("MessageUserAdapter", "User data is null for userId: $userId")
                            }
                        } else {
                            Log.d(
                                "MessageUserAdapter",
                                "Snapshot does not exist for userId: $userId"
                            )
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                        Log.e("MessageUserAdapter", "Database error: ${error.message}")
                    }
                })
        holder.itemView.setOnClickListener {
            val intent = Intent(context, MessageActivity::class.java)
            intent.putExtra("chat_id", chatKey[position])
            intent.putExtra("userId", userId)
            context.startActivity(intent)
        }
    }
}

//package com.example.datingapp.adapter
//
//
//import android.content.Context
//import android.content.Intent
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.datingapp.activity.MessageActivity
//import com.example.datingapp.databinding.UserItemLayoutBinding
//import com.example.datingapp.model.UserModel
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//
//
//class MessageUserAdapter(
//    val context: Context,
//    val userIds: List<String>,
//    val chatKey: List<String>,
//    val userName: String?
//
//
//
//) : RecyclerView.Adapter<MessageUserAdapter.MessageUserViewHolder>() {
//
//    inner class MessageUserViewHolder(val binding: UserItemLayoutBinding) :
//        RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageUserViewHolder {
//        return MessageUserViewHolder(
//            UserItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
//        )
//    }
//
//    override fun getItemCount(): Int {
//        return userIds.size
//    }
//
//    override fun onBindViewHolder(holder: MessageUserViewHolder, position: Int) {
//        val userId = userIds[position]
//
//        // Fetch user data from Firebase
//        FirebaseDatabase.getInstance().getReference("users")
//            .child(userId).addListenerForSingleValueEvent(
//                object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        if (snapshot.exists()) {
//                            val data = snapshot.getValue(UserModel::class.java)
//                            // Null check for data
//                            if (data != null) {
//                                // Set username to TextView
//                                holder.binding.userName.text = data.name ?: "Unknown"
//                                // Load user image
//                                Glide.with(context).load(data.image).into(holder.binding.userImage)
//                            } else {
//                                Log.d("MessageUserAdapter", "User data is null for userId: $userId")
//                            }
//                        } else {
//                            Log.d(
//                                "MessageUserAdapter",
//                                "Snapshot does not exist for userId: $userId"
//                            )
//                        }
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
//                        Log.e("MessageUserAdapter", "Database error: ${error.message}")
//                    }
//                })
//        holder.itemView.setOnClickListener {
//            val intent = Intent(context, MessageActivity::class.java)
//            intent.putExtra("chat_id", chatKey[position])
//            intent.putExtra("userId", userId)
//            context.startActivity(intent)
//        }
//    }
//}
//
//
//
////    override fun onBindViewHolder(holder: MessageUserViewHolder, position: Int) {
////        val userId = userIds[position]
////
////        // Fetch user data from Firebase
////        FirebaseDatabase.getInstance().getReference("users")
////            .child(userId).addListenerForSingleValueEvent(
////                object : ValueEventListener {
////                    override fun onDataChange(snapshot: DataSnapshot) {
////                        if (snapshot.exists()) {
////                            val data = snapshot.getValue(UserModel::class.java)
////                            // Set username to TextView
////                            holder.binding.userName.text = data?.name ?: "Unknown"
////                            // Load user image
////                            Glide.with(context).load(data?.image).into(holder.binding.userImage)
////                        }
////                    }
////
////                    override fun onCancelled(error: DatabaseError) {
////                        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
////                    }
////                })
//
//
//// Handle item click
////        holder.itemView.setOnClickListener {
////            val intent = Intent(context, MessageActivity::class.java)
////            intent.putExtra("chat_id", chatKey[position])
////            intent.putExtra("userId", userId)
////            context.startActivity(intent)
////        }
////    }
////}
//
//
//
//
//
////class MessageUserAdapter(
////    val context: Context,
////    val userIds: List<String>, // List of user IDs
////    val chatKey: List<String>,
////    username: String?
////) : RecyclerView.Adapter<MessageUserAdapter.MessageUserViewHolder>() {
////
////    inner class MessageUserViewHolder(val binding: UserItemLayoutBinding) :
////        RecyclerView.ViewHolder(binding.root)
////
////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageUserViewHolder {
////        return MessageUserViewHolder(
////            UserItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
////        )
////    }
////
////    override fun getItemCount(): Int {
////        return userIds.size
////    }
////
////    override fun onBindViewHolder(holder: MessageUserViewHolder, position: Int) {
////        val userId = userIds[position]
////
////        FirebaseDatabase.getInstance().getReference("users")
////            .child(userId).addListenerForSingleValueEvent(
////                object : ValueEventListener {
////                    override fun onDataChange(snapshot: DataSnapshot) {
////                        if (snapshot.exists()) {
////                            val data = snapshot.getValue(UserModel::class.java)
////                            Glide.with(context).load(data!!.image).into(holder.binding.userImage)
////                            holder.binding.userName.text = data.name
////                        }
////                    }
////
////                    override fun onCancelled(error: DatabaseError) {
////                        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
////                    }
////                })
////
////        holder.itemView.setOnClickListener {
////            val intent = Intent(context, MessageActivity::class.java)
////            intent.putExtra("chat_id", chatKey[position])
////            intent.putExtra("userId", userId)
////            context.startActivity(intent)
////        }
////    }
////}
//
//
////class MessageUserAdapter(
////    val context: Context,
////    val list: ArrayList<String>,
////    val chatKey: ArrayList<String>,
////    name: String?
////) : RecyclerView.Adapter<MessageUserAdapter.MessageUserViewHolder>() {
////
////    inner class MessageUserViewHolder(val binding: UserItemLayoutBinding) :
////        RecyclerView.ViewHolder(binding.root)
////
////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageUserViewHolder {
////        return MessageUserViewHolder(
////            UserItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
////        )
////    }
////
////    override fun getItemCount(): Int {
////        return list.size
////    }
////
////    override fun onBindViewHolder(holder: MessageUserViewHolder, position: Int) {
////        FirebaseDatabase.getInstance().getReference("users")
////            .child(list[position]).addListenerForSingleValueEvent(
////                object : ValueEventListener {
////                    override fun onDataChange(snapshot: DataSnapshot) {
////                        if (snapshot.exists()) {
////                            val data = snapshot.getValue(UserModel::class.java)
////                            Glide.with(context).load(data!!.image).into(holder.binding.userImage)
////                            holder.binding.userName.text = data.name
////                        }
////                    }
////
////                    override fun onCancelled(error: DatabaseError) {
////                        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
////                    }
////                })
////
////        holder.itemView.setOnClickListener {
////            val intent = Intent(context, MessageActivity::class.java)
////            intent.putExtra("chat_id", chatKey[position])
////            intent.putExtra("userId", list[position])
////            context.startActivity(intent)
////        }
////    }
////}
////
