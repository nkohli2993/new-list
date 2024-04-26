package com.retofit.app

import androidx.navigation.fragment.findNavController
import com.retofit.app.base.BaseFragment
import com.retofit.app.data.APIDataBean
import com.retofit.app.databinding.FragmentDetailBinding
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