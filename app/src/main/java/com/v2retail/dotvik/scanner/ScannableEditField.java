package com.v2retail.dotvik.scanner;


import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

public class ScannableEditField extends AppCompatEditText {


    public ScannableEditField(@NonNull Context context) {
        super(context);
    }

    public ScannableEditField(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScannableEditField(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
