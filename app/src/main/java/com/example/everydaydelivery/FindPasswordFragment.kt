package com.example.everydaydelivery

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener


class FindPasswordFragment : Fragment() {
    lateinit var imm: InputMethodManager

    lateinit var etEmail: EditText
    lateinit var etAuthNum: EditText
    lateinit var etChangePw: EditText
    lateinit var btnAuthNum: Button
    lateinit var btnCheck: Button
    lateinit var btnChangePw: Button
    lateinit var btnLogin: Button

    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_find_password, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        etEmail = view.findViewById(R.id.editText_findPw_email)
        btnAuthNum = view.findViewById(R.id.button_findPw_authNum)
        btnLogin = view.findViewById(R.id.button_login)

        btnAuthNum.setOnClickListener{
            val user = firebaseAuth.currentUser

            resetPassword(etEmail.text.toString())
        }

        btnLogin.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        return view
    }

    private fun resetPassword(email:String){
        firebaseAuth?.sendPasswordResetEmail(email)
            ?.addOnCompleteListener{ it ->
                if(it.isSuccessful){
                    Toast.makeText(activity, "재설정을 위한 이메일을 전송에 성공하였습니다.", Toast.LENGTH_LONG).show()
                    btnLogin.isEnabled = true
                } else {
                    Toast.makeText(activity, "이메일 전송에 실패하였습니다.", Toast.LENGTH_LONG).show()
                }
            }
    }
}