package me.rodik.twit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    public final static String EXTRA_MESSAGE = "me.rodik.twit.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, FeedActivity.class);
        String message;
        if (view.getTag() != null && view.getTag().toString().equals("link")) {
            TextView v = (TextView) view;
            message = v.getText().toString();
            if (message.charAt(0) == '@') {
                message = message.substring(1);
            }
        } else {
            EditText editText = (EditText) findViewById(R.id.edit_message);
            message = editText.getText().toString();
        }
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
