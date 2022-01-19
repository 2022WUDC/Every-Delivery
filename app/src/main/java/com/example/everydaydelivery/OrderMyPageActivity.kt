package com.example.everydaydelivery

import android.hardware.input.InputManager
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.*

class OrderMyPageActivity : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var storedVerificationId: String
    private lateinit var database: FirebaseDatabase
    lateinit var ref: DatabaseReference

    lateinit var tvName: TextView
    lateinit var etNickname: EditText
    lateinit var tvPhone: TextView
    lateinit var etAddress: EditText
    lateinit var etAccount: EditText

    lateinit var btnChangeNickname: ImageButton
    lateinit var btnChangeAddress: ImageButton
    lateinit var btnChangeAccount: ImageButton

    lateinit var btnCheck1: ImageButton
    lateinit var btnCheck2: ImageButton
    lateinit var btnCheck3: ImageButton

    lateinit var inputManager: InputMethodManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_my_page)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        val dbReference = database.reference

        tvName = findViewById(R.id.textview_name)
        etNickname = findViewById(R.id.eidtText_nickname)
        tvPhone = findViewById(R.id.textview_phone)
        etAddress = findViewById(R.id.editText_address)
        etAccount = findViewById(R.id.editText_account)

        btnChangeNickname = findViewById(R.id.button_changeNickname)
        btnChangeAddress = findViewById(R.id.button_changeAddress)
        btnChangeAccount = findViewById(R.id.button_changeAccount)

        btnCheck1 = findViewById(R.id.button_check1)
        btnCheck2 = findViewById(R.id.button_check2)
        btnCheck3 = findViewById(R.id.button_check3)

        inputManager = getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager


        val uid = firebaseAuth.currentUser?.uid.toString()
        val userQuery = dbReference.child("users").orderByChild("uid").equalTo(uid)

        btnChangeNickname.setOnClickListener {
            btnChangeNickname.visibility = View.GONE
            btnCheck1.visibility = View.VISIBLE

            etNickname.isEnabled = true
            inputManager?.showSoftInput(etNickname, 0)
        }

        btnCheck1.setOnClickListener {
            btnChangeNickname.visibility = View.VISIBLE
            btnCheck1.visibility = View.GONE

            etNickname.isEnabled = false
            dbReference.child("users/" + uid + "/nickname").setValue(etNickname.text.toString())
        }


        btnChangeAddress.setOnClickListener {
            btnChangeAddress.visibility = View.GONE
            btnCheck2.visibility = View.VISIBLE

            etAddress.isEnabled = true
            inputManager?.showSoftInput(etAddress, 0)
        }

        btnCheck2.setOnClickListener {
            btnChangeAddress.visibility = View.VISIBLE
            btnCheck2.visibility = View.GONE

            etAddress.isEnabled = false
            dbReference.child("users/" + uid + "/address").setValue(etAddress.text.toString())
        }

        btnChangeAccount.setOnClickListener {
            btnChangeAccount.visibility = View.GONE
            btnCheck3.visibility = View.VISIBLE

            etAccount.isEnabled = true
            inputManager?.showSoftInput(etAccount, 0)
        }

        btnCheck2.setOnClickListener {
            btnChangeAccount.visibility = View.VISIBLE
            btnCheck3.visibility = View.GONE

            etAccount.isEnabled = false
            dbReference.child("users/" + uid + "/account").setValue(etAccount.text.toString())
        }



        userQuery.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d("User uid", snapshot.toString())
                val name:String = snapshot.child("name").value.toString()
                val nickname:String = snapshot.child("nickname").value.toString()
                val phone:String = snapshot.child("phone").value.toString()
                val address:String = snapshot.child("address").value.toString()
                val account:String = snapshot.child("account").value.toString()

                tvName.text = name
                etNickname.setText(nickname)
                tvPhone.text = phone
                etAddress.setText(address)
                etAccount.setText(account)


            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                val name:String = snapshot.child("name").value.toString()
                val nickname:String = snapshot.child("nickname").value.toString()
                val phone:String = snapshot.child("phone").value.toString()
                val address:String = snapshot.child("address").value.toString()
                val account:String = snapshot.child("account").value.toString()

                tvName.text = name
                etNickname.setText(nickname)
                tvPhone.text = phone
                etAddress.setText(address)
                etAccount.setText(account)
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }
}