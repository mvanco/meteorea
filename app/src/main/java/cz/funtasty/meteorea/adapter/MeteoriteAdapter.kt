package cz.funtasty.meteorea.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import cz.funtasty.meteorea.databinding.FragmentMainItemBinding
import cz.funtasty.meteorea.entity.Meteorite

class MeteoriteAdapter(private val mListener: OnItemClickListener?) : RecyclerView.Adapter<MeteoriteAdapter.ViewHolder>() {

    private val mMeteorites: MutableList<Meteorite?> = mutableListOf()

    interface OnItemClickListener {
        fun onItemClicked(meteorite: Meteorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentMainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var meteorite = mMeteorites[position]
        holder.mBinding.apply {
            data = meteorite
            root.setOnClickListener {
                meteorite?.let { mListener?.onItemClicked(meteorite) }
            }
        }
    }

    override fun getItemCount(): Int {
        return mMeteorites.size
    }

    fun setData(data: List<Meteorite>) {
        mMeteorites.clear()
        mMeteorites.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(internal var mBinding: FragmentMainItemBinding) : RecyclerView.ViewHolder(mBinding.root)
}