package ru.mtusi_skf.recipeapplication.screens.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ru.mtusi_skf.recipeapplication.R
import ru.mtusi_skf.recipeapplication.adapters.HomeAdapter
import ru.mtusi_skf.recipeapplication.api.RetrofitRepository
import ru.mtusi_skf.recipeapplication.databinding.FragmentHomeBinding
import ru.mtusi_skf.recipeapplication.screens.base.CustomBaseFragment

class HomeFragment : CustomBaseFragment(R.layout.fragment_home) {
    override var bottomNavigationViewVisibility = View.VISIBLE

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter
    private lateinit var viewModel: HomeViewModel
    private lateinit var chipGroup: ChipGroup
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val repository = RetrofitRepository()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        adapter = HomeAdapter { selectedRecipe ->
            val action = HomeFragmentDirections.actionHomeFragmentToRecipeFragment(selectedRecipe)
            findNavController().navigate(action)
        }
        chipGroup = view.findViewById(R.id.chip_group)
        swipeRefreshLayout = binding.swipeRefreshLayout
        viewModel = ViewModelProvider(this, HomeViewModelFactory(repository))[HomeViewModel::class.java]
        viewModel.ingredientsLD.observe(viewLifecycleOwner) { ingredients ->
            chipGroup.removeAllViews()
            ingredients.forEach { ingredient ->
                val chip = Chip(requireContext())
                chip.text = ingredient.name
                chip.isClickable = true
                chip.isCheckable = true
                chip.isChecked = viewModel.selectedIngredients.contains(ingredient.apiName)
                // Применение селекторов для фона и текста
                chip.setChipBackgroundColorResource(R.color.chip_background_selector)
                chip.setTextColor(resources.getColorStateList(R.color.chip_text_selector, null))
                chip.chipStrokeWidth = 0.toFloat()

                chip.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        // Добавляем apiName нажатого чипа в listOf
                        viewModel.addToSelectedIngredients(ingredient)
                    } else {
                        // Удаление apiName из listOf при отмене выбора
                        viewModel.removeFromSelectedIngredients(ingredient)
                    }
                }
                chipGroup.addView(chip)
            }
        }

        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        viewModel.recipesLD.observe(viewLifecycleOwner) { recipes ->
            adapter.recipes = recipes
            swipeRefreshLayout.isRefreshing = false
        }

        viewModel.emptyScreenTextVisibility.observe(viewLifecycleOwner) { visibility ->
            binding.emptyScreenText.visibility = visibility
        }

        binding.searchButton.setOnClickListener {
            viewModel.fetchRecipes()
            viewModel.setEmptyScreenTextVisibility(View.GONE)
        }

        swipeRefreshLayout.setOnRefreshListener {
            if (viewModel.recipesLD.value != null && viewModel.recipesLD.value!!.isNotEmpty()) {
                viewModel.fetchRandomRecipes()
                viewModel.setEmptyScreenTextVisibility(View.GONE)
            } else {
                swipeRefreshLayout.isRefreshing = false // Отключить обновление, если список рецептов пуст
            }
        }
    }
}