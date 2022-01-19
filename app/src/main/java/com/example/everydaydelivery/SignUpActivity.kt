package com.example.everydaydelivery

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit

class SignUpActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    lateinit var storedVerificationId: String
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    lateinit var imm: InputMethodManager

    lateinit var layout: ConstraintLayout
    lateinit var btn_back: ImageButton

    lateinit var edt_name: EditText
    lateinit var edt_nickname: EditText
    lateinit var edt_id: EditText
    lateinit var edt_passwd: EditText
    lateinit var edt_passwd_chk: EditText
    lateinit var edt_phone: EditText
    lateinit var edt_phone_check_num: EditText
    lateinit var btn_phone_chk: Button
    lateinit var btn_cert_chk: Button
    lateinit var btn_sign_up: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        window.setStatusBarColor(Color.WHITE)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val dbReference = database.reference
        val userRef = dbReference.child("users")

        imm = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager

        layout = findViewById(R.id.layout_signup)
        btn_back = findViewById(R.id.btn_back)

        edt_name = findViewById(R.id.edt_name)
        edt_nickname = findViewById(R.id.edt_nickname)
        edt_id = findViewById(R.id.edt_id)
        edt_passwd = findViewById(R.id.edt_passwd)
        edt_passwd_chk = findViewById(R.id.edt_passwd_chk)
        edt_phone = findViewById(R.id.edt_phone)
        edt_phone_check_num = findViewById(R.id.edt_cert_chk)
        btn_cert_chk = findViewById(R.id.btn_cert_chk)
        btn_phone_chk = findViewById(R.id.btn_phone_chk)
        btn_sign_up = findViewById(R.id.btn_sign_up)


        layout.setOnClickListener {
            imm.hideSoftInputFromWindow(edt_name.windowToken, 0)
            imm.hideSoftInputFromWindow(edt_nickname.windowToken, 0)
            imm.hideSoftInputFromWindow(edt_id.windowToken, 0)
            imm.hideSoftInputFromWindow(edt_passwd.windowToken, 0)
            imm.hideSoftInputFromWindow(edt_passwd_chk.windowToken, 0)
            imm.hideSoftInputFromWindow(edt_phone.windowToken, 0)
            imm.hideSoftInputFromWindow(edt_phone_check_num.windowToken, 0)
        }

        btn_back.setOnClickListener {
            finish()
        }


        // 인증번호 전송
        btn_phone_chk.setOnClickListener {
            phoneCheck()
            imm?.hideSoftInputFromWindow(edt_phone.windowToken, 0)
        }


        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext, "인증 번호 전송에 실패하였습니다.", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("TAG", "onCodeSent:$verificationId")
                storedVerificationId = verificationId
            }
        }

        //인증번호 확인
        btn_cert_chk.setOnClickListener {
            imm?.hideSoftInputFromWindow(btn_cert_chk.windowToken, 0)

            var check_num = edt_phone_check_num.text.toString()
            if (!check_num.isEmpty()) {
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId, check_num
                )
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(this, "인증 번호를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }

        btn_sign_up.setOnClickListener {
            imm?.hideSoftInputFromWindow(btn_cert_chk.windowToken, 0)

            var name = edt_name.text.toString()
            var nickname = edt_nickname.text.toString()
            var email = edt_id.text.toString()
            var password = edt_passwd.text.toString()
            var pwCheck = edt_passwd_chk.text.toString()
            var phone = edt_phone.text.toString()

            if (name.length == 0) {
                Toast.makeText(this, "이름을 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (nickname.length == 0) {
                Toast.makeText(this, "닉네임을 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (email.length == 0) {
                Toast.makeText(this, "이메일을 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (password.length == 0) {
                Toast.makeText(this, "비밀번호를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (pwCheck.length == 0) {
                Toast.makeText(this, "비밀번호 확인을 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (password != pwCheck) {
                Toast.makeText(this, "비밀번호가 동일하지 않습니다.", Toast.LENGTH_SHORT).show()
            } else {

                if (edt_name.toString().length == 0) {
                    Toast.makeText(this, "이름을 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { result ->
                            if (result.isSuccessful) {
                                val user = firebaseAuth.currentUser
                                Toast.makeText(this, "계정이 생성되었습니다.", Toast.LENGTH_SHORT).show()


                                val user_db = userRef.child(user?.uid.toString())
                                Log.d(TAG, "child.uid : " + user?.uid.toString())

                                user_db.child("uid").setValue(user?.uid.toString())
                                Log.d(TAG, "child.uid : " + user?.uid.toString())

                                user_db.child("name").setValue(name)
                                Log.d(TAG, "child.name : " + name)

                                user_db.child("nickname").setValue(nickname)
                                Log.d(TAG, "child.nickname : " + nickname)

                                user_db.child("email").setValue(email)
                                Log.d(TAG, "child.email : " + email)

                                user_db.child("phone").setValue(phone)
                                Log.d(TAG, "child.phone : " + phone)

                                var intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(
                                    this,
                                    "Authentication failed" + email + password,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            }
        }

    }


    private fun phoneCheck() {
        var phone = edt_phone.text.toString()

        if (!phone.isEmpty()) {
            phone = "+82" + phone.substring(1)
            Log.d("TAG", "$phone")
            sendVerificationcode(phone)
            btn_cert_chk.isEnabled = true
            btn_cert_chk.setBackgroundColor(getColor(R.color.bg_orange))
        } else {
            Toast.makeText(this, "전화번호를 입력하세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        Log.d("TAG", "$options")
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "인증 되었습니다", Toast.LENGTH_SHORT).show()
                    btn_sign_up.isEnabled = true
                    btn_sign_up.setBackgroundColor(getColor(R.color.bg_orange))
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(this, "인증번호가 틀렸습니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}