package com.qlj.lakinqiandemo.file;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qlj.lakinqiandemo.BaseActivity;
import com.qlj.lakinqiandemo.R;
import com.qlj.lakinqiandemo.utils.SharedPreferenceUtil;

import java.io.File;
import java.io.IOException;

import static com.qlj.lakinqiandemo.utils.FileUtil.deleteSdcardFile;
import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.ACTION_OPEN_DOCUMENT_TREE_URL;
import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.DEMO_CONFIG;

/**
 * Created by lakinqian on 2018/12/5.
 */

public class FileActivity extends BaseActivity implements View.OnClickListener {
    private static final int DOCUMENT_TREE_REQUEST = 42;
    String path = "/storage/sdcard1/2222/simCountry.txt";
    File file = new File(path);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_related);
        initViews();
    }

    private void initViews() {
        findViewById(R.id.bt_sd_file_delete).setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DOCUMENT_TREE_REQUEST) {
            Uri treeUri = null;
            if (resultCode == Activity.RESULT_OK) {
                treeUri = data.getData();
                if (treeUri.getPath().contains("primary")) {
                    return;
                } else {
                    SharedPreferenceUtil.saveString(this, DEMO_CONFIG,
                            ACTION_OPEN_DOCUMENT_TREE_URL, treeUri.toString());
                    final int takeFlags = data.getFlags()
                            & (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    getContentResolver().takePersistableUriPermission(treeUri,
                            takeFlags);
                    try {
                        deleteSdcardFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_sd_file_delete:
                if (file.exists()){
                    if (!checkSdPermission(file)){
                        Intent delete = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                        startActivityForResult(delete, DOCUMENT_TREE_REQUEST);
                    }
                }
                break;
        }
    }

    private boolean checkSdPermission(File file){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            return file.canWrite();
        }
        return true;
    }
}
