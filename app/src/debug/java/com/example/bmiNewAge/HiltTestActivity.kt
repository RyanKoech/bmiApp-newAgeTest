package com.example.bmiNewAge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HiltTestActivity : AppCompatActivity() {
    companion object {
        const val THEME_EXTRAS_BUNDLE_KEY = "androidx.fragment.app.testing.FragmentScenario.EmptyFragmentActivity.THEME_EXTRAS_BUNDLE_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(intent.getIntExtra(THEME_EXTRAS_BUNDLE_KEY, R.style.Theme_BmiApp))
        super.onCreate(savedInstanceState)
    }


}