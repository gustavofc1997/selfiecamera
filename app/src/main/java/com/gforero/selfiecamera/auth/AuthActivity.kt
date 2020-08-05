package com.gforero.selfiecamera.auth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gforero.selfiecamera.R
import com.gforero.selfiecamera.home.HomeActivity
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        btnLogin.setOnClickListener {
            loginHuaweiIDAuth()
        }
    }

    private fun loginHuaweiIDAuth() {
        val authParams = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
            .setEmail()
            .setAccessToken()
            .setProfile()
            .setIdToken()
            .setUid()
            .setId().createParams()
        val authManager = HuaweiIdAuthManager.getService(this, authParams)
        startActivityForResult(
            authManager.signInIntent,
            result
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == result) {
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Login Cancelado", Toast.LENGTH_SHORT).show()
            } else if (resultCode == Activity.RESULT_OK) {
                val authHuawei = HuaweiIdAuthManager.parseAuthResultFromIntent(data)
                if (authHuawei.isSuccessful) {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Login Failure", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val result = 1000
    }
}