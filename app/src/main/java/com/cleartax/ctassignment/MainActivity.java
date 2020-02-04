package com.cleartax.ctassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    String previous_entry="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText myInputBox = (EditText)findViewById(R.id.inputcontroller);
        myInputBox.setOnFocusChangeListener(this);
        final Button undo_button = (Button)findViewById(R.id.undo_button);
        final TextView wordcount_tv = (TextView)findViewById(R.id.textView);
        undo_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myInputBox.setText(previous_entry);
                wordcount_tv.setText(getWordCount(previous_entry)+" words");
            }
        });

    }
    public int getWordCount(CharSequence word){
        String words = word.toString().trim();
        if (words.isEmpty())
            return 0;
        return words.split("\\s+").length;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        TextView wordcount_tv = (TextView)findViewById(R.id.textView);
        EditText myInputBox = (EditText)findViewById(R.id.inputcontroller);
        Button undo_button = (Button)findViewById(R.id.undo_button);
        if(previous_entry.length()>0){
            undo_button.setEnabled(true);
        }
        if (hasFocus) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String name = preferences.getString("string_value", "");
            if(!name.equalsIgnoreCase(""))
            {
                previous_entry= name;
            }
        }
        if (!hasFocus){
            wordcount_tv.setText(getWordCount(myInputBox.getText())+" words");
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("string_value",myInputBox.getText().toString());
            editor.apply();
            hideKeyboard(v);
        }
    }
    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
