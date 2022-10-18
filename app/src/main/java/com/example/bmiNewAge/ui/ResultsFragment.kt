package com.example.bmiNewAge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.bmiNewAge.databinding.FragmentResultsBinding
import com.example.bmiNewAge.other.BmiCategories
import com.example.bmiNewAge.other.Utilities
import kotlin.properties.Delegates


class ResultsFragment : Fragment() {

    private var _binding : FragmentResultsBinding? = null
    private val binding get() = _binding!!
    private val args : ResultsFragmentArgs by navArgs()
    private var weight by Delegates.notNull<Double>()
    private var height by Delegates.notNull<Double>()
    private lateinit var name : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentResultsBinding.inflate(inflater, container, false)

        weight = args.weight.toDouble()
        height = args.height.toDouble()
        name = args.name

        initializeViewElements()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initializeViewElements() {

        println(weight)
        println(height)

        val bmi : Double = Utilities.calculateBmi(weight, height)
        val _ponderalIndex : Double = Utilities.calculatePonderalIndex(weight, height)
        val ponderalIndex : String = Utilities.roundOffToTwoDp(_ponderalIndex)
        val bmiCategory : BmiCategories = Utilities.getBmiCategory(bmi)
        val formatedBmi = Utilities.getFormatBmiResult(bmi)

        println(bmi)
        println(ponderalIndex)
        println(bmiCategory)

        binding.textViewUserMessage.text = Utilities.getUserBmiResultMessage(name, bmiCategory)
        binding.textViewBmiRange.text = Utilities.getBmiRangeMessage(bmiCategory)
        binding.textViewPonderalResult.text = "Ponderal Index : ${ponderalIndex}kg/m3"
        binding.textViewBmiWhole.text = formatedBmi[0]
        binding.textViewBmiDecimal.text = formatedBmi[1]
    }
}