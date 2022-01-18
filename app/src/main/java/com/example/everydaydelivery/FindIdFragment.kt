package com.example.everydaydelivery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class FindIdFragment : Fragment() {

    lateinit var etFindId_phone: EditText
    lateinit var etFindId_authNum: EditText
    lateinit var btnFindId_authNum: Button
    lateinit var btnFindId_check: Button
    lateinit var btnFindId: Button

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var storedVerificationId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_find_id, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        etFindId_phone = view.findViewById(R.id.editText_findId_phone)
        etFindId_authNum = view.findViewById(R.id.editText_findId_authNum)
        btnFindId_authNum = view.findViewById(R.id.button_findId_authNum)
        btnFindId_check = view.findViewById(R.id.button_findId_check)
        btnFindId = view.findViewById(R.id.button_findId)

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(activity, MainActivity::class.java))
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(activity, "Failed", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("TAG", "onCodeSent:$verificationId")
                storedVerificationId = verificationId
            }
        }

        btnFindId_authNum.setOnClickListener{
            phoneCheck()
            btnFindId_check.isEnabled = true
        }


        btnFindId_check.setOnClickListener{
            var check_num = etFindId_authNum.text.toString()
            if (!check_num.isEmpty()) {
                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId, check_num
                )
                signInWithPhoneAuthCredential(credential)
            } else {
                Toast.makeText(activity, "인증 번호를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }

        btnFindId.setOnClickListener {

        }

        return view
    }


    private fun phoneCheck() {
        var etPhone = etFindId_phone.text.toString()

        var front = etPhone.substring(1, 2)
        var middle = etPhone.substring(3, 6)
        var end = etPhone.substring(7, 10)

        var phone = front + '-' + middle + '-' + end

        if (!phone.isEmpty()) {
            phone = "+82" + phone
            Log.d("TAG", "$phone")
            sendVerificationcode(phone)
        } else {
            Toast.makeText(activity, "전화번호를 입력하세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationcode(number: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this.requireActivity()) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        Log.d("TAG", "$options")
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this.requireActivity()) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity, "인증 되었습니다", Toast.LENGTH_SHORT).show()
                    btnFindId.isEnabled = true
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(activity, "인증번호가 틀렸습니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}