package cs.ecl.w13.finalproject.fazalgodhaniveis;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends Activity {
	
	TextView textviewIntro;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu_activity);
		
		textviewIntro = (TextView) findViewById(R.id.mainmenu_intro);
		int lol = getIntent().getIntExtra("UserProfileID", -1);
		Toast.makeText(getApplicationContext(), "The Current User Profile's ID is: " + lol, Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing_activity, menu);
		return true;
	}

}