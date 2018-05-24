package com.amazonaws.demo.appsync;

import android.content.Context;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.auth.STSAssumeRoleSessionCredentialsProvider;
import com.amazonaws.auth.STSSessionCredentialsProvider;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.amazonaws.mobileconnectors.appsync.sigv4.BasicAPIKeyAuthProvider;
import com.amazonaws.regions.Regions;
import com.apollographql.apollo.fetcher.ResponseFetcher;
import com.apollographql.apollo.interceptor.ApolloInterceptor;
import com.apollographql.apollo.internal.ApolloLogger;

public class ClientFactory {
    private static volatile AWSAppSyncClient client;

    public synchronized static AWSAppSyncClient getInstance(Context context) {
        if (client == null) {
            client = AWSAppSyncClient.builder()
                    .context(context)
                    .credentialsProvider(new CognitoCachingCredentialsProvider(
                            context.getApplicationContext(),
                            "",
                            Regions.AP_NORTHEAST_1
                    ))
                    .region(Constants.APPSYNC_REGION)
                    .serverUrl(Constants.APPSYNC_ENDPOINT)
                    .build();
        }
        return client;
    }
}
