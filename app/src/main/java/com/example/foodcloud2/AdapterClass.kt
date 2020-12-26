package com.example.foodcloud2


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodcloud2.Cart
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class AdapterClass(private val list: ArrayList<Cart>): RecyclerView.Adapter<AdapterClass.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.itemname)
        val date: TextView = itemView.findViewById(R.id.date)
        val category : TextView = itemView.findViewById(R.id.category1)
        val qt: TextView = itemView.findViewById(R.id.quantity1)
        val time: TextView = itemView.findViewById(R.id.time)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.card_holder_receiver, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.date.text=list[position].date
        holder.time.text=list[position].time
        holder.name.text = list[position].itemName
        holder.category.text = list[position].itemCategory
        holder.qt.text = "Quantity: ".plus(list[position].quantity)
    }
}