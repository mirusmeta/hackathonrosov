package pl.vometix.savchenkohackathon

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class admin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        var db2 = Firebase.database
        var db = Firebase.firestore
        var update:ImageView = findViewById(R.id.update)
        val sharedPref = getSharedPreferences("ALLPREFEN", Context.MODE_PRIVATE)
        var recycleview: RecyclerView = findViewById(R.id.recycleview)
        recycleview.layoutManager = LinearLayoutManager(this)
        update.setOnClickListener {
            var data2 = mutableListOf<MyAdapter.ItemData>()
            db.collection("reports").get().addOnSuccessListener {
                for(res in it){
                    if(res.getString("state").toString()=="none")
                        data2.add(MyAdapter.ItemData(R.drawable.pencil, res.getString("maintext").toString(), res.id.toString()))
                    //Иначе проблема решена => ничего
                }
                var adapter = MyAdapter(data2)
                adapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        var intent = Intent(this@admin, openreport::class.java)
                        intent.putExtra("id", view.findViewById<TextView>(R.id.documentid).text.toString())
                        intent.putExtra("from","admin")
                        startActivity(intent)
                    }
                })
                recycleview.adapter = adapter

            }
        }
        db2.getReference("a").addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                var data2 = mutableListOf<MyAdapter.ItemData>()
                db.collection("reports").get().addOnSuccessListener {
                    for(res in it){
                        if(res.getString("state").toString()=="none")
                            data2.add(MyAdapter.ItemData(R.drawable.pencil, res.getString("maintext").toString(), res.id.toString()))
                        //Иначе проблема решена => ничего
                    }
                    var adapter = MyAdapter(data2)
                    adapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            var intent = Intent(this@admin, openreport::class.java)
                            intent.putExtra("id", view.findViewById<TextView>(R.id.documentid).text.toString())
                            intent.putExtra("from","admin")
                            startActivity(intent)
                        }
                    })
                    recycleview.adapter = adapter

                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        var data2 = mutableListOf<MyAdapter.ItemData>()
        db.collection("reports").get().addOnSuccessListener {
            for(res in it){
                if(res.getString("state").toString()=="none")
                    data2.add(MyAdapter.ItemData(R.drawable.pencil, res.getString("maintext").toString(), res.id.toString()))
                //Иначе проблема решена => ничего
            }
            var adapter = MyAdapter(data2)
            adapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    var intent = Intent(this@admin, openreport::class.java)
                    intent.putExtra("id", view.findViewById<TextView>(R.id.documentid).text.toString())
                    intent.putExtra("from","admin")
                    startActivity(intent)
                }
            })
            recycleview.adapter = adapter

        }


    }
}