package com.bdg.telkom.appinvite;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.applinks.AppLinkData;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

import bolts.AppLinks;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        Uri targetUrl = AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if (targetUrl != null) {
            Log.i("Activity", "App Link Target URL: " + targetUrl.toString());
        }
        /*
        FacebookSdk.sdkInitialize(this);
        Uri targetUrl =
                AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if (targetUrl != null) {
            Log.i("Activity", "App Link Target URL: " + targetUrl.toString());
        } else {
            AppLinkData.fetchDeferredAppLinkData(
                    this,
                    new AppLinkData.CompletionHandler() {
                        @Override
                        public void onDeferredAppLinkDataFetched(AppLinkData appLinkData) {
                            String appLinkUrl, previewImageUrl;
                            appLinkUrl = "https://fb.me/550388755166492";
//                            appLinkUrl = "https://play.google.com/store/apps/details?id=com.path&hl=en";
                            previewImageUrl = "http://2.bp.blogspot.com/-zBqFgFWZDnI/VNhT39MrhFI/AAAAAAAAA7M/cBszF5Mz1z8/s1600/kidsIslam.png";
                            AppInviteContent content = new AppInviteContent.Builder()
                                    .setApplinkUrl(appLinkUrl)
                                    .setPreviewImageUrl(previewImageUrl)
                                    .build();
                            AppInviteDialog.show(MainActivity.this, content);
                        }
                    });
        }
*/

       /* Uri targetUrl = AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if (targetUrl != null) {
            Log.i("Activity", "App Link Target URL: " + targetUrl.toString());
        }*//**/
       /* Button invite = (Button) findViewById(R.id.inv_fb_btn);

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appLinkUrl, previewImageUrl;
                appLinkUrl = "https://fb.me/167672533668166";
                appLinkUrl = "https://www.labedug.wordpress.com";
                previewImageUrl = "http://2.bp.blogspot.com/-zBqFgFWZDnI/VNhT39MrhFI/AAAAAAAAA7M/cBszF5Mz1z8/s1600/kidsIslam.png";
                AppInviteContent content = new AppInviteContent.Builder()
                        .setApplinkUrl(appLinkUrl)
                        .setPreviewImageUrl(previewImageUrl)
                        .build();
                AppInviteDialog.show(MainActivity.this, content);


            }
        });
*/




    }

}