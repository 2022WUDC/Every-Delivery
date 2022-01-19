package com.example.everydaydelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class DeliveryActivity : AppCompatActivity() {

    // 파이어베이스 추가
    private lateinit var auth: FirebaseAuth

    private lateinit var deliveryExFragment: DeliveryExFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)

        // 파이어베이스 추가
        auth = Firebase.auth

        deliveryExFragment = DeliveryExFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.delivery_frame, deliveryExFragment).commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_refresh -> {

                Toast.makeText(this, "새로고침", Toast.LENGTH_SHORT).show()

                // 새로고침
                var intent = getIntent()
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}