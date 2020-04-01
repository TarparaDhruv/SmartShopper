package com.example.smartshopper

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CompoundBarcodeView
import kotlinx.android.synthetic.main.fragment_profiles.*


class Profile_custom : Fragment() {
    ////////////////////////////////////////////////////
//    lateinit var v: View
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        v = inflater.inflate(R.layout.fragment_profiles, container, false)
//        Toast.makeText(context, "call scanner", Toast.LENGTH_SHORT).show()
//        startActivityForResult(Intent(context, getBarcode::class.java), 10)
//        return v
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (data == null) {
//            Toast.makeText(context, "null data", Toast.LENGTH_LONG).show()
//        } else {
//            v.scanned_value.text = data.data.toString()
//        }
//
//        Toast.makeText(context, data?.data.toString(), Toast.LENGTH_LONG).show()
//        //v.scanned_value = data?.data.toString()
//        //got the code here
//    }
    //////////////////////////////////////////////////////
    lateinit var barcodeView: CompoundBarcodeView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container == null) {
            return null
        }
        val v: View
        v = inflater.inflate(R.layout.fragment_profiles, container, false)
        barcodeView = v.findViewById(R.id.barcode_scanner)
        barcodeView.decodeContinuous(callback)
        return v
    }

    private val callback: BarcodeCallback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            if (result.text != null) {
                barcodeView.setStatusText(result.text)
            }
            //Do something with code result
            scanned_value.text = result.text
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }

    override fun onResume() {
        barcodeView.resume()
        super.onResume()
    }

    override fun onPause() {
        barcodeView.pause()
        super.onPause()
    }
}