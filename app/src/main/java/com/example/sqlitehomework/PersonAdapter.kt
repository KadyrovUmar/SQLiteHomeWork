package com.example.sqlitehomework

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlitehomework.databinding.ItemBinding

class PersonAdapter(): RecyclerView.Adapter<PersonAdapter.Holder>() {

    var personList: List<Person> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        with(holder) {
            bind(personList[position])
        }
    }

    override fun getItemCount(): Int = personList.size


    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemBinding.bind(view)
        fun bind(person: Person) {
            person.apply {
                binding.textView.text = person.name
                binding.textView2.text = person.age.toString()
            }
        }

    }
}