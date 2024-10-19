package com.android.developer.prof.reda.voyagex.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.developer.prof.reda.voyagex.R
import com.android.developer.prof.reda.voyagex.databinding.FragmentIntroBinding
import com.android.developer.prof.reda.voyagex.ui.activities.HomeActivity

class IntroFragment : Fragment() {

    private lateinit var binding: FragmentIntroBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

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
        }
    }
}