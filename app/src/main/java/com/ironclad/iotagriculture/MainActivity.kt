package com.ironclad.iotagriculture

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Toast.makeText(this@MainActivity, "Verification Complete", Toast.LENGTH_LONG).show()
                signInWithPhone(p0)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(this@MainActivity, "Verification Failed", Toast.LENGTH_LONG).show()

            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)

                Toast.makeText(this@MainActivity, "Code Sent", Toast.LENGTH_LONG).show()

            }

        }


        btnLogin.setOnClickListener {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91${phone.text}",
                60,
                TimeUnit.SECONDS,
                this,
                callback
            )

            btnLogin.doResult(true)
        }
    }

    private fun signInWithPhone(p0: PhoneAuthCredential) {
        auth.signInWithCredential(p0)
            .addOnCompleteListener { }
            .addOnFailureListener { }
            .addOnSuccessListener { }
    }
}
