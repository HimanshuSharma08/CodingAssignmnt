package com.accenture.codingassignment.adapter

import android.content.Context
import android.graphics.Bitmap
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.accenture.codingassignment.R
import com.accenture.codingassignment.databinding.ListItemBinding
import com.accenture.codingassignment.model.MainActivivityAdapterListModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target

class MainActivityRecyclerAdapter(private val context: Context) :
    RecyclerView.Adapter<MainActivityRecyclerAdapter.MainActivityViewHolder>() {

    protected var list: List<MainActivivityAdapterListModel> = ArrayList()

    fun setDataList(list: List<MainActivivityAdapterListModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        val rowListItemBiniding =
            DataBindingUtil.inflate<ListItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.list_item,
                parent,
                false
            )
        return MainActivityViewHolder(rowListItemBiniding)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        holder.bind(list.get(position))
    }

    class MainActivityViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MainActivivityAdapterListModel) {
            binding.item = item
            if (!TextUtils.isEmpty(item.imageHref))
                Glide.with(binding.root.context).asBitmap().load(item.imageHref)
                    .apply(RequestOptions())
                    .addListener(object : RequestListener<Bitmap> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            e?.printStackTrace()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Bitmap?,
                            model: Any?,
                            target: Target<Bitmap>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.image.setImageBitmap(resource)
                            return false
                        }
                    }).submit()
        }
    }
}