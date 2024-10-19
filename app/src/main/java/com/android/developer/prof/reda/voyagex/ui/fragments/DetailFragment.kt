package com.android.developer.prof.reda.voyagex.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.developer.prof.reda.voyagex.R
import com.android.developer.prof.reda.voyagex.databinding.FragmentDetailBinding
import com.android.developer.prof.reda.voyagex.models.ItemModel
import com.bumptech.glide.Glide

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private var item: ItemModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )

        item = DetailFragmentArgs.fromBundle(requireArguments()).item

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (item != null ){
            Glide.with(view)
                .load(item!!.pic)
                .into(binding.placePic)

            binding.placeTitleTv.text = item!!.title
            binding.addressTv.text = item!!.address
            binding.ratingTv.text = item!!.score.toString()
            binding.ratingBar.rating = item!!.score.toFloat()
            binding.durationTv.text = item!!.duration
            binding.distanceTv.text = item!!.distance
            binding.bedNumTv.text = "Bed ${item!!.bed}"
            binding.descriptionTv.text = item!!.description
            binding.priceTv.text = "$${item!!.price}"
        }

        binding.backImageView.setOnClickListener {
            findNavController().navigateUp()
        }


        binding.bookNowBtn.setOnClickListener {
            findNavController().navigate(DetailFragmentDirections.actionDetailFragmentToTicketFragment(item!!))
        }
    }
}