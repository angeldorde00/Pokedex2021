package ph.edu.dlsu.mobdeve.lastname.firstname.pokedex2021

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import getPokemonID
import okhttp3.Callback
import okhttp3.Response
import ph.edu.dlsu.mobdeve.lastname.firstname.pokedex2021.api.PokemonAPIClient
import ph.edu.dlsu.mobdeve.lastname.firstname.pokedex2021.databinding.FragmentPokemonInfoBinding
import ph.edu.dlsu.mobdeve.lastname.firstname.pokedex2021.models.Pokemon
import ph.edu.dlsu.mobdeve.lastname.firstname.pokedex2021.models.PokemonInfoResponse

class PokemonInfoFragment : Fragment() {

   private val receiver = object : BroadcastReceiver(){
       override fun onReceive(context: Context?, intent: Intent?) {
           var message: String? = intent!!.getStringExtra("data")

           Log.i("Pokemon Info", message!!.toString())
           message?.let{
               getData(message.getPokemonID())
           }
       }
   }

    var binding: FragmentPokemonInfoBinding? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setupReceiver()
    }

    override fun onCreateView(
        inflater:LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPokemonInfoBinding.inflate(inflater, container, false)
        var view = binding!!.root
        return view
    }

    override fun onDestroyView(){
        requireActivity().unregisterReceiver(receiver)
        super.onDestroyView()
    }

    private fun setupReceiver(){
        val intentFilter = IntentFilter()
        intentFilter.addAction("ph.edu.dlsu.mobdeve.broadcast.GETDATA")
        requireActivity().registerReceiver(receiver, intentFilter)
    }

    private fun getData(id: Int){
        val call: Call<PokemonInfoResponse> =
            PokemonAPIClient.getPokemonData.getPokemonInfo(id)
        val enqueue = call.enqueue(object : Callback<PokemonInfoResponse> {
            override fun onFailure(call: Call<PokemonInfoResponse>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<PokemonInfoResponse>,
                response: Response<PokemonInfoResponse>
            ) {
                var response: PokemonInfoResponse = response!!.body()!!


                Intent().also {
                    Log.d("Pokemon", "${response.sprites.front_default}")
                    it.setAction("ph.edu.dlsu.android.api.broadcast.LOADIMAGEACTION")
                    it.putExtra("data", response.sprites.front_default)
                    context!!.sendBroadcast(it)
                }

                Log.d("API INFO CALL", response.name)
            }
        })
    }

}