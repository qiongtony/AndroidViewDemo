package com.example.adaptivelayoutdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.concatadapterdemo.adapter.StudentAdapter
import kotlinx.android.synthetic.main.fragment_rv.*

/**
 * A simple [Fragment] subclass.
 * Use the [RvFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RvFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = StudentAdapter()
        rv_list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_list.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() =
                RvFragment().apply {

                }
    }
}