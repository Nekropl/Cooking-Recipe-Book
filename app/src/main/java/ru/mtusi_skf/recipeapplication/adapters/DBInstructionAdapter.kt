package ru.mtusi_skf.recipeapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.mtusi_skf.recipeapplication.data.local.entity.StepEntity
import ru.mtusi_skf.recipeapplication.databinding.ItemSavedInstructionStepsBinding

class DBInstructionAdapter: RecyclerView.Adapter<DBInstructionAdapter.DBInstructionViewHolder>() {

    var steps: List<StepEntity> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class DBInstructionViewHolder(
        val binding: ItemSavedInstructionStepsBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DBInstructionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSavedInstructionStepsBinding.inflate(inflater, parent, false)
        return DBInstructionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return steps.size
    }

    override fun onBindViewHolder(holder: DBInstructionViewHolder, position: Int) {
        val step = steps[position]
        with(holder.binding) {
            stepNumber.text = step.number.toString()
            stepTitle.text = step.stepTitle
            equipment.text = step.equipments
            ingredients.text = step.ingredients
        }
    }
}