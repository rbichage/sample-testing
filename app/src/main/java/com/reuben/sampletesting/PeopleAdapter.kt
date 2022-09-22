package com.reuben.sampletesting

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.reuben.sampletesting.data.Person
import com.reuben.sampletesting.databinding.ItemPeopleBinding

class PeopleAdapter : ListAdapter<Person, PeopleAdapter.PeopleViewHolder>(peopleDiffer) {

    inner class PeopleViewHolder(
        private val binding: ItemPeopleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(person: Person) {
            with(binding) {
                tvPersonName.text = "${person.firstName} ${person.lastName}"
                tvAge.text = "Age: ${person.age}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        return PeopleViewHolder(
            ItemPeopleBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private val peopleDiffer = object : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {

        val old = oldItem.firstName + " " + oldItem.lastName
        val new = newItem.firstName + " " + newItem.lastName
        return old.contentEquals(new, false)
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }

}