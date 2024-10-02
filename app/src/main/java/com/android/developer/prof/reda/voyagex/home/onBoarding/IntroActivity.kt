package com.android.developer.prof.reda.voyagex.home.onBoarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.developer.prof.reda.voyagex.BaseActivity
import com.android.developer.prof.reda.voyagex.R
import com.android.developer.prof.reda.voyagex.databinding.ActivityIntroBinding
import com.android.developer.prof.reda.voyagex.home.HomeActivity

class IntroActivity : BaseActivity() {
    private lateinit var binding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro)

        binding.introBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java).also {intent ->
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                )
            }
            startActivity(intent)
        }
    }

}