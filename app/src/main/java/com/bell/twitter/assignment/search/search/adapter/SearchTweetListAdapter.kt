package com.bell.twitter.assignment.search.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bell.twitter.assignment.R
import com.bell.twitter.assignment.search.search.OnTweetListItemClickListener
import com.bell.twitter.assignment.utils.*
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.android.synthetic.main.item_tweet_row.view.*
import kotlinx.android.synthetic.main.layout_tweet_interaction.view.*

class SearchTweetListAdapter(

    private val context: Context,
    private var tweetsList: MutableList<Tweet>,
    private val listItemCallBack: OnTweetListItemClickListener
) :
    RecyclerView.Adapter<SearchTweetListAdapter.TweetRowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TweetRowViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_tweet_row, parent, false)
        return TweetRowViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return tweetsList.size
    }

    override fun onBindViewHolder(holder: TweetRowViewHolder, position: Int) {
        holder.bind(tweetsList[position])
    }

    inner class TweetRowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(tweet: Tweet) = with(itemView) {
            userAvatar.loadImageFromUrl(tweet.user.profileImageUrl)
            userName.text = tweet.user.name
            twitterHandle.text = "@${tweet.user.screenName}"
            tweetText.text = tweet.text
            tweetText.setOnClickListener {
                listItemCallBack.onTweetItemClick(tweet.id)
            }
            favoriteCount.text = tweet.favoriteCount.toString()
            favoriteIcon.setImageResource(R.drawable.ic_un_favorite)
            retweetCount.text = tweet.retweetCount.toString()
            reTweetIcon.setImageResource(R.drawable.ic_retweet)
            tweetTimeStamp.text = Utils.formatTime(tweet.createdAt)
            mediaImage.apply {
                when {
                    tweet.hasPhoto() -> {
                        visibility = View.VISIBLE
                        loadImageFromUrl(tweet.getImageUrl())
                    }
                    tweet.hasSingleVideo() -> {
                        visibility = View.VISIBLE
                        loadImageFromUrl(tweet.getVideoCoverUrl())
                    }
                    else -> visibility = View.GONE
                }
            }

            setOnClickListener {
                listItemCallBack.onTweetItemClick(tweet.id)
            }
        }
    }
}