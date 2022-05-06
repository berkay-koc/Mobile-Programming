package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {
    EditText MailToField, FirstNameField, LastNameField, PhoneNrField, PasswordField, PasswordReField;
    ImageView imageView;
    String selectedImageUri;
    Context context;
    UserLocalStore storeUser;
    private static final int IMAGE_PICK_CODE = 1000;
    int RETURN_VAL;
    private static final int PERMISSION_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        context = getApplicationContext();
        storeUser = new UserLocalStore(context);
    }

    /*Login ekranı, login ekranında kullanıcı adı ve şifresi kontrol edilecek, yanlış girilirse 2-3 kez disable edilecek login ekranı, signup ekranı, signup ekranında ad soyad
    telno mail, signupa basınca mail yeri açılacak oradan yollanması gerkeiyor. Sonrasında müzik ekranı tasarlanacak, liste olarak telefondaki müzikler gözükecek
    şarkıların resimleri fln olsun cardview falan bişey yapısı şarkı seçilince bir başka aktivitede musicplayer ile çalınacak şarkı. Liste edilen şarkıların silinmesi ve
    eklenmesi oluşturulacak.*/

    public void sendMail(View view){

        MailToField = (EditText)findViewById(R.id.email);
        FirstNameField = (EditText)findViewById(R.id.firstname);
        LastNameField = (EditText)findViewById(R.id.lastname);
        PasswordField = (EditText)findViewById(R.id.password);
        PasswordReField = (EditText)findViewById(R.id.password_2);
        PhoneNrField = (EditText)findViewById(R.id.phonenr);

        String MailTo = MailToField.getText().toString();
        String FirstName = FirstNameField.getText().toString();
        String LastName = LastNameField.getText().toString();
        String Password = PasswordField.getText().toString();
        String rePassword = PasswordReField.getText().toString();
        String PhoneNumber = PhoneNrField.getText().toString();

        if(!rePassword.equals(Password)){
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            CharSequence text = "Passwords don't match";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        else{
            if(FirstName.equals(storeUser.getLoggedInUser().getName())){
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;
                CharSequence text = "This User Already Exists!";
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            }
            else {
                addNewUser(MailTo, Password, FirstName, LastName, PhoneNumber, selectedImageUri);
                String subject = "Doğrulama Maili";
                String message = "Bu bir doğrulama mesajıdır.\n" + "First Name: " + FirstName + "\n" + "Last Name: " + LastName + "\n" + "Phone Number: " + PhoneNumber;

                Intent email = new Intent(Intent.ACTION_SEND);

                email.putExtra(Intent.EXTRA_EMAIL, new String[]{MailTo});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);

                //need this to prompts email client only
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "E-Mail istemcisini seçiniz: "));
                RETURN_VAL = 104;
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                User newUser = storeUser.getLoggedInUser();
                System.out.println("name: " + newUser.getName());

                //Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                //startActivity(activity2Intent);
            }
        }
    }

    public void getImage(View view){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permissions, PERMISSION_CODE);
                RETURN_VAL = 100;
            }
            else {
                getImageFromAlbum();
                RETURN_VAL = 100;
            }
        }
        else{
            getImageFromAlbum();
            RETURN_VAL = 100;
        }
    }

    private void getImageFromAlbum() {
        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), IMAGE_PICK_CODE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(RETURN_VAL == 104) {
            Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(activity2Intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getImageFromAlbum();
                }
                else{
                    Toast.makeText(this, "Permission denied!..", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        imageView = findViewById(R.id.imageView);
        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            selectedImageUri = data.getData().toString();
            Uri myUri = Uri.parse(selectedImageUri);
            imageView.setImageURI(myUri);
        }
    }

    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    String [] proj={MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImageUri, proj, null, null, null);
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAA" + cursor.getString(column_index));

                    Bitmap bm = BitmapFactory.decodeFile(cursor.getString(column_index));
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.WEBP, 100, baos); // bm is the bitmap object
                    byte[] b = baos.toByteArray();
                    System.out.println(b.toString());
                }
            }
        }
    }*/

    public void addNewUser(String email, String password, String firstname, String lastname, String phonenr, String imageUri){
        User user = new User(firstname, lastname, email, password, phonenr, imageUri);
        storeUser.storeUserData(user);
    }
}