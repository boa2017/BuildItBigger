package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.tellJokesActivity.TellJokesActivity;

public class MainActivityFragment extends Fragment {
    private ProgressBar mProgressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        mProgressBar = root.findViewById(R.id.progressBar);

        root.findViewById(R.id.button_jokes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);

                new JokesAsyncTask(new JokesAsyncTask.JokesAsyncTaskCallback() {
                    @Override
                    public void onResult(String result) {
                        mProgressBar.setVisibility(View.GONE);
                        if (result != null) {
                            showJokeActivity(result);
                        } else {
                            Toast.makeText(getActivity(), R.string.failed_loading_joke, Toast.LENGTH_SHORT).show();
                        }
                    }
                }).execute();
            }
        });
        return root;
    }

    private void showJokeActivity(String joke) {
        Intent intent = new Intent(getActivity(), TellJokesActivity.class);
        intent.putExtra(TellJokesActivity.EXTRA_JOKE, joke);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intent);
    }
}

