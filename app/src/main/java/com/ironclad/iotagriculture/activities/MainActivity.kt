package com.ironclad.iotagriculture.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.ironclad.iotagriculture.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var verificationCode :String = ""
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
                otp.text = p0.smsCode
                btnLogin.doResult(true)
                val intent = Intent(this@MainActivity, WeatherActivity::class.java)
                startActivity(intent)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                btnLogin.doResult(false)
                Toast.makeText(this@MainActivity, "Verification Failed", Toast.LENGTH_LONG).show()

            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)

                Toast.makeText(this@MainActivity, "Code Sent", Toast.LENGTH_LONG).show()
                verificationCode = p0
                Log.d("Code","""
                    String: $p0
                    Token: $p1
                """.trimIndent())

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
        }
    }

    private fun signInWithPhone(p0: PhoneAuthCredential) {
        auth.signInWithCredential(p0)
            .addOnCompleteListener {
                Log.d("Code",p0.smsCode)
            }
            .addOnFailureListener { }
            .addOnSuccessListener { }
    }
}
