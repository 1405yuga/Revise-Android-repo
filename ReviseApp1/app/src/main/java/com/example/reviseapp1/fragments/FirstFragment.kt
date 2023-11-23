package com.example.reviseapp1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.reviseapp1.R
import com.example.reviseapp1.database.BaseApplication
import com.example.reviseapp1.database.DataBaseModel
import com.example.reviseapp1.databinding.FragmentFirstBinding
import com.example.reviseapp1.viewModel.FragmentViewModel
import com.example.reviseapp1.viewModel.FragmentViewModelFactory

private const val TAG = "FirstFragment tag"

class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
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
        binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitbutton.setOnClickListener {
            val submittedData = binding.stringData.text.toString()
            findNavController().navigate(R.id.secondFragment, args = Bundle().apply {
                putString("DATA", submittedData)
            })
            val intData = binding.viewModelData.text.toString().toInt()
            viewModel.setSharedData(intData)
        }

        binding.addButton.setOnClickListener {
            viewModel.addData(
                DataBaseModel(
                    stringData = binding.stringDataDB.text.toString(),
                    intData = binding.intDataDB.text.toString().toInt()
                )
            )
        }
    }
}

