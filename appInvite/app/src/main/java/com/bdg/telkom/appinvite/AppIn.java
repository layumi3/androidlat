package com.bdg.telkom.appinvite;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.applinks.AppLinkData;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.widget.AppInviteDialog;

import bolts.AppLinks;

/**
 * Created by lacorp on 9/3/2016.
 */
public class AppIn extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        openDialogInvite();
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
                            //process applink data
                            String appLinkUrl, previewImageUrl;

                            appLinkUrl = "https://fb.me/167672533668166";
//                            appLinkUrl = "https://www.labedug.wordpress.com";
                            previewImageUrl = "http://2.bp.blogspot.com/-zBqFgFWZDnI/VNhT39MrhFI/AAAAAAAAA7M/cBszF5Mz1z8/s1600/kidsIslam.png";
                            if (AppInviteDialog.canShow()) {
                                AppInviteContent content = new AppInviteContent.Builder()
                                        .setApplinkUrl(appLinkUrl)
                                        .setPreviewImageUrl(previewImageUrl)
                                        .build();
                                AppInviteDialog.show(AppIn.this, content);

                            }
                        }
                    });
        }

    }

    public static void openDialogInvite()
    {
        String appLinkUrl, previewImageUrl;
        appLinkUrl = "https://fb.me/167672533668166";
        previewImageUrl = "http://2.bp.blogspot.com/-zBqFgFWZDnI/VNhT39MrhFI/AAAAAAAAA7M/cBszF5Mz1z8/s1600/kidsIslam.png";

        if (AppInviteDialog.canShow())
        {
            AppInviteContent content = new AppInviteContent.Builder()
                    .setApplinkUrl(appLinkUrl)
                    .setPreviewImageUrl(previewImageUrl)
                    .build();

            AppInviteDialog appInviteDialog = new AppInviteDialog(activity);
            CallbackManager sCallbackManager = CallbackManager.Factory.create();
            appInviteDialog.registerCallback(sCallbackManager, new FacebookCallback<AppInviteDialog.Result>()
            {
                @Override
                public void onSuccess(AppInviteDialog.Result result)
                {
                }

                @Override
                public void onCancel()
                {
                }

                @Override
                public void onError(FacebookException e)
                {
                }
            });

            appInviteDialog.show(content);
        }
    }
}
