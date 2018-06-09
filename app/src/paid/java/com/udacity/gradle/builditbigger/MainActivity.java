package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.tellJokesActivity.TellJokesActivity;

public class MainActivityFragment extends AppCompatActivity implements TaskFinishedCallback {

    private static final String JOKE_KEY = "joke";
    private ProgressBar progressBar;
    private RelativeLayout mainLay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.loading_indicator);
        mainLay = (RelativeLayout) findViewById(R.id.main_lay);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        showMainLay();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        showLoadingIndicator();
        new JokesAsyncTask(this).execute();
    }


    @Override
    public void onTaskFinished(String result) {
        Context context = getApplicationContext();
        Intent intent = new Intent(context, TellJokesActivity.class);
        intent.putExtra(JOKE_KEY, result);
        context.startActivity(intent);
    }

    private void showMainLay() {
        progressBar.setVisibility(View.INVISIBLE);
        mainLay.setVisibility(View.VISIBLE);
    }

    private void showLoadingIndicator() {
        progressBar.setVisibility(View.VISIBLE);
        mainLay.setVisibility(View.INVISIBLE);
    }
}
