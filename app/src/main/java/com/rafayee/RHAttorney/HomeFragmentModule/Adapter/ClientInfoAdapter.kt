package com.rafayee.RHAttorney.HomeFragmentModule.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.rafayee.RHAttorney.AppointmentInfoModule.ClientInfoFragment
import com.rafayee.RHAttorney.HomeFragmentModule.UsersFragment
import com.rafayee.RHAttorney.HomeModule.FragmentInteractionListener
import com.rafayee.RHAttorney.HomeModule.HomeWithBottomTabsActivity
import com.rafayee.RHAttorney.R
import de.hdodenhof.circleimageview.CircleImageView


class ClientInfoAdapter: RecyclerView.Adapter<ClientInfoAdapter.ViewHolder>(){
    lateinit var activity: Context
    var listNames: ArrayList<String> = ArrayList()

    var userF : UsersFragment = UsersFragment()
    var hBT : HomeWithBottomTabsActivity = HomeWithBottomTabsActivity()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientInfoAdapter.ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.client_info_item_layout, null)
        return ViewHolder(layout)
    }

    fun ClientInfoAdapter(listNames: ArrayList<String>, activity: Context) {
        this.listNames = listNames
        this.activity = activity
    }
    fun filterList(listNames: ArrayList<String>) {
        this.listNames = listNames
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listNames.size
    }

    override fun onBindViewHolder(holder: ClientInfoAdapter.ViewHolder, position: Int) {

        var navController: NavController? = null
        holder.itemView.setOnClickListener(View.OnClickListener {

          var fragment:Fragment?=null

            Navigation.findNavController(holder.itemView).navigate(R.id.thired_fragment);
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