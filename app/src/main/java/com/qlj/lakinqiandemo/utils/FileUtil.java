package com.qlj.lakinqiandemo.utils;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.provider.DocumentFile;
import android.util.Log;

import com.qlj.lakinqiandemo.JianApplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.ACTION_OPEN_DOCUMENT_TREE_URL;
import static com.qlj.lakinqiandemo.utils.SharedPreferenceUtil.DEMO_CONFIG;

/**
 * Created by lakinqian on 2018/12/5.
 */

public class FileUtil {
    public static boolean deleteSdcardFile(File file) throws IOException {
        if (file.isFile()) {
            return FileUtil.deleteFile(file);
        }

        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                return FileUtil.rmdirFiveCycles(file);
            }

            boolean ret = true;
            for (File f : childFile) {
                if (!deleteSdcardFile(f)) {
                    ret = false;
                }
            }

            return FileUtil.rmdirFiveCycles(file) && ret;
        }

        return true;
    }

    public static boolean rmdirFiveCycles(@NonNull final File file) {

        int retryCounter = 5; // MAGIC_NUMBER
        while (!FileUtil.rmdir(file) && retryCounter > 0) {
            try {
                Thread.sleep(100); // MAGIC_NUMBER
            } catch (InterruptedException e) {
                // do nothing
            }
            retryCounter--;
        }
        return !file.exists();
    }

    public static boolean rmdir(@NonNull final File file) {
        if (!file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {
            return false;
        }
        String[] fileList = file.list();
        if (fileList != null && fileList.length > 0) {
            // Delete only empty folder.
            return false;
        }

        // Try the normal way
        if (file.delete()) {
            return true;
        }
        // Try with Storage Access Framework.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            DocumentFile document = getDocumentFile(file, true, true);
            boolean a = false;
            if (document != null) {
                document.delete();
            }
            return !file.exists();
        }

        // Try the Kitkat workaround.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ContentResolver resolver = JianApplication.get().getContentResolver();
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());
            resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            // Delete the created entry, such that content provider will delete the file.
            resolver.delete(MediaStore.Files.getContentUri("external"), MediaStore.MediaColumns.DATA + "=?",
                    new String[]{file.getAbsolutePath()});
        }

        return !file.exists();
    }

    public static boolean deleteFile(@NonNull final File file) {
        // First try the normal deletion.
        if (file.delete()) {
            return true;
        }

        // Try with Storage Access Framework.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            DocumentFile document = getDocumentFile(file, false, true);
            if (document != null) {
                document.delete();
                Log.e("6666", "deleteFile: " + document.delete());
            }
            return !file.exists();
        }

        // Try the Kitkat workaround.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ContentResolver resolver = JianApplication.get().getContentResolver();

            try {
//                Uri uri = MediaStoreUtil.getUriFromFile(file.getAbsolutePath());
                Uri uri = Uri.fromFile(file);
                if (uri != null) {
                    resolver.delete(uri, null, null);
                }
                return !file.exists();
            } catch (Exception e) {
                Log.e("", "Error when deleting file " + file.getAbsolutePath(), e);
                return false;
            }
        }

        return !file.exists();
    }


    public static DocumentFile getDocumentFile(final File file, final boolean isDirectory, boolean undefined) {
        String baseFolder = getExtSdCardFolder(file);

        if (baseFolder == null) {
            return null;
        }

        String relativePath = null;
        try {
            String fullPath = file.getCanonicalPath();
            relativePath = fullPath.substring(baseFolder.length() + 1);
        } catch (IOException e) {
            return null;
        }

        String uri = SharedPreferenceUtil.readString(JianApplication.get(), DEMO_CONFIG, ACTION_OPEN_DOCUMENT_TREE_URL, "");
        Uri treeUri = Uri.parse(uri);
//        Uri treeUri = PreferenceUtil.getSharedPreferenceUri(R.string.key_internal_uri_extsdcard);

        if (treeUri == null) {
            return null;
        }

        // start with root of SD card and then parse through document tree.
        Log.e("6666", "getDocumentFile: " + treeUri);
        DocumentFile document = DocumentFile.fromTreeUri(JianApplication.get(), treeUri);

        String[] parts = relativePath.split("\\/");
        for (int i = 0; i < parts.length; i++) {
            DocumentFile nextDocument = document.findFile(parts[i]);

            if (nextDocument == null) {
                if ((i < parts.length - 1) || isDirectory) {
                    nextDocument = document.createDirectory(parts[i]);
                } else {
                    nextDocument = document.createFile("image", parts[i]);
                }
            }
            document = nextDocument;
        }

        return document;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getExtSdCardFolder(@NonNull final File file) {
        String[] extSdPaths = getExtSdCardPaths();
        try {
            for (String extSdPath : extSdPaths) {
                if (file.getCanonicalPath().startsWith(extSdPath)) {
                    return extSdPath;
                }
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static String[] getExtSdCardPaths() {
        List<String> paths = new ArrayList<String>();
        for (File file : JianApplication.get().getExternalFilesDirs("external")) {
            if (file != null && !file.equals(JianApplication.get().getExternalFilesDir("external"))) {
                int index = file.getAbsolutePath().lastIndexOf("/Android/data");
                if (index < 0) {
                    Log.w("", "Unexpected external file dir: " + file.getAbsolutePath());
                } else {
                    String path = file.getAbsolutePath().substring(0, index);
                    try {
                        path = new File(path).getCanonicalPath();
                    } catch (IOException e) {
                        // Keep non-canonical path.
                    }
                    paths.add(path);
                }
            }
        }


        return paths.toArray(new String[paths.size()]);
    }
}
