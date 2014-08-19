package com.control.amigo;

import java.util.Timer;
import java.util.TimerTask;

import com.example.amigo.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.Toast;

public class FragtabsActivity extends FragmentActivity {
	private TabHost mTabHost;
    private TabManager mTabManager;
    private static boolean isExit = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.fragment_tabs);
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        
        mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);
        
        mTabHost.setCurrentTab(0);
        mTabManager.addTab(mTabHost.newTabSpec("Connect").setIndicator("Connect",this.getResources().getDrawable(android.R.drawable.ic_dialog_alert)),
        		Connect.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("Teleop").setIndicator("Teleop",this.getResources().getDrawable(android.R.drawable.ic_lock_lock)),
        		Teleop.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("Wander").setIndicator("Wander",this.getResources().getDrawable(android.R.drawable.ic_input_add)),
        		Wander.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("Monitor").setIndicator("Monitor",this.getResources().getDrawable(android.R.drawable.ic_delete)),
        		Monitor.class, null);

      
		   
        DisplayMetrics dm = new DisplayMetrics();   
        getWindowManager().getDefaultDisplay().getMetrics(dm); 
        int screenWidth = dm.widthPixels;   
           
           
        TabWidget tabWidget = mTabHost.getTabWidget(); 
        int count = tabWidget.getChildCount(); 
        if (count > 4) { 
            for (int i = 0; i < count; i++) {   
                tabWidget.getChildTabViewAt(i).setMinimumWidth((screenWidth) / 3);
            }   
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Intent intentBluetooth = new Intent(BluetoothService.ACTION_STOP);
		startService(intentBluetooth);
		stopService(intentBluetooth);
		
		FloatWindowManager.removeBigWindow(this);
		FloatWindowManager.removeSmallWindow(this);
		Intent intentFloatWindow = new Intent(this, FloatWindowService.class);
		stopService(intentFloatWindow);
		
		System.exit(0);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if( keyCode == KeyEvent.KEYCODE_BACK ){
			exitBy2Click();
		}
		return false;
	}
	
	private void exitBy2Click() {  
	    Timer tExit = null;  
	    if (isExit == false) {  
	        isExit = true;  
	        Toast.makeText(this, "再按一次退出程式", Toast.LENGTH_SHORT).show();  
	        tExit = new Timer();  
	        tExit.schedule(new TimerTask() {  
	            @Override  
	            public void run() {  
	                isExit = false;
	            }  
	        }, 2000);
	  
	    } else {
	    	finish();
	    }  
	}
	
}
