package com.example.camohawkdudhiya;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    private static String tag = "==MAIN ACTIVITY2==";
    public static final int PICK_CONTACT = 101;
    public static final int ASK_CONTACT = 1001;
    public String user;
    public String course;
    public String sem;
    private static Activity currentActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.d(tag,"OnCreate");
        currentActivity = this;
        Intent intent = getIntent();
        course= intent.getStringExtra("course");
        Log.d(tag,"onCreate() - received Course '" + course);
        sem = intent.getStringExtra("semester");
        Log.d(tag,"onCreate() - received Course '" + sem);
        user = intent.getStringExtra("user");
        Toast myToast = new Toast(this);
        myToast.makeText(this, "Welcome "+ user+"!", Toast.LENGTH_LONG).show();
        Button contact= findViewById(R.id.contactButton);
        contact.setOnClickListener(this::showContact);
        Button image= findViewById(R.id.imageButton);
        image.setOnClickListener(this::showImages);
        Button comment= findViewById(R.id.commentButton);
        comment.setOnClickListener(this::showComments);
        FragmentManager fm = getSupportFragmentManager();
        // New Fragment Transaction...
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // Create a new instance of our Fragment class
        Fragment myFragment1 = new CourseFragment();
        // Attach the fragment instance to a frame (viewgroup) in our layout.
        // Use .replace to ensure that any previous fragment in the frame is detached.
        fragmentTransaction.replace(R.id.frame2, myFragment1);
        // Commit when done (you can do multiple transactions in a single commit)
        fragmentTransaction.commit();
    }


    @Override
    public void onResume(){
        super.onResume();
    }


    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    public void showContact(View view){
        FragmentManager fm = getSupportFragmentManager();
        // New Fragment Transaction...
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // Create a new instance of our Fragment class
        Fragment myFragment2 = new ContactFragment();
        // Attach the fragment instance to a frame (viewgroup) in our layout.
        // Use .replace to ensure that any previous fragment in the frame is detached.
        fragmentTransaction.replace(R.id.frame2, myFragment2);
        // Commit when done (you can do multiple transactions in a single commit)
        fragmentTransaction.commit();
        // request permissions
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS);
        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, ASK_CONTACT);
        } else {
        // Set up our ACTION_PICK Intent, and let Android start the pick activity
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, PICK_CONTACT);
        }
        Log.d(tag, "selectContact");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d(tag, "onActivityResult");
        if (requestCode == PICK_CONTACT) {
            if (resultCode == RESULT_OK) {
                boolean hasPhone = false;
                int numPhones = 0;
                String contactPhone = "";
                Uri uri = intent.getData();
                Log.d(tag, "URI = " + uri.toString());
                // Not using projection, return all columns....
                Cursor cursor = getContentResolver().query(uri, null,
                        null, null, null);
                cursor.moveToFirst();
                // Store the contact ID to use with content resolver queries
                String contactId = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Log.d(tag, "contactID = " + contactId);
                // Some data we can get right from the base data structure
                // - display name, has phone number, photo
                int nameColumnIndex = cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                String contactName = cursor.getString(nameColumnIndex);
                // We could, if we wanted, run a query to retrieve a cursor to
                // the Phone Numbers and count the records
                // However, "HAS_PHONE_NUMBER" in the main contact
                // record provides us with a true/false value,
                int hasPhoneColumnIndex = cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER);
                // String will be "1" or "0"
                hasPhone = cursor.getString(hasPhoneColumnIndex).equals("1");
                if (hasPhone) {
                // You know it has a number so now query it like this
                    Cursor phones = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +
                                    " = "+ contactId, null, null);
                    while (phones.moveToNext()) {
                        numPhones++;
                        contactPhone += phones.getString(phones.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER)) + "\n";
                    }
                    phones.close();
                } Log.d(tag, "Phone#s"+ numPhones);
                // Update the TextView
                TextView nameText = (TextView) findViewById(R.id.CName);
                nameText.setText(contactName);
                TextView phoneText = (TextView) findViewById(R.id.CPhone);
                phoneText.setText(contactPhone);
            }
        }
    }

    public void showComments(View view){
        Intent switch2Activity3 = new Intent(MainActivity2.this, MainActivity3.class);
        switch2Activity3.putExtra("user",user);
        startActivity(switch2Activity3);
    }
    public void showImages(View view){
        FragmentManager fm = getSupportFragmentManager();
        // New Fragment Transaction...
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        // Create a new instance of our Fragment class
        Fragment myFragment3 = new ImageFragment();
        // Attach the fragment instance to a frame (viewgroup) in our layout.
        // Use .replace to ensure that any previous fragment in the frame is detached.
        fragmentTransaction.replace(R.id.frame2, myFragment3);
        // Commit when done (you can do multiple transactions in a single commit)
        fragmentTransaction.commit();
    }
}