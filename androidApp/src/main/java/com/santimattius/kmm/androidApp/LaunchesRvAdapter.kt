package com.santimattius.kmm.androidApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.santimattius.kmm.androidApp.databinding.ItemLaunchBinding
import com.santimattius.kmm.shared.entity.RocketLaunch

class LaunchesRvAdapter(var launches: List<RocketLaunch>) :
    RecyclerView.Adapter<LaunchesRvAdapter.LaunchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_launch, parent, false)
            .run(::LaunchViewHolder)
    }

    override fun getItemCount(): Int = launches.count()

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.bindData(launches[position])
    }

    inner class LaunchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val viewBinding = ItemLaunchBinding.bind(itemView)

        fun bindData(launch: RocketLaunch) = with(viewBinding) {
            val ctx = itemView.context
            missionName.text =
                ctx.getString(R.string.mission_name_field, launch.missionName)
            launchYear.text =
                ctx.getString(R.string.launch_year_field, launch.launchYear.toString())
            details.text =
                ctx.getString(R.string.details_field, launch.details ?: "")
            val success = launch.launchSuccess
            if (success != null) {
                if (success) {
                    launchSuccess.text = ctx.getString(R.string.successful)
                    launchSuccess.setTextColor(
                        (ContextCompat.getColor(
                            itemView.context,
                            R.color.colorSuccessful
                        ))
                    )
                } else {
                    launchSuccess.text = ctx.getString(R.string.unsuccessful)
                    launchSuccess.setTextColor(
                        (ContextCompat.getColor(
                            itemView.context,
                            R.color.colorUnsuccessful
                        ))
                    )
                }
            } else {
                launchSuccess.text = ctx.getString(R.string.no_data)
                launchSuccess.setTextColor(
                    (ContextCompat.getColor(
                        itemView.context,
                        R.color.colorNoData
                    ))
                )
            }
        }
    }
}