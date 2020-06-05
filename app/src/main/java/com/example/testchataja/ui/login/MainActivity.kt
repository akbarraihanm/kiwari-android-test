package com.example.testchataja.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.testchataja.R
import com.example.testchataja.ui.chat.ChatActivity
import com.example.testchataja.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LoginContract {

    private lateinit var loginPresenter: LoginPresenter
    private lateinit var pref : AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginPresenter = LoginPresenter(this)
        pref = AppPreferences(this)
        pref.setPreferences()

        if (pref.getNameUser() != "") {
            val intent = Intent(this,ChatActivity::class.java)
            startActivity(intent)
            finish()
        }

        btn_login.setOnClickListener {
            checkCredentials()
        }
    }

    private fun checkCredentials() {
        val emailTxt = "${et_email.text}"
        val pwTxt = "${et_password.text}"

        if (emailTxt.isEmpty()) {
            et_email.error = "Email must be filled"
        }
        if (pwTxt.isEmpty()) {
            et_password.error = "Password must be filled"
        }
        if (emailTxt.isNotEmpty() && pwTxt.isNotEmpty()) {
            loginPresenter.doLogin(emailTxt,pwTxt)
        }
    }

    override fun whenSuccessLogin(nameUser: String) {
        pref.setNameUser(nameUser)
        val intent = Intent(this,ChatActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun whenPasswordWrong(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    override fun whenEmailWrong(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}
