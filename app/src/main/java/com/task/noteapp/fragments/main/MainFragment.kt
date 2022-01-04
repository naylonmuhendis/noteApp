package com.task.noteapp.fragments.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.task.noteapp.R
import com.task.noteapp.core.BaseFragment
import com.task.noteapp.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding, MainViewModel>() {
    override fun getLayoutRes(): Int = R.layout.fragment_main
    override val viewModel: MainViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.noteEntryDialogFragment)
        }
    }
}