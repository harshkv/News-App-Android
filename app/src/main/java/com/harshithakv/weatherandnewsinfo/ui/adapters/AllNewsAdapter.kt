package com.harshithakv.weatherandnewsinfo.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harshithakv.openweathernews.models.Article
import com.harshithakv.weatherandnewsinfo.R
import com.harshithakv.weatherandnewsinfo.models.ArticleTop
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.each_item.view.*
import java.util.ArrayList

class AllNewsAdapter(mdata: ArrayList<ArticleTop>, onNewsListener: OnNewsListener ) :
    RecyclerView.Adapter<AllNewsAdapter.viewHolder>() {
    var mdataAll: ArrayList<ArticleTop>
    var onNewsListener: OnNewsListener?= null

    init {
        this.mdataAll = mdata
        this.onNewsListener = onNewsListener

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): viewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.all_news, parent, false)
        return viewHolder(view, onNewsListener!!)
    }

    override fun onBindViewHolder(
        holder: viewHolder,
        position: Int
    ) {
        var article: ArticleTop = mdataAll[position]
        holder.tv_source.text = article.author
        holder.tv_article.text = article.title
        Picasso.get().load(article.urlToImage).resize(300, 300).centerCrop().into(holder.iv_newsImg)
        holder.url = article.url

    }

    override fun getItemCount(): Int {
        return mdataAll.size
    }

    inner class viewHolder(itemView: View, onNewsListener: OnNewsListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var tv_article: TextView
        var tv_source: TextView
        var iv_newsImg: ImageView
        var url:String? = null
        var onNewsListener: OnNewsListener?= null

        init {
            tv_article = itemView.tv_article
            tv_source = itemView.tv_source
            iv_newsImg = itemView.iv_newsImg
            this.onNewsListener = onNewsListener
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onNewsListener?.onNewsClick(adapterPosition)
        }
    }

    interface OnNewsListener{
        fun onNewsClick(position:Int)
    }
}



