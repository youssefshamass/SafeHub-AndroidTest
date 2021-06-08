package com.youssefshamass.safehub.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.transition.MaterialFadeThrough
import com.youssefshamass.core.extensions.observe
import com.youssefshamass.safehub.R
import com.youssefshamass.safehub.databinding.FragmentUserDetailsBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.parameter.parametersOf

@KoinApiExtension
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class UserDetailsFragment : Fragment() {
    private val _args: UserDetailsFragmentArgs by navArgs()
    private val _viewModel: UserDetailsViewModel by viewModel {
        parametersOf(_args.userId)
    }

    private var _binding: FragmentUserDetailsBinding? = null
    private val binding: FragmentUserDetailsBinding
        get() = _binding!!

    private var _followersAdapter: UserHeaderAdapter? = null
    private var _followingsAdapter: UserHeaderAdapter? = null

    //region Lifecycle members

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialFadeThrough()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()
        setupFollowersAndFollowingLists()

        _viewModel.state.observe(this, ::render)
    }

    private fun setupTabLayout() {
        binding.tabLayoutListSource.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.id == R.id.tab_followers)
                    _viewModel.updateDisplayState(DisplayState.followers)
                else
                    _viewModel.updateDisplayState(DisplayState.following)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun setupFollowersAndFollowingLists() {
        _followingsAdapter = _followingsAdapter ?: UserHeaderAdapter()
        _followersAdapter = _followingsAdapter ?: UserHeaderAdapter()

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = _followersAdapter
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    //endregion

    //region Private members

    private fun render(viewState: UserDetailsViewState) {
        binding.user = viewState.user

        lifecycleScope.launch {
            _followersAdapter?.submitData(viewState.followers)
        }

        viewState.displayState.onlyIfChanged {
            when (it) {
                DisplayState.followers ->
                    binding.recyclerView.adapter = _followersAdapter
                DisplayState.following ->
                    binding.recyclerView.adapter = _followingsAdapter
            }
        }
    }

    //endregion

}