package com.pagination.app

import android.view.View
import androidx.navigation.fragment.findNavController
import com.pagination.app.base.BaseFragment
import com.pagination.app.data.APIDataBean
import com.pagination.app.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    override fun getLayoutRes() = R.layout.fragment_detail
    private var detail: APIDataBean? = null
    override fun initViewBinding() {
        binding.backIV.setOnClickListener {
            findNavController().popBackStack()
        }

        if (requireArguments().containsKey("detail_info")) {
            detail = requireArguments().getParcelable("detail_info")

            if (detail != null) {
                binding.idTxt.text = baseActivity!!.setData(detail!!.id.toString(), "ID")
                binding.titleTxt.text = baseActivity!!.setData(detail!!.title.toString(), "Title")
                binding.detailTxt.text = baseActivity!!.setData(detail!!.body.toString(), "Detail")
            }
        }
    }

}