package com.pagination.app
import androidx.core.text.HtmlCompat
import com.pagination.app.base.BaseActivity
import com.pagination.app.base.BaseAdapter
import com.pagination.app.base.BaseViewHolder
import com.pagination.app.data.APIDataBean
import com.pagination.app.databinding.AdapterDataBinding

class ActivityAdapter(
    val baseActivity: BaseActivity,
    val dataList: ArrayList<APIDataBean>
) : BaseAdapter<AdapterDataBinding>(),BaseAdapter.OnItemClick {
    override fun getLayoutRes()= R.layout.adapter_data
    init {
        setHasStableIds(true)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val binding = holder.binding as AdapterDataBinding
        val data = dataList[position]

        binding.idTxt.text = baseActivity.setData(data.id.toString(),"Id")
        binding.titleTxt.text =  baseActivity.setData(data.title,"Title")
        binding.root.setOnClickListener {
            onItemClick(data)
        }
    }

    override fun getItemCount(): Int {
        return  dataList.size
    }


}