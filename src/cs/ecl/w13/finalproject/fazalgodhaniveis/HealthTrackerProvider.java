package cs.ecl.w13.finalproject.fazalgodhaniveis;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class HealthTrackerProvider extends ContentProvider {

	public static final String PROVIDER_NAME = "cs.ecl.w13.finalproject.fazalgodhaniveis.HealthTracker";

	//UserProfile Table
	private static final String USERPROFILE_TABLE = "Userprofile";
	public static final String USERPROFILE_ID = "ID";
	public static final String USERPROFILE_NAME = "name";
	public static final String USERPROFILE_ADDRESS = "address";
	private static final int USERPROFILE = 1;
	public static final Uri CONTENT_URI1 = Uri.parse("content://" + PROVIDER_NAME + "/userprofile");

	//UserProfile Table
	private static final String PHONENUMBERS_TABLE = "Phonenumbers";
	public static final String PHONENUMBERS_USERID = "name";
	public static final String PHONENUMBERS_PHONENUMBER = "phonenumber";
	private static final int PHONENUMBERS = 2;
	public static final Uri CONTENT_URI2 = Uri.parse("content://" + PROVIDER_NAME + "/phonenumbers");
	
	
	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "userprofile", USERPROFILE);
		uriMatcher.addURI(PROVIDER_NAME, "phonenumbers", PHONENUMBERS);
	}
	
	
	// for using SQLite database
		private SQLiteDatabase healthtrackerDB;
		private static final String DATABASE_NAME = "HealthTracker";
		private static final int DATABASE_VERSION = 1;
		private static final String DATABASE_CREATE_USERPROFILE = "create table "
				+ USERPROFILE_TABLE + " ("
				+ USERPROFILE_ID + " INTEGER PRIMARY KEY, "
				+ USERPROFILE_NAME + " text not null, "
				+ USERPROFILE_ADDRESS + " text not null);";
		private static final String DATABASE_CREATE_PHONENUMBERS = "create table "
				+ PHONENUMBERS_TABLE + " ("
				+ PHONENUMBERS_USERID + " integer not null, "
				+ PHONENUMBERS_PHONENUMBER + " integer not null);";
		
		// for using SQLite database
		private static class DatabaseHelper extends SQLiteOpenHelper {
			DatabaseHelper(Context context) {
				super(context, DATABASE_NAME, null, DATABASE_VERSION);
			}

			@Override
			public void onCreate(SQLiteDatabase db) {
				db.execSQL(DATABASE_CREATE_USERPROFILE);
				db.execSQL(DATABASE_CREATE_PHONENUMBERS);
			}

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				db.execSQL("DROP TABLE IF EXISTS " + USERPROFILE_TABLE);
				db.execSQL("DROP TABLE IF EXISTS " + PHONENUMBERS_TABLE);
				onCreate(db);
			}
		}
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		//Right now this function just deletes ALL rows in specificed table
		switch (uriMatcher.match(arg0)) {
			case USERPROFILE:
				healthtrackerDB.delete(USERPROFILE_TABLE, "1=1", null);
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + arg0);
		}
		
		return 1;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		long rowID;
		Uri _uri = null;
		
		switch (uriMatcher.match(arg0)) {
			case USERPROFILE:
				rowID = healthtrackerDB.insert(USERPROFILE_TABLE, "", arg1);
				if (rowID > 0) {
					_uri = ContentUris.withAppendedId(CONTENT_URI1, rowID);
					getContext().getContentResolver().notifyChange(_uri, null);
				}
				break;
			case PHONENUMBERS:
				rowID = healthtrackerDB.insert(USERPROFILE_TABLE, "", arg1);
				if (rowID > 0) {
					_uri = ContentUris.withAppendedId(CONTENT_URI1, rowID);
					getContext().getContentResolver().notifyChange(_uri, null);
				}
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + arg0);
		}
		return _uri;
	}

	@Override
	public boolean onCreate() {
		Context context = getContext();
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		healthtrackerDB = dbHelper.getWritableDatabase();
		return (healthtrackerDB == null) ? false : true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		
		SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
		
		switch (uriMatcher.match(uri)) {
			case USERPROFILE:
				sqlBuilder.setTables(USERPROFILE_TABLE);
				break;
			case PHONENUMBERS:
				sqlBuilder.setTables(PHONENUMBERS_TABLE);
				break;
			default:
				throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		
		Cursor cl = sqlBuilder.query(healthtrackerDB, projection, selection,
				selectionArgs, null, null, sortOrder);

		cl.setNotificationUri(getContext().getContentResolver(), uri);
		return cl;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// Unused
		return 0;
	}

}
