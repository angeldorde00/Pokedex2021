package ph.edu.dlsu.mobdeve.lastname.firstname.pokedex2021

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import ph.edu.dlsu.mobdeve.lastname.firstname.pokedex2021.databinding.FragmentPokemonImageBinding

class PokemonImageFragment : Fragment() {

    private val receiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?){
            var imageUrl: String? = intent!!.getStringExtra("data")

            imageUrl?.let{
                Picasso
                    .with(activity!!.applicationContext)
                    .load(it)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .placeholder(R.drawable.egg)
                    .error(R.drawable.egg)
                    .into(binding!!.pokemonImage)

            }
        }
    }

    var binding: FragmentPokemonImageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupReceiver()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonImageBinding.inflate(inflater, container, false)
       var view =  binding!!.root
        return view
    }

    override fun onDestroyView(){
        requireActivity().unregisterReceiver(receiver)
        super.onDestroyView()
    }

    private fun setupReceiver(){
        val intentFilter = IntentFilter()
        intentFilter.addAction("ph.edu.dlsu.android.api.broadcast.LOADINGIMAGEACTION")
        requireActivity().registerReceiver(receiver, intentFilter)
    }
}