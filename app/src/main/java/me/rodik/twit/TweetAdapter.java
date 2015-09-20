package me.rodik.twit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.twitter.sdk.android.core.models.Tweet;

import java.io.InputStream;
import java.util.List;

/**
 * Created by rodik on 8/30/15
 */
public class TweetAdapter extends BaseAdapter {
    private final LayoutInflater inflater;
    private List<Tweet> data;


    public TweetAdapter(Context context, List<Tweet> tweets) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = tweets;
    }

    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Object getItem(int position) {
        return this.data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.data.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        Tweet tweet = this.data.get(position);

        if (convertView == null) {
            view = this.inflater.inflate(R.layout.tweet_image, parent, false);
        } else {
            view = convertView;
        }

        return bindData(view, tweet);
    }

    public static View bindData(View view, Tweet tweet) {
        View viewElement = view.findViewById(R.id.name);
        TextView tv = (TextView)viewElement;
        tv.setText(tweet.user.name);

        viewElement = view.findViewById(R.id.text);
        tv = (TextView)viewElement;
        tv.setText(tweet.text);

        viewElement = view.findViewById(R.id.profile_image);
        new DownloadImageTask((ImageView)viewElement).execute(tweet.user.profileImageUrl);

        if (tweet.entities.media != null && !tweet.entities.media.isEmpty()) {
            viewElement = view.findViewById(R.id.content_image);
            viewElement.setVisibility(View.GONE);
            new DownloadImageTask((ImageView)viewElement).execute(tweet.entities.media.get(0).mediaUrl);
        } else {
            viewElement = view.findViewById(R.id.content_image);
            viewElement.setVisibility(View.GONE);
        }

        return view;
    }


    public static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urlDisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urlDisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            if (bmImage != null) {
                bmImage.setImageBitmap(result);
                bmImage.setVisibility(View.VISIBLE);
            }
        }
    }
}