package com.dakuinternational.common.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dakuinternational.common.ui.dialog.LoadingDialog

abstract class BaseFragment(private var layoutRes: Int): Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    fun onBackPressed(){
        requireActivity().onBackPressed()
    }

    private var loadingDialog: LoadingDialog? = null

    fun showLoading(isLoading: Boolean){
        if(isLoading) getLoadingDialog()?.show()
        else getLoadingDialog()?.hide()
    }

    fun getLoadingDialog(): LoadingDialog? {
        if(loadingDialog == null) loadingDialog = LoadingDialog(requireContext())
        return loadingDialog
    }
}