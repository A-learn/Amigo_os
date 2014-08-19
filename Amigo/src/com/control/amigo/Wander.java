package com.control.amigo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.amigo.R;

public class Wander extends Fragment implements OnClickListener {
	private Button startWander;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View InputFragmentView = inflater.inflate(R.layout.wander_view, container, false);
		startWander = (Button) InputFragmentView.findViewById(R.id.startwander);
		startWander.setOnClickListener(this);
		
		return InputFragmentView;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if( v.getId()==R.id.startwander ){
			if( startWander.getText().equals("start") ){
				try {
					BluetoothService.Amigo.startWanderMode();
					startWander.setText("stop");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if( startWander.getText().equals("stop") ){
				try {
					BluetoothService.Amigo.stopWanderMode();
					startWander.setText("start");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
