package com.example.bmiNewAge.presentation.results

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.bmiNewAge.R
import com.example.bmiNewAge.databinding.FragmentResultsBinding
import com.example.bmiNewAge.common.Utilities
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

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
            buttonShare.setOnClickListener{
                val file : File? = saveImage()
                if(file != null) {
                    shareFile(file)
                }
            }
        }
    }

    private fun shareFile(file: File) {
        val uri : Uri = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            FileProvider.getUriForFile(requireActivity(), requireActivity().packageName + ".provider", file)
        }else {
            Uri.fromFile(file)
        }

        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_SUBJECT, "BMI Screenshot")
        intent.putExtra(Intent.EXTRA_TEXT, "BMI App has calculated my BMI. Take a look.")
        intent.putExtra(Intent.EXTRA_STREAM, uri)

        try {
            startActivity(Intent.createChooser(intent, "Share using"))
        }catch (e : Exception) {
            e.printStackTrace()
            Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            binding.buttonShare.performClick()
        }else {
            Toast.makeText(this.requireActivity(), "Permission denied", Toast.LENGTH_SHORT).show()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun saveImage() : File?{
        if(!checkPermission()) return null

        try {
            val path : String = Environment.getExternalStorageDirectory().toString() + "/DCIM/" + getString(R.string.app_name)
            val fileDir = File(path)
            if(!fileDir.exists())
                fileDir.mkdir()

            val mPath : String = path + "/bmi_screenshot_" + Calendar.getInstance().timeInMillis.toString() + ".png"
            
            val bitmap : Bitmap = getScreenshot()
            val file = File(mPath)
            val fOut = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut)
            fOut.flush()
            fOut.close()

            Toast.makeText(this.requireActivity(), "Image Saved Successfully", Toast.LENGTH_LONG).show()

            return file

        } catch (e : Exception) {
            e.printStackTrace()
            Toast.makeText(this.requireActivity(), "Something went wrong", Toast.LENGTH_LONG).show()
        }

        return null
    }

    private fun getScreenshot(): Bitmap {
        val view : View = this.requireView().findViewById(R.id.linearLayout_results)
        val bitmap : Bitmap = Bitmap.createBitmap(
            view.width,
            view.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun checkPermission() : Boolean {
        val permission : Int = ActivityCompat.checkSelfPermission(this.requireActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if(permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.requireActivity(), arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 1)
            return false
        }
        return true
    }
}