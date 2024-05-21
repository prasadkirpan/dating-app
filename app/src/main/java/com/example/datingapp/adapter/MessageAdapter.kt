package com.example.datingapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.datingapp.R
import com.example.datingapp.model.MessageModel
import com.example.datingapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MessageAdapter(private val context: Context, private val list: MutableList<MessageModel>)
    : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    companion object {
        private const val MSG_TYPE_RIGHT = 0
        private const val MSG_TYPE_LEFT = 1
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.messageText)
        val image: ImageView = itemView.findViewById(R.id.senderImage)
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].senderId == FirebaseAuth.getInstance().currentUser?.phoneNumber) {
            MSG_TYPE_RIGHT
        } else {
            MSG_TYPE_LEFT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val layoutId = if (viewType == MSG_TYPE_RIGHT) {
            R.layout.layout_receiver_message
        } else {
            R.layout.layout_sender_message
        }
        val view = LayoutInflater.from(context).inflate(layoutId, parent, false)
        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = list[position]

        // Set message text
        holder.text.text = message.message

        // Load sender image
        loadSenderImage(message.senderId, holder.image)
    }

    private fun loadSenderImage(senderId: String?, imageView: ImageView) {
        val usersRef = FirebaseDatabase.getInstance().getReference("users")
        senderId?.let { id ->
            usersRef.child(id).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val data = snapshot.getValue(UserModel::class.java)
                    data?.let {
                        Glide.with(context)
                            .load(it.image)
                            .placeholder(R.drawable.man) // Placeholder image
                            .into(imageView)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}

//
//package com.example.datingapp.adapter
//
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import android.widget.Toast
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.datingapp.R
//import com.example.datingapp.activity.MessageActivity
//import com.example.datingapp.model.MessageModel
//import com.example.datingapp.model.UserModel
//import com.example.datingapp.ui.DatingFragment.Companion.list
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//
//
//
////class MessageAdapter(val context: MessageActivity, list: MutableList<MessageModel>)
//  //  :RecyclerView.Adapter<MessageAdapter.MessageViewHolder>(){
//class MessageAdapter(val context: Context, list: MutableList<MessageModel>)
//    :RecyclerView.Adapter<MessageAdapter.MessageViewHolder>(){
//
//    val MSG_TYPE_RIGHT = 0
//    val MSG_TYPE_LEFT = 1
//
//
//    inner class MessageViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
//
//        val text = itemView.findViewById<TextView>(R.id.messageText)
//        val image = itemView.findViewById<ImageView>(R.id.senderImage)
//
//    }
//
//  override fun getItemViewType(position: Int): Int {
//        return  if (list!![position].senderId == FirebaseAuth.getInstance().currentUser!!.phoneNumber){
//            MSG_TYPE_RIGHT
//       }else MSG_TYPE_LEFT
//
//    }
//
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
//       return if(viewType == MSG_TYPE_RIGHT){
//           MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_receiver_message,parent,false))
//       }else{
//           MessageViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_sender_message,parent,false))
//       }
//    }
//
//    override fun getItemCount(): Int {
//        return list!!.size
//    }
//
//    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
//       holder.text.text = list!![position].senderId as CharSequence?
//
//        FirebaseDatabase.getInstance().getReference("users")
//            .child(list!![position].senderId!! as String).addListenerForSingleValueEvent(
//                object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        if (snapshot.exists()) {
//                            val data = snapshot.getValue(UserModel::class.java)
//
//                            Glide.with(context).load(data!!.image).placeholder(R.drawable.man).into(holder.image)
//
//
//                        }
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
//                    }
//                })
//
//    }
//
//}
