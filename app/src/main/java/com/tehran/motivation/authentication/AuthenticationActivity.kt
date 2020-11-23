package com.tehran.motivation.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.hideProgress
import com.github.razir.progressbutton.showProgress
import com.google.android.material.snackbar.Snackbar
import com.mobsandgeeks.saripaar.annotation.Password
import com.tehran.motivation.MainActivity
import com.tehran.motivation.R
import com.tehran.motivation.data.LoginData
import com.tehran.motivation.data.Result
import com.tehran.motivation.data.SignUpData
import com.tehran.motivation.data.succeeded
import com.tehran.motivation.databinding.ActivityAuthenticationBinding
import com.tehran.motivation.util.setupSnackbar
import kotlinx.android.synthetic.main.fragment_profile.*
import timber.log.Timber

class AuthenticationActivity : AppCompatActivity() {
    private val binding by lazy{
        DataBindingUtil.setContentView<ActivityAuthenticationBinding>(
            this,
            R.layout.activity_authentication
        )
    }

    private val viewModel: AuthenticationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        setupNavigationToMainActivity()
        setupAuthentication()
        setupSnackBar()

        binding.buttonLogin.attachTextChangeAnimator()
    }

    private fun setupSnackBar() {
        binding.root.setupSnackbar(this, viewModel.snackMessage, Snackbar.LENGTH_SHORT)
    }

    private fun setupAuthentication() {
        viewModel.signupHappened.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                viewModel.signUp(
                    SignUpData(
                        binding.editTextNameSignup.text.toString(),
                        binding.editTextEmailSignup.text.toString(),
                        binding.editTextPasswordSignup.text.toString()
                    )
                )
            }
        })

        viewModel.loginHappened.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                viewModel.login(
                    LoginData(
                        binding.editTextEmailLogin.text.toString(),
                        binding.editTextPasswordLogin.text.toString()
                    )
                )
            }
        })
        viewModel.authResultSignup.observe(this, Observer {
            if (it is Result.Loading) {
                binding.buttonSignup.showProgress()
            } else {
                binding.buttonSignup.hideProgress(R.string.sign_up)
                if (it.succeeded) {
                    // Todo do stuff after sign up
                }
            }
        })
        viewModel.authResultLogin.observe(this, Observer {
            if (it is Result.Loading) {
                binding.buttonLogin.showProgress()
            } else {
                binding.buttonLogin.hideProgress(R.string.log_in)
                if (it.succeeded) {
                    // Todo do stuff after sign up
                }
            }
        })
    }

    private fun setupNavigationToMainActivity() {
        viewModel.showMainActivity.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                startActivity(Intent(this, MainActivity::class.java))
            }
        })
    }
}
