package com.defiance.defitrx.presentation.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.dakuinternational.common.domain.model.DataContent
import com.dakuinternational.common.domain.model.Response
import com.dakuinternational.common.ui.ActivityViewModel
import com.dakuinternational.common.ui.base.BaseFragment
import com.dakuinternational.common.ui.binding.viewBinding
import com.dakuinternational.common.ui.dialog.AlertUtils
import com.google.gson.Gson
import com.defiance.defitrx.R
import com.defiance.defitrx.databinding.FragmentDetailsNavBinding
import com.defiance.defitrx.presentation.adapter.DetailsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsNavFragment : BaseFragment(R.layout.fragment_details_nav) {

    private val binding by viewBinding(FragmentDetailsNavBinding::bind)

    private val args : DetailsNavFragmentArgs by navArgs()

    private val viewModel by viewModels<ActivityViewModel>()

    private val detailsAdapter = DetailsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        viewModel.getData(args.database)
        viewModel.uiState.observe(viewLifecycleOwner){
            when(it){
                is Response.Loading -> {}
                is Response.Success -> detailsAdapter.setList(it.data)
                is Response.Error -> errorHandler(it.message)
            }
        }
    }

    private fun errorHandler(message: String) {
        AlertUtils.alertNoInternet(
            requireContext()){
                dialog, which ->
            when(which){
                DialogInterface.BUTTON_NEGATIVE -> requireActivity().finish()
                DialogInterface.BUTTON_POSITIVE -> dialog.dismiss()
            }
        }
    }

    private fun setupViews() {
        with(binding.detailsRecycler){
            adapter = detailsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    companion object{
        const val DATA_TYPE = "database"
    }

}