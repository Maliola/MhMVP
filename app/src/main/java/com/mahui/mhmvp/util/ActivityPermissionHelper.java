package com.mahui.mhmvp.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.ActivityCompat;

/**
 * Created by stone on 16/8/12.
 */
public class ActivityPermissionHelper {
    public static final int REQUEST_CODE_ASK_PERMISSIONS = 0x01;
    public static String[] DEFALUT = new String[]{(Manifest.permission.READ_EXTERNAL_STORAGE)};

    public static boolean checkPermiss(final Activity activity) {
        boolean isHas = PermissionUtils.hasSelfPermissions(activity, DEFALUT);
        if (!isHas) {
            if (!PermissionUtils.shouldShowRequestPermissionRationale(activity, DEFALUT)) {
                showMessageOKCancel(activity, "你需要允许读取存储卡,才可以正常运行。",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(activity, DEFALUT, REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        });
                return false;
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            return false;
        }
        return true;
    }

    public static boolean checkPermiss(final Activity activity, final String[] permissions, String messages) {
        boolean isHas = PermissionUtils.hasSelfPermissions(activity, permissions);
        if (!isHas) {
            if (!PermissionUtils.shouldShowRequestPermissionRationale(activity, permissions)) {
                showMessageOKCancel(activity, messages,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(activity, permissions, REQUEST_CODE_ASK_PERMISSIONS);
                            }
                        });
                return false;
            }
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            return false;
        }
        return true;
    }

    private static void showMessageOKCancel(final Activity activity, String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("确定", okListener)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                })
                .create()
                .show();
    }


    public static void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults, OnRequestPermissionListener requestPermissionListener) {
        //  System.out.println("------------------------------------" + permissions[0]);
        if (PermissionUtils.verifyPermissions(grantResults)) {
            requestPermissionListener.onGranted();
        } else {
            requestPermissionListener.onDenied();
        }
    }

    public interface OnRequestPermissionListener {
        void onGranted();

        void onDenied();
    }
}
