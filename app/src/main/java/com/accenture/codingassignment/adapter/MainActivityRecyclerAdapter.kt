package com.accenture.codingassignment.adapter

import android.graphics.Bitmap
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.accenture.codingassignment.R
import com.accenture.codingassignment.databinding.RowMainAdapterItemBinding
import com.accenture.codingassignment.model.MainActivivityAdapterListModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target


/* This class is used to inflated rows of list data.
 * @MainActivityViewHolder it creates new view for each item of list and hold its reference.
*/
class MainActivityRecyclerAdapter() :
    RecyclerView.Adapter<MainActivityRecyclerAdapter.MainActivityViewHolder>() {

    // Initialize list of MainActivivityAdapterListModel.
    protected var list: List<MainActivivityAdapterListModel> = ArrayList()

    // Update list with Server data list and notify Adapter View's to change accordingly.
    fun setDataList(list: List<MainActivivityAdapterListModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    // This is used to create new View of MainActivityViewHolder Type.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainActivityViewHolder {
        val rowListItemBiniding =
            DataBindingUtil.inflate<RowMainAdapterItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.row_main_adapter_item,
                parent,
                false
            )
        return MainActivityViewHolder(rowListItemBiniding)

    }

    // This is to notify how many rows will be there to create.
    override fun getItemCount(): Int {
        return list.size
    }

    // Update each row item on basis of position in list and view in adapter data based on that position.
    override fun onBindViewHolder(holder: MainActivityViewHolder, position: Int) {
        holder.bind(list.get(position))
    }


    // this is used to create seprate view to each list data
    class MainActivityViewHolder(val binding: RowMainAdapterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MainActivivityAdapterListModel) {
            binding.item = item
            if (!TextUtils.isEmpty(item.imageHref))
                // Load image
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