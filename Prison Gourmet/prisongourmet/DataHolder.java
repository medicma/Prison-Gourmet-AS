package com.wolfenterprisesllc.prisongourmet;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseInstallation;

public class DataHolder extends Application{
    String list;

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(new Parse.Configuration.Builder(getBaseContext())
                .applicationId("")
                .clientKey("")
                .server("https://parseapi.back4app.com")
               // .enableLocalDataStore()
                .build());
      
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
