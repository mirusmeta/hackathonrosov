package pl.vometix.savchenkohackathon

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class auth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        var conbut:Button = findViewById(R.id.conbut)
        var textFieldcodeofmember:TextInputEditText = findViewById(R.id.textFieldcodeofmember)
        var textFieldpasscode:TextInputEditText = findViewById(R.id.textFieldpasscode)
        textFieldpasscode.addTextChangedListener{
            conbut.isEnabled = textFieldpasscode.text.toString().length ==8
        }
        var sharedPref = getSharedPreferences("ALLPREFEN", Context.MODE_PRIVATE).edit()
        conbut.setOnClickListener {
            var db = Firebase.firestore
            db.collection("users").get().addOnSuccessListener {
                var fg = true
                for(res in it){
                    if(res.getString("code") == textFieldcodeofmember.text.toString()){
                        if(res.getString("password") == textFieldpasscode.text.toString()){
                            sharedPref.putString("id","${res.id}").apply()
                            sharedPref.putString("password","${res.getString("password")}").apply()
                            sharedPref.putString("role","${res.getString("role")}").apply()
                            sharedPref.putString("code","${res.getString("code")}").apply()
                            sharedPref.putString("name","${res.getString("name")}").apply()
                            sharedPref.putString("lastname","${res.getString("lastname")}").apply()
                            sharedPref.putString("status","${res.getString("status")}").apply()
                            sharedPref.putString("entertoapplication","7").apply()
                            fg = false
                            if(res.getString("role")=="user"){
                                startActivity(Intent(this, user::class.java))
                            }
                            else if(res.getString("role") == "admin"){
                                startActivity(Intent(this, admin::class.java))
                            }
                            else{
                                Snackbar.make(findViewById(R.id.bg),"Ошибка!",Snackbar.LENGTH_LONG).show()
                            }
                        }
                        else{
                            Snackbar.make(findViewById(R.id.bg),"Неправильное имя пользователя!",Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
                if(!fg){
                    Snackbar.make(findViewById(R.id.bg),"Такого пользователя не существует!",Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
    override fun onBackPressed() {}
}