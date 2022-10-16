package com.example.bmiNewAge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import com.example.bmiNewAge.R
import com.example.bmiNewAge.databinding.FragmentAddDetailsBinding

class AddDetailsFragment : Fragment() {

    private var _binding : FragmentAddDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddDetailsBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

}