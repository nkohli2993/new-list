package com.pagination.app


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pagination.app.base.BaseAdapter
import com.pagination.app.base.BaseFragment
import com.pagination.app.data.APIDataBean
import com.pagination.app.databinding.FragmentListBinding
import com.pagination.app.network.retrofit.DataResult
import com.pagination.app.network.retrofit.observeEvent
import com.pagination.app.view_model.PaginationViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(), BaseAdapter.OnItemClick {
    override fun getLayoutRes() = R.layout.fragment_list
    private var adapter: ActivityAdapter? = null
    private val viewModel: PaginationViewModel by viewModels()
    private var showDataList: ArrayList<APIDataBean> = arrayListOf()
    private var dataList: ArrayList<APIDataBean> = arrayListOf()
    private var totalItems = 0
    private var numItemsPages = 10
    private val updateHandler = Handler(Looper.myLooper()!!)
    override fun initViewBinding() {
        setAdapter()
        initScrollListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (isNetworkAvailable(baseActivity!!)) {
            viewModel.getDataList()
        } else {
            Toast.makeText(
                baseActivity!!,
                getString(R.string.no_internet_connection_found_please_try_again_later),
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.dataResponseLiveData.observeEvent(this) { result ->
            when (result) {
                is DataResult.Failure -> {
                    baseActivity!!.hideLoading()
                    Toast.makeText(baseActivity!!, result.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                is DataResult.Loading -> {
                    baseActivity!!.showLoading()
                }

                is DataResult.Success -> {
                    baseActivity!!.hideLoading()
                    dataList.addAll(result.data!!)
                    totalItems = dataList.size
                    loadList()
                }

                else -> {}
            }
        }
    }

    private fun setAdapter() {
        adapter = ActivityAdapter(baseActivity!!, showDataList)
        binding.listRl.adapter = adapter
        adapter!!.setOnItemClickListener(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadList() {
        val start = showDataList.size
        Log.e("catch_size", "start $start ${showDataList.size}")
        for (i in start until start + numItemsPages) {
            if (i < dataList.size) {
                showDataList.add(dataList[i])
            } else {
                break
            }
        }
        binding.progressBar.visibility = View.GONE
        binding.listRl.post {
            adapter?.notifyDataSetChanged()
        }
    }

    private fun initScrollListener() {

        binding.idNestedSV.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY: Int, _, _ ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                val linearLayoutManager = binding.listRl.layoutManager as LinearLayoutManager
                if (linearLayoutManager.findLastVisibleItemPosition() < dataList.size - 1) {
                    binding.progressBar.visibility = View.VISIBLE
                    val runnable = Runnable {
                        loadList()
                    }
                    updateHandler.postDelayed(runnable, 2000)
                } else {
                    binding.progressBar.visibility = View.GONE
                }
            }
        })
    }

    override fun onItemClick(vararg items: Any) {
        findNavController().navigate(
            R.id.action_listFragment_to_detailFragment,
            bundleOf("detail_info" to items[0] as APIDataBean)
        )
    }

}