package com.android.developer.prof.reda.voyagex.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.developer.prof.reda.voyagex.R
import com.android.developer.prof.reda.voyagex.databinding.FragmentLoginBinding
import com.android.developer.prof.reda.voyagex.mvvm.LoginViewModel
import com.android.developer.prof.reda.voyagex.ui.activities.HomeActivity
import com.android.developer.prof.reda.voyagex.util.LoginFailedState
import com.android.developer.prof.reda.voyagex.util.LoginValidation
import com.android.developer.prof.reda.voyagex.util.Resources
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val TAG = "LoginFragment"
@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel by viewModels<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginBtn.setOnClickListener {
            loginViewModel.loginUser(
                binding.emailEt.text.toString().trim(),
                binding.passwordEt.text.toString().trim()
            )
        }

        binding.signUp.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        lifecycleScope.launch {
            loginViewModel.login.collectLatest {
                when(it){
                    is Resources.Loading ->{
                        binding.loginProgressBar.visibility = View.VISIBLE
                    }
                    is Resources.Success ->{
                        binding.loginProgressBar.visibility = View.GONE
                        val intent = Intent(requireActivity(), HomeActivity::class.java).also { intent->
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }

                        startActivity(intent)
                    }
                    is Resources.Failed ->{
                        binding.loginProgressBar.visibility = View.GONE
                        Timber.tag(TAG).d("Login Error: ${it.message}")
                        Toast.makeText(requireActivity(), "Login Failed", Toast.LENGTH_LONG).show()
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launch {
            loginViewModel.validation.collectLatest {
                if (it.email is LoginValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.emailEt.apply {
                            requestFocus()
                            error = it.email.message
                        }
                    }
                }

                if (it.password is LoginValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.passwordEt.apply {
                            requestFocus()
                            error = it.password.message
                        }
                    }
                }
            }
        }

    }


}