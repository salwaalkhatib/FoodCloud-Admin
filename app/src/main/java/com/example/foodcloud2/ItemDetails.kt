package com.example.foodcloud2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.foodcloud2.AdapterClass
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ItemDetails : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var list:ArrayList<Cart>
    lateinit var saved:String
    private lateinit var phonenb:String
    lateinit var received:Button
    lateinit var ID:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        recyclerView=findViewById(R.id.rv)
        received=findViewById(R.id.received_btn)
        saved=intent.getStringExtra("SAVED").toString()
        phonenb=intent.getStringExtra("PHONE").toString()
        ID=intent.getStringExtra("ID").toString()


        val database = FirebaseDatabase.getInstance()
        val itemRef= database.reference.child("Cart List").child("Admin View").child(phonenb).child("Item")
        itemRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.exists()){
                    list = arrayListOf()
                    for(postSnapshot in dataSnapshot.children){
                        val item = postSnapshot.getValue(Cart::class.java)!!
                        if(saved.contains(item.iid)){
                            list.add(item)
                       }
                        }
                    recyclerView.setLayoutManager(LinearLayoutManager(this@ItemDetails));
                    val adapterClass = AdapterClass(list)
                    recyclerView.adapter = adapterClass

                }

            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(applicationContext,"error",Toast.LENGTH_SHORT).show()
            }
        })
    received.setOnClickListener {
    val orderRef= database.reference.child("Orders").child(phonenb).child(ID).child("redeemed")
    orderRef.setValue("true")
     Toast.makeText(this, "The order has been successfully redeemed", Toast.LENGTH_LONG).show()

}

    }
}