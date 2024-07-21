package ru.mtusi_skf.recipeapplication.screens.dbrecipe
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ru.mtusi_skf.recipeapplication.R
import ru.mtusi_skf.recipeapplication.adapters.DBRecipeAdapter
import ru.mtusi_skf.recipeapplication.data.local.db.AppDatabase
import ru.mtusi_skf.recipeapplication.data.repository.RecipeRepository
import ru.mtusi_skf.recipeapplication.databinding.FragmentDbrecipeBinding
import ru.mtusi_skf.recipeapplication.screens.base.CustomBaseFragment

class DBRecipeFragment : CustomBaseFragment(R.layout.fragment_dbrecipe) {
    private val args: DBRecipeFragmentArgs by navArgs()
    override var bottomNavigationViewVisibility = View.GONE
    private lateinit var binding: FragmentDbrecipeBinding
    private lateinit var adapter: DBRecipeAdapter
    private lateinit var viewModel: DBRecipeViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dbrecipe = args.dbrecipe
        binding = FragmentDbrecipeBinding.bind(view)
        with(binding) {
            recipeTitle.text = dbrecipe.title
            val uri = Uri.parse(dbrecipe.imageUri)
            recipeImage.setImageURI(uri)
        }

        val db = AppDatabase.getDatabase(requireContext())
        val repository = RecipeRepository(db.recipeDao())
        binding = FragmentDbrecipeBinding.bind(view)
        viewModel = ViewModelProvider(this, DBRecipeViewModelFactory(repository))[DBRecipeViewModel::class.java]
        adapter = DBRecipeAdapter()
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        val layoutManager = LinearLayoutManager(context)
        binding.instructionsRecycler.layoutManager = layoutManager
        binding.instructionsRecycler.adapter = adapter

        viewModel.fetchRecipes(dbrecipe.id)

        viewModel.instructionsLD.observe(viewLifecycleOwner) { instructions ->
            adapter.instructions = instructions

        }
    }
}