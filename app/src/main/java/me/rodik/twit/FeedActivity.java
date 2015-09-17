package me.rodik.twit;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;

import java.util.List;

public class FeedActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TwitterManager twitterManager = new TwitterManager(FeedActivity.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        findViewById(android.R.id.empty).setVisibility(View.GONE);
        findViewById(android.R.id.list).setVisibility(View.GONE);

        twitterManager.getUserTweets(message, new Callback<TimelineResult<Tweet>>() {
            @Override
            public void success(Result<TimelineResult<Tweet>> result) {
                findViewById(R.id.loading_spinner).setVisibility(View.GONE);
                List<Tweet> items = result.data.items;
                if (items.isEmpty()) {
                    findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
                } else {
                    TweetAdapter adapter = new TweetAdapter(FeedActivity.this, items);
                    setListAdapter(adapter);
                    findViewById(android.R.id.list).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void failure(TwitterException e) {
                findViewById(R.id.loading_spinner).setVisibility(View.GONE);
                TextView view = (TextView) findViewById(android.R.id.empty);
                view.setVisibility(View.VISIBLE);
                view.setText(R.string.api_error);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
