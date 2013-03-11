package cs.ecl.w13.finalproject.fazalgodhaniveis;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

@SuppressWarnings("deprecation")
public class LandingActivity extends Activity {
	
	Spinner spinnerSelectUser;
	Button buttonSelectUser;
	Button buttonCreateNewUser;
	ImageView imageviewLogo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.landing_activity);
		
		imageviewLogo = (ImageView) findViewById(R.id.landing_iv1);
		buttonSelectUser = (Button) findViewById(R.id.landing_b1);
		buttonCreateNewUser = (Button) findViewById(R.id.landing_b2);
		spinnerSelectUser = (Spinner) findViewById(R.id.landing_sp1);
		
		setUpSpinner();
		
		//Setup create button
		buttonCreateNewUser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(LandingActivity.this, NewUserActivity.class));
			}
		});
	}
	
	public void setUpSpinner(){
		//Get all users from user table, store them in list of users
		List<String> listOfUsers = new ArrayList<String>();
		Cursor users = managedQuery(HealthTrackerProvider.CONTENT_URI1, null, "1 = 1", null, null);
		do {
			if (users.moveToFirst()){
				listOfUsers.add(users.getString(users.getColumnIndex(HealthTrackerProvider.USERPROFILE_NAME)));
			}
		} while (users.moveToNext());
		
		//Set that list as the Spinner's Data
		ArrayAdapter<String> userSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listOfUsers);
		userSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerSelectUser.setAdapter(userSpinnerAdapter);
		
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
