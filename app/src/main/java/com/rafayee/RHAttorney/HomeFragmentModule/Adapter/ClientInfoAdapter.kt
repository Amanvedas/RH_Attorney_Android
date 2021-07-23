package com.rafayee.RHAttorney.HomeFragmentModule.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rafayee.RHAttorney.AppointmentInfoModule.ClientInfoFragment
import com.rafayee.RHAttorney.HomeFragmentModule.Model.ClienListModel
import com.rafayee.RHAttorney.HomeFragmentModule.UsersFragment
import com.rafayee.RHAttorney.HomeModule.FragmentInteractionListener
import com.rafayee.RHAttorney.HomeModule.HomeWithBottomTabsActivity
import com.rafayee.RHAttorney.R
import com.rafayee.RHAttorney.ServerConnections.ServerApiCollection
import de.hdodenhof.circleimageview.CircleImageView


class ClientInfoAdapter: RecyclerView.Adapter<ClientInfoAdapter.ViewHolder>(), Filterable {
    lateinit var activity: Context
    var listNames: List<ClienListModel.Result> = ArrayList()
    var userF : UsersFragment = UsersFragment()
    var hBT : HomeWithBottomTabsActivity = HomeWithBottomTabsActivity()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientInfoAdapter.ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.client_info_item_layout, null)
        return ViewHolder(layout)
    }

    fun ClientInfoAdapter(listNames: List<ClienListModel.Result>, activity: Context) {
        this.listNames = listNames
        this.activity = activity
    }
    fun filterList(listNames: List<ClienListModel.Result>) {
        this.listNames = listNames
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listNames.size
    }

    override fun onBindViewHolder(holder: ClientInfoAdapter.ViewHolder, position: Int) {

        var navController: NavController? = null

        holder.name.text=listNames.get(position).firstName+"  "+listNames.get(position).lastName
        Glide.with(activity)
            .load(ServerApiCollection.IMAGE_URL+ listNames.get(position).profilePic)
            .placeholder(R.drawable.profile_ic)
            .into(holder.pic)
        holder.itemView.setOnClickListener(View.OnClickListener {
           /*var bundle:Bundle
            val newFragment = ClientInfoFragment()
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.main_fragment, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()*/


          var fragment:Fragment?=null
           var bundle: Bundle
            Navigation.findNavController(holder.itemView).navigate(R.id.thired_fragment)
            fragment = ClientInfoFragment()
            //replaceFragment(fragment)
            lateinit var fragmentInteractionListener : FragmentInteractionListener
            fragmentInteractionListener = activity as FragmentInteractionListener
            fragmentInteractionListener.onClickButton(ClientInfoFragment())
            /* var fragment:Fragment?=null
             fragment = ClientInfoFragment()
             userF.replaceFragment(fragment)*/

          /*  val myFragment: Fragment =  ClientInfoFragment()
            userF.replaceFragment(myFragment)
            findNavController().navigate(R.id.thired_fragment)
*/
            //  userF.pushFragment(myFragment,activity)
           // ((activity) context)?.getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, myFragment).addToBackStack(null).commit();

            //   activity.startActivity(Intent(activity, ClientInfoFragment::class.java))

        })
    }
    override  fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(charSequence: CharSequence?, filterResults: Filter.FilterResults) {
                listNames = filterResults.values as List<ClienListModel.Result>
                notifyDataSetChanged()
            }

            override fun performFiltering(charSequence: CharSequence?): Filter.FilterResults {
                val queryString = charSequence?.toString()?.toLowerCase()

                val filterResults = Filter.FilterResults()
                filterResults.values = if (queryString==null || queryString.isEmpty())
                    listNames
                else
                    listNames.filter {
                        it.firstName!!.toLowerCase().contains(queryString)
                    }
                return filterResults
            }
        }
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
       /* var nextAvailable: TextView
        var desc: TextView*/
        var name: TextView
        var pic: CircleImageView
        var horiLine: View
        var seeMoreLay: LinearLayout

        init {
            name = v.findViewById(R.id.name_)
          /*  desc = v.findViewById(R.id.desc)
            nextAvailable = v.findViewById(R.id.nextAvailable)*/
            pic = v.findViewById(R.id.attorneyPic)
            horiLine = v.findViewById(R.id.horiLine)
            seeMoreLay = v.findViewById(R.id.seeMoreLay)
        }
    }


/*
    fun replaceFragment(someFragment: Fragment?) {
        Log.e("hgkj","khjgkj")
        val fragmentManager: FragmentManager =
            (context as Activity).getFragmentManager()

        val transaction: FragmentTransaction = ((AppCompatActivity)activity).requireFragmentManager().beginTransaction()


        transaction.replace(R.id.main_fragment, someFragment!!)
        transaction.addToBackStack(null)
        transaction.commit()
    }
*/

}