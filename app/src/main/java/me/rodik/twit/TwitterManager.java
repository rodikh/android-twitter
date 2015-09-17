package me.rodik.twit;

import android.content.Context;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.UserTimeline;

import io.fabric.sdk.android.Fabric;

/**
 * Created by rodik on 8/25/15
 */
public class TwitterManager {

    public TwitterManager(Context context) {
        String TWITTER_KEY = context.getString(R.string.twitter_key);
        String TWITTER_SECRET = context.getString(R.string.twitter_secret);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(context, new Twitter(authConfig));
    }

    public void getUserTweets(String userName, Callback<TimelineResult<Tweet>> callback) {
        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(userName)
                .maxItemsPerRequest(30)
                .build();

        userTimeline.next(null, callback);
    }
}
