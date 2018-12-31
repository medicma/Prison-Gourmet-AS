package com.wolfenterprisesllc.prisongourmet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class Recipie extends AppCompatActivity {

    DataHolder global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipie);
        global = ((DataHolder) getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button home = (Button)findViewById(R.id.btnReturn);
        if (home != null) {
            home.setText(R.string.goBack);
        }
        if (home != null) {
            home.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }

        final TextView name = (TextView) findViewById(R.id.txtRName);
        final TextView ingredients = (TextView) findViewById(R.id.txtIngredients);
        if (ingredients!=null) {
            ingredients.setMovementMethod(new ScrollingMovementMethod());
        }


        ParseQuery<ParseObject> queryClass = ParseQuery.getQuery("Recipies");
        queryClass.whereContains("Name", global.getList());
        queryClass.getFirstInBackground(new GetCallback<ParseObject>() {
                                            @Override
                                            public void done(ParseObject object, ParseException e) {
                                                try {
                                                    if (name != null) {
                                                        name.setText(object.getString("Name"));
                                                    }
                                                    try {
                                                        if (ingredients != null) {
                                                            ingredients.setText(object.getString("Ingredients"));
                                                        }
                                                    } catch (Exception e1) {
                                                        e1.printStackTrace();
                                                    }
                                                } catch (Exception e1) {
                                                    e1.printStackTrace();
                                                }
                                            }
                                        }
        );
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.home:
                Intent intent2 = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent2);
            case R.id.contact:
                String[] TO = {"wolfnremtreview@yahoo.com"};
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:theprisongourmet@gmail.com"));
                emailIntent.setType("text/plain");
                startActivity(Intent.createChooser(emailIntent, "Choose an Email client to use:"));
                break;
            case R.id.action_settings:
                final Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.website:
                goToUrl("http://fireladychicago.wix.com/theprisongourmet");
            case R.id.chef:
                goToUrl("http://fireladychicago.wix.com/theprisongourmet#!chef/c42f");
            case R.id.blog:
                goToUrl("http://fireladychicago.wix.com/theprisongourmet#!blog/t0cr0");
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
