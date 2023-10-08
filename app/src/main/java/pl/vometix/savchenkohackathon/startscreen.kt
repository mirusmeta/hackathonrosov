package pl.vometix.savchenkohackathon

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class startscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startscreen)
        val sharedPref = getSharedPreferences("ALLPREFEN", Context.MODE_PRIVATE)
        sharedPref.edit().remove("id").apply()
        sharedPref.edit().remove("password").apply()
        sharedPref.edit().remove("role").apply()
        sharedPref.edit().remove("code").apply()
        sharedPref.edit().remove("name").apply()
        sharedPref.edit().remove("lastname").apply()
        sharedPref.edit().remove("entertoapplication").apply()
        var conbut:Button =findViewById(R.id.conbut)
        conbut.setOnClickListener {
            startActivity(Intent(this, auth::class.java))
        }
    }
    override fun onBackPressed() {}
}