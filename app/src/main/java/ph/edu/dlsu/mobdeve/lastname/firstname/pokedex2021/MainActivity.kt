package ph.edu.dlsu.mobdeve.lastname.firstname.pokedex2021

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import ph.edu.dlsu.mobdeve.lastname.firstname.pokedex2021.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val pokemonListFragment = PokemonListFragment()
    val pokemonInfoFragment = PokemonInfoFragment()
    val pokemonImageFragment = PokemonImageFragment()
    val pokemonInfoFragment1 = PokemonInfoFragment()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var pokemonInfoAdapater: PokemonInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_holder, pokemonListFragment)
            .commit()

        pokemonInfoAdapater = PokemonInfoAdapter(supportFragmentManager)
        pokemonInfoAdapater.add(pokemonInfoFragment, "Pokemon Information")
        pokemonInfoAdapater.add(pokemonImageFragment, "Pokemon Image")
        pokemonInfoAdapater.add(pokemonInfoFragment1, "Pokemon Details")
        binding!!.pokemonInfoViewpager.adapter = pokemonInfoAdapater
    }
}
