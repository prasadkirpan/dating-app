package com.example.datingapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.datingapp.R
import com.example.datingapp.activity.MessageActivity
import com.example.datingapp.databinding.ItemUserLayoutBinding
import com.example.datingapp.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DatingAdapter(val context: Context, val list: ArrayList<UserModel>) :
    RecyclerView.Adapter<DatingAdapter.DatingViewHolder>() {

    inner class DatingViewHolder(val binding: ItemUserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatingViewHolder {
        return DatingViewHolder(
            ItemUserLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DatingViewHolder, position: Int) {

        val user = list[position]

        holder.binding.textView5.text = user.name
        holder.binding.textView4.text = user.email

        Glide.with(context).load(user.image).into(holder.binding.userImage)

        holder.binding.chat.setOnClickListener {
            val intent = Intent(context, MessageActivity::class.java)
            intent.putExtra("userId", user.number)
            context.startActivity(intent)
        }
        var isRed = false

        holder.binding.heart.setOnClickListener {

            if (!isRed) {
                // Change the heart button color to red
                holder.binding.heart.setColorFilter(ContextCompat.getColor(context, R.color.red))
                isRed = true

                // Schedule a delayed task to revert the color after a certain duration
                Handler(Looper.getMainLooper()).postDelayed({
                    // Remove the color filter to revert to the original color
                    holder.binding.heart.clearColorFilter()

                    //     isRed = false // Reset the state to allow toggling again
                    //   }, 10000) // 10 seconds delay, adjust as needed


                    val userId = FirebaseAuth.getInstance().currentUser?.uid
                    userId?.let {
                        val databaseReference =
                            FirebaseDatabase.getInstance().getReference("users").child(it)
                        val userMap = HashMap<String, Any>()
                        // Here you can add any user information you want to store in the database
                        userMap["heartTouched"] = true
                        databaseReference.updateChildren(userMap)
                    }

                    isRed = false // Reset the state to allow toggling again
                }, 100000) // 10 seconds delay, adjust as needed
            }

        }

    }
}



//package com.example.datingapp.adapter
//
//import android.content.Context
//import android.content.Intent
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.datingapp.R
//import com.example.datingapp.activity.MessageActivity
//import com.example.datingapp.databinding.ItemUserLayoutBinding
//import com.example.datingapp.model.UserModel
//
//class DatingAdapter(val context : Context, val list : ArrayList<UserModel>) : RecyclerView.Adapter<DatingAdapter.DatingViewHolder>() {
//
//    inner class DatingViewHolder(val binding : ItemUserLayoutBinding)
//        : RecyclerView.ViewHolder(binding.root)
//    //new
//    private var isLiked = false
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatingViewHolder {
//
//        return DatingViewHolder(ItemUserLayoutBinding.inflate(LayoutInflater.from(context)
//        , parent,false))
//
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    override fun onBindViewHolder(holder: DatingViewHolder, position: Int) {
//
//        holder.binding.textView5.text = list[position].name
//        holder.binding.textView4.text = list[position].email
//
//        Glide.with(context).load(list[position].image).into(holder.binding.userImage)
//
//        holder.binding.chat.setOnClickListener {
//            val intent = Intent(context,MessageActivity::class.java)
//            intent.putExtra("userId", list[position].number)
//
//
//            context.startActivity(intent)
//
//        }
//        holder.binding.heart.setOnClickListener {
//            isLiked = !isLiked
//          //  updateLikeButtonUI()
//        }
//
//    }
//
//   // private fun updateLikeButtonUI(binding: ItemUserLayoutBinding) {
//     //   val iconResId = if (isLiked) R.drawable.ic_liked else R.drawable.ic_unliked
//
//       // binding.likeButton.setImageResource(iconResId)
//
////    }
//}