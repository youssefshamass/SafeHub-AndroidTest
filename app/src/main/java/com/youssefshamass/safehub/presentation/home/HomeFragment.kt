package com.youssefshamass.safehub.presentation.home

import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.youssefshamass.core_android.extensions.gone
import com.youssefshamass.core_android.extensions.visibile
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.youssefshamass.core.extensions.observe
import com.youssefshamass.safehub.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.youssefshamass.core_android.decorators.HorizontalSpaceDecorator
import com.youssefshamass.core_android.decorators.VerticalSpaceItemDecoration
import com.youssefshamass.core_android.extensions.bindToList
import com.google.android.material.transition.platform.MaterialFadeThrough
import com.youssefshamass.data.entities.local.User
import com.youssefshamass.safehub.R

class HomeFragment : Fragment() {
    private val _viewModel: HomeViewModel by viewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private var _adapter: UserAdapter? = null

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackButtonInterceptor()
        bindFabToSearchContainer()
        setupRecyclerView()
        attachListeners()
        binding.fabSearch.bindToList(binding.recyclerViewRecentMatches)

        _viewModel.state.observe(this, ::render)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    //endregion

    //region Private members

    private fun render(viewState: HomeViewState) {
        _adapter?.submitList(viewState.recentMatches)
        binding.isLoading = viewState.isLoading

        viewState.errorMessage?.onlyIfChanged {
            it?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        viewState.match?.onlyIfChanged {
            it?.let {
                navigateToUserDetails(it)
            }
        }
    }

    private fun setupRecyclerView() {
        _adapter = _adapter ?: UserAdapter {
            navigateToUserDetails(it)
        }

        binding.recyclerViewRecentMatches.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(VerticalSpaceItemDecoration(resources.getDimensionPixelSize(R.dimen.spacing_regular)))
            addItemDecoration(HorizontalSpaceDecorator(resources.getDimensionPixelSize(R.dimen.spacing_regular)))
            adapter = _adapter
        }
    }

    private fun attachListeners() {
        binding.viewCardSearchInputField.setOnEditorActionListener { _, actionId, _ ->
            hideKeyboard(requireActivity())
            if (actionId == EditorInfo.IME_ACTION_GO) {
                invokeSearch()
                return@setOnEditorActionListener true
            }

            false
        }

        binding.buttonSearch.setOnClickListener {
            hideKeyboard(requireActivity())
            invokeSearch()
        }
    }

    private fun invokeSearch() {
        if (!validateAction()) {
            binding.viewCardSearchInputField.error = "Required"
            return
        }

        _viewModel.search(binding.viewCardSearchInputField.text.toString())
    }

    private fun validateAction(): Boolean =
        !binding.viewCardSearchInputField.text.isNullOrEmpty()

    private fun navigateToUserDetails(user: User) {
        findNavController().navigate(
            HomeFragmentDirections.actionHomeFragmentToUserDetailsFragment(
                user.id
            )
        )
    }

    private fun setupBackButtonInterceptor() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.fabSearch.visibility == View.GONE)
                        reverseSearchTransition()
                    else
                        activity?.finish()
                }
            })
    }

    private fun bindFabToSearchContainer() {
        binding.fabSearch.setOnClickListener {
            binding.fabSearch.tag = View.GONE
            TransitionManager.beginDelayedTransition(
                binding.viewGroupMain,
                getTransform(it, binding.cardViewSearch)
            )
            binding.fabSearch.gone()
            binding.cardViewSearch.visibile()
            binding.viewCardOverlay.visibile()
        }

        binding.viewCardOverlay.setOnClickListener {
            reverseSearchTransition()
        }
    }

    private fun reverseSearchTransition() {
        if (binding.fabSearch.visibility == View.VISIBLE)
            return

        binding.fabSearch.tag = View.VISIBLE
        TransitionManager.beginDelayedTransition(
            binding.viewGroupMain,
            getTransform(binding.cardViewSearch, binding.fabSearch)
        )
        binding.fabSearch.visibile()
        binding.cardViewSearch.gone()
        binding.viewCardOverlay.gone()
    }

    private fun getTransform(sourceView: View, destinationView: View): MaterialContainerTransform =
        MaterialContainerTransform().apply {
            startView = sourceView
            endView = destinationView
            addTarget(destinationView)
            pathMotion = MaterialArcMotion()
            duration = 550
            scrimColor = Color.TRANSPARENT
        }

    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    //endregion
}