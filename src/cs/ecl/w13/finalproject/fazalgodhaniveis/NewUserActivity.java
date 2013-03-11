package cs.ecl.w13.finalproject.fazalgodhaniveis;

import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class NewUserActivity extends Activity {
	
	TextView textviewName;
	TextView textviewAddress;
	Button buttonSubmit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newuser_activity);
		
		textviewName = (TextView) findViewById(R.id.newuser_entername);
		textviewAddress = (TextView)  findViewById(R.id.newuser_enteraddress);
		buttonSubmit = (Button) findViewById(R.id.newuser_submit);
		buttonSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getContentResolver().delete(HealthTrackerProvider.CONTENT_URI1, null, null);
				ContentValues values = new ContentValues();
				values.put(HealthTrackerProvider.USERPROFILE_NAME, textviewName.getText().toString());
				values.put(HealthTrackerProvider.USERPROFILE_ADDRESS, textviewAddress.getText().toString());
				getContentResolver().insert(HealthTrackerProvider.CONTENT_URI1, values);
				values.clear();
				Log.d("TEST", "ONE");
				NewUserActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing_activity, menu);
		return true;
	}

}