package com.example.mobileproject5.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mobileproject5.Marker
import com.example.mobileproject5.MarkerViewModel
import com.example.mobileproject5.R
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*

class AddFragment : Fragment() {
    private lateinit var  mMarkerViewModel: MarkerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add, container, false)

        mMarkerViewModel = ViewModelProvider(this).get(MarkerViewModel::class.java)

        view.add_btn.setOnClickListener{
            insertDatatoDatabase()
        }
        return view
    }
private fun insertDatatoDatabase(){
    val name = addName.text.toString()
    val type = addType.text.toString()
    val description = addDescription.text.toString()

    if(inputCheck(name,type,description)){
        val marker = Marker(0,name,type,description)
        mMarkerViewModel.addMarker(marker)
        Toast.makeText(requireContext(), "Successfully Created", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_addFragment_to_listFragment)
    }else{
        Toast.makeText(requireContext(), "Empty fields", Toast.LENGTH_LONG).show()
    }
}
    private fun inputCheck(name: String, type: String, description: String): Boolean{
        return !(TextUtils.isEmpty(name) && TextUtils.isEmpty(type) && TextUtils.isEmpty(description))
    }

}