package com.tematihonov.githubtest.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.tematihonov.githubtest.App
import com.tematihonov.githubtest.data.local.FavoritesUserEntity
import com.tematihonov.githubtest.databinding.FragmentFavoritesBinding
import com.tematihonov.githubtest.presentation.ui.rcview.FavoriteUsersAdapter
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
        viewModel.getAllFavoritesUsers()
        viewModel.responseFavoritesUsers.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    //TODO()
                }
                is Resource.Loading -> {
                    //TODO()
                }
                is Resource.Success -> { if(it.data != null) { searchUserAdapter(it.data) } }
            }
        }
    }

    private fun searchUserAdapter(userList: List<FavoritesUserEntity>) {
        adapter = FavoriteUsersAdapter { userLogin ->
            viewModel.deleteFromFavorites(userLogin)
        }
        adapter.userList = userList
        val layoutManager = LinearLayoutManager(this.context)
        binding.apply {
            rvFavorites.layoutManager = layoutManager
            rvFavorites.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}