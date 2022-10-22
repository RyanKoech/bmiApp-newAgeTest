package com.example.bmiNewAge.presentation.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.bmiNewAge.R
import com.example.bmiNewAge.databinding.FragmentResultsBinding
import com.example.bmiNewAge.common.Utilities
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest

class ResultsFragment : Fragment() {

    private var _binding : FragmentResultsBinding? = null
    private val binding get() = _binding!!
    private val args : ResultsFragmentArgs by navArgs()
    private lateinit var viewModel : ResultsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        this.activity?.findViewById<TextView>(R.id.textView_toolbarTitle)?.text = getString(R.string.fragment_title_results)
        _binding = FragmentResultsBinding.inflate(inflater, container, false)

        viewModel =  ViewModelProvider(requireActivity()).get(ResultsViewModel::class.java)
        viewModel.setUpViewModel(
            args.weight.toDouble(),
            args.height.toDouble(),
            args.name
        )

        initializeViewElements()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun initializeViewElements() {

        val ponderalIndex : String = Utilities.roundOffToTwoDp(viewModel.getPonderalCalculation())
        val formattedBmi = Utilities.getFormatBmiResult(viewModel.bmi)
        val adLoader: AdLoader = AdLoader.Builder(this.requireActivity(), getString(R.string.ads_id_native))
            .forNativeAd { nativeAd ->
                val styles =
                    NativeTemplateStyle.Builder().build()
                val template = binding.nativeAd
                template.setStyles(styles)
                template.setNativeAd(nativeAd)
            }
            .build()

        adLoader.loadAd(AdRequest.Builder().build())

        binding.apply {
            textViewUserMessage.text = viewModel.getBmiMessage()
            textViewBmiRange.text = viewModel.getBmiRangeMessage()
            textViewPonderalResult.text = getString(R.string.message_ponderal_index, ponderalIndex)
            textViewBmiWhole.text = formattedBmi[0]
            textViewBmiDecimal.text = formattedBmi[1]
        }
    }
}