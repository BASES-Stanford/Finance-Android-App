package bases.reimbursementform;

import java.net.URLEncoder;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	TextView textTargetUri;
	ImageView targetImage;

	private static final int SELECT_PICTURE = 1;

	private String selectedImagePath;
	private ImageView img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i("Uploadtest", "OnCreate()");
		img = (ImageView)findViewById(R.id.ImageView01);

		((Button) findViewById(R.id.button2))
		.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				//added
				Thread t = new Thread(new Runnable() {
					@Override
					public void run(){
						postData();
					}
				});
				t.start();
				
			}
		});
		
		
		((Button) findViewById(R.id.button1))
		.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
			}
		});

	}
	

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
 
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
 
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
             
            ImageView imageView = (ImageView) findViewById(R.id.ImageView01);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
         
        }
     
     
    }
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		if (resultCode == RESULT_OK) {
//			if (requestCode == SELECT_PICTURE) {
//				Uri selectedImageUri = data.getData();
//				selectedImagePath = getPath(selectedImageUri);
//				System.out.println("Image Path : " + selectedImagePath);
//				img.setImageURI(selectedImageUri);
//			}
//		}
//	}
//
//	public String getPath(Uri uri) {
//		String[] projection = { MediaStore.Images.Media.DATA };
//		Cursor cursor = managedQuery(uri, projection, null, null, null);
//		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//		cursor.moveToFirst();
//		return cursor.getString(column_index);
//	}


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
	
	public void postData() {
		String fullUrl1 = "https://docs.google.com/a/stanford.edu/forms/d/1wjD7Uj-Xl--MS6t19yAtHnyruhgRG3Tm3tYNr8wSYKs/formResponse";
		HttpRequest nreq = new HttpRequest();
		
		EditText text = (EditText)findViewById(R.id.edit_message);
		String value = text.getText().toString();
		
		EditText text2 = (EditText)findViewById(R.id.editText1);
		String value2 = text2.getText().toString();
		
		Spinner mySpinner=(Spinner) findViewById(R.id.spinner1);
		String value3 = mySpinner.getSelectedItem().toString();
		
		Spinner mySpinner2=(Spinner) findViewById(R.id.Spinner01);
		String value4 = mySpinner2.getSelectedItem().toString();
		
		EditText text3 = (EditText)findViewById(R.id.editText2);
		String value5 = text3.getText().toString();
		
		EditText text4 = (EditText)findViewById(R.id.editText3);
		String value6 = text4.getText().toString();

		EditText text5 = (EditText)findViewById(R.id.editText4);
		String value7 = text5.getText().toString();
		
		String Name = value;
		String email = value2;
		String team = value3;
		String requestType = value4;
		String amount = value5;
		String vendor = value6;
		String explanation = value7;
		
		String data = "entry_55699924=" + URLEncoder.encode(Name) + "&" + 
				"entry_1823874452=" + URLEncoder.encode(email) + "&" + 
				"entry_1788023367=" + URLEncoder.encode(team) + "&" + 
				"entry_1977199048=" + URLEncoder.encode(requestType) + "&" + 
				"entry_1004067983=" + URLEncoder.encode(amount) + "&" + 
				"entry_1038464947=" + URLEncoder.encode(vendor) + "&" + 
				"entry_459968047=" +URLEncoder.encode(explanation);
		String response = nreq.sendPost(fullUrl1, data);
		Log.i("Uploadtest", response);
	}
}
