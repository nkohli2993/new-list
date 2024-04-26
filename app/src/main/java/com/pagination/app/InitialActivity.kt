package com.pagination.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.pagination.app.base.BaseActivity
import com.pagination.app.databinding.ActivityInitialBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitialActivity : BaseActivity() {
    private lateinit var binding: ActivityInitialBinding
    private var navController: NavController? = null
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_initial)
        navController = findNavController(R.id.nav_host_fragment)

        navController?.navigate(R.id.listFragment)
        initDestinationChangeListener()
    }
    private fun initDestinationChangeListener() {
        navController?.addOnDestinationChangedListener { controller, destination, arguments ->
        }
    }

    fun getCurrentFragment(): Int? {
        return navController?.currentDestination?.id
    }
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val destArray = listOf<Int>(R.id.listFragment)
        if (destArray.contains(getCurrentFragment())) {
            backAction()

        } else {
            navController?.popBackStack()
        }
    }
}