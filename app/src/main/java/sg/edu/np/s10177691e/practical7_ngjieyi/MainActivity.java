package sg.edu.np.s10177691e.practical7_ngjieyi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHandler(this, null, null, 1);

        TextView tv = findViewById(R.id.tvCreate);
        tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    Intent i= new Intent(MainActivity.this, CreateUser.class);
                    startActivity(i);
                    return true;
                }
                return false;
            }
        });
    }

    public void onClick(View v) {
        //userName
        EditText userName = findViewById(R.id.editName);
        String nameinput = userName.getText().toString();

        Pattern pattern1 = Pattern.compile("^(?=.*[A-Za-z])" +
                "(?=.*[0-9])" +
                "[A-Za-z0-9]{6,12}$");
        Matcher matcher1 = pattern1.matcher(nameinput);

        //password
        EditText userPassword = findViewById(R.id.editPassword);
        String pwinput = userPassword.getText().toString();

        Pattern pattern2 = Pattern.compile("^(?=.*[A-Z])" + //at least an Uppercase
                "(?=.*[0-9])" + //at least a nummeric number
                "(?=.*[!@#$%^&*+=])" + //at least one symbol
                "([A-Za-z0-9-!@#$%^&*+=]){8,20}$"); // assumption:length of normal password
        Matcher matcher2 = pattern2.matcher(pwinput);

        //Toast message
        if (matcher1.matches() && matcher2.matches() == true && db.findAccount(nameinput, pwinput) != null) { //must be in db
            //Read data of shared preferences
            /*SharedPreferences prefs = getSharedPreferences("MYPREF", MODE_PRIVATE);
            String name = prefs.getString("Name", "");
            String pw = prefs.getString("Password", "");*/

            Toast.makeText(MainActivity.this, "Valid", Toast.LENGTH_LONG).show();



        }
        else
        {
            Toast.makeText(MainActivity.this,"Invalid", Toast.LENGTH_LONG).show();
        }
    }

}
