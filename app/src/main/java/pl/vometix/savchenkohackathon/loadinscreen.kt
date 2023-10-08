package pl.vometix.savchenkohackathon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class loadinscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loadinscreen)
        val sharedPref = getSharedPreferences("ALLPREFEN", Context.MODE_PRIVATE)
        val value = sharedPref.getString("entertoapplication", null)
        var h = Handler()
        if (value == "7") {
            var db= Firebase.firestore
            db.collection("users").get().addOnSuccessListener {
                var x = false
                for(res in it){
                    if(res.getString("code")==sharedPref.getString("code","null").toString()){
                        x = true
                        if(res.getString("password")==sharedPref.getString("password","null").toString()){
                            if(sharedPref.getString("role","null").toString() == "admin"){
                                h.postDelayed({startActivity(Intent(this, hello::class.java))},800)
                            }
                            else if(sharedPref.getString("role","null").toString() == "user"){
                                h.postDelayed({startActivity(Intent(this, hello::class.java))},800)
                            }
                            else{
                                Snackbar.make(findViewById(R.id.bg),"Ошибка!",Snackbar.LENGTH_LONG).show()
                            }
                        }
                        else{
                            Snackbar.make(findViewById(R.id.bg),"Пароль от данного аккаунта был изменён!",Snackbar.LENGTH_LONG).show()
                            h.postDelayed({startActivity(Intent(this, startscreen::class.java))},800)
                        }
                    }
                }
                if(!x){
                    Snackbar.make(findViewById(R.id.bg),"Данного пользователя не существует!",Snackbar.LENGTH_LONG).show()
                    h.postDelayed({startActivity(Intent(this, startscreen::class.java))},800)
                }
            }
        }
        else{
            h.postDelayed({startActivity(Intent(this, startscreen::class.java))},800)
        }
    }

    override fun onBackPressed() {}
}