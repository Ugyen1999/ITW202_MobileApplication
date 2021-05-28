package com.example.todo22;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.lang.ref.WeakReference;

public class BookLoader extends AsyncTaskLoader<String> {

    public String mQueryString;
    @Override
    public void onStartLoading(){
        super.onStartLoading();
        forceLoad();
    }
    @Override
    public String loadInBackground() {
        return NetworkUtils.getBookInfo(mQueryString);
    }

    BookLoader(Context context, String mQueryString){
        super(context);
        this.mQueryString = mQueryString;

    }
    public BookLoader(@NonNull Context context){
        super((context));
    }
}
