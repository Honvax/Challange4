package com.alfrsms.challange4.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alfrsms.challange4.data.local.note.NoteEntity
import com.alfrsms.challange4.databinding.ItemListBinding

class HomeAdapter(private val listener: HomeItemClickListener) :
    RecyclerView.Adapter<HomeAdapter.NoteViewHolder>() {

    private var items: MutableList<NoteEntity> = mutableListOf()

    fun setItems(items: List<NoteEntity>) {
        clearItems()
        addItems(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<NoteEntity>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun editItem(item: NoteEntity, position: Int) {
        this.items[position] = item
        notifyDataSetChanged()
    }

    fun clearItems() {
        this.items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class NoteViewHolder(
        private val binding: ItemListBinding,
        private val listener: HomeItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: NoteEntity) {
            with(item) {
                binding.noteEntity = item

                itemView.setOnClickListener { listener.onItemClicked(this) }
                binding.btnEdit.setOnClickListener { listener.onEditClicked(item, adapterPosition) }
                binding.btnDelete.setOnClickListener { listener.onDeleteClicked(this) }
            }
        }
    }

}

interface HomeItemClickListener {
    fun onItemClicked(item: NoteEntity)
    fun onEditClicked(item: NoteEntity, position: Int)
    fun onDeleteClicked(item: NoteEntity)
}