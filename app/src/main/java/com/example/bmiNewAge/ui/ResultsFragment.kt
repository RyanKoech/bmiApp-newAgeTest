package com.example.bmiNewAge.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.example.bmiNewAge.R
import com.example.bmiNewAge.databinding.FragmentResultsBinding
import com.example.bmiNewAge.other.BmiCategories
import com.example.bmiNewAge.other.Utilities
import com.google.android.gms.ads.AdRequest
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

        this.activity?.findViewById<TextView>(R.id.textView_toolbarTitle)?.text = getString(R.string.fragment_title_results)
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        weight = args.weight.toDouble()
        height = args.height.toDouble()
        name = args.name

        initializeViewElements()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initializeViewElements() {

        val bmi : Double = Utilities.calculateBmi(weight, height)
        val _ponderalIndex : Double = Utilities.calculatePonderalIndex(weight, height)
        val ponderalIndex : String = Utilities.roundOffToTwoDp(_ponderalIndex)
        val bmiCategory : BmiCategories = Utilities.getBmiCategory(bmi)
        val formattedBmi = Utilities.getFormatBmiResult(bmi)
        val addRequest : AdRequest = AdRequest.Builder().build()

        binding.apply {
            textViewUserMessage.text = Utilities.getUserBmiResultMessage(name, bmiCategory)
            textViewBmiRange.text = Utilities.getBmiRangeMessage(bmiCategory)
            textViewPonderalResult.text = getString(R.string.message_ponderal_index, ponderalIndex)
            textViewBmiWhole.text = formattedBmi[0]
            textViewBmiDecimal.text = formattedBmi[1]
            adView.loadAd(addRequest)
        }
    }
}