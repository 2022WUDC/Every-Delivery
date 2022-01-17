package com.example.everydaydelivery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    lateinit var edt_name: EditText
    lateinit var edt_nickname: EditText
    lateinit var edt_email: EditText
    lateinit var edt_passwd: EditText
    lateinit var edt_passwd_chk: EditText
    lateinit var edt_phone_chk: EditText
    lateinit var btn_phone_send: Button
    lateinit var btn_cert_chk: Button
    lateinit var btn_sign_up: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = Firebase.auth

        edt_name = findViewById(R.id.edt_name)
        edt_nickname = findViewById(R.id.edt_nickname)
        edt_email = findViewById(R.id.edt_email)
        edt_passwd = findViewById(R.id.edt_passwd)
        edt_passwd_chk = findViewById(R.id.edt_passwd_chk)
        edt_phone_chk = findViewById(R.id.edt_phone_chk)
        btn_phone_send = findViewById(R.id.btn_phone_send)
        btn_cert_chk = findViewById(R.id.btn_cert_chk)
        btn_sign_up = findViewById(R.id.btn_sign_up)

        btn_phone_send.setOnClickListener {
            phoneCheck()
        }

        btn_sign_up.setOnClickListener {
            signup()
        }

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {

                Log.d("TAG","onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token

                var intent = Intent(applicationContext,MainActivity::class.java)
                intent.putExtra("storedVerificationId",storedVerificationId)
                startActivity(intent)
            }
        }

    }

    private fun phoneCheck() {

        var phone = edt_phone_chk.text.toString()

        if (!phone.isEmpty()) {
            phone = "+82" + phone
            sendVerificationcode(phone)
        } else {
            Toast.makeText(this, "전화번호를 입력하세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signup() {
        if (edt_name.text.toString().length == 0 || edt_nickname.text.toString().length == 0 ||
            edt_email.text.toString().length == 0 || edt_passwd.text.toString().length == 0 ||
            edt_passwd_chk.text.toString().length == 0 || edt_phone_chk.text.toString().length == 0
        ) {
            Toast.makeText(this, "양식을 모두 작성해주세요", Toast.LENGTH_SHORT).show()
        } else if (edt_passwd.text.toString() != edt_passwd_chk.text.toString()) {
            Toast.makeText(this, "password가 같지 않습니다.", Toast.LENGTH_SHORT).show()
        } else {
            auth.createUserWithEmailAndPassword(
                edt_email.text.toString(),
                edt_passwd.text.toString()
            )
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "회원가입 실패", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

}