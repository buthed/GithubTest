package com.tematihonov.githubtest.presentation.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tematihonov.githubtest.App
import com.tematihonov.githubtest.data.models.responseSearch.Item
import com.tematihonov.githubtest.data.models.responseSearch.ResponseSearch
import com.tematihonov.githubtest.databinding.FragmentMainBinding
import com.tematihonov.githubtest.presentation.ui.rcview.SearchUsersAdapter
import com.tematihonov.githubtest.presentation.ui.utils.loadImageWithCoil
import com.tematihonov.githubtest.presentation.ui.utils.usersAccountDateCreater
import com.tematihonov.githubtest.presentation.viewmodel.MainViewModel
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
        setupOnBackPressed()

        searchInput()
        currentUser()

        viewModel.responseSearch.observe(viewLifecycleOwner) {
            if (it.data != null) {
                responseSearchObserverAction(it.data)
            }
        } //TODO loading success
    }

    private fun searchInput() {
        binding.edLogin.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchUsers(s.toString())
            } //TODO optimize?
        })
    }

    private fun currentUser() {
        viewModel.currentUser.observe(viewLifecycleOwner) {
            it.data?.let { user ->
                binding.fragmentMainUser.apply {
                    userAvatar.loadImageWithCoil(user.avatar_url)
                    userName.text = user.name
                    if (user.email == null) {
                        userEmail.visibility = View.GONE
                    } else {
                        userEmail.text = user.email.toString()
                        userEmail.visibility = View.VISIBLE
                    }
                    if (user.company == null) {
                        userCompany.visibility = View.GONE
                    } else {
                        userCompanyTitle.text = user.company
                        userCompany.visibility = View.VISIBLE
                    }
                    userFollowing.text = user.following.toString()
                    userFollowers.text = user.followers.toString()
                    userGithubCreated.text =
                        usersAccountDateCreater(requireContext(), user.created_at)
                }
            }
        }
    }

    private fun responseSearchObserverAction(responseSearch: ResponseSearch) {
        if (responseSearch.items != null) {
            searchUserAdapter(responseSearch.items)
        }
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

    private fun setupOnBackPressed() {
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
        viewModel.setCurrentUser(userLogin)
        fragmentMainSearch.visibility = View.GONE
        fragmentMainUser.root.visibility = View.VISIBLE
    }

    private fun navigation() = with(binding) {
        fragmentMainUser.closeBtn.setOnClickListener { hideUserScreen() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}