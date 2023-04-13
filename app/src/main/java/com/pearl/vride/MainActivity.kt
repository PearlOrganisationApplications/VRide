package com.pearl.vride

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.figgo.customer.pearlLib.PrefManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.pearl.test5.R

class MainActivity : AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 1

    lateinit var pref: PrefManager
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pref = PrefManager(this@MainActivity)
        val signup = findViewById<TextView>(R.id.signupBt)
        val login = findViewById<Button>(R.id.loginBT)
        val showHideBtn = findViewById<TextView>(R.id.show)
        val pwdEd = findViewById<EditText>(R.id.passwordET)
        val img_vis = findViewById<ImageView>(R.id.img_vis)
        val userID = findViewById<EditText>(R.id.userID)
        val ll_password = findViewById<LinearLayout>(R.id.ll_password)
        val signInButton = findViewById<TextView>(R.id.google_login_button)

        pref.setLogin("yes")
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this@MainActivity, gso);
        val account = GoogleSignIn.getLastSignedInAccount(this@MainActivity)



        signup.setOnClickListener {
            startActivity(Intent(this@MainActivity,SignUpActivity::class.java))
//            finish()
        }

        login.setOnClickListener {
            if (userID.text.toString().equals("") || userID.text.toString().equals("null")){
                Toast.makeText(this@MainActivity, "Please Enter User name First !:", Toast.LENGTH_LONG).show()

            }else {
                ll_password.isVisible = true
            }
            if (pwdEd.text.toString().equals("") || pwdEd.text.toString().equals("null")){
              //  Toast.makeText(this@MainActivity, "Wrong Password !:", Toast.LENGTH_LONG).show()

            }else {

                startActivity(Intent(this@MainActivity, HomeScreen::class.java))
            }
        }

        showHideBtn.setOnClickListener {
            if(showHideBtn.text.toString().equals("Show")){
                pwdEd.transformationMethod = HideReturnsTransformationMethod.getInstance()
                showHideBtn.text = "Hide"
                img_vis.setBackgroundResource(R.drawable.hide)

            } else{
                pwdEd.transformationMethod = PasswordTransformationMethod.getInstance()
                showHideBtn.text = "Show"
                img_vis.setBackgroundResource(R.drawable.view)
            }
        }
        signInButton.setOnClickListener {
            val signInIntent: Intent = mGoogleSignInClient!!.getSignInIntent()
            startActivityForResult(signInIntent, RC_SIGN_IN)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }





    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.d("Account ", "" + account.account)


            // prefManager.setToken("")
            Toast.makeText(this@MainActivity, "Signed In :" + account.account.toString(), Toast.LENGTH_LONG).show()

            /* if (pref.getMpin().equals("") || pref.getMpin().equals("null")) {
                 startActivity(Intent(this,MPinGenerate::class.java))
             } else {

             }*/
            startActivity(Intent(this,HomeScreen::class.java))
            // Signed in successfully, show authenticated UI.
            // updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("SignIN = ", "signInResult:failed code=" + e.statusCode)
            //updateUI(null)
        }
    }



}