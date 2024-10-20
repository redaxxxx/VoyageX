package com.android.developer.prof.reda.voyagex.ui.fragments

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
import com.android.developer.prof.reda.voyagex.databinding.FragmentRegisterBinding
import com.android.developer.prof.reda.voyagex.models.User
import com.android.developer.prof.reda.voyagex.mvvm.RegisterViewModel
import com.android.developer.prof.reda.voyagex.util.LoginValidation
import com.android.developer.prof.reda.voyagex.util.RegisterValidation
import com.android.developer.prof.reda.voyagex.util.Resources
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

private const val TAG = "RegisterFragment"
@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()
    private var userId: Long = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpBtn.setOnClickListener {
            val user = User(
                userId,
                binding.usernameEt.text.toString().trim(),
                binding.emailEt.text.toString().trim()
            )

            val password = binding.passwordEt.text.toString().trim()

            registerViewModel.signupUser(user, password)
        }

        binding.signIn.setOnClickListener {
            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
        }

        lifecycleScope.launch {
            registerViewModel.register.collectLatest {
                when(it){
                    is Resources.Loading ->{
                        binding.registerProgressBar.visibility = View.VISIBLE
                    }
                    is Resources.Success ->{
                        binding.registerProgressBar.visibility = View.GONE
                        findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())

                        binding.usernameEt.text?.clear()
                        binding.emailEt.text?.clear()
                        binding.passwordEt.text?.clear()
                    }
                    is Resources.Failed ->{
                        binding.registerProgressBar.visibility = View.GONE

                        Toast.makeText(requireActivity(), "Failed register: ${it.message}", Toast.LENGTH_LONG)
                            .show()

                        Timber.tag(TAG).d("Register Error: ${it.message}")
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launch {
            registerViewModel.userID.collectLatest {
                if (it.data != null){
                    userId = it.data
                }else{
                    Timber.tag(TAG).d("user id is null")
                }
            }
        }

        lifecycleScope.launch {
            registerViewModel.validation.collectLatest {
                //check if user enter invalid username
                if (it.username is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.passwordEt.apply {
                            requestFocus()
                            error = it.username.message
                        }
                    }
                }

                //check if user enter invalid email
                if (it.email is RegisterValidation.Failed){
                    withContext(Dispatchers.Main){
                        binding.emailEt.apply {
                            requestFocus()
                            error = it.email.message
                        }
                    }
                }

                //check if user enter invalid password
                if (it.password is RegisterValidation.Failed){
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