package com.tematihonov.githubtest.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tematihonov.githubtest.App
import com.tematihonov.githubtest.R
import com.tematihonov.githubtest.data.local.FavoritesUserEntity
import com.tematihonov.githubtest.databinding.FragmentFavoritesBinding
import com.tematihonov.githubtest.presentation.ui.rcview.FavoriteUsersAdapter
import com.tematihonov.githubtest.presentation.ui.utils.loadImageWithCoil
import com.tematihonov.githubtest.presentation.ui.utils.usersAccountDateCreater
import com.tematihonov.githubtest.presentation.viewmodel.FavoriteViewModel
import com.tematihonov.githubtest.utils.Resource
import javax.inject.Inject

class FavoritesFragment : Fragment() {

    @Inject
    lateinit var viewModel: FavoriteViewModel

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FavoriteUsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.responseFavoritesUsers.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    if (it.data != null) {
                        setupFavoriteAdapter(it.data)
                    }
                }
            }
        }
        navigation()
        currentUser()
        setupOnBackPressed()
    }

    private fun setupFavoriteAdapter(userList: List<FavoritesUserEntity>) {
        adapter = FavoriteUsersAdapter(
            deleteUserFromFavorites = { userLogin ->
                viewModel.deleteFromFavorites(userLogin)
            },
            onClickListener = {
                openUserScreen(it)
            })
        adapter.userList = userList
        val layoutManager = LinearLayoutManager(this.context)
        binding.apply {
            rvFavorites.layoutManager = layoutManager
            rvFavorites.adapter = adapter
        }
    }

    private fun setupOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.apply {
                    if (fragmentFavorite.visibility == View.GONE && fragmentMainUser.root.visibility == View.VISIBLE) {
                        hideUserScreen()
                    } else {
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        })
    }

    private fun hideUserScreen() = with(binding) {
        fragmentFavorite.visibility = View.VISIBLE
        fragmentMainUser.root.visibility = View.GONE
    }

    private fun openUserScreen(userLogin: String) = with(binding) {
        viewModel.setCurrentUser(userLogin)
        fragmentFavorite.visibility = View.GONE
        fragmentMainUser.root.visibility = View.VISIBLE
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
                    userLogin.text = user.login
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
                    userFavorite.setOnClickListener {
                        viewModel.deleteFromFavorite(
                            FavoritesUserEntity(
                                user.avatar_url,
                                user.id,
                                user.login
                            )
                        )
                        hideUserScreen()
                    }
                    userFavorite.setBackgroundResource(R.drawable.icon_favorite_filled)
                }
            }
        }
    }

    private fun navigation() = with(binding) {
        fragmentMainUser.closeBtn.setOnClickListener {
            hideUserScreen()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}