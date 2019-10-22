package com.bell.twitter.assignment.details.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bell.twitter.assignment.R
import com.bell.twitter.assignment.details.TweetDetailsActivity.Companion.TWEET_EXTRA_KEY
import com.bell.twitter.assignment.utils.Constants.Companion.MEDIA_TYPE_IMAGE
import com.bell.twitter.assignment.utils.Constants.Companion.MEDIA_TYPE_VIDEO
import com.bell.twitter.assignment.utils.*
import com.twitter.sdk.android.core.models.Tweet
import kotlinx.android.synthetic.main.fragment_tweet_details.*
import kotlinx.android.synthetic.main.layout_tweet_interaction.*

class TweetDetailsFragment : Fragment(), TweetDetailsContract.View {

    private lateinit var interactor: TweetDetailsContract.Interactor
    private lateinit var router: TweetDetailsContract.Router
    private lateinit var presenter: TweetDetailsContract.Presenter
    private var tweetId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tweetId = it.get(TWEET_EXTRA_KEY) as Long
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tweet_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        interactor = TweetDetailsInteractor()
        router = TweetDetailsRouter(context)
        presenter = TweetDetailsPresenter(this, interactor, router)
        presenter.onInitializeRequested()

        if (tweetId > 0) {
            presenter.showTweet(tweetId)
        }
    }

    override fun showUpdatedTweet(tweet: Tweet) {
        userAvatar.loadImageFromUrl(tweet.user.profileImageUrl)
        userName.text = tweet.user.name
        twitterHandle.text = "@${tweet.user.screenName}"
        tweetText.text = tweet.text
        favoriteCount.text = tweet.favoriteCount.toString()
        retweetCount.text = tweet.retweetCount.toString()
        tweetTimeStamp.text = Utils.formatTime(tweet.createdAt)
        if (tweet.favorited) {
            favoriteIcon.setImageResource(R.drawable.ic_favorite)
        } else {
            favoriteIcon.setImageResource(R.drawable.ic_un_favorite)
        }

        if (tweet.retweeted) {
            reTweetIcon.setImageResource(R.drawable.ic_retweeted)
        } else {
            reTweetIcon.setImageResource(R.drawable.ic_retweet)
        }

        favoriteIcon.setOnClickListener {
            if (tweet.favorited)
                presenter.markTweetUnFavorite(tweet.id)
            else
                presenter.markTweetFavorite(tweet.id)
        }

        reTweetIcon.setOnClickListener {
            if (tweet.retweeted)
                presenter.markReTweetUndo(tweet.id)
            else
                presenter.markReTweet(tweet.id)
        }

        mediaImage.apply {
            when {
                tweet.hasPhoto() -> {
                    visibility = View.VISIBLE
                    loadImageFromUrl(tweet.getImageUrl())
                    setOnClickListener {
                        presenter.openViewMediaPage(MEDIA_TYPE_IMAGE, tweet.getImageUrl(), "")
                    }
                }
                tweet.hasSingleVideo() -> {
                    visibility = View.VISIBLE
                    loadImageFromUrl(tweet.getVideoCoverUrl())
                    setOnClickListener {
                        val pair = tweet.getVideoUrl()
                        val url = pair.first
                        val contentType = pair.second
                        presenter.openViewMediaPage(MEDIA_TYPE_VIDEO, url, contentType)
                    }
                }
                else -> visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onCleanUpRequested()
    }

    companion object {
        @JvmStatic
        fun newInstance(tweetId: Long) = TweetDetailsFragment()
            .apply {
                arguments = Bundle().apply {
                    putLong(TWEET_EXTRA_KEY, tweetId)
                }
            }
    }
}