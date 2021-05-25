package com.example.todo_21;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

//String is for onPost method
public class SimpleAsyncTask extends AsyncTask<Void, Void, String> {
    private WeakReference<TextView> mTextView;

    //Works in background
    public SimpleAsyncTask(TextView tv){
        //WeakReference is used by Garbage collector overcome object no longer used in the memory
        mTextView = new WeakReference<>(tv);
    }

    @Override
    protected String doInBackground(Void... voids) {
        Random r = new Random();
        int n = r.nextInt(11);
        int s = n * 200;

        //Threads helps to do works of app in background
        try{
            Thread.sleep(s);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "Awake at last after sleeping for " + s + " milliseconds!";
    }

    //It is call when doInBackground method is finish executing
    // and gets String passwd by doInBackground method
    @Override
    protected void onPostExecute(String s) {
        mTextView.get().setText(s);
    }
}
