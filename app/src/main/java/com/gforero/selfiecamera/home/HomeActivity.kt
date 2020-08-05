package com.gforero.selfiecamera.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gforero.selfiecamera.R
import com.gforero.selfiecamera.auth.AuthActivity
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        logout.setOnClickListener {
            logout()
        }
    }

    override fun onBackPressed() {
        val authParams =
            HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM).createParams()
        val authManager = HuaweiIdAuthManager.getService(this, authParams)
        val logoutTask = authManager.signOut()
        logoutTask.addOnSuccessListener {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }
        logoutTask.addOnFailureListener {
            Toast.makeText(this, "Logout failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun logout() {

    }
}