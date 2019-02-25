package com.qlj.lakinqiandemo.share;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.*;
import com.qlj.lakinqiandemo.JianApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakinqian on 2019/2/20.
 */

public class ShareUtils {

    public static final String WHATSAPP_PACKAGE_NAME = "com.whatsapp";
    public static final String FACEBOOK_PACKAGE_NAME = "com.facebook.katana";
    private static final String URL = "http://www.gotube.video/?channel=share";
    private static final String URL1 = "https://play.google.com/store/apps/details?id=com.neondeveloper.player";


    public static void shareLink(Context context, String packageName) {
        if (!isAppInstalled(packageName)) {
            Toast.makeText(context, "请先安装WhatsApp...", Toast.LENGTH_SHORT).show();
        }
        String type = "text/plain";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType(type);
        share.putExtra(Intent.EXTRA_TEXT, URL);
        share.setPackage(packageName);
        context.startActivity(Intent.createChooser(share, "Share to"));
    }

    public static void shareBySystem(Context context, String content, String title) {

        List<Intent> targetedShareIntents = new ArrayList<Intent>();
        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/*");
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(shareIntent, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo resolveInfo : resInfo) {
                String packageName = resolveInfo.activityInfo.packageName;
                Log.e("6666", "shareBySystem: " + packageName);
                Intent targetedShareIntent = new Intent(android.content.Intent.ACTION_SEND);
                targetedShareIntent.setType("text/plain");
                targetedShareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
                targetedShareIntent.setComponent(new ComponentName(resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name));
                if (TextUtils.equals(packageName, "com.facebook.katana")) {
                    targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, URL);
                } else {
                    targetedShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
                }
                if (TextUtils.equals(packageName, "com.facebook.katana") || TextUtils.equals(packageName, "com.whatsapp")
                        || TextUtils.equals(packageName, "com.google.android.gm")) {
                    targetedShareIntents.add(targetedShareIntent);
                }

            }
            Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), title);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[targetedShareIntents.size()]));
            context.startActivity(chooserIntent);
        }
    }

    public static void shareLinkFacebook(Activity context) {
        com.facebook.share.widget.ShareDialog shareDialog = new com.facebook.share.widget.ShareDialog(context);
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(URL1))
                .build();
        shareDialog.show(linkContent);
    }


    public static boolean isAppInstalled(String packageName) {
        PackageManager pm = JianApplication.get().getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
