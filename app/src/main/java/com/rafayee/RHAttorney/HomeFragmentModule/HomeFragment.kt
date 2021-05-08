package com.rafayee.RHAttorney.HomeFragmentModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rafayee.RH.AttorneyList.Adapter.AttorneyListAdapter
import com.rafayee.RHAttorney.HomeFragmentModule.Model.AttorneyList
import com.rafayee.RHAttorney.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home2, container, false)
        val firstListView : RecyclerView = view.findViewById(R.id.firstListAttorneys)
        val secondListView : RecyclerView = view.findViewById(R.id.today_list)
        val adapter= AttorneyListAdapter()
        val adapter1= AttorneyListAdapter()
        val linearLayoutManager = LinearLayoutManager(activity)
        val linearLayoutManager1 = LinearLayoutManager(activity)

        val list : ArrayList<AttorneyList> = ArrayList()
        activity?.let { adapter1.AttorneyListAdapter(it,"total") }
        activity?.let { adapter.AttorneyListAdapter(it,"non") }

        firstListView.layoutManager=linearLayoutManager
        secondListView.layoutManager=linearLayoutManager1

        firstListView.adapter=adapter1
        secondListView.adapter=adapter


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}

