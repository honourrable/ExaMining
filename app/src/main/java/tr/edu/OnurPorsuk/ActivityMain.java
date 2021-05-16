package tr.edu.OnurPorsuk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity {

    private ImageButton buttonSignIn;
    private ImageButton buttonSignUp;
    private EditText email;
    private EditText password;
    private ArrayList<User> users;
    private Integer attempt;
    private String fName, lName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defineVariables();
        defineListeners();
    }

    public void defineVariables(){
        attempt = 1;
        users = new ArrayList<>();
        String usersFileName = "user.txt";

        // Saving users to file. Since it's already saved, related lines commented below
        // users = User.getUsers();
        // writeUsers(usersFileName);
        readUsers(usersFileName);

        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        email = findViewById(R.id.textEmailAddress);
        password = findViewById(R.id.textPassword);
    }

    public void defineListeners(){
        buttonSignIn.setOnClickListener(v -> {
            Intent intent;

            if(TextUtils.isEmpty(email.getText().toString()) ||
                    TextUtils.isEmpty(password.getText().toString())){
                Toast.makeText(ActivityMain.this, R.string.enter_info_sign_in,
                        Toast.LENGTH_SHORT).show();
            }
            else{
                if (checkUser()) {
                    intent = new Intent(v.getContext(), ActivityMenu.class);
                    intent.putExtra("Name", fName + " " + lName);
                    cleanTextBox();
                    Toast.makeText(ActivityMain.this, R.string.sign_in_success,
                            Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else {
                    password.setText("");
                    attempt += 1;
                    Toast.makeText(ActivityMain.this, R.string.login_error,
                            Toast.LENGTH_SHORT).show();

                    if (attempt > 3) {
                        Toast.makeText(ActivityMain.this, R.string.over_attempt,
                                Toast.LENGTH_SHORT).show();

                        buttonSignIn.setVisibility(View.INVISIBLE);
                        intent = new Intent(v.getContext(), ActivitySignUp.class);
                        cleanTextBox();
                        startActivity(intent);
                    }
                }
            }
        });

        buttonSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ActivitySignUp.class);
            cleanTextBox();
            startActivity(intent);
        });
    }

    public void writeUsers(String fileName){
        ObjectOutput out;

        try {
            String filePath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                    + File.separator + fileName;
            out = new ObjectOutputStream(new FileOutputStream(filePath));

            out.writeObject(users);
            out.flush();

            Toast.makeText(ActivityMain.this, R.string.write_object_error,
                    Toast.LENGTH_SHORT).show();
            out.close();
        } catch (IOException e) {
            Toast.makeText(ActivityMain.this, R.string.write_object_error,
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void readUsers(String fileName){
        ObjectInputStream input;

        try {
            String filePath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                    + File.separator + fileName;
            input = new ObjectInputStream(new FileInputStream(filePath));
            users = (ArrayList<User>) input.readObject();

            Toast.makeText(ActivityMain.this, R.string.read_object_success,
                    Toast.LENGTH_SHORT).show();
            input.close();
        } catch (ClassNotFoundException | IOException e) {
            Toast.makeText(ActivityMain.this, R.string.read_object_error,
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void cleanTextBox(){
        email.setText("");
        password.setText("");
    }

    private boolean checkUser(){
        for(User u : users){
            if(email.getText().toString().equals(u.getEmail())
                && password.getText().toString().equals(u.getPassword())) {
                fName = u.getFName();
                lName = u.getLName();
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setIcon(R.drawable.icon_alert);
        alert.setTitle("Exit");
        alert.setMessage(R.string.exit_app);
        alert.setPositiveButton(R.string.yes, (dialog, which) -> {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        alert.setNegativeButton(android.R.string.no, (dialog, which) -> dialog.cancel());
        alert.show();
    }

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
    }
}
