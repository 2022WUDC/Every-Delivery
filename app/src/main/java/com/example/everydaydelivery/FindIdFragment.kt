package com.example.everydaydelivery

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
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
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.*
import java.util.Arrays.toString
import java.util.Objects.toString
import java.util.concurrent.TimeUnit

class FindIdFragment : Fragment() {
    lateinit var imm: InputMethodManager

    lateinit var etFindId_phone: EditText
    lateinit var etFindId_authNum: EditText
    lateinit var btnFindId_authNum: Button
    lateinit var btnFindId_check: Button
    lateinit var btnFindId: Button

    lateinit var textView1: TextView
    lateinit var textView2: TextView
    lateinit var textView3: TextView
    lateinit var btnLogin: Button

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var storedVerificationId: String
    private lateinit var database: FirebaseDatabase
    lateinit var ref: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_find_id, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val dbReference = database.reference
        val userRef = dbReference.child("users")
        ref = userRef

        imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        etFindId_phone = view.findViewById(R.id.editText_findId_phone)
        etFindId_authNum = view.findViewById(R.id.editText_findId_authNum)
        btnFindId_authNum = view.findViewById(R.id.button_findId_authNum)
        btnFindId_check = view.findViewById(R.id.button_findId_check)
        btnFindId = view.findViewById(R.id.button_findId)
        textView1 = view.findViewById(R.id.textView_findId1)
        textView2 = view.findViewById(R.id.textView_findId2)
        textView3 = view.findViewById(R.id.textView_findId3)
        btnLogin = view.findViewById(R.id.button_login)

        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(activity, MainActivity::class.java))
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(activity, "인증 번호 전송에 실패하였습니다.", Toast.LENGTH_LONG).show()
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
            imm.hideSoftInputFromWindow(etFindId_authNum.windowToken, 0)
        }


        btnFindId_check.setOnClickListener{
            imm.hideSoftInputFromWindow(etFindId_authNum.windowToken, 0)

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
            val userQuery = dbReference.child("users").orderByChild("phone").equalTo(etFindId_phone.text.toString())
//            Toast.makeText(activity, "db : " + userQuery.toString(), Toast.LENGTH_SHORT).show()

            userQuery.addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val email = snapshot.child("email").getValue()

                    if (email != null){
                        etFindId_phone.visibility = View.GONE
                        etFindId_authNum.visibility = View.GONE
                        btnFindId_authNum.visibility = View.GONE
                        btnFindId_check.visibility = View.GONE
                        btnFindId.visibility = View.GONE

                        textView2.text = email.toString()
                        textView1.visibility = View.VISIBLE
                        textView2.visibility = View.VISIBLE
                        textView3.visibility = View.VISIBLE
                        btnLogin.visibility = View.VISIBLE

                        val user = firebaseAuth.currentUser!!

                        user.delete()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Log.d(TAG, "User account deleted.")
                                }
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })



        if(btnLogin.isVisible == false) {
            Toast.makeText(activity, "아이디를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()

            val user = firebaseAuth.currentUser!!

            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User account deleted.")
                        etFindId_phone.text = null
                        etFindId_authNum.text = null
                        btnFindId_check.isEnabled = false
                        btnFindId.isEnabled = false
                    } else{
                        Log.d(TAG, "User account deleted is Error.")
                    }
                }
            }
        }

        btnLogin.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        return view
    }

    private fun phoneCheck() {
        var phone = etFindId_phone.text.toString()

        if (!phone.isEmpty()) {
            phone = "+82" + phone.substring(1)
            Log.d("TAG", "$phone")
            sendVerificationcode(phone)
            btnFindId_check.isEnabled = true
            btnFindId_check.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.bg_orange))
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
                    btnFindId.setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, R.color.bg_orange))

                    val user = firebaseAuth.currentUser
                    val user_db = ref.child(user?.uid.toString())
                    Log.d(ContentValues.TAG, "child.uid : " + user?.uid.toString())

                    user_db.child("email").get().addOnSuccessListener {
                        Log.i("firebase", "Got value ${it.value}")
                    }.addOnFailureListener{
                        Log.e("firebase", "Error getting data", it)
                    }

                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Toast.makeText(activity, "인증번호가 틀렸습니다", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}