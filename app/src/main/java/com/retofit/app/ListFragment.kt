package com.retofit.app


import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.retofit.app.databinding.FragmentListBinding
import com.retofit.app.base.BaseAdapter
import com.retofit.app.base.BaseFragment
import com.retofit.app.data.Articles
import com.retofit.app.data.ExampleJson2KtKotlin
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class ListFragment : BaseFragment<FragmentListBinding>(), BaseAdapter.OnItemClick {
    override fun getLayoutRes() = R.layout.fragment_list
    private var adapter: ActivityAdapter? = null
    private var showDataList: ArrayList<Articles> = arrayListOf()
    private var dataList: ExampleJson2KtKotlin? = null
    private var numItemsPages = 10
    private val updateHandler = Handler(Looper.myLooper()!!)
    override fun initViewBinding() {
        setAdapter()
        initScrollListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (isNetworkAvailable(baseActivity!!)) {
            val apiInterface: ApiService =
                ApiClient().getApiClient()!!.create(ApiService::class.java)
            apiInterface.getDataList(
                "tesla","2024-06-21","publishedAt","ba909ad409f1423ab53e1e37a2aaf2fe"
            ).enqueue(object : Callback<ExampleJson2KtKotlin> {
                override fun onResponse(
                    call: Call<ExampleJson2KtKotlin>,
                    response: Response<ExampleJson2KtKotlin>
                ) {
                    if (response.isSuccessful) {
                        dataList = response.body()!!
                        loadList()
                    } else {
                        // Log the error response
                        val errorBody = response.errorBody()?.string()
                        Log.e("ApiClient", "Error response: $errorBody")
                        println("Raw error response: $errorBody")
                    }

                }

                override fun onFailure(call: Call<ExampleJson2KtKotlin>, t: Throwable) {
                    Toast.makeText(
                        baseActivity!!,
                        t.message,
                        Toast.LENGTH_SHORT
                    ).show()

                }
            })

        } else {
            Toast.makeText(
                baseActivity!!,
                getString(R.string.no_internet_connection_found_please_try_again_later),
                Toast.LENGTH_SHORT
            ).show()
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
            if (i < (dataList?.articles?.size ?: 0)) {
                dataList?.articles?.get(i)?.let { showDataList.add(it) }
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
                if (linearLayoutManager.findLastVisibleItemPosition() < (dataList?.articles?.size?.minus(
                        1
                    ) ?: 0)
                ) {
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
            bundleOf("detail_info" to items[0] as Articles)
        )
    }

}