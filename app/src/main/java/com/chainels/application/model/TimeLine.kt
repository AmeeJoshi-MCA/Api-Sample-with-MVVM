package com.chainels.application.model

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.chainels.application.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.Serializable

data class TimeLine(
    val account: Account,
    val attachments: List<Attachment>,
    val calendar_only: Boolean,
    val can_register: Boolean,
    val channel: Channel,
    val channel_id: String,
    val company: Company,
    val content: String?,
    val created_at: String,
    val date: String,
    val embeds: List<Any>,
    val end_date: String,
    val end_time: String,
    val follow_count: Int,
    val has_replied: Boolean,
    val id: Long,
    val in_group_id: String,
    val interest_count: Int,
    val is_following: Boolean,
    var is_interested: Boolean,
    val is_present: Boolean,
    val is_private: Boolean,
    val is_replies_disabled: Boolean,
    val issue_time: String,
    val issue_type: IssueType,
    val last_activity: String,
    val location: Location,
    val location_string: String,
    val msg_type: String,
    val place: String,
    val present_count: Int,
    val publish_status: String,
    val reply_count: Int,
    val score: Long,
    val start_time: String,
    val status: String,
    val survey: Survey,
    val targets: Targets,
    val title: String
) : Serializable {
    data class Account(
        val active_company: String,
        val contact: Contact,
        val email: String,
        val first_name: String,
        val function: String,
        val functions: List<Function>,
        val has_app: Boolean,
        val id: String,
        val image: Image,
        val last_name: String,
        val locale: String,
        val name: String,
        val rights: String,
        val timezone: String,
        val visibility: String
    ) : Serializable {
        data class Contact(
            val remark: String
        ) : Serializable

        data class Function(
            val company_id: String,
            val function: String
        ) : Serializable

        data class Image(
            val created_at: String,
            val id: String,
            val mime_type: String,
            val name: String,
            val resource_type: String,
            val size: Int,
            val url: String
        ) : Serializable
    }

    data class Attachment(
        val created_at: String,
        val id: String,
        val mime_type: String,
        val name: String,
        val resource_type: String,
        val size: Int,
        val url: String
    ) : Serializable

    data class Channel(
        val can_unfollow: Boolean,
        val can_write: Boolean,
        val community_id: String,
        val default_target_id: String,
        val description: String,
        val draft_count: Int,
        val follow_count: Int,
        val id: String,
        val image: Image,
        val is_following: Boolean,
        val is_new: Boolean,
        val is_replies_disabled: Boolean,
        val is_system: Boolean,
        val limit_target_id: String,
        val message_types: List<String>,
        val name: String,
        val new_content_count: Int,
        val scheduled_count: Int
    ) : Serializable {
        data class Image(
            val created_at: String,
            val id: String,
            val mime_type: String,
            val name: String,
            val resource_type: String,
            val size: Int,
            val url: String
        ) : Serializable
    }

    data class Company(
        val address_line: String,
        val entity_type: String,
        val group: Boolean,
        val id: String,
        val is_dwelling: Boolean,
        val logo: String,
        val logo_resource: LogoResource,
        val logo_resource_square: LogoResourceSquare,
        val logo_thumbnail: String,
        val name: String
    ) : Serializable {
        data class LogoResource(
            val created_at: String,
            val id: String,
            val mime_type: String,
            val name: String,
            val resource_type: String,
            val size: Int,
            val url: String
        ) : Serializable

        data class LogoResourceSquare(
            val created_at: String,
            val id: String,
            val mime_type: String,
            val name: String,
            val resource_type: String,
            val size: Int,
            val url: String
        ) : Serializable
    }

    data class IssueType(
        val category: String,
        val id: String,
        val is_alert: Boolean,
        val name: String,
        val status_enabled: Boolean,
        val targets: Targets
    ) : Serializable {
        data class Targets(
            val account_count: Int,
            val community_id: String,
            val count: Int,
            val description: String,
            val entity_counts: EntityCounts,
            val id: String,
            val is_default: Boolean,
            val is_favorite: Boolean,
            val is_private: Boolean,
            val name: String
        ) : Serializable {
            data class EntityCounts(
                val community: Int,
                val company: Int
            ) : Serializable
        }
    }

    data class Location(
        val coordinates: List<Double>,
        val type: String
    ) : Serializable

    data class Survey(
        val close_date: String,
        val question_count: Int,
        val results_public: Boolean,
        val submission_count: Int,
        val user_type: String
    ) : Serializable

    data class Targets(
        val account_count: Int,
        val community_id: String,
        val count: Int,
        val description: String,
        val entity_counts: EntityCounts,
        val id: String,
        val is_default: Boolean,
        val is_favorite: Boolean,
        val is_private: Boolean,
        val name: String
    ) : Serializable {
        data class EntityCounts(
            val community: Int,
            val company: Int
        ) : Serializable
    }
}

@BindingAdapter("app:picture", "app:progress")
fun setPicture(image: ImageView, imageUrl: String?, progress: ProgressBar) {
    progress.visibility = View.VISIBLE
    Picasso.get().load(imageUrl)
        .into(image, object : Callback {
            override fun onSuccess() {
                progress.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
                progress.visibility = View.GONE
            }
        })
}

@BindingAdapter("app:pictureLike")
fun setTimelineLikeImage(image: ImageView, imageStatus: Boolean) {
    if (imageStatus) {
        image.setImageResource(R.drawable.ic_fav_fill)
    } else {
        image.setImageResource(R.drawable.ic_fav_outline)
    }
}
