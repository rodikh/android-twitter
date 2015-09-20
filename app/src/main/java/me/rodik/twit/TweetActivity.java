package me.rodik.twit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twitter.sdk.android.core.models.Tweet;

public class TweetActivity extends Activity {

    private LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Intent intent = getIntent();
        int item_id = intent.getIntExtra(getString(R.string.item_id), 0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        Tweet tweet = FeedActivity.items.get(item_id);

        View view = findViewById(R.id.view_tweet);
        View tweetView = this.inflater.inflate(R.layout.tweet_image, (ViewGroup) view, false);
        ((ViewGroup) view).addView(tweetView);
        TweetAdapter.bindData(tweetView, tweet);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
