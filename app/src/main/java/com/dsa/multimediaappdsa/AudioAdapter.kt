package com.dsa.multimediaappdsa

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dsa.multimediaappdsa.databinding.MusicItemBinding

class AudioAdapter(
    private val elements: ArrayList<AudioResource>,
    private val onClickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<AudioAdapter.AudioViewHolder>() {

    class AudioViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val binding = MusicItemBinding.bind(view)

        fun render(element: AudioResource, onClickListener: (Int) -> Unit) {

            binding.title.text = element.name
            itemView.setOnClickListener { onClickListener(element.resource) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AudioViewHolder(layoutInflater.inflate(R.layout.music_item, parent, false))
    }

    override fun getItemCount(): Int = elements.size

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        val item = elements[position]
        holder.render(item, onClickListener)
    }
}