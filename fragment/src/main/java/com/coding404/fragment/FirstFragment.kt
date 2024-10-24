package com.coding404.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coding404.fragment.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {

    //Fragment를 상속받고, OnCreateView를 오버라이딩 해주면 됩니다.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_first, container, false)
        val binding = FragmentFirstBinding.inflate(layoutInflater, container, false)



        return binding.root
    }

}