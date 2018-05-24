package com.amazonaws.demo.appsync;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.AppSyncSubscriptionCall;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.api.Subscription;
import com.apollographql.apollo.exception.ApolloException;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class ListEventsActivity extends AppCompatActivity {

    private static final String TAG = ListEventsActivity.class.getSimpleName();

    private AWSAppSyncClient mAWSAppSyncClient;
    private AppSyncSubscriptionCall<NewCommentOnEpisodeSubscription.Data> subscriptionWatcher;
    private AppSyncSubscriptionCall<NewCommentsOnEpisodeSubscription.Data> subscriptionWatcher2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        NewCommentOnEpisodeSubscription subscription = NewCommentOnEpisodeSubscription.builder().episode_id(1).build();
        subscriptionWatcher = ClientFactory.getInstance(this.getApplicationContext()).subscribe(subscription);
        subscriptionWatcher.execute(new AppSyncSubscriptionCall.Callback<NewCommentOnEpisodeSubscription.Data>() {
            @Override
            public void onResponse(@Nonnull final Response<NewCommentOnEpisodeSubscription.Data> response) {
                Log.d("-------------", response.data().toString());
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.d("-------------", "fail", e);
            }

            @Override
            public void onCompleted() {
                Log.d("-------------", "completed");
            }
        });

        NewCommentsOnEpisodeSubscription subscription2 = NewCommentsOnEpisodeSubscription.builder().build();
        subscriptionWatcher2 = ClientFactory.getInstance(this.getApplicationContext()).subscribe(subscription2);
        subscriptionWatcher2.execute(new AppSyncSubscriptionCall.Callback<NewCommentsOnEpisodeSubscription.Data>() {
            @Override
            public void onResponse(@Nonnull final Response<NewCommentsOnEpisodeSubscription.Data> response) {
                Log.d("-------------", response.data().toString());
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
                Log.d("-------------", "fail", e);
            }

            @Override
            public void onCompleted() {
                Log.d("-------------", "completed");
            }
        });
    }

    @Override
    protected void onStop() {
        if (subscriptionWatcher != null) {
            subscriptionWatcher.cancel();
        }
        if (subscriptionWatcher2 != null) {
            subscriptionWatcher2.cancel();
        }
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
