package com.tematihonov.githubtest.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tematihonov.githubtest.App
import com.tematihonov.githubtest.data.models.responseSearch.ResponseSearch
import com.tematihonov.githubtest.databinding.FragmentMainBinding
import com.tematihonov.githubtest.presentation.ui.rcview.SearchUsersAdapter
import com.tematihonov.githubtest.presentation.viewmodel.MainViewModel
import com.tematihonov.githubtest.data.models.responseSearch.Item
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SearchUsersAdapter

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
        setuoOnBackPressed()

        viewModel.searchUsers()

        viewModel.responseSearch.observe(viewLifecycleOwner) {
            if (it.data != null) {
                it.data.let { responseSearch ->
                    responseSearchObserverAction(responseSearch)
                }
            }
        }
    } //TODO loading success

    private fun navigation() = with(binding) {
        fragmentMainUser.closeBtn.setOnClickListener { hideUserScreen() }
    }

    private fun responseSearchObserverAction(responseSearch: ResponseSearch) {
        if (responseSearch.items != null) { searchUserAdapter(responseSearch.items) }
    }

    private fun searchUserAdapter(userList: List<Item>) {
        adapter = SearchUsersAdapter { openUserScreen(it) }
        adapter.userList = userList
        val layoutManager = LinearLayoutManager(this.context)
        binding.apply {
            rvUsers.layoutManager = layoutManager
            rvUsers.adapter = adapter
        }
    }

    private fun setuoOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.apply {
                    if (fragmentMainSearch.visibility == View.GONE && fragmentMainUser.root.visibility == View.VISIBLE) {
                        hideUserScreen()
                    } else {
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        })
    }

    private fun hideUserScreen() = with(binding) {
        fragmentMainSearch.visibility = View.VISIBLE
        fragmentMainUser.root.visibility = View.GONE
    }

    private fun openUserScreen(userLogin: String) = with(binding) {
        //TODO add new user from vm part
        fragmentMainSearch.visibility = View.GONE
        fragmentMainUser.root.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}