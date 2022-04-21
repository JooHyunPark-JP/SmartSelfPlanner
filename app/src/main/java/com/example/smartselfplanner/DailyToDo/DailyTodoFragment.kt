package com.example.smartselfplanner.DailyToDo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartselfplanner.R
import com.example.smartselfplanner.databinding.FragmentDailyTodoBinding

// TODO: Rename parameter arguments, choose names that match

class DailyTodoFragment : Fragment() {

    lateinit var binding : FragmentDailyTodoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDailyTodoBinding.inflate(inflater)
        return binding.root
    }

}