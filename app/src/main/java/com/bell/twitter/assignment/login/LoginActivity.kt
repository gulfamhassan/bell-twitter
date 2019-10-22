package com.bell.twitter.assignment.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bell.twitter.assignment.R
import com.bell.twitter.assignment.utils.PermissionValidator
import com.twitter.sdk.android.core.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var interactor: LoginContract.Interactor
    private lateinit var router: LoginContract.Router
    private lateinit var presenter: LoginContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(!PermissionValidator.isAllPermissionsAllowed(this)){
            PermissionValidator.requestPermission(this)
        }
        interactor = LoginInteractor()
        router = LoginRouter(this)
        presenter = LoginPresenter(this, interactor, router)
        presenter.onInitializeRequested()

        twitterLoginButton.callback = presenter as Callback<TwitterSession>
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        twitterLoginButton.onActivityResult(requestCode, resultCode, data)
    }
}
