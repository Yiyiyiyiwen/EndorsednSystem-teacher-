package com.example.even1.endorsedsystemteacher.View.MyClass;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.even1.endorsedsystemteacher.R;

import java.io.File;

import antlr.Tool;
import de.hdodenhof.circleimageview.CircleImageView;

public class AddClass extends AppCompatActivity implements View.OnClickListener {

    private TextView Ttitle;
    private Button button;
    private CircleImageView head;
    private static final int IMAGE = 1;
    public static final int TAKE_PHOTO = 2;
    private Toolbar toolbar;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        Ttitle = (TextView) findViewById(R.id.title);
        button = (Button)findViewById(R.id.button);
        head = (CircleImageView)findViewById(R.id.head);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        init();
    }

    private void init() {
        Ttitle.setText("创建班级");
        button.setOnClickListener(this);
        head.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                Toast.makeText(AddClass.this, "创建成功", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.head:
                showListDialog();
        }
    }
    private void showListDialog() {
        final String[] items = { "拍照","从相册选择图片" };
        AlertDialog.Builder listDialog =
                new AlertDialog.Builder(AddClass.this);
        listDialog.setTitle("选择头像");
        listDialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        takepicture();
                        break;
                    case 1:
                        choosepic();
                        break;
                }
            }
        });
        listDialog.show();
    }

    private void takepicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {//判断是否有相机应用
            startActivityForResult(takePictureIntent, TAKE_PHOTO);
        }
    }

    private void choosepic() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO && resultCode == Activity.RESULT_OK && data != null){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            head.setImageBitmap(imageBitmap);
        }
        //获取图片路径
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }


    }

    //加载图片
    private void showImage(String imaePath){
        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        head.setImageBitmap(bm);
    }
}
