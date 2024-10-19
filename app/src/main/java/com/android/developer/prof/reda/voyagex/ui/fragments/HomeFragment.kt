package com.android.developer.prof.reda.voyagex.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.android.developer.prof.reda.voyagex.R
import com.android.developer.prof.reda.voyagex.databinding.FragmentHomeBinding
import com.android.developer.prof.reda.voyagex.adapters.CategoryAdapter
import com.android.developer.prof.reda.voyagex.mvvm.HomeViewModel
import com.android.developer.prof.reda.voyagex.adapters.ItemAdapter
import com.android.developer.prof.reda.voyagex.adapters.PopularAdapter
import com.android.developer.prof.reda.voyagex.models.LocationModel
import com.android.developer.prof.reda.voyagex.adapters.SliderAdapter
import com.android.developer.prof.reda.voyagex.util.Resources
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG_HOME = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val sliderAdapter by lazy { SliderAdapter() }
    private val categoryAdapter by lazy { CategoryAdapter() }
    private val itemAdapter by lazy { ItemAdapter() }
    private val popularAdapter by lazy { PopularAdapter() }
    private val viewModel by viewModels<HomeViewModel>()
    private lateinit var locationAdapter: ArrayAdapter<String>
    private var locationList = mutableListOf<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationAdapter = ArrayAdapter(requireActivity(), R.layout.sp_item, locationList)

        prepareBanners()
        initLocationSpinner()
        binding.recommendedRv.adapter = itemAdapter
        binding.popularRv.adapter = popularAdapter
        binding.categoryRv.adapter = categoryAdapter

        itemAdapter.onClickItem = {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it))
            Timber.tag(TAG_HOME).d("Navigate Error")
        }

        popularAdapter.onClickItem = {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(it))
        }

        lifecycleScope.launch {
            viewModel.locations.collectLatest {
                when(it){
                    is Resources.Loading ->{

                    }
                    is Resources.Success ->{
                        val locations = it.data?.toList()
                        if (locations != null) {
                            for (location in locations){
                                locationList.add(location.loc)
                            }
                        }
                    }
                    is Resources.Failed ->{
                        Timber.tag(TAG_HOME).d("Locations Error: %s", it.data)
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launch {
            viewModel.banners.collectLatest {
                when(it){
                    is Resources.Loading ->{
                        binding.progressBarSlider.visibility = View.VISIBLE
                    }
                    is Resources.Success ->{
                        binding.progressBarSlider.visibility = View.GONE
                        sliderAdapter.submitList(it.data)
                    }
                    is Resources.Failed ->{
                        binding.progressBarSlider.visibility = View.GONE
                        Timber.tag(TAG_HOME).d("Banners Error: %s", it.message)
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launch {
            viewModel.categories.collectLatest {
                when(it){
                    is Resources.Loading ->{
                        binding.progressBarCategory.visibility = View.VISIBLE
                    }
                    is Resources.Success ->{
                        binding.progressBarCategory.visibility = View.GONE
                        categoryAdapter.submitList(it.data)
                    }
                    is Resources.Failed ->{
                        binding.progressBarCategory.visibility = View.GONE
                        Timber.tag(TAG_HOME).d("Category Error: %s", it.message)
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launch {
            viewModel.populars.collectLatest {
                when(it){
                    is Resources.Loading ->{
                        binding.progressBarPopular.visibility = View.VISIBLE
                    }
                    is Resources.Success ->{
                        binding.progressBarPopular.visibility = View.GONE
                        popularAdapter.submitList(it.data)
                        Timber.tag(TAG_HOME).d("Popular Items: %s", it.data)
                    }
                    is Resources.Failed ->{
                        binding.progressBarPopular.visibility = View.GONE
                        Timber.tag(TAG_HOME).d("Popular Error: %s", it.message)
                    }
                    else -> Unit
                }
            }
        }

        lifecycleScope.launch {
            viewModel.recommendPlaces.collectLatest {
                when(it){
                    is Resources.Loading ->{
                        binding.progressBarRecommend.visibility = View.VISIBLE
                    }
                    is Resources.Success ->{
                        binding.progressBarRecommend.visibility = View.GONE
                        itemAdapter.submitList(it.data)
                    }
                    is Resources.Failed ->{
                        binding.progressBarRecommend.visibility = View.GONE
                        Timber.tag(TAG_HOME).d("Recommend Error: %s", it.message)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun initLocationSpinner(){
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        binding.locationSpinner.adapter = locationAdapter
    }
    private fun prepareBanners(){
        binding.viewPagerSlider.apply {
            adapter = sliderAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3

            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            val compositePageTransformer = CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(40))
            }

            setPageTransformer(compositePageTransformer)
        }
    }
}