package com.android.developer.prof.reda.voyagex.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.android.developer.prof.reda.voyagex.R
import com.android.developer.prof.reda.voyagex.databinding.FragmentIntroBinding
import com.android.developer.prof.reda.voyagex.mvvm.IntroViewModel
import com.android.developer.prof.reda.voyagex.ui.activities.HomeActivity
import com.android.developer.prof.reda.voyagex.util.HOME_ACTIVITY
import com.android.developer.prof.reda.voyagex.util.LOGIN_FRAGMENT
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
@AndroidEntryPoint
class IntroFragment : Fragment() {

    private lateinit var binding: FragmentIntroBinding
    private val viewModel by viewModels<IntroViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_intro,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.introBtn.setOnClickListener {
            findNavController().navigate(IntroFragmentDirections.actionIntroFragmentToLoginFragment())
            viewModel.startClickButton()
        }

        lifecycleScope.launch {
            viewModel.navigate.collectLatest {
                when(it){
                    HOME_ACTIVITY ->{
                        val intent = Intent(requireActivity(), HomeActivity::class.java).also { intent->
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }

                        startActivity(intent)
                        activity?.finish()
                    }

                    LOGIN_FRAGMENT ->{
                        findNavController().navigate(IntroFragmentDirections.actionIntroFragmentToLoginFragment())
                    }
                }
            }
        }

    }
}