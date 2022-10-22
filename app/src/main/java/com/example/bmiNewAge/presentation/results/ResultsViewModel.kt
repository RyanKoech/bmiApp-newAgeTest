package com.example.bmiNewAge.presentation.results

import androidx.lifecycle.ViewModel
import com.example.bmiNewAge.common.BmiCategories
import com.example.bmiNewAge.domain.usecase.get_bmi_calculation.GetBmiCalculationUseCase
import com.example.bmiNewAge.domain.usecase.get_bmi_category.GetBmiCategoryUseCase
import com.example.bmiNewAge.domain.usecase.get_bmi_message.GetBmiMessageUseCase
import com.example.bmiNewAge.domain.usecase.get_bmi_message_range.GetBmiMessageRangeUseCase
import com.example.bmiNewAge.domain.usecase.get_ponderal_calculation.GetPonderalCalulcationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject
import kotlin.properties.Delegates

@HiltViewModel
class ResultsViewModel @Inject constructor(
    private val getBmiCalculationUseCase: GetBmiCalculationUseCase,
    private val getPonderalCalculationUseCase: GetPonderalCalulcationUseCase,
    private val getBmiMessageUseCase: GetBmiMessageUseCase,
    private val getBmiMessageRangeUseCase: GetBmiMessageRangeUseCase,
    private val getBmiCategoryUseCase: GetBmiCategoryUseCase
) : ViewModel() {

    private var weight by Delegates.notNull<Double>()
    private var height by Delegates.notNull<Double>()
    private var name by Delegates.notNull<String>()
    private lateinit var bmiCategory : BmiCategories
    var bmi by Delegates.notNull<Double>()
    private var _file : File? = null
    val file get() = _file


    fun setUpViewModel(weight : Double, height : Double, name : String) {
        this.weight = weight
        this.height = height
        this.name = name
        this.bmi = getBmiCalculationUseCase(weight, height)
        bmiCategory = getBmiCategoryUseCase(bmi)
        _file = null
    }

    fun getPonderalCalculation () = getPonderalCalculationUseCase(weight, height)

    fun getBmiMessage () = getBmiMessageUseCase(name, bmiCategory)

    fun getBmiRangeMessage () = getBmiMessageRangeUseCase(bmiCategory)

    fun setSavedFile(file : File) {
        _file = file
    }

}