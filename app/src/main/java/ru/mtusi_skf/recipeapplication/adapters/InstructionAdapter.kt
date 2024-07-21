package ru.mtusi_skf.recipeapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.mtusi_skf.recipeapplication.databinding.ItemInstructionStepsBinding
import ru.mtusi_skf.recipeapplication.model.Step

class InstructionAdapter : RecyclerView.Adapter<InstructionAdapter.InstructionViewHolder>() {

    var steps: List<Step> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class InstructionViewHolder(
        val binding: ItemInstructionStepsBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstructionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInstructionStepsBinding.inflate(inflater, parent, false)
        return InstructionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return steps.size
    }

    override fun onBindViewHolder(holder: InstructionViewHolder, position: Int) {
        val step = steps[position]
        with(holder.binding) {
            stepNumber.text = step.number.toString()
            stepTitle.text = step.step

            val equipmentsAdapter = EquipmentsAdapter()
            val ingredientsAdapter = IngredientsAdapter()

            equipmentRecycler.layoutManager= LinearLayoutManager(
                holder.itemView.context,
                LinearLayoutManager.HORIZONTAL,
                false)
            equipmentRecycler.adapter = equipmentsAdapter
            equipmentsAdapter.equipments = step.equipment

            ingredientsRecycler.layoutManager = LinearLayoutManager(
                holder.itemView.context,
                LinearLayoutManager.HORIZONTAL,
                false)
            ingredientsRecycler.adapter = ingredientsAdapter
            ingredientsAdapter.ingredients = step.ingredients

        }
    }
}