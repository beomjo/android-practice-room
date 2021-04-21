package com.beomjo.codelab.room.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.beomjo.codelab.room.R
import com.beomjo.codelab.room.persistence.entity.Word

class WordListAdapter : RecyclerView.Adapter<WordListAdapter.WordViewHolder>() {

    private val items = mutableListOf<Word>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addList(words: List<Word>) {
        val diffCallback = WordsComparator(items, words)
        val result = DiffUtil.calculateDiff(diffCallback)
        items.clear()
        items.addAll(words)
        result.dispatchUpdatesTo(this)
    }

    class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val wordItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(word: Word) {
            wordItemView.text = word.word
        }

        companion object {
            fun create(parent: ViewGroup): WordViewHolder {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
                return WordViewHolder(view)
            }
        }
    }

    class WordsComparator(
        private val oldItems: List<Word>,
        private val newItems: List<Word>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItems.size
        }

        override fun getNewListSize(): Int {
            return newItems.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].word == newItems[newItemPosition].word
        }
    }
}