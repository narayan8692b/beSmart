package com.example.pt.kioskbynarayan;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toast;


public class HomeScreen extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);

        Utilities utilities=new Utilities(HomeScreen.this);

        utilities.makeFolder();

        if(!utilities.isMyLauncherDefault()) {
            Toast.makeText(HomeScreen.this,"Not Default",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(HomeScreen.this,"Default",Toast.LENGTH_LONG).show();
        }

        //Hide the icon
  /*      PackageManager pkg=this.getPackageManager();
        pkg.setComponentEnabledSetting(new ComponentName(this,HomeScreen.class),PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        // nothing to do here
        // … really
    }
}
