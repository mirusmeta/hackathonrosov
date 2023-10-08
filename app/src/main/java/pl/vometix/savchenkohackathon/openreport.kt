package pl.vometix.savchenkohackathon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class openreport : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_openreport)
        var db2 = Firebase.database
        var x = intent.getStringExtra("id")
        var messageedittext:TextView = findViewById(R.id.messageedittext)
        var sendimg:ImageView = findViewById(R.id.sendimg)
        var z = intent.getStringExtra("from")
        if(z == null){
            Toast.makeText(this, "Ошибка, перезапустите приложение!",Toast.LENGTH_LONG).show()
        }
        var fromuser:TextView = findViewById(R.id.fromuser)
        var maintextview:TextView = findViewById(R.id.maintextview)
        var fromadmincost:ConstraintLayout = findViewById(R.id.fromadmincost)
        var fromadmin:TextView = findViewById(R.id.fromadmin)
        var db = Firebase.firestore
        db.collection("reports").document(x.toString()).get().addOnSuccessListener {
            maintextview.text = it.getString("maintext")
            fromuser.text = it.getString("message").toString()
            if(it.getString("answer")!="null"){
                fromadmincost.visibility= View.VISIBLE
                fromadmin.text = it.getString("answer").toString()
            }
            else if(it.getString("answer") == "null" && z=="admin"){
                messageedittext.visibility=View.VISIBLE
                sendimg.visibility = View.VISIBLE
            }
        }
        sendimg.setOnClickListener {
            if(messageedittext.text.toString().length > 10){
                var updates = hashMapOf<String, Any>(
                    "answer" to messageedittext.text.toString(),
                    "state" to "yes"
                )
                db.collection("reports").document(x.toString()).update(updates).addOnSuccessListener {
                    Toast.makeText(this, "Успешно!",Toast.LENGTH_LONG).show()
                    db2.getReference("a").setValue(Random.nextLong(2132,900000000).toString())
                    fromadmincost.visibility= View.VISIBLE
                    fromadmin.text = messageedittext.text.toString()
                    var h = Handler()
                    h.postDelayed({startActivity(Intent(this, admin::class.java))},1200)
                }.addOnFailureListener {
                    Toast.makeText(this, "Ошибка",Toast.LENGTH_LONG).show()
                }
                messageedittext.visibility=View.INVISIBLE
                sendimg.visibility = View.INVISIBLE
            }
            else{
                Toast.makeText(this, "Вы Мало написали",Toast.LENGTH_LONG).show()
            }

        }
    }
}