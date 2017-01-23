package com.example.ln_20.implicitexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private EditText editText,phnText,smsText;
    //private WebView webView;
    private Button button,phn,sms,btn_upload;
    private String URL;
    private ImageView imageView;
    private static final int PICK_IMAGE = 1;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //webView = (WebView) findViewById(R.id.editText);
        editText = (EditText) findViewById(R.id.editText);
        phnText = (EditText) findViewById(R.id.phn_num_Edit);
        smsText = (EditText) findViewById(R.id.smsEdit);
        button = (Button) findViewById(R.id.btn_go);
        phn = (Button) findViewById(R.id.dial);
        sms = (Button) findViewById(R.id.sms);
        btn_upload = (Button) findViewById(R.id.btn_pic);
        imageView = (ImageView) findViewById(R.id.imgView);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL = editText.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(URL));
                startActivity(intent);
                //webView.loadUrl("https://www.google.co.in");
            }
        });
        phn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+phnText.getText().toString()));
                //Intent intent = new Intent(Intent.ACTION_DIAL);
                //intent.setData(Uri.parse("tel:"+phnText.getText().toString()));
                startActivity(intent);
            }
        });
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(Intent.ACTION_SENDTO);
                Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+smsText.getText().toString()));
                //intent.addCategory(Intent.CATEGORY_APP_MESSAGING);
                //intent.setAction(Intent.EXTRA_SUBJECT);
                intent.putExtra("sms_body","Hello brother");
                //intent.setType("vnd.android-dir/mms-sm");
                //intent.setData(Uri.parse("Hello world"));
                startActivity(intent);
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK){
            try{
                if(bitmap != null){
                    bitmap.recycle();
                }
                InputStream stream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(stream);
                stream.close();
                imageView.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
