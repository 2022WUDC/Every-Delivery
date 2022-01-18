package com.example.everydaydelivery

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnCompleteListener





class FindPasswordFragment : Fragment() {

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
        etAuthNum = view.findViewById(R.id.editText_findPw_authNum)
        etChangePw = view.findViewById(R.id.editText_findPw_changePw)
        btnAuthNum = view.findViewById(R.id.button_findPw_authNum)
        btnCheck = view.findViewById(R.id.button_findPw_check)
        btnChangePw = view.findViewById(R.id.button_changePw)
        btnLogin = view.findViewById(R.id.button_login)


        btnAuthNum.setOnClickListener{
            val user = firebaseAuth.currentUser

            user!!.sendEmailVerification()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(activity, "확인메일을 보냈습니다", Toast.LENGTH_LONG).show()
                        btnCheck.isEnabled = true
                    } else {
                        Toast.makeText(activity, task.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
        }

        btnCheck.setOnClickListener {
            btnChangePw.isEnabled = true
        }

        btnChangePw.setOnClickListener {
            FirebaseAuth.getInstance().sendPasswordResetEmail(etEmail.text.toString()).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(activity, "비밀번호 변경 메일을 전송했습니다", Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(activity, task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }



        return view
    }
}