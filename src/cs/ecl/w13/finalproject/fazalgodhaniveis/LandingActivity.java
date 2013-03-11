package cs.ecl.w13.finalproject.fazalgodhaniveis;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class LandingActivity extends Activity {
	
	Spinner spinnerSelectUser;
	Button buttonSelectUser;
	Button buttonCreateNewUser;
	ImageView imageviewLogo;
	TextView textviewSelectAUser;
	List<String> listOfUsers;
	List<Integer> listOfIds;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.landing_activity);
		
		imageviewLogo = (ImageView) findViewById(R.id.landing_iv1);
		buttonSelectUser = (Button) findViewById(R.id.landing_b1);
		buttonCreateNewUser = (Button) findViewById(R.id.landing_b2);
		spinnerSelectUser = (Spinner) findViewById(R.id.landing_sp1);
		textviewSelectAUser = (TextView) findViewById(R.id.landing_tv1);
		
		setUpSpinner();
		
		//Setup create button
		buttonCreateNewUser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LandingActivity.this, NewUserActivity.class));
			}
		});
		
		buttonSelectUser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//Get id
				int idOfUserSelected = listOfIds.get(spinnerSelectUser.getSelectedItemPosition());
				
				Intent i = new Intent(LandingActivity.this, MainMenuActivity.class);
				i.putExtra("UserProfileID", idOfUserSelected);
				startActivity(i);
			}
		});
	}
	
	public void setUpSpinner(){
		//Get all users from user table, store them in list of users
		listOfUsers = new ArrayList<String>();
		listOfIds = new ArrayList<Integer>();

		Cursor users = managedQuery(HealthTrackerProvider.CONTENT_URI1, null, null, null, null);
		if (users.moveToFirst()){
			do {
				listOfUsers.add(users.getString(users.getColumnIndex(HealthTrackerProvider.USERPROFILE_NAME)));
				listOfIds.add(users.getInt(users.getColumnIndex(HealthTrackerProvider.USERPROFILE_ID)));
			} while (users.moveToNext());
		}
		
		
		if (listOfUsers.isEmpty()){
			spinnerSelectUser.setVisibility(View.GONE);
			buttonSelectUser.setVisibility(View.GONE);
			textviewSelectAUser.setText("Create a New User to Begin");
		}
		else{
			Resources res = getResources();
			spinnerSelectUser.setVisibility(View.VISIBLE);
			buttonSelectUser.setVisibility(View.VISIBLE);
			textviewSelectAUser.setText(res.getString(R.string.landing_text_intro));
			//Set that list as the Spinner's Data
			ArrayAdapter<String> userSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOfUsers);
			userSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerSelectUser.setAdapter(userSpinnerAdapter);
		}
	}

	@Override
	public void onResume(){
		super.onResume();
		setUpSpinner();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing_activity, menu);
		return true;
	}

}
