package com.example.everydaydelivery

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var btnSignup: Button

    lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        etEmail = findViewById(R.id.editText_email)
        etPassword = findViewById(R.id.editText_pw)
        btnLogin = findViewById(R.id.button_login)
        btnSignup = findViewById(R.id.button_signup)




        btnLogin.setOnClickListener {
            var email = etEmail.text.toString()
            var password = etPassword.text.toString()

            if (email.toString().length == 0 || password.toString().length == 0){
                Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                firebaseAuth.signInWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
//                            val user = firebaseAuth.currentUser
                            var intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",  Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }


        btnSignup.setOnClickListener{
            if(firebaseAuth.currentUser != null){
                firebaseAuth.signOut()
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "no logout", Toast.LENGTH_SHORT).show()
            }

            var intent = Intent(this, SignUpActivity2::class.java)
            startActivity(intent)
        }

    }



//            firebaseAuth.createUserWithEmailAndPassword(email, password) // 회원 가입
//                .addOnCompleteListener {
//                        result ->
//                    if(result.isSuccessful){
//                        Toast.makeText(this,"회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
//                        if(firebaseAuth.currentUser!=null){
//                            var intent = Intent(this, MainActivity::class.java)
//                            startActivity(intent)
//                        }
//                    }
//                    else if(result.exception?.message.isNullOrEmpty()){
//                        Toast.makeText(this,"오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
//                    }
//                    else{
//                        login(email, password)
//                    }
//                }

//    fun login(email:String, password:String){
//        firebaseAuth.signInWithEmailAndPassword(email, password) // 로그인
//            .addOnCompleteListener {
//                    result->
//                if(result.isSuccessful){
//                    var intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)
//                }
//            }
//    }
}