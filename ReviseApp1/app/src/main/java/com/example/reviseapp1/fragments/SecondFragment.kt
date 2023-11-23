package com.example.reviseapp1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.reviseapp1.database.BaseApplication
import com.example.reviseapp1.databinding.FragmentSecondBinding
import com.example.reviseapp1.viewModel.FragmentViewModel
import com.example.reviseapp1.viewModel.FragmentViewModelFactory


class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private val viewModel: FragmentViewModel by activityViewModels {
        FragmentViewModelFactory(
            (requireActivity().application as BaseApplication).database
                .databaseDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = arguments?.getString("DATA", "Null data")
        binding.stringData.text = data ?: "null data"

        viewModel.sharedData.observe(viewLifecycleOwner, {
            binding.viewModelData.text = it?.toString() ?: "no data"

        })

    }
}