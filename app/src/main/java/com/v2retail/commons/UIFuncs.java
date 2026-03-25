package com.v2retail.commons;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.text.Html;
import android.text.InputType;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.v2retail.dotvik.R;

import java.util.Locale;

public class UIFuncs {

    public static void blinkEffectOnError(Context context, EditText view,boolean errorBeep) {
        ObjectAnimator anim = ObjectAnimator.ofInt(view, "backgroundColor", Color.WHITE, Color.RED,Color.WHITE);
        blinkEffectOnError(context, view, errorBeep, anim);
    }

    public static void blinkEffectOnErrorBordered(Context context, EditText view,boolean errorBeep) {
        ObjectAnimator anim = ObjectAnimator.ofInt(view, "backgroundColor", Color.WHITE, Color.RED,Color.WHITE);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setBackgroundResource(R.drawable.border);
            }
        });
        blinkEffectOnError(context, view, errorBeep, anim);
    }

    private static void blinkEffectOnError(Context context, EditText view,boolean errorBeep,ObjectAnimator anim) {
        if(errorBeep){
            errorSound(context);
        }
        anim.setDuration(500);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setRepeatCount(4);
        anim.start();
    }

    public static void errorSound(Context context){
        MediaPlayer mp = MediaPlayer.create(context, R.raw.error_beep);
        mp.start();
    }
    public static void blinkEffectOnSuccess(EditText view) {
        ObjectAnimator anim = ObjectAnimator.ofInt(view, "backgroundColor", Color.WHITE, Color.rgb(29, 179, 74),Color.WHITE);
        anim.setDuration(500);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(ValueAnimator.REVERSE);
        anim.setRepeatCount(2);
        anim.start();
    }
    public static String removeLeadingZeros(String str)
    {
        String regex = "^0+(?!$)";
        return str.replaceAll(regex, "");
    }
    public static void enableInput(Context con, View view){
        view.setBackground(getInputEnabledStyle(con));
        view.setEnabled(true);
        view.requestFocus();
    }
    public static void disableInput(Context con, View view){
        view.setBackground(getInputDisabledStyle(con));
        view.setEnabled(false);
    }

    public static Drawable getInputEnabledStyle(Context con){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ContextCompat.getDrawable(con, R.drawable.border);
        } else {
            return con.getResources().getDrawable(R.drawable.border);
        }
    }
    public static Drawable getInputDisabledStyle(Context con){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ContextCompat.getDrawable(con, R.drawable.border_disabled_input);
        } else {
            return con.getResources().getDrawable(R.drawable.border_disabled_input);
        }
    }

    public static Spanned getSmallTitle(String title){
        title = "<small>"+title+"</small>";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(title, 0);
        } else {
            return Html.fromHtml(title);
        }
    }

    public static String toUpperTrim(EditText target){
        if(target == null || target.getText() == null){
            return "";
        }
        return target.getText().toString().trim().toUpperCase(Locale.ROOT);
    }

    public static void enableInputShowKeyboard(Context con, View view){
        enableInput(con, view);
        InputMethodManager imm = (InputMethodManager) con.getSystemService(con.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }
    public static void hideKeyboard(Activity activity) {
        try {
            if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
            }
        }catch (Exception e) {}
    }

    public static void disableKeyInput(EditText targetEditText, View view, Context con){
        //targetEditText.setKeyListener(null);
        targetEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                InputMethodManager imm = (InputMethodManager) con.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });
        targetEditText.setFocusable(true);
        targetEditText.setFocusableInTouchMode(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            targetEditText.setShowSoftInputOnFocus(false);
        } else {
            targetEditText.setInputType(InputType.TYPE_NULL);
        }
    }
}
