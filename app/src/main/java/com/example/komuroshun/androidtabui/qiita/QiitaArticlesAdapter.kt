package com.example.komuroshun.androidtabui.qiita

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.komuroshun.androidtabui.data.source.remote.QitaArticle
import com.example.komuroshun.androidtabui.databinding.ItemQitaArticleBinding

class QiitaArticlesAdapter(val dataList: List<QitaArticle>):
        RecyclerView.Adapter<QiitaArticlesAdapter.BindingHolder>() {
    lateinit var mListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        setOnItemClickListener(mListener)
        val layoutInflater = LayoutInflater.from(parent!!.context)
        val binding = ItemQitaArticleBinding.inflate(layoutInflater, parent, false)
        return BindingHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val data = dataList[position]
        holder.binding.setItem(data)
        holder.binding.originalLinearLayout.setOnClickListener(object: View.OnClickListener {
            override fun onClick(view: View?) {
                mListener.onClick(view, data)
            }
        })
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    interface OnItemClickListener {
        fun onClick(view: View?, data: QitaArticle)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.mListener = listener
    }

    class BindingHolder(val binding: ItemQitaArticleBinding): RecyclerView.ViewHolder(binding.root)
}