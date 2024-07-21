package ru.mtusi_skf.recipeapplication.screens.post
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.room.Room
import ru.mtusi_skf.recipeapplication.R
import ru.mtusi_skf.recipeapplication.databinding.FragmentPostBinding
import ru.mtusi_skf.recipeapplication.data.repository.RecipeRepository
import ru.mtusi_skf.recipeapplication.data.local.db.AppDatabase
import ru.mtusi_skf.recipeapplication.data.local.entity.InstructionEntity
import ru.mtusi_skf.recipeapplication.data.local.entity.RecipeEntity
import ru.mtusi_skf.recipeapplication.data.local.entity.StepEntity
import ru.mtusi_skf.recipeapplication.screens.base.CustomBaseFragment
import java.io.File

class PostFragment : CustomBaseFragment(R.layout.fragment_post) {

    private lateinit var binding: FragmentPostBinding
    private lateinit var db: AppDatabase
    private val viewModel: PostViewModel by viewModels {
        val db = AppDatabase.getDatabase(requireContext())
        val repository = RecipeRepository(db.recipeDao())
        PostViewModelFactory(repository)
    }

    override var bottomNavigationViewVisibility = View.VISIBLE

    private var stepNumber = 1

    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeDatabase()
        binding = FragmentPostBinding.bind(view)

        binding.buttonAddStep.setOnClickListener {
            addStep()
        }

        binding.buttonRemoveStep.setOnClickListener {
            removeStep()
        }

        binding.buttonSaveRecipe.setOnClickListener {
            saveRecipeWithSteps()
        }

        binding.imageButton.setOnClickListener {
            openGallery()
        }

        viewModel.imageBitmap.observe(viewLifecycleOwner, Observer { bitmap ->
            binding.imageButton.setImageBitmap(bitmap)
            binding.imageButtonIcon.visibility = View.INVISIBLE
        })

        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val imageUri = result.data?.data
                val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
                viewModel.processSelectedImage(bitmap, requireContext())
            }
        }
    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        pickImageLauncher.launch(gallery)
    }

    private fun saveRecipeWithSteps() {
        val title = binding.titleEditText.text.toString()
        val imageUri = viewModel.imageUri.value.toString()
        val recipe = RecipeEntity(title = title, imageUri = imageUri)

        val steps = mutableListOf<StepEntity>()
        for (i in 0 until binding.stepsContainer.childCount) {
            val stepView = binding.stepsContainer.getChildAt(i)
            val stepTitle = stepView.findViewById<EditText>(R.id.step_description).text.toString()
            val stepIngredients = stepView.findViewById<EditText>(R.id.step_ingredients).text.toString()
            val stepEquipment = stepView.findViewById<EditText>(R.id.step_equipment).text.toString()
            val step = StepEntity(stepTitle = stepTitle, number = i + 1, ingredients = stepIngredients, equipments = stepEquipment, instructionId = 0)
            steps.add(step)
        }

        val instruction = InstructionEntity(recipeId = 0)

        viewModel.insertRecipeWithInstructionsAndSteps(recipe, instruction, steps)
    }

    private fun initializeDatabase() {
        val databasePath = requireContext().getDatabasePath("recipe_db").absolutePath
        if (File(databasePath).exists()) {
            db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "recipe_db").build()
        } else {
            db = Room.databaseBuilder(requireContext(), AppDatabase::class.java, "recipe_db").build()
        }
    }

    private fun addStep() {
        val inflater = LayoutInflater.from(context)
        val stepView = inflater.inflate(R.layout.item_add_step, binding.stepsContainer, false)
        val stepNumberTextView: TextView = stepView.findViewById(R.id.step_number)
        stepNumberTextView.text = "${getString(R.string.step)} $stepNumber"

        binding.stepsContainer.addView(stepView)
        stepNumber++
    }

    private fun removeStep() {
        if (stepNumber > 1) {
            binding.stepsContainer.removeViewAt(binding.stepsContainer.childCount - 1)
            stepNumber--
        }
    }
}
