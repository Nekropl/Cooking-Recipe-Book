package ru.mtusi_skf.recipeapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.mtusi_skf.recipeapplication.data.local.dao.InstructionsWithSteps
import ru.mtusi_skf.recipeapplication.databinding.ItemInstructionBinding

class DBRecipeAdapter : RecyclerView.Adapter<DBRecipeAdapter.DBRecipeViewHolder>() {

    var instructions: List<InstructionsWithSteps> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class DBRecipeViewHolder(
        val binding: ItemInstructionBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DBRecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInstructionBinding.inflate(inflater, parent, false)
        return DBRecipeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return instructions.size
    }

    override fun onBindViewHolder(holder: DBRecipeViewHolder, position: Int) {
        val instruction = instructions[position]
        with(holder.binding) {
            val instructionAdapter = DBInstructionAdapter()
            instructionsStepsRecycler.layoutManager = LinearLayoutManager(holder.itemView.context)
            instructionsStepsRecycler.adapter = instructionAdapter
            instructionAdapter.steps = instruction.steps
        }
    }

}