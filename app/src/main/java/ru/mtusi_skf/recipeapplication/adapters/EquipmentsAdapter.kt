package ru.mtusi_skf.recipeapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.mtusi_skf.recipeapplication.R
import ru.mtusi_skf.recipeapplication.databinding.ItemInstructionStepsItemsBinding
import ru.mtusi_skf.recipeapplication.model.Equipment

class EquipmentsAdapter : RecyclerView.Adapter<EquipmentsAdapter.EquipmentsViewHolder>() {

    var equipments: List<Equipment> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class EquipmentsViewHolder(
        val binding: ItemInstructionStepsItemsBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemInstructionStepsItemsBinding.inflate(inflater, parent, false)
        return EquipmentsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return equipments.size
    }

    override fun onBindViewHolder(holder: EquipmentsViewHolder, position: Int) {
        val equipment = equipments[position]
        with(holder.binding) {
            titleInstructionStepsItems.text = equipment.name
            if (equipment.image.isNotBlank()) {
                Picasso.get().load(equipment.image).into(imageInstructionStepsItems)
            } else {
                imageInstructionStepsItems.setImageResource(R.drawable.ic_image_button_select)
            }
        }
    }
}