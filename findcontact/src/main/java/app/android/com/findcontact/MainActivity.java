package app.android.com.findcontact;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;

public class MainActivity extends Activity {

    @InjectView(R.id.textView)
    EditText textView;
    @InjectView(R.id.btnFind)
    Button btnFind;
    private ContactAdapter adapter;
    @InjectView(R.id.listView)
    ListView listView;
    private static final String TAG = "MainActivity";
    private List<Contact> contacts;

    String inputName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);
        contacts = new ArrayList<>();
        getContacts();
        for (Contact contact : contacts) {
            Log.v(TAG, contact.toString());
        }
        adapter = new ContactAdapter(this, contacts);

        listView.setAdapter(adapter);
    }

    public void OnClick(View view) {
        inputName = textView.getText().toString();
        contacts = new ArrayList<>();
        getContacts();
        adapter = new ContactAdapter(this, contacts);
        listView.setVisibility(View.VISIBLE);
        listView.setAdapter(adapter);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @OnItemClick(R.id.listView)
    public void onItemClick(int position) {
        Contact contact = contacts.get(position);
        String phoneNumber = contact.getMobile();
        if (phoneNumber.indexOf(",") != -1) {
            String[] pn = phoneNumber.split(",");
            phoneNumber = pn[0];
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNumber);
        intent.setData(data);
        if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }
    private void getContacts() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;

        ContentResolver resolver = this.getContentResolver();
        Cursor cursor = resolver.query(uri,null,ContactsContract.Contacts.DISPLAY_NAME+" like ? ",new String[]{"%"+inputName+"%"},null);

        while(cursor.moveToNext()){

            String id = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts._ID));
            String name = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME));
            String mobile = "";

            Cursor phoneCursor = resolver.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID+"="+id,null,null);

            while (phoneCursor.moveToNext()){
                String phone = phoneCursor.getString(phoneCursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                mobile += phone+",";
                if (mobile.length()>0){
                    mobile = mobile.substring(0,mobile.length()-1);
                }
            }
            Contact contact = new Contact();
            contact.setId(id);
            contact.setName(name);
            contact.setMobile(mobile);
            contacts.add(contact);
        }
    }
}
