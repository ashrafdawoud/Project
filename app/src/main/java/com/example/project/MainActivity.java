package com.example.project;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.CallbackInterface {
    EditText edit1;
    EditText edit2;
    ArrayList<String>fname=new ArrayList<>();
    ArrayList<String>bname=new ArrayList<>();
    ArrayList<String>itemid=new ArrayList<>();
    RecyclerView recy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                // if defined
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
         edit1=findViewById(R.id.edit1);
         edit2=findViewById(R.id.edit2);
         recy=findViewById(R.id.recycler);
        recy.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        recy.hasFixedSize();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getall();
    }

    void addData(String e1, String e2){
        ParseObject soccerPlayers = new ParseObject("Data");
// Store an object
        soccerPlayers.put("firstname", e1);
        soccerPlayers.put("lastname", e2);
// Saving object
        soccerPlayers.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Success
                    Toast.makeText(MainActivity.this,"data are added",Toast.LENGTH_LONG).show();
                    getall();
                } else {
                    // Error
                }
            }
        });
    }

    void  getall(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Data");
        //query.whereEqualTo("objectId", "QHjRWwgEtd");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    fname.clear();bname.clear();itemid.clear();
                    for (int i =0 ;i<objects.size();i++) {
                        Log.d("getall", objects.get(i).getObjectId());
                        fname.add(objects.get(i).getString("firstname").toString());
                        bname.add(objects.get(i).getString("lastname").toString());
                        itemid.add(objects.get(i).getObjectId());

                    }
                    Adapter adapter=new Adapter(fname,bname,itemid,MainActivity.this,MainActivity.this);
                    recy.setAdapter(adapter);
                } else {
                    // Something is wrong
                }
            }
        });
    }
    void  deletdata(String id){
        ParseQuery<ParseObject> soccerPlayers = ParseQuery.getQuery("Data");
// Query parameters based on the item name
        soccerPlayers.whereEqualTo("objectId", id);
        soccerPlayers.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> player, ParseException e) {
                if (e == null) {
                    player.get(0).deleteInBackground(new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                getall();
                                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_LONG).show();

                            } else {
                                // Failed
                            }
                        }
                    });
                } else {
                    // Something is wrong
                }
            }
        });
    }
    public void onclick(View view) {
        if (!edit1.getText().toString().equals("")&&!edit2.getText().toString().equals("")) {
            Log.d("edit1edit1", edit1.getText().toString());
            addData(edit1.getText().toString(), edit2.getText().toString());
        }
        else
            Toast.makeText(MainActivity.this,"enter any thing in edittext",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onclick(String id) {
        deletdata(id);

    }
}