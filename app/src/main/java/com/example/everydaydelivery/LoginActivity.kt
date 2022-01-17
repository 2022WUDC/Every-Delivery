package com.example.everydaydelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button

    lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.editText_email)
        etPassword = findViewById(R.id.editText_pw)
        btnLogin = findViewById(R.id.button_login)

        btnLogin.setOnClickListener {
            var email = etEmail.text.toString()
            var password = etPassword.text.toString()
            firebaseAuth.createUserWithEmailAndPassword(email, password) // 회원 가입
                .addOnCompleteListener {
                        result ->
                    if(result.isSuccessful){
                        Toast.makeText(this,"회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        if(firebaseAuth.currentUser!=null){
                            var intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    else if(result.exception?.message.isNullOrEmpty()){
                        Toast.makeText(this,"오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        login(email, password)
                    }
                }
        }
    }

    fun login(email:String, password:String){
        firebaseAuth.signInWithEmailAndPassword(email, password) // 로그인
            .addOnCompleteListener {
                    result->
                if(result.isSuccessful){
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
    }
}