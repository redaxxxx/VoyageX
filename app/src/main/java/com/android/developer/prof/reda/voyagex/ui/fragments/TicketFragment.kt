package com.android.developer.prof.reda.voyagex.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.android.developer.prof.reda.voyagex.R
import com.android.developer.prof.reda.voyagex.databinding.FragmentTicketBinding
import com.android.developer.prof.reda.voyagex.models.ItemModel
import com.bumptech.glide.Glide

class TicketFragment : Fragment() {

    private lateinit var binding: FragmentTicketBinding
    private var item: ItemModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = DataBindingUtil.inflate(
           inflater,
           R.layout.fragment_ticket,
           container,
           false
       )

        item = TicketFragmentArgs.fromBundle(requireArguments()).item

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireActivity())
            .load(item?.pic)
            .into(binding.pic)

        Glide.with(requireActivity())
            .load(item?.tourGuidePic)
            .into(binding.guideImg)

        binding.titleTv.text = item?.title
        binding.tourDateTv.text = item?.dateTour
        binding.timeTv.text = item?.timeTour
        binding.durationTv.text = item?.duration
        binding.totalTv.text = item?.bed.toString()
        binding.guideName.text = item?.tourGuideName

        binding.messageImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("sms:${item?.tourGuidePhone}")
            intent.putExtra("sms body", "type your message")
            startActivity(intent)
        }

        binding.callImg.setOnClickListener {
            val phone = item?.tourGuidePhone
            val intent = Intent(Intent.ACTION_DIAL,
                Uri.fromParts("tel", phone, null))

            startActivity(intent)
        }

        binding.ticketBackBtn.setOnClickListener {
            findNavController().navigateUp()
        }

    }

}