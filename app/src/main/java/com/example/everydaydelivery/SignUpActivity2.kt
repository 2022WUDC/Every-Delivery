package com.example.everydaydelivery

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit

class SignUpActivity2 : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    lateinit var edt_name : EditText
    lateinit var edt_nickname : EditText
    lateinit var edt_id : EditText
    lateinit var edt_passwd : EditText
    lateinit var edt_passwd_chk : EditText
    lateinit var edt_phone : EditText
    lateinit var edt_phone_check_num: EditText
    lateinit var btn_phone_chk : Button
    lateinit var btn_sign_up : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up2)


        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val dbReference = database.reference
        val userRef = dbReference.child("users")


        edt_name = findViewById(R.id.edt_name)
        edt_nickname = findViewById(R.id.edt_nickname)
        edt_id = findViewById(R.id.edt_id)
        edt_passwd = findViewById(R.id.edt_passwd)
        edt_passwd_chk = findViewById(R.id.edt_passwd_chk)
        edt_phone = findViewById(R.id.edt_phone)
        edt_phone_check_num = findViewById(R.id.edt_phone_check_num)
        btn_phone_chk = findViewById(R.id.btn_phone_chk)
        btn_sign_up = findViewById(R.id.btn_sign_up)



//        btn_phone_chk.setOnClickListener {
//            if (edt_phone.toString().length == 0){
//                Toast.makeText(this, "전화번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
//            } else {
//                edt_phone_check_num.setVisibility(View.VISIBLE)
//
//            }
//        }


        btn_sign_up.setOnClickListener {
            var name = edt_name.text.toString()
            var nickname = edt_nickname.text.toString()
            var email = edt_id.text.toString()
            var password = edt_passwd.text.toString()
            var pwCheck = edt_passwd_chk.text.toString()
            var phone = edt_phone.text.toString()

            if (name.length == 0){
                Toast.makeText(this, "이름을 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (nickname.length == 0){
                Toast.makeText(this, "닉네임을 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (email.length == 0){
                Toast.makeText(this, "이메일을 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (password.length == 0){
                Toast.makeText(this, "비밀번호를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (pwCheck.length == 0){
                Toast.makeText(this, "비밀번호 확인을 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (password != pwCheck){
                Toast.makeText(this, "비밀번호가 동일하지 않습니다.", Toast.LENGTH_SHORT).show()
            } else {

                if (edt_name.toString().length == 0){
                    Toast.makeText(this, "이름을 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { result->
                            if (result.isSuccessful){
                                val user = firebaseAuth.currentUser
                                Toast.makeText(this, "Authentication Success", Toast.LENGTH_SHORT).show()


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
                                Toast.makeText(this, "Authentication failed" + email + password, Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }


    }


//    fun signup(email:String, password:String){
//        firebaseAuth.createUserWithEmailAndPassword(email, password)
//            .addOnCompleteListener { result->
//                if (result.isSuccessful){
//                    val user = firebaseAuth.currentUser
//                    Toast.makeText(this, "Authentication Success", Toast.LENGTH_SHORT).show()
//
//                    var intent = Intent(this, LoginActivity::class.java)
//                    startActivity(intent)
//                } else {
//                    Toast.makeText(this, "Authentication failed" + email + password, Toast.LENGTH_SHORT).show()
//                }
//            }
//        }


    }


//    // 전화번호 인증코드 요청
//    private fun startPhoneNumberVerification(phoneNumber: String) {
//        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
//            .setPhoneNumber(phoneNumber)       // Phone number to verify
//            .setTimeout(90L, TimeUnit.SECONDS) // Timeout and unit
//            .setActivity(this)                 // Activity (for callback binding)
//            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)

//        binding.phoneAuthBtnAuth.run {
//            text = "재전송"
//            setTextColor(
//                ContextCompat.getColor(
//                    this@PhoneAuthActivity, R.color.dark_gray_333333
//                )
//            )
//            background = ContextCompat.getDrawable(
//                this@PhoneAuthActivity, R.drawable.bg_btn_stroke_dark_gray_333333_radius_8dp
//            )
//        }
//    }

//}