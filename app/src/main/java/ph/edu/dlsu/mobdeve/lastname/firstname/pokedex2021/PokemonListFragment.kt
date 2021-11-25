package ph.edu.dlsu.mobdeve.lastname.firstname.pokedex2021

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Downloader
import ph.edu.dlsu.mobdeve.lastname.firstname.pokedex2021.api.PokemonAPIClient
import ph.edu.dlsu.mobdeve.lastname.firstname.pokedex2021.databinding.FragmentPokemonListBinding
import ph.edu.dlsu.mobdeve.lastname.firstname.pokedex2021.models.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class PokemonListFragment : Fragment() {

    lateinit var pokemonAdapter: PokemonAdapter
    var pokemonList = ArrayList<Pokemon>()
    var binding:FragmentPokemonListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPokemonListBinding.inflate(inflater, container,false)
        var view = binding!!.root

        pokemonAdapter = PokemonAdapter(requireActivity().applicationContext, pokemonList)
        binding!!.pokemonList.layoutManager = LinearLayoutManager(requireActivity().applicationContext,
             LinearLayoutManager.VERTICAL,
            false)
        binding!!.pokemonList.adapter = pokemonAdapter

        getData()

        return view
    }

    fun getData(){
        val call: Call<Pokemon.PokemonListResponse> =
            PokemonAPIClient.getPokemonData.getList(0, 100)

        call.enqueue(object: Callback<Pokemon.PokemonListResponse> {
            override fun onFailure(call: Call<Pokemon.PokemonListResponse>, t: Throwable){
                Log.d("API CALL", "Failed API Call")
            }
            override fun onResponse(call: Call<Pokemon.PokemonListResponse>,
                                    response: Response<Pokemon.PokemonListResponse>){
                var response: Pokemon.PokemonListResponse = response!!.body()!!
                pokemonAdapter.setList(response.pokemonList)

 //               var pokeList = response.pokemonList
//               for(pokemon in pokelist){
//                   Log.d("API CALL", pokemon.name)
//               }

            }
        })
    }
}