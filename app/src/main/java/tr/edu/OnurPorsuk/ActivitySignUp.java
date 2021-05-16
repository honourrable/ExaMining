package tr.edu.OnurPorsuk;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivitySignUp extends AppCompatActivity {

    private ImageButton buttonFinish;
    private ImageButton buttonReset;
    private ImageButton buttonAddPhoto;
    private EditText fName;
    private EditText lName;
    private EditText emailSign;
    private EditText phoneNumber;
    private EditText passwordSign;
    private EditText passwordSign2;
    private String imagePath, userFileName;
    private final static int SELECT_PHOTO = 123;
    private ArrayList<User> users;
    private final ArrayList<EditText> editTexts = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        defineVariables();
        defineListeners();
    }

    public void defineVariables(){
        buttonFinish = findViewById(R.id.buttonFinish);
        buttonReset = findViewById(R.id.buttonReset);
        buttonAddPhoto = findViewById(R.id.buttonAddPhoto);
        fName = findViewById(R.id.editTextPersonFirstName);
        lName = findViewById(R.id.editTextPersonLastName);
        emailSign = findViewById(R.id.editTextEmailAddressSign);
        phoneNumber = findViewById(R.id.editTextPhone);
        passwordSign = findViewById(R.id.editTextPasswordSign);
        passwordSign2 = findViewById(R.id.editTextPasswordSign2);
        users = new ArrayList<>();
        userFileName = "user.txt";

        //users = User.getUsers();
        readUsers();

        editTexts.add(fName);
        editTexts.add(lName);
        editTexts.add(emailSign);
        editTexts.add(phoneNumber);
        editTexts.add(passwordSign);
        editTexts.add(passwordSign2);
    }

    public void defineListeners(){

        buttonFinish.setOnClickListener(v -> {
            Intent intent;
            if(checkEmailTaken()) {
                passwordSign.setText("");
                passwordSign2.setText("");
                Toast.makeText(ActivitySignUp.this, R.string.user_exists_already,
                        Toast.LENGTH_SHORT).show();
            }
            else{
                if(isFilled()){
                    if (isValid()) {
                        intent = new Intent(v.getContext(), ActivityMain.class);
                        User user = new User(fName.getText().toString(), lName.getText().toString(),
                                emailSign.getText().toString(), phoneNumber.getText().toString(),
                                passwordSign.getText().toString(), imagePath);

                        users.add(user);
                        writeUsers();

                        Toast.makeText(ActivitySignUp.this, R.string.sign_up_success,
                                Toast.LENGTH_SHORT).show();
                        cleanTextBoxAndPhoto();
                        startActivity(intent);
                    }
                }
                else{
                    passwordSign.setText("");
                    passwordSign2.setText("");
                    Toast.makeText(ActivitySignUp.this, R.string.enter_info,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonAddPhoto.setOnClickListener(this::fileChooser);

        buttonReset.setOnClickListener(v -> cleanTextBoxAndPhoto());
    }

    public void readUsers(){
        ObjectInputStream input;

        try {
            String filePath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                    + File.separator + userFileName;
            input = new ObjectInputStream(new FileInputStream(filePath));

            users = (ArrayList<User>) input.readObject();

            input.close();
        } catch (ClassNotFoundException | IOException e) {
            Toast.makeText(ActivitySignUp.this, R.string.read_object_error,
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void writeUsers(){
        ObjectOutput out;

        try {
            String filePath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                    + File.separator + userFileName;
            out = new ObjectOutputStream(new FileOutputStream(filePath));

            out.writeObject(users);
            out.flush();
            out.close();
        } catch (IOException e) {
            Toast.makeText(ActivitySignUp.this, R.string.write_object_error,
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                Toast.makeText(ActivitySignUp.this, "No data in file!",
                    Toast.LENGTH_SHORT).show();
                return;
            }

            Uri selectedImageURI = data.getData();
            assert selectedImageURI != null;
            File file = new File(selectedImageURI.getPath());
            imagePath = file.getPath();
            buttonAddPhoto.setImageURI(selectedImageURI);

            Toast.makeText(ActivitySignUp.this, "Image Path: " + imagePath,
                        Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(ActivitySignUp.this, "Could not open file!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void fileChooser(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Pick a photo"), SELECT_PHOTO);
    }

    private void cleanTextBoxAndPhoto(){
        for(EditText i : editTexts){
            i.setText("");
        }
        buttonAddPhoto.setImageResource(R.drawable.icon_user_sign_up);
    }

    private boolean checkEmailTaken(){
        for(User u : users){
            if(emailSign.getText().toString().equals(u.getEmail()))
                return true;
        }
        return false;
    }

    private boolean isFilled(){
        String str;
        for(EditText i : editTexts){
            str = i.getText().toString();
            if(TextUtils.isEmpty(str)){
                return false;
            }
        }
        return imagePath != null;
    }

    private boolean isValid() {
        Pattern pattern;
        Matcher matcher;
        String patterns;

        if(!(fName.getText().toString().matches("[a-zA-Z ]+")
                && lName.getText().toString().matches("[a-zA-Z ]+"))){
            Toast.makeText(ActivitySignUp.this, R.string.name_invalid,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if(!phoneNumber.getText().toString().matches("^[+]?[0-9]{10,13}$")){
            Toast.makeText(ActivitySignUp.this, R.string.format_phone,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        patterns = "^[\\w.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        pattern = Pattern.compile(patterns, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(emailSign.getText().toString());
        if(!matcher.matches()){
            Toast.makeText(ActivitySignUp.this, R.string.format_email,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        patterns = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&.,*?!+=])(?=\\S+$).{8,}$";
        pattern = Pattern.compile(patterns);
        matcher = pattern.matcher(passwordSign.getText().toString());
        if(!matcher.matches()){
            Toast.makeText(ActivitySignUp.this, R.string.password_invalid,
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if(!passwordSign2.getText().toString().equals(passwordSign.getText().toString())){
            Toast.makeText(ActivitySignUp.this, R.string.password_not_match,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}