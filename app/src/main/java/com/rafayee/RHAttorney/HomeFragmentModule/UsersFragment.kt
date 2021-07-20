package com.rafayee.RHAttorney.HomeFragmentModule

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rafayee.RHAttorney.HomeFragmentModule.Adapter.ClientInfoAdapter
import com.rafayee.RHAttorney.MenuModule.Presenter.AlertDialogPresenter
import com.rafayee.RHAttorney.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UsersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UsersFragment : Fragment() {
    lateinit var alertDialogPresenter: AlertDialogPresenter

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
   lateinit var listNames: ArrayList<String>
    lateinit var adapter1 : ClientInfoAdapter

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
        val view: View = inflater.inflate(R.layout.fragment_users, container, false)
        val firstListView : RecyclerView = view.findViewById(R.id.user_list)
        val txtFilter : TextView = view.findViewById(R.id.txt_filter)
        val edtSearch : EditText = view.findViewById(R.id.edt_search)
        alertDialogPresenter = AlertDialogPresenter()
        alertDialogPresenter.AlertDialogPresenter(view.context)
        val linearLayoutManager = LinearLayoutManager(activity)
       // activity?.let { adapter1.AttorneyListAdapter(it,"") }
        listNames = ArrayList()
        listNames.add("Client Name")
        listNames.add("Client Name")
        listNames.add("Client Name")

        adapter1 = ClientInfoAdapter()
        activity?.let { adapter1.ClientInfoAdapter(listNames,view.context) }
        firstListView.layoutManager=linearLayoutManager
        firstListView.adapter=adapter1

        txtFilter.setOnClickListener(View.OnClickListener {
            alertDialogPresenter.filterAlertDialog()
        })
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun onTextChanged(
                charSequence: CharSequence,
                i: Int,
                i1: Int,
                i2: Int
            ) {
            }

            override fun afterTextChanged(editable: Editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString())
            }
        })

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UsersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UsersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun filter(text: String) {
        //new array list that will hold the filtered data
        val filterdNames: ArrayList<String> = ArrayList()

        //looping through existing elements
        for (s in listNames) {
            //if the existing elements contains the search input
            if (s.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s)
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter1.filterList(filterdNames)
    }

    fun pushFragment(
        newFragment: Fragment?,
        context: Context
    ) {
        val transaction =
            (context as FragmentActivity).supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment, newFragment!!)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}