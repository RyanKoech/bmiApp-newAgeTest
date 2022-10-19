package com.example.bmiNewAge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.navigation.fragment.findNavController
import com.example.bmiNewAge.R
import com.example.bmiNewAge.databinding.FragmentAddDetailsBinding
import com.example.bmiNewAge.other.Constants
import com.example.bmiNewAge.other.Utilities

class AddDetailsFragment : Fragment() {

    private var _binding : FragmentAddDetailsBinding? = null
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        this.activity?.findViewById<TextView>(R.id.textView_toolbarTitle)?.text = getString(R.string.fragment_title_addDetails)
        _binding = FragmentAddDetailsBinding.inflate(inflater, container, false)

        initializeViewElements()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initializeViewElements() {
        binding.numberPickerWeight.apply {
            minValue = Constants.MIN_WEIGHT
            maxValue = Constants.MAX_WEIGHT
            value = Constants.BASE_WEIGHT
        }

        binding.numberPickerHeight.apply {
            minValue = Constants.MIN_HEIGHT
            maxValue = Constants.MAX_HEIGHT
            value = Constants.BASE_HEIGHT
        }

        binding.numberPickerGender.apply {
            minValue = 0
            maxValue = Utilities.genders.size-1
            value = 0
            displayedValues = Utilities.getGenderString()
        }

        binding.buttonCalculate.setOnClickListener {
            buttonCalculateOnClick()
            // this@AddDetailsFragment.findNavController().navigate(R.id.action_addDetailsFragment_to_resultsFragment)
        }
    }

    private fun buttonCalculateOnClick() {
        val name = binding.editTextName.text.toString()

        if(name.isBlank()){
            binding.editTextName.error = "Please enter your name."
            return
        }

        val weight : Int = binding.numberPickerWeight.value
        val height : Int = binding.numberPickerHeight.value

        val action  = AddDetailsFragmentDirections.actionAddDetailsFragmentToResultsFragment(weight.toFloat(), height.toFloat(), name)
        findNavController().navigate(action)

    }

}