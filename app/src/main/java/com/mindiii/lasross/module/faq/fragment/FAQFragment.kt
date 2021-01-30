package com.mindiii.lasross.module.faq.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindiii.lasross.R
import com.mindiii.lasross.base.BaseKotlinFragment
import com.mindiii.lasross.module.faq.adapter.FAQAdapter
import com.mindiii.lasross.module.faq.model.FAQModel
import kotlinx.android.synthetic.main.fragment_faq.*
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FAQFragment : BaseKotlinFragment(), View.OnClickListener {

    private var param1: String? = null
    private var param2: String? = null
    lateinit private var list: ArrayList<FAQModel>
    lateinit private var faqAdapter: FAQAdapter

    override fun onClick(p0: View?) {
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_faq, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                FAQFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val window = getActivity()!!.window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = this.resources.getColor(R.color.home_header_bg1)

        list = ArrayList()

        for (i in 1..10) {
            val itemList = FAQModel("How long does it take for me to receive my order?",
                    "Meditation williamsburg kogi blog bushwick pitchfork polaroid austin dreamcatcher narwhal taxidermy tofu gentrify aesthetic.")
            list.add(itemList)
        }

        faqAdapter = FAQAdapter(list, view.context)
        rvFAQList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rvFAQList.adapter = faqAdapter

    }
}