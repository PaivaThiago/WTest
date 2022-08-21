package paiva.thiago.wtest.app.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import paiva.thiago.wtest.app.database.entity.PostalCode
import paiva.thiago.wtest.databinding.ViewItemPostalCodeBinding

class PostalCodeViewHolder(private val view: ViewItemPostalCodeBinding) :
    RecyclerView.ViewHolder(view.root) {
    fun bind(postalCode: PostalCode) {
        view.postalCode.text = postalCode.codeFullName
    }
}

class PostalCodeAdapter(diffCallback: DiffUtil.ItemCallback<PostalCode>) :
    PagingDataAdapter<PostalCode, PostalCodeViewHolder>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostalCodeViewHolder {
        return PostalCodeViewHolder(
            ViewItemPostalCodeBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PostalCodeViewHolder, position: Int) {
        getItem(position)?.let { postalCode ->
            holder.bind(postalCode)
        }
    }
}

object PostalCodeComparator : DiffUtil.ItemCallback<PostalCode>() {
    override fun areItemsTheSame(oldItem: PostalCode, newItem: PostalCode): Boolean {
        return oldItem.code == newItem.code && oldItem.codeExt == newItem.codeExt
    }

    override fun areContentsTheSame(oldItem: PostalCode, newItem: PostalCode): Boolean {
        return oldItem == newItem
    }
}