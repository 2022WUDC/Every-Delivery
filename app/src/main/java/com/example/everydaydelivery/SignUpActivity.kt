package com.example.everydaydelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    lateinit var edt_name : EditText
    lateinit var edt_nickname : EditText
    lateinit var edt_id : EditText
    lateinit var edt_passwd : EditText
    lateinit var edt_passwd_chk : EditText
    lateinit var edt_phone_chk : EditText
    lateinit var btn_phone_chk : Button
    lateinit var btn_sign_up : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        edt_name = findViewById(R.id.edt_name)
        edt_nickname = findViewById(R.id.edt_nickname)
        edt_id = findViewById(R.id.edt_id)
        edt_passwd = findViewById(R.id.edt_passwd)
        edt_passwd_chk = findViewById(R.id.edt_passwd_chk)
        edt_phone_chk = findViewById(R.id.edt_phone_chk)
        btn_phone_chk = findViewById(R.id.btn_phone_chk)
        btn_sign_up = findViewById(R.id.btn_sign_up)

        btn_phone_chk.setOnClickListener {

        }

    }

}