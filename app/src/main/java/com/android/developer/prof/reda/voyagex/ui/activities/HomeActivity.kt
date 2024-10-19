package com.android.developer.prof.reda.voyagex.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemSelectedListener
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.android.developer.prof.reda.voyagex.BaseActivity
import com.android.developer.prof.reda.voyagex.R
import com.android.developer.prof.reda.voyagex.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        val navController by lazy {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
            navHostFragment.navController
        }

        binding.bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener{_,destination,_ ->
            if (destination.id == R.id.detailFragment || destination.id == R.id.ticketFragment){
                binding.bottomNavView.visibility = View.GONE
            }else{
                binding.bottomNavView.visibility = View.VISIBLE
            }
        }
    }
}