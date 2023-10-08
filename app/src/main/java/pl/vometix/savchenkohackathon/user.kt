package pl.vometix.savchenkohackathon

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class user : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        var recycleview:RecyclerView = findViewById(R.id.recycleview)
        recycleview.layoutManager = LinearLayoutManager(this)
        var db = Firebase.firestore
        var list = mutableListOf<String>()
        var listid = mutableListOf<String>()
        var sharedPref = getSharedPreferences("ALLPREFEN", Context.MODE_PRIVATE)
        var data2 = mutableListOf<MyAdapter.ItemData>()
        var db2 = Firebase.database
        db2.getReference("a").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var data2 = mutableListOf<MyAdapter.ItemData>()
                db.collection("reports").get().addOnSuccessListener {
                    for(res in it){
                        if(res.getString("id")=="${sharedPref.getString("code",null)}main"){
                            if(res.getString("state")=="none"){
                                data2.add(MyAdapter.ItemData(R.drawable.pencil, res.getString("maintext").toString(), res.id.toString()))
                            }
                            else{
                                data2.add(MyAdapter.ItemData(R.drawable.trueicon, res.getString("maintext").toString(), res.id.toString()))
                            }
                        }

                    }
                    var adapter = MyAdapter(data2)
                    adapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
                        override fun onItemClick(view: View, position: Int) {
                            var intent = Intent(this@user, openreport::class.java)
                            intent.putExtra("id", view.findViewById<TextView>(R.id.documentid).text.toString())
                            intent.putExtra("from","user")
                            startActivity(intent)
                        }
                    })
                    recycleview.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        db.collection("reports").get().addOnSuccessListener {
            for(res in it){
                if(res.getString("id")=="${sharedPref.getString("code",null)}main"){
                    if(res.getString("state")=="none"){
                        data2.add(MyAdapter.ItemData(R.drawable.pencil, res.getString("maintext").toString(), res.id.toString()))
                    }
                    else{
                        data2.add(MyAdapter.ItemData(R.drawable.trueicon, res.getString("maintext").toString(), res.id.toString()))
                    }
                }

            }
            var adapter = MyAdapter(data2)
            adapter.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    var intent = Intent(this@user, openreport::class.java)
                    intent.putExtra("id", view.findViewById<TextView>(R.id.documentid).text.toString())
                    intent.putExtra("from","user")
                    startActivity(intent)
                }
            })
            recycleview.adapter = adapter
        }


        var fab:Button = findViewById(R.id.fab)
        fab.setOnClickListener {
            startActivity(Intent(this, writingreport::class.java))
        }
    }

    override fun onBackPressed() {

    }
}