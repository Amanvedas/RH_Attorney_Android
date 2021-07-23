package com.rafayee.RHAttorney.HomeFragmentModule.userPresenter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project.api.ApiService
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.rafayee.RHAttorney.Helpers.ProgressDialog
import com.rafayee.RHAttorney.HomeFragmentModule.Adapter.ClientInfoAdapter
import com.rafayee.RHAttorney.HomeFragmentModule.Model.ClienListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception
import java.lang.reflect.Array

class UserFragmentPresenter  {
    lateinit var firstListView: RecyclerView
    lateinit var txtFilter: TextView
    lateinit var edtSearch: EditText
    lateinit var context: Context
    lateinit var adapter1 : ClientInfoAdapter
    lateinit var listclient: List<ClienListModel.Result>
    fun UserFragmentPresenter(
        context: Context, firstListView: RecyclerView,
        txtFilter: TextView, edtSearch: EditText,
        listclient: List<ClienListModel.Result>
    ) {
        this.context = context
        this.firstListView = firstListView
        this.txtFilter = txtFilter
        this.edtSearch = edtSearch
        this.listclient = listclient

    }

fun filterCall(){
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
            adapter1.getFilter().filter(charSequence.toString());
        }

        override fun afterTextChanged(editable: Editable) {
            //after the change calling the method and passing the search input

        }
    })
}
    fun getAllClientApi(context: Context, page: String, size: String) {
        ProgressDialog.getInstance().showProgress(context)
        var emialObject: JsonObject = JsonObject()
        val jsonObject = JSONObject()

        try {
            jsonObject.put("pageNo", page)
            jsonObject.put("size", size)
            val jsonParser = JsonParser()
            emialObject = jsonParser.parse(jsonObject.toString()) as JsonObject
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        ApiService.setApiCall().getClientList(emialObject)?.enqueue(object :
            Callback<ClienListModel> {
            override fun onResponse(call: Call<ClienListModel>, response: Response<ClienListModel>) {
                ProgressDialog.getInstance().hideProgress()

                try {

                    if(response.body()!!.getResults()!!.size>0) {
                       Log.e("value","=="+ response.body()!!.getResults()?.size)
                        listclient= response.body()!!.getResults() as List<ClienListModel.Result>
                        val linearLayoutManager = LinearLayoutManager(context)
                        adapter1 = ClientInfoAdapter()
                         adapter1.ClientInfoAdapter(listclient,context)
                        firstListView.layoutManager=linearLayoutManager
                        firstListView.adapter=adapter1
                        Log.e("value","=="+ listclient.size)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ClienListModel>, t: Throwable) {
                ProgressDialog.getInstance().hideProgress()
            }


        })

    }




    //http://dev-api.robinsonandhenry.com/api/clients/FetchAllClients


    }