package com.defiance.defitrx.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.dakuinternational.common.domain.model.DataContent
import com.dakuinternational.common.domain.model.Response
import com.dakuinternational.common.ui.ActivityViewModel
import com.dakuinternational.common.ui.base.BaseActivity
import com.dakuinternational.common.ui.binding.viewBinding
import com.dakuinternational.common.ui.utils.showToast
import com.google.gson.Gson
import com.defiance.defitrx.R
import com.defiance.defitrx.databinding.ActivityMainBinding
import com.defiance.defitrx.presentation.adapter.DashboardAdapter
import com.defiance.defitrx.presentation.fragment.DashboardFragment
import com.defiance.defitrx.presentation.fragment.DashboardFragmentDirections
import com.defiance.defitrx.presentation.fragment.DetailsNavFragment
import com.defiance.defitrx.presentation.fragment.DetailsNavFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), DashboardAdapter.OnItemClickListener {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private val viewModel by viewModels<ActivityViewModel>()

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.navigation_host) as NavHostFragment
    }

    private val navController get() = navHostFragment.navController

    private lateinit var contentData: List<DataContent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.getData(DATABASE_NAME)
        viewModel.uiState.observe(this){
            when(it){
                is Response.Loading -> showLoading(it.isLoading)
                is Response.Error -> showToast(it.message)
                is Response.Success ->  shareData(it.data)
                else -> {
                    //no - op
                }
            }
        }

        with(binding.bottomNavigationView){
            setOnItemSelectedListener {
                when(it.itemId){
                    R.id.dashboardFragment -> navigateToHome()
                    R.id.mobileFragment -> navigateToMobile()
                    R.id.computerFragment -> navigateToComputer()
                }
                return@setOnItemSelectedListener true
            }
        }
    }

    private fun navigateToHome() {
        intent.putExtra(DashboardFragment.LIST_CONTENT, Gson().toJson(contentData))
        navController.navigate(R.id.dashboardFragment,intent.extras)
    }

    private fun navigateToComputer() {
        intent.putExtra(DetailsNavFragment.DATA_TYPE, "mining_pc_requirements")
        navController.navigate(R.id.mobileFragment, intent.extras)
    }

    private fun navigateToMobile() {
        intent.putExtra(DetailsNavFragment.DATA_TYPE, "mining_cp_requirements")
        navController.navigate(R.id.computerFragment, intent.extras)
    }

    private fun shareData(data: List<DataContent>) {
        contentData = data
        intent.putExtra(DashboardFragment.LIST_CONTENT, Gson().toJson(contentData))
        navController.setGraph(R.navigation.navigation_graph, intent.extras)
    }

    companion object{
        private val DATABASE_NAME = "tron_mining"
        fun createIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    override fun onItemClick(data: DataContent, position: Int) {
        val direction = DashboardFragmentDirections.actionDashboardFragmentToDetailsFragment(Gson().toJson(data), position)
        navController.navigate(direction)
    }
}