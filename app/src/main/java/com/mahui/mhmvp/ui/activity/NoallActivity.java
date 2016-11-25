package com.mahui.mhmvp.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.os.Bundle;

import com.jude.beam.bijection.RequiresPresenter;
import com.jude.beam.expansion.BeamBaseActivity;
import com.mahui.mhmvp.R;
import com.mahui.mhmvp.presenter.NoallActivityPresenter;
import com.mahui.mhmvp.util.ActivityPermissionHelper;
import com.mahui.mhmvp.util.JUtils;
import com.mahui.mhmvp.util.PermissionUtils;

/**
 * Created by Administrator on 2016/11/16.
 */
@RequiresPresenter(NoallActivityPresenter.class)
public class NoallActivity extends BeamBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noall_activity);
        boolean givepremission= ActivityPermissionHelper.checkPermiss(NoallActivity.this,new String[]{(Manifest.permission.READ_EXTERNAL_STORAGE)},"您需要允许读电话状态，才可以正常使用");
        if(givepremission){
            //getPresenter().onRefresh(null);
            JUtils.Toast("已经设置过了");
        }
    }
    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionUtils.verifyPermissions(grantResults);
        if(PermissionUtils.verifyPermissions(grantResults)){
            JUtils.Toast("设置成功");
            //刷新界面
            // getPresenter().onRefresh(null);
        }else{
            JUtils.Toast("设置失败");
            finish();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
