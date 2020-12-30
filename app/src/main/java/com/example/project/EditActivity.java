package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class EditActivity extends AppCompatActivity {
    EditText edit1;
    EditText edit2;
    String id,fname,lname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            id=bundle.getString("id");
            fname=bundle.getString("fname");
            lname=bundle.getString("lname");
        }
        edit1=findViewById(R.id.edit1);
        edit1.setText(fname);
        edit2=findViewById(R.id.edit2);
        edit2.setText(lname);

    }

    public void onclick(View view) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Data");
// Retrieve the object by id
            query.getInBackground(id, new GetCallback<ParseObject>() {
                public void done(ParseObject player, ParseException e) {
                    if (e == null) {
                        // Now let's update it with some new data. In this case, only cheatMode and score
                        // will get sent to the Parse Cloud. playerName hasn't changed.
                        player.put("firstname", edit1.getText().toString());
                        player.put("lastname", edit2.getText().toString());
                        Toast.makeText(EditActivity.this,"editing done",Toast.LENGTH_LONG).show();
                        player.saveInBackground();
                    } else {
                        // Failed
                    }
                }
            });
        }

}