package com.youssefshamass.safehub.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialFadeThrough
import com.youssefshamass.core.extensions.observe
import com.youssefshamass.safehub.databinding.FragmentUserDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class UserDetailsFragment : Fragment() {
    private var _binding: FragmentUserDetailsBinding? = null
    private val binding: FragmentUserDetailsBinding
        get() = _binding!!

    private val _args: UserDetailsFragmentArgs by navArgs()
    private val _viewModel: UserDetailsViewModel by viewModel {
        parametersOf(_args.userId)
    }

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

        _viewModel.state.observe(this, ::render)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    //endregion

    //region Private members

    private fun render(viewState: UserDetailsViewState) {
        binding.user = viewState.user
    }

    //endregion

}