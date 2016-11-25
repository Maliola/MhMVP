package com.jude.beam.bijection;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

public class Presenter<ViewType> {
    /**
     * activity 第一次create直到到主动退出Activity之前都不会调用。
     */
    protected void onCreate(@NonNull ViewType view, Bundle savedState) {
    }
    /**
     * presenter销毁时的回调。代表着activity正式退出
     */
    protected void onDestroy() {
    }

    /**
     * activity$OnCreate的回调,但执行延迟到OnCreate之后。
     */
    protected void onCreateView(@NonNull ViewType view) {
        this.view = view;
    }
    /**
     * activity$OnDestory的回调
     */
    protected void onDestroyView() {
    }
    protected void onResume() {
    }
    protected void onPause() {
    }

    protected void onSave(Bundle state) {
    }
    protected void onResult(int requestCode, int resultCode, Intent data) {
    }

    protected void onHiddenChanged(boolean hidden){

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

    }



    String id;
    ViewType view;
    @NonNull
    public final ViewType getView() {
        return view;
    }

    void create(ViewType view,Bundle savedState){
        this.view = view;
        onCreate(view,savedState);
    }

//    private boolean checkPermiss(String[] permissions) {
//        boolean isHas = PermissionUtils.hasSelfPermissions(getView(), permissions);
//        if (!isHas) {
//            if (!PermissionUtils.shouldShowRequestPermissionRationale(getView(), new String[]{(Manifest.permission.READ_EXTERNAL_STORAGE)})) {
//                showMessageOKCancel("您需要允许读电话状态，才可以正常使用",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                ActivityCompat.requestPermissions(getView(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
//                            }
//                        });
//                return false;
//            }
//            ActivityCompat.requestPermissions(getView(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
//            return false;
//        }
//        return true;
//    }
//
//    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
//        new AlertDialog.Builder((Activity)getView())
//                .setMessage(message)
//                .setPositiveButton("确定", okListener)
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        ((Activity)getView()).finish();
//                    }
//                })
//                .create()
//                .show();
//    }
}
