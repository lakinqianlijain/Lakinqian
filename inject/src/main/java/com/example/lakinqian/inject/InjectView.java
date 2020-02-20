package com.example.lakinqian.inject;

import android.app.Activity;

public class InjectView {
    public static void bind(Activity activity){
        String className = activity.getClass().getName();
        try {
            Class<?> viewBindClass = Class.forName(className+"$$ViewBinder");
            ViewBinder viewBinder = (ViewBinder) viewBindClass.newInstance();
            viewBinder.bind(activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

    }
}
