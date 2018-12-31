package com.wolfenterprisesllc.prisongourmet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected DataHolder globalHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        globalHolder = ((DataHolder) getApplication());

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        defaultACL.setWriteAccess(ParseUser.getCurrentUser(), false);
        ParseACL.setDefaultACL(defaultACL, true);

        final ListView list = (ListView) findViewById(R.id.lvRecipies);
        if (list != null) {
            list.setSelection(0);
            list.clearChoices();
        }

        final Button exit = (Button) findViewById(R.id.btnExit);
        if (exit != null) {
            exit.setText(R.string.Exit);
            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    homeIntent.addCategory( Intent.CATEGORY_HOME );
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                    // System.exit(0);
                }
            });
        }
        ParseQuery<ParseObject> queryRecipies = ParseQuery.getQuery("Recipies");
        queryRecipies.orderByAscending("Name");
        queryRecipies.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list1, ParseException e) {
                if (e == null) {
                    ArrayList<String> listHolder = new ArrayList<>();
                    for (int i = 0; i < list1.size(); i++) {
                        try {
                            listHolder.add(list1.get(i).getString("Name"));
                        } catch (Exception ex) {
                            ex.getMessage();
                        }
                        if (!listHolder.isEmpty()) {
                            ArrayAdapter<String> adapter;
                            adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, listHolder);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            if (list != null) {
                                list.setAdapter(adapter);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Oops. There was an error. Please try again or contact us if the problem persists.  MA01", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        if (list != null) {
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    globalHolder.setList(parent.getItemAtPosition(position).toString());

                    Intent intent = new Intent(getBaseContext(), Recipie.class);
                    list.clearChoices();
                    startActivity(intent);
                    list.setSelection(0);
                }
            });
        }
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (item.getItemId()) {
            // BE SURE TO TAKE INFLATER ALSO!!!!!  to add the string to the toolbar menu you must add it as a layout menu!!!!  and to the layout strings!!!!! AND BE SURE YOU ADD THE CORRECT LAYOUT MENU!!!!
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
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
