package pl.vometix.savchenkohackathon

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView

class hello : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello)
        val sharedPref = getSharedPreferences("ALLPREFEN", Context.MODE_PRIVATE)
        var name = sharedPref.getString("name","null")
        var role = sharedPref.getString("role","null")
        var nametext:TextView = findViewById(R.id.nametext)
        nametext.text = "Приветствую,\n$name"
        var roletext:TextView = findViewById(R.id.roletext)
        var h = Handler()
        if(role=="admin") {
            roletext.text = "Поддержка"
            h.postDelayed({startActivity(Intent(this, admin::class.java))},2150)
        }
        else if(role=="user") {
            roletext.text = "Пользователь"
            h.postDelayed({startActivity(Intent(this, user::class.java))},2150)
        }
    }
}