package com.example.retrorequest

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrorequest.databinding.RecyclerViewItemBinding

class RecyclerViewAdapter(private val context: Context, private val contacts: List<User>,/*val clickListener: OnItemClickListener*/) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val TAG = "ContactAdapter"

    // Usually involves inflating a layout from XML and returning the holder - THIS IS EXPENSIVE
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        return ViewHolder(
            RecyclerViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // Returns the total count of items in the list
    override fun getItemCount() = contacts.size

    // Involves populating data into the item through holder - NOT expensive
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i(TAG, "onBindViewHolder at position $position")
        val contact = contacts[position]
        holder.bind(contact)
        holder.itemView.setOnClickListener { v ->
           // clickListener.onItemEditCLick(contact)
            val context = v.context
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(DetailsActivity.ARG_ITEM_ID,contact.id)

            context.startActivity(intent)

        }

    }

    inner class ViewHolder(val binding: RecyclerViewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.title.text = user.title


        }
    }

   /* interface OnItemClickListener {
        fun onItemEditCLick(user : User)
    }*/
}
