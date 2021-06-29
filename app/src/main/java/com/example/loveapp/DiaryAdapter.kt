package com.example.fragment

import android.app.AlertDialog
import android.net.sip.SipSession
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.loveapp.Diary
import com.example.loveapp.R
import java.io.File
import java.text.SimpleDateFormat

class DiaryAdapter(private val fileList: List<Diary>,
                   private val listener : OnItemClickListener
): RecyclerView.Adapter<DiaryAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val itemView =LayoutInflater.from(parent.context).inflate(
            R.layout.item_recycler,
            parent, false)

        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val diary = fileList[position]
        holder.Title.text = diary.Title
        holder.Text.text = diary.text
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    inner class Holder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val Title : TextView = itemView.findViewById(R.id.etitle)
        val Text : TextView = itemView.findViewById(R.id.etext)
        init {
            itemView.findViewById<ImageButton>(R.id.deleteButton).setOnClickListener(this)
            itemView.findViewById<CardView>(R.id.cardView).setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position : Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
            when(v?.id) {
                R.id.deleteButton -> listener.onButtonClick(position)
                R.id.cardView -> listener.onItemClick(position)
                }
            }
        }
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onButtonClick(position: Int)
    }
}