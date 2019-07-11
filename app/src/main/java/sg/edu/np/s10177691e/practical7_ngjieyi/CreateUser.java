package sg.edu.np.s10177691e.practical7_ngjieyi;

import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateUser extends AppCompatActivity {
    DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        db = new DBHandler(this, null, null, 1);
    }

    public void cancel (View v)
    {
        Intent i= new Intent(CreateUser.this, MainActivity.class); //redirect back to log in page
        startActivity(i);
    }

    public void create (View v)
    {
        //userName
        EditText userName = findViewById(R.id.editName1);
        String nameinput = userName.getText().toString();

        Pattern pattern1 = Pattern.compile("^(?=.*[A-Za-z])" +
                "(?=.*[0-9])" +
                "[A-Za-z0-9]{6,12}$");
        Matcher matcher1 = pattern1.matcher(nameinput);

        //password
        EditText userPassword = findViewById(R.id.editPassword1);
        String pwinput = userPassword.getText().toString();

        Pattern pattern2 = Pattern.compile("^(?=.*[A-Z])" + //at least an Uppercase
                "(?=.*[0-9])" + //at least a nummeric number
                "(?=.*[!@#$%^&*+=])" + //at least one symbol
                "([A-Za-z0-9-!@#$%^&*+=]){8,20}$"); // assumption:length of normal password
        Matcher matcher2 = pattern2.matcher(pwinput);

        //Toast message
        if (matcher1.matches() && matcher2.matches() == true)
        {
            //SharedPreference
            /*SharedPreferences preferences = getSharedPreferences("MYPREF", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Name", nameinput);
            editor.putString("Password", pwinput);
            editor.apply();*/

            //Insert the details in database
            Account a = new Account();
            a.setUsername(nameinput);
            a.setPassword(pwinput);
            db.addAccount(a);

            Toast.makeText(CreateUser.this,"New User Created Successfully", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(CreateUser.this,"Invalid User Creation. Please Try Again.", Toast.LENGTH_LONG).show();
        }
    }
}
