package com.tematihonov.githubtest.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tematihonov.githubtest.App
import com.tematihonov.githubtest.databinding.FragmentMainBinding
import com.tematihonov.githubtest.presentation.viewmodel.MainViewModel
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigation()
        viewModel.testLog()
    }

    private fun navigation() {
        binding.testNav.setOnClickListener {
            binding.fragmentMainSearch.visibility = View.GONE
            binding.fragmentMainUser.root.visibility = View.VISIBLE
        }
        binding.fragmentMainUser.closeBtn.setOnClickListener {
            binding.fragmentMainSearch.visibility = View.VISIBLE
            binding.fragmentMainUser.root.visibility = View.GONE
        }
    } //TODO refactor

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}