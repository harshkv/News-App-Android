package com.harshithakv.openweathernews.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.harshithakv.openweathernews.models.Article
import com.harshithakv.weatherandnewsinfo.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.each_item.view.*
import java.util.*


class TopNewsAdapter(mdata: ArrayList<Article>, onNewsListener: OnNewsListener ) :
    RecyclerView.Adapter<TopNewsAdapter.viewHolder>() {
    var mdata: ArrayList<Article>
    var onNewsListener: OnNewsListener?= null

    init {
        this.mdata = mdata
        this.onNewsListener = onNewsListener

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): viewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.each_item, parent, false)
        return viewHolder(view, onNewsListener!!)
    }

    override fun onBindViewHolder(
        holder: viewHolder,
        position: Int
    ) {
        var article: Article = mdata[position]
        holder.tv_source.text = article.author
        holder.tv_article.text = article.title
        Picasso.get().load(article.urlToImage).resize(300, 300).centerCrop().into(holder.iv_newsImg)
        holder.url = article.url

    }

    override fun getItemCount(): Int {
        return mdata.size
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



