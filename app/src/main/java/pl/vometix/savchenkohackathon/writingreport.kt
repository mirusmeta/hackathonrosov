package pl.vometix.savchenkohackathon

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

class writingreport : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_writingreport)
        var sharedPref = getSharedPreferences("ALLPREFEN", Context.MODE_PRIVATE)
        var textFiledmaintext:TextInputEditText = findViewById(R.id.textFiledmaintext)
        var textFieldcodeofmember:TextInputEditText = findViewById(R.id.textFieldcodeofmember)
        var db = Firebase.firestore
        var db2 = Firebase.database
        var sendbut:Button = findViewById(R.id.sendbut)
        var firstbool = false;
        var secondbool = false;
        textFiledmaintext.addTextChangedListener {
            firstbool = it.toString().isNotEmpty()
            sendbut.isEnabled = firstbool && secondbool
        }
        textFieldcodeofmember.addTextChangedListener {
            secondbool = it.toString().length>=10
            sendbut.isEnabled = firstbool && secondbool
        }
        sendbut.setOnClickListener {
            var collection = db.collection("reports")
            var status = sharedPref.getString("status",null).toString()
            var code = sharedPref.getString("code",null).toString()
            var nameofuser = sharedPref.getString("name",null).toString()
            var counter = 0;
            db.collection("reports").get().addOnSuccessListener {
                for(res in it){
                    if(res.getString("id").toString()=="${code}main"){
                        counter++;
                    }
                }
                if(counter==3){
                    Snackbar.make(findViewById(R.id.bg2),"Вы исчерпали лимит!",Snackbar.LENGTH_LONG).show()
                    startActivity(Intent(this, user::class.java))
                }
                else{
                    var report = hashMapOf(
                        "answer" to "null",
                        "state" to "none",
                        "id" to "${code}main",
                        "kolvo" to "${counter+1}",
                        "from" to code,
                        "maintext" to textFiledmaintext.text.toString(),
                        "statusofuser" to status,
                        "message" to textFieldcodeofmember.text.toString(),
                        "name" to nameofuser
                    )

                    db.collection("reports").add(report)
                        .addOnSuccessListener { documentReference ->
                            db2.getReference("a").setValue(Random.nextLong(2132,900000000).toString())
                            Snackbar.make(findViewById(R.id.bg2),"Отправлено",Snackbar.LENGTH_LONG).show()
                            startActivity(Intent(this, user::class.java))
                        }
                        .addOnFailureListener { e ->
                            Snackbar.make(findViewById(R.id.bg2),"Ошибка, попробуйте снова",Snackbar.LENGTH_LONG).show()
                            startActivity(Intent(this, user::class.java)) }
                }

            }





        }
    }
}