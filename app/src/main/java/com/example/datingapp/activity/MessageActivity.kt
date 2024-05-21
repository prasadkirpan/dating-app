
package com.example.datingapp.activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.datingapp.adapter.MessageAdapter
import com.example.datingapp.databinding.ActivityMessageBinding
import com.example.datingapp.model.MessageModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MessageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessageBinding
    private lateinit var msg: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        msg = intent.getStringExtra("msg") ?: "" // Get the message from intent

        verifyChatId()

        binding.imageView4.setOnClickListener {
            if (binding.yourMessage.text!!.isEmpty()) {
                Toast.makeText(this, "Please enter your message", Toast.LENGTH_SHORT).show()
            } else {
                storeData(binding.yourMessage.text.toString())
            }
        }
    }

    private fun storeData(msg: String) {
        val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm a", Locale.getDefault()).format(Date())

        val map = hashMapOf<String, String>()
        map["message"] = msg
        map["senderId"] = FirebaseAuth.getInstance().currentUser!!.phoneNumber!!
        map["currentTime"] = currentTime
        map["currentDate"] = currentDate

        val reference = FirebaseDatabase.getInstance().getReference("chats").child(chatId!!)

        reference.child(reference.push().key!!).setValue(map).addOnCompleteListener {
            if (it.isSuccessful) {
                binding.yourMessage.text = null



                Toast.makeText(this, "Message Sent", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private var senderId: String? = null
    private var chatId: String? = null

    private fun verifyChatId() {
        val receiverId = intent.getStringExtra("userId")
        senderId = FirebaseAuth.getInstance().currentUser!!.phoneNumber

        chatId = senderId + receiverId
        val reverseChatId = receiverId + senderId

        val reference = FirebaseDatabase.getInstance().getReference("chats")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(chatId!!)) {
                    getData(chatId)
                } else if (snapshot.hasChild(reverseChatId!!)) {
                    chatId = reverseChatId
                    getData(chatId)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MessageActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getData(chatId: String?) {
        if (chatId.isNullOrEmpty()) {
            Toast.makeText(this, "Chat ID is null or empty", Toast.LENGTH_SHORT).show()
            return
        }

        FirebaseDatabase.getInstance().getReference("chats").child(chatId!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<MessageModel>()
                    for (show in snapshot.children) {
                        show.getValue(MessageModel::class.java)?.let {
                            list.add(it)
                        }
                    }
                    binding.recyclerView2.adapter = MessageAdapter(this@MessageActivity,list)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@MessageActivity, error.message, Toast.LENGTH_SHORT).show()
                }
            })
    }


}

//package com.example.datingapp.activity

//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Toast
//import com.example.datingapp.adapter.MessageAdapter
//import com.example.datingapp.databinding.ActivityMessageBinding
//import com.example.datingapp.model.MessageModel
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DataSnapshot
//import com.google.firebase.database.DatabaseError
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ValueEventListener
//import java.text.SimpleDateFormat
//import java.util.Date
//import java.util.Locale
//
//class MessageActivity(var msg: String) : AppCompatActivity() {
//    private lateinit var binding: ActivityMessageBinding
//
//
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMessageBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//
//        // getData(intent.getStringExtra("chat_id"))
//
//         verifyChatId()
//
//
//        binding.imageView4.setOnClickListener {
//            if (binding.yourMessage.text!!.isEmpty()) {
//                Toast.makeText(this, "Please enter your message", Toast.LENGTH_SHORT).show()
//            } else {
//                (binding.yourMessage.text.toString())
//                // sendMessage(binding.yourMessage.text.toString())
//                storeData(binding.yourMessage.text.toString())
//            }
//        }
//    }
//        private fun storeData(msg: String) {
//            val currentDate: String =
//                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
//            val currentTime: String =
//                SimpleDateFormat("HH:mm a", Locale.getDefault()).format(Date())
//
//            val map = hashMapOf<String, String>()
//            map["message"] = msg
//            map["senderId"] = senderId!!
//            map["currentTime"] = currentTime
//            map["currentDate"] = currentDate
//
//            val reference = FirebaseDatabase.getInstance().getReference("chats").child(chatId!!)
//
//            reference.child(reference.push().key!!).setValue(map).addOnCompleteListener {
//                if (it.isSuccessful) {
//                    binding.yourMessage.text = null
//                    Toast.makeText(this, "Message Sended", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//
//        private var senderId: String? = null
//        private var chatId: String? = null
//
//        private fun verifyChatId() {
//            val receiverId = intent.getStringExtra("userId")
//            senderId = FirebaseAuth.getInstance().currentUser!!.phoneNumber
//
//            //  chatId = createChatId(senderId!!, receiverId.toString())
//            chatId = senderId + receiverId
//            val reverseChatId = receiverId + senderId
//
//            val reference =
//                FirebaseDatabase.getInstance().getReference("chats")//.child(chatId) //chatId
//            reference.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//
//                    if (snapshot.hasChild(chatId!!)) {
//                        getData(chatId)
//
//                    } else if (snapshot.hasChild(reverseChatId!!)) {
//
//                        chatId = reverseChatId
//                        getData(chatId)
//                    }
//
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Toast.makeText(this@MessageActivity, "something went wrong", Toast.LENGTH_SHORT)
//                        .show()
//                }
//
//            })
//
//        }
//
//        private fun getData(chatId: String?) {
//
//            if (chatId.isNullOrEmpty()) {
//                // Handle the case where chatId is null or empty
//                Toast.makeText(this, "Chat ID is null or empty", Toast.LENGTH_SHORT).show()
//                return
//
//
//            }
//                FirebaseDatabase.getInstance().getReference("chats")
//                    .child(chatId!!).addValueEventListener(object : ValueEventListener {
//                        override fun onDataChange(snapshot: DataSnapshot) {
//                            val list = mutableListOf<MessageModel>()
//
//                            for (show in snapshot.children) {
//                               // list.add(show.getValue(MessageModel::class.java)!!)
//                                show.getValue(MessageModel::class.java)?.let {
//                                    list.add(it)
//                                }
//                            }
//
//                            binding.recyclerView2.adapter =
//                                MessageAdapter(this@MessageActivity, list)
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//                            Toast.makeText(this@MessageActivity, error.message, Toast.LENGTH_SHORT)
//                                .show()
//                        }
//
//                    })
//
//            }
//        }

