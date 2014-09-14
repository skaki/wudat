package com.wudat.android;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

// leeloo oAuth lib https://bitbucket.org/smartproject/oauth-2.0/wiki/Home
import net.smartam.leeloo.client.OAuthClient;
import net.smartam.leeloo.client.URLConnectionClient;
import net.smartam.leeloo.client.request.OAuthClientRequest;
import net.smartam.leeloo.client.response.OAuthAccessTokenResponse;
import net.smartam.leeloo.client.response.OAuthAuthzResponse;
import net.smartam.leeloo.common.exception.OAuthProblemException;
import net.smartam.leeloo.common.exception.OAuthSystemException;
import net.smartam.leeloo.common.message.types.GrantType;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created with IntelliJ IDEA.
 * Author: Adrian Maurer
 * Date: 1/29/13
 * Time: 7:46 PM
 */
public class MeetupAuthActivity extends Activity {
    private final String TAG = getClass().getName();

    // Meetup OAuth Endpoints
    public static final String AUTH_URL = "https://secure.meetup.com/oauth2/authorize";
    public static final String TOKEN_URL = "https://secure.meetup.com/oauth2/access";

    //     Consumer
    //public static final String REDIRECT_URI_SCHEME = "oauthresponse";
    //public static final String REDIRECT_URI_HOST = "com.yourpackage.app";
    //public static final String REDIRECT_URI_HOST_APP = "yourapp";
    //public static final String REDIRECT_URI = REDIRECT_URI_SCHEME + "://" + REDIRECT_URI_HOST + "/";
    public static final String REDIRECT_URI = "http://successfuthinkersnetwork.com";
    public static final String CONSUMER_KEY = "euioujldhprrbn41s6m61r1es9";
    public static final String CONSUMER_SECRET = "ahkm29ih65qsehuns3ptd6a957";

    private WebView _webview;
    private Intent _intent;
    private Context _context;

    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        _intent = getIntent();
        _context = getApplicationContext();

        _webview = new WebView(this);
        _webview.setWebViewClient(new MyWebViewClient());
        setContentView(_webview);

        _webview.getSettings().setJavaScriptEnabled(true);
        OAuthClientRequest request = null;
        try {
            request = OAuthClientRequest.authorizationLocation(
                    AUTH_URL).setClientId(
                    CONSUMER_KEY).setRedirectURI(
                    REDIRECT_URI).buildQueryMessage();
        } catch (OAuthSystemException e) {
            Log.d(TAG, "OAuth request failed", e);
        }
        _webview.loadUrl(request.getLocationUri() + "&response_type=code&set_mobile=on");
    }

    public void finishActivity() {
        //do something here before finishing if needed
    	
        finish();
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);

            String code = uri.getQueryParameter("code");
            String error = uri.getQueryParameter("error");

            if (code != null) {
                new MeetupRetrieveAccessTokenTask().execute(uri);
                setResult(RESULT_OK, _intent);
                finishActivity();
            } else if (error != null) {
                setResult(RESULT_CANCELED, _intent);
                finishActivity();
            }
            return false;
        }
    }

    private class MeetupRetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {

        @Override
        protected Void doInBackground(Uri... params) {

            Uri uri = params[0];
            String code = uri.getQueryParameter("code");

            OAuthClientRequest request = null;

            try {
                request = OAuthClientRequest.tokenLocation(TOKEN_URL)
                        .setGrantType(GrantType.AUTHORIZATION_CODE).setClientId(
                                CONSUMER_KEY).setClientSecret(
                                CONSUMER_SECRET).setRedirectURI(
                                REDIRECT_URI).setCode(code)
                        .buildBodyMessage();

                OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());

                OAuthAccessTokenResponse response = oAuthClient.accessToken(request);
                
                // do something with these like add them to _intent
                MeetingActivity.meetupToken = response.getAccessToken();
                Log.d(TAG, response.getAccessToken());
                Log.d(TAG, response.getExpiresIn());
                Log.d(TAG, response.getRefreshToken());
  
            } catch (OAuthSystemException e) {
                Log.e(TAG, "OAuth System Exception - Couldn't get access token: " + e.toString());
                Toast.makeText(_context, "OAuth System Exception - Couldn't get access token: " + e.toString(), Toast.LENGTH_LONG).show();
            } catch (OAuthProblemException e) {
                Log.e(TAG, "OAuth Problem Exception - Couldn't get access token");
                Toast.makeText(_context, "OAuth Problem Exception - Couldn't get access token", Toast.LENGTH_LONG).show();
            }
            return null;
        }
    }

    @Override
    public void onBackPressed()
    {
        setResult(RESULT_CANCELED, _intent);
        finishActivity();
    }
}