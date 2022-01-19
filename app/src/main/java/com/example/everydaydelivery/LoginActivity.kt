package com.example.everydaydelivery

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager


class LoginActivity : AppCompatActivity() {

    lateinit var layout: LinearLayout

    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: Button
    lateinit var tvSignup: TextView
    lateinit var tvFind: TextView

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var imm: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()

        imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager

        layout = findViewById(R.id.layout_login)
        etEmail = findViewById(R.id.editText_email)
        etPassword = findViewById(R.id.editText_pw)
        btnLogin = findViewById(R.id.button_login)
        tvSignup = findViewById(R.id.textview_signup)
        tvFind = findViewById(R.id.textview_find_id_pw)

        layout.setOnClickListener {
            imm.hideSoftInputFromWindow(etEmail.windowToken, 0)
            imm.hideSoftInputFromWindow(etPassword.windowToken, 0)
        }

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
                            var intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Authentication failed.",  Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        tvFind.setOnClickListener{
            var intent = Intent(this, FindActivity::class.java)
            startActivity(intent)
        }


        tvSignup.setOnClickListener{
            if(firebaseAuth.currentUser != null){
                firebaseAuth.signOut()
            }

            var intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

    }
}