package com.example.bmiNewAge.presentation.add_details

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.navigation.fragment.findNavController
import com.example.bmiNewAge.R
import com.example.bmiNewAge.databinding.FragmentAddDetailsBinding
import com.example.bmiNewAge.common.Constants
import com.example.bmiNewAge.common.Utilities
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AddDetailsFragment : Fragment() {

    private var _binding : FragmentAddDetailsBinding? = null
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val binding get() = _binding!!
    private var mInterstitialAd: InterstitialAd? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        this.activity?.findViewById<TextView>(R.id.textView_toolbarTitle)?.text = getString(R.string.fragment_title_addDetails)
        _binding = FragmentAddDetailsBinding.inflate(inflater, container, false)

        loadInterstitialAd()
        initializeViewElements()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireActivity(),getString(R.string.ads_id_interstitial), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })

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
            binding.editTextName.error = getString(R.string.message_input_error)
            return
        }

        val weight : Float = binding.numberPickerWeight.value.toFloat()
        val height : Float = binding.numberPickerHeight.value.toFloat()
        setUpInterstitialAdCallback(weight, height, name)

        if (mInterstitialAd != null) {
            mInterstitialAd?.show(requireActivity())
        }else{
            navigateToResultsScreen(weight, height, name)
        }

    }

    private fun setUpInterstitialAdCallback(weight : Float, height: Float, name : String) {

        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                mInterstitialAd = null
                navigateToResultsScreen(weight, height, name)
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                super.onAdFailedToShowFullScreenContent(p0)
                mInterstitialAd = null
            }
        }
    }

    private fun navigateToResultsScreen(weight : Float, height: Float, name : String) {
        val action  = AddDetailsFragmentDirections.actionAddDetailsFragmentToResultsFragment(weight, height, name)
        binding.editTextName.text = Editable.Factory.getInstance().newEditable("")
        findNavController().navigate(action)
    }

}