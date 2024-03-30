package com.tematihonov.githubtest.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isInvisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.tematihonov.githubtest.App
import com.tematihonov.githubtest.R
import com.tematihonov.githubtest.data.local.FavoritesUserEntity
import com.tematihonov.githubtest.databinding.FragmentMainBinding
import com.tematihonov.githubtest.presentation.ui.paginate.DefaultLoadStateAdapter
import com.tematihonov.githubtest.presentation.ui.paginate.TryAgainAction
import com.tematihonov.githubtest.presentation.ui.paginate.simpleScan
import com.tematihonov.githubtest.presentation.ui.rcview.SearchUsersAdapter
import com.tematihonov.githubtest.presentation.ui.utils.loadImageWithCoil
import com.tematihonov.githubtest.presentation.ui.utils.usersAccountDateCreater
import com.tematihonov.githubtest.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModel: MainViewModel

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: SearchUsersAdapter
    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder

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

//        viewModel.responseSearch.observe(viewLifecycleOwner) {
//            when (it) {
//                is Resource.Error -> {
//                    //TODO()
//                }
//                is Resource.Loading -> {
//                    //TODO()
//                }
//                is Resource.Success -> { if (it.data != null) { searchUserAdapter(it.data.items) } }
//            }
//
//        } //TODO loading success

        setupSearchInput()
        currentUser()
        setupUserAdapter()
    }

    private fun setupSearchInput() {
        binding.edLogin.addTextChangedListener {
            if (!it.isNullOrEmpty()) { viewModel.setSearchBy(it.toString()) }
        }
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
                    testUserForFavorite(user.login)
                    userFavorite.setOnClickListener {
                        viewModel.addOrDeleteFromFavorite(FavoritesUserEntity(user.avatar_url,user.id, user.login)) { result ->
                            when (result) {
                                true -> userFavorite.setBackgroundResource(R.drawable.icon_favorite_border)
                                false -> userFavorite.setBackgroundResource(R.drawable.icon_favorite_filled)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun testUserForFavorite(user: String) = with(binding.fragmentMainUser) {
        viewModel.testDbFavorites(user) { result ->
            when (result) {
                true -> userFavorite.setBackgroundResource(R.drawable.icon_favorite_filled)
                false -> userFavorite.setBackgroundResource(R.drawable.icon_favorite_border)
            }
        }
    }

    private fun setupUserAdapter() {
        adapter = SearchUsersAdapter( onClickListener = { openUserScreen(it) } )

        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)

        val layoutManager = LinearLayoutManager(this.context)
        binding.apply {
            rvUsers.layoutManager = layoutManager
            //rvUsers.adapter = adapter
            rvUsers.adapter = adapterWithLoadState
            (rvUsers.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = false
        }

        mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
            binding.loadStateView,
            binding.swipeRefreshLayout,
            tryAgainAction
        )

        observeUsers(adapter)
        observeLoadState(adapter)

        handleScrollingToTopWhenSearching(adapter)
        handleListVisibility(adapter)
    }

    private fun observeUsers(adapter: SearchUsersAdapter) {
        lifecycleScope.launch {
            viewModel.usersFlow.collectLatest { pagingData ->
                if (!viewModel.searchBy.value.isNullOrBlank()) adapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadState(adapter: SearchUsersAdapter) {
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }

    private fun handleScrollingToTopWhenSearching(adapter: SearchUsersAdapter) = lifecycleScope.launch {
        getRefreshLoadStateFlow(adapter)
            .simpleScan(count = 2)
            .collectLatest { (previousState, currentState) ->
                if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                    binding.rvUsers.scrollToPosition(0)
                }
            }
    }

    private fun handleListVisibility(adapter: SearchUsersAdapter) = lifecycleScope.launch {
        getRefreshLoadStateFlow(adapter)
            .simpleScan(count = 3)
            .collectLatest { (beforePrevious, previous, current) ->
                binding.rvUsers.isInvisible = current is LoadState.Error
                        || previous is LoadState.Error
                        || (beforePrevious is LoadState.Error && previous is LoadState.NotLoading
                        && current is LoadState.Loading)
            }
    }

    private fun getRefreshLoadStateFlow(adapter: SearchUsersAdapter): Flow<LoadState> {
        return adapter.loadStateFlow
            .map { it.refresh }
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
        tiLogin.visibility = View.VISIBLE
    }

    private fun openUserScreen(userLogin: String) = with(binding) {
        testUserForFavorite(user = userLogin)
        viewModel.setCurrentUser(userLogin)
        fragmentMainSearch.visibility = View.GONE
        tiLogin.visibility = View.GONE
        fragmentMainUser.root.visibility = View.VISIBLE
    }

    private fun navigation() = with(binding) {
        fragmentMainUser.closeBtn.setOnClickListener {
            hideUserScreen()
            viewModel.searchUsers(edLogin.text.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}