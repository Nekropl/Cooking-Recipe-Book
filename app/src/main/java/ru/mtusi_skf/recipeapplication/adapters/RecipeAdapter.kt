package ru.mtusi_skf.recipeapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.mtusi_skf.recipeapplication.databinding.ItemInstructionBinding
import ru.mtusi_skf.recipeapplication.model.InstructionResponse

class RecipeAdapter : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    var instructions: List<InstructionResponse> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class RecipeViewHolder(
        val binding: ItemInstructionBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInstructionBinding.inflate(inflater, parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return instructions.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val instruction = instructions[position]
        with(holder.binding) {
            val instructionAdapter = InstructionAdapter()
            instructionsStepsRecycler.layoutManager = LinearLayoutManager(holder.itemView.context)
            instructionsStepsRecycler.adapter = instructionAdapter
            instructionsStepsRecycler.setHasFixedSize(true)
            instructionAdapter.steps = instruction.steps
        }
    }
}