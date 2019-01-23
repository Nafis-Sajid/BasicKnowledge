package com.example.nafissajid.basicknowledge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_edit_profile.*


class EditProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profilePicture.setOnClickListener{
            Toast.makeText(context,"Profile Picture",Toast.LENGTH_SHORT).show()
        }
        userName.setOnClickListener{
            Toast.makeText(context,"User Name",Toast.LENGTH_SHORT).show()
        }

        dateofBirth.setOnClickListener{
            Toast.makeText(context,"Date of Birth",Toast.LENGTH_SHORT).show()
        }

        contactNo.setOnClickListener{
            Toast.makeText(context,"Contact No",Toast.LENGTH_SHORT).show()
        }
        currentIns.setOnClickListener{
            Toast.makeText(context,"current Institute",Toast.LENGTH_SHORT).show()
        }
    }
}
