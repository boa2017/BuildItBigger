package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.udacity.gradle.builditbigger.backend.jokeApi.JokeApi;

import java.io.IOException;

class JokesAsyncTask extends AsyncTask<String, Void, String> {
    private static JokeApi mJokeApi;
    private TaskFinishedCallback callback;

    public JokesAsyncTask(TaskFinishedCallback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(String... param) {
        if (mJokeApi == null) {  // Only do this once
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/");

            mJokeApi = builder.build();
        }

        try {
            return mJokeApi.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();

        }
    }

    @Override
    protected void onPostExecute(String joke) {
        callback.onTaskFinished(joke);
    }
}

