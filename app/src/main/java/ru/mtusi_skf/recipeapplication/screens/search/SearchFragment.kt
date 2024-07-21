package ru.mtusi_skf.recipeapplication.screens.search

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.mtusi_skf.recipeapplication.R
import ru.mtusi_skf.recipeapplication.adapters.SearchAdapter
import ru.mtusi_skf.recipeapplication.api.RetrofitRepository
import ru.mtusi_skf.recipeapplication.databinding.FragmentSearchBinding
import ru.mtusi_skf.recipeapplication.screens.base.CustomBaseFragment

class SearchFragment : CustomBaseFragment(R.layout.fragment_search) {
    override var bottomNavigationViewVisibility = View.VISIBLE

    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter
    private lateinit var viewModel: SearchViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val repository = RetrofitRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        adapter = SearchAdapter { selectedRecipe ->
            val action = SearchFragmentDirections.actionSearchFragmentToRecipeFragment(selectedRecipe)
            findNavController().navigate(action)
        }
        viewModel = ViewModelProvider(this, SearchViewModelFactory(repository))[SearchViewModel::class.java]
        swipeRefreshLayout = binding.swipeRefreshLayout

        val layoutManager = GridLayoutManager(context, 2)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        viewModel.recipesLD.observe(viewLifecycleOwner) { recipes ->
            adapter.recipes = recipes
            swipeRefreshLayout.isRefreshing = false
        }

        viewModel.emptyScreenTextVisibility.observe(viewLifecycleOwner) { visibility ->
            binding.emptyScreenText.visibility = visibility
        }

        swipeRefreshLayout.setOnRefreshListener {
            if (viewModel.recipesLD.value != null && viewModel.recipesLD.value!!.isNotEmpty()) {
                viewModel.fetchRecipes()
                viewModel.setEmptyScreenTextVisibility(View.GONE)
            } else {
                swipeRefreshLayout.isRefreshing = false // Отключить обновление, если список рецептов пуст
            }
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.query = query
                viewModel.fetchRecipes()
                viewModel.setEmptyScreenTextVisibility(View.GONE)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Дополнительная логика при изменении текста в поисковом поле
                return false
            }
        })
    }
}