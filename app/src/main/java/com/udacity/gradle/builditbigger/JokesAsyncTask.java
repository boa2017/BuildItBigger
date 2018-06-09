package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.jokeApi.JokeApi;

import java.io.IOException;

class JokesAsyncTask extends AsyncTask<String, Void, String> {
    private static JokeApi mJokeApi;
    private JokesAsyncTaskCallback jokesAsyncTaskCallback;

    public JokesAsyncTask(JokesAsyncTaskCallback callback) {
        this.jokesAsyncTaskCallback = callback;
    }

    @Override
    protected String doInBackground(String... param) {
        if (mJokeApi == null) {  // Only do this once
            JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://192.168.1.87/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            mJokeApi = builder.build();
        }

        try {
            return mJokeApi.getJoke().execute().getJoke();
        } catch (IOException e) {
            Log.e("JokesAsyncTask", e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (jokesAsyncTaskCallback != null) {
            jokesAsyncTaskCallback.onResult(result);
        }
    }

    interface JokesAsyncTaskCallback {
        void onResult(String result);
    }
}