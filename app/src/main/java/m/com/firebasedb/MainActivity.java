package m.com.firebasedb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    Button inserBtn;
    Button readBtn;
    Button updateBtn;
    Button deleteBtn;
    String strDBURL="https://demoproject-a8cbc.firebaseio.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Firebase.setAndroidContext(this);

        inserBtn = (Button)findViewById(R.id.button);
        readBtn = (Button)findViewById(R.id.button2);
        updateBtn = (Button)findViewById(R.id.button3);
        deleteBtn = (Button)findViewById(R.id.button4);

        inserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                EditText inputKey = (EditText) findViewById(R.id.editText);
                EditText inputValue = (EditText) findViewById(R.id.editText2);

                Firebase fbRef = new Firebase(strDBURL);
                Firebase alaRef = fbRef.child(inputKey.getText().toString());


                alaRef.setValue(inputValue.getText().toString());

                /* How to save Business Object */
                StudentBO obj =  new StudentBO();

                obj.setStrUserName("bhavin");
                obj.setStrEmailID("bpatel68@hawk");
                obj.setStrPhone("111111");
                obj.setStrStudentID("A20410380");

                //Marking studentid as key to save business object
                Firebase alaRef1 = fbRef.child("students/"+obj.getStrStudentID());
                alaRef1.setValue(obj);

                StudentBO obj2 =  new StudentBO();

                obj2.setStrUserName("bhavin222");
                obj2.setStrEmailID("bpatel68@hawk222");
                obj2.setStrPhone("111111222");
                obj2.setStrStudentID("A2041222");

                Firebase alaRef2 = fbRef.child("students/"+obj2.getStrStudentID());
                alaRef2.setValue(obj2);



                //alaRef.setValue(<Pass Business Object Directly>);

                Toast.makeText(MainActivity.this, "Insert Success", Toast.LENGTH_SHORT).show();
            }
        });

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                EditText inputKey = (EditText) findViewById(R.id.editText);

                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference(inputKey.getText().toString());
                //write the url which returns business object json  from firebase so in my case below will return business object.
                //DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("students").child("A20410380");


                dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot snapshot)
                    {
                        if (snapshot.exists())
                        {
                            //UserProfileBO upBO = (UserProfileBO) snapshot.getValue(UserProfileBO.class);
                            String value = (String)snapshot.getValue(String.class);
                            Toast.makeText(MainActivity.this, value, Toast.LENGTH_SHORT).show();

                            //This is how you retrieve business object from DB
                            //StudentBO obj = (StudentBO) snapshot.getValue(StudentBO.class);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError firebaseError) {

                        Toast.makeText(MainActivity.this, "DB Error"+firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                EditText inputKey = (EditText) findViewById(R.id.editText);
                EditText inputValue = (EditText) findViewById(R.id.editText2);

                Firebase fbRef = new Firebase(strDBURL);
                Firebase alaRef = fbRef.child(inputKey.getText().toString());
                alaRef.setValue(inputValue.getText().toString());

                Firebase alaRef1 = fbRef.child("students/"+"A2121021"+"/strEmailID");
                alaRef1.setValue("newmemai");

                Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                EditText inputKey = (EditText) findViewById(R.id.editText);

                Firebase fbRef = new Firebase(strDBURL);
                Firebase alaRef = fbRef.child(inputKey.getText().toString());
                alaRef.removeValue();;

                Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();

                Log.d("bhavin ", "Book ID1"+"1");

            }
        });
    }

}
