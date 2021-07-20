package com.rafayee.RHAttorney.ParalegalModule

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rafayee.RHAttorney.AppointmentInfoModule.DetailsModel
import com.rafayee.RHAttorney.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClientInfoParalegalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClientInfoParalegalFragment : Fragment() {

    lateinit var detailsModel: DetailsModel
    lateinit var detailsModel1: DetailsModel
    lateinit var listOfMatters : ArrayList<DetailsModel>
    lateinit var listOfSchedule : ArrayList<DetailsModel>
    lateinit var matterAdapter : ClientInfoParalegalAdapter
    lateinit var scheduleAdapter : ClientInfoParalegalAdapter
    lateinit var linearManager : LinearLayoutManager

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view : View = inflater.inflate(R.layout.fragment_client_info_paralegal, container, false)
        var matterListView : RecyclerView = view.findViewById(R.id.open_matters_list)
        var administrationListView : RecyclerView = view.findViewById(R.id.administation_list)
        linearManager = LinearLayoutManager(view.context)
        detailsModel = DetailsModel()
        detailsModel.name="Dissolution of Marriage"
        listOfSchedule.add(detailsModel)
        detailsModel = DetailsModel()
        detailsModel.name="Will Drafting"
        listOfSchedule.add(detailsModel)
        scheduleAdapter.ClientInfoParalegalAdapter(listOfSchedule,view.context)
        detailsModel1 = DetailsModel()
        detailsModel1.name="Accounting Department"
        listOfSchedule.add(detailsModel1)
        detailsModel1 = DetailsModel()
        detailsModel1.name="Customer Service"
        listOfMatters.add(detailsModel1)
        matterAdapter.ClientInfoParalegalAdapter(listOfMatters,view.context)
        administrationListView.layoutManager = linearManager
        administrationListView.adapter=scheduleAdapter
        matterListView.layoutManager = linearManager
        matterListView.adapter =matterAdapter
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClientInfoParalegalFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClientInfoParalegalFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}