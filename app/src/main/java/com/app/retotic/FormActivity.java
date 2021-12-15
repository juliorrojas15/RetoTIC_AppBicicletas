package com.app.retotic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.retotic.datos.DBHelper;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FormActivity extends AppCompatActivity {

    private final int REQUEST_CODE_GALLERY = 999;
    private LinearLayout olayoutForm;
    private TextView otvTexto;
    private EditText oetCampo1, oetCampo2, oetCampo3, oetID;
    private Button obtnBuscar, obtnInsertar, obtnConsultar, obtnEliminar, obtnActualizar;
    private ImageView oimgForm;
    private DBHelper dbHelper;
    String sCampo1Insert, sCampo2Insert, sCampo3Insert;
    byte[] imgInsert;
    String nameExtra = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        olayoutForm = (LinearLayout) findViewById(R.id.layoutForm);
        otvTexto = (TextView) findViewById(R.id.tvTexto);
        oetCampo1 = (EditText) findViewById(R.id.etCampo1);
        oetCampo2 = (EditText) findViewById(R.id.etCampo2);
        oetCampo3 = (EditText) findViewById(R.id.etCampo3);
        oetID = (EditText) findViewById(R.id.etID);
        obtnBuscar = (Button) findViewById(R.id.btnBuscar);
        obtnInsertar = (Button) findViewById(R.id.btnInsertar);
        obtnConsultar = (Button) findViewById(R.id.btnConsultar);
        obtnEliminar = (Button) findViewById(R.id.btnEliminar);
        obtnActualizar = (Button) findViewById(R.id.btnActualizar);
        oimgForm = (ImageView) findViewById(R.id.imgForm);
        dbHelper = new DBHelper(getApplicationContext());

        Intent intent = getIntent();
        nameExtra = intent.getStringExtra("nameExtra");

        otvTexto.setText(nameExtra);

        if("Productos".equals(nameExtra)){
            oetCampo1.setHint("Name");
            oetCampo2.setHint("Description");
            oetCampo3.setHint("Prices");
        }
        else if ("Servicios".equals(nameExtra)){
            oetCampo1.setHint("Name");
            oetCampo2.setHint("Description");
            oetCampo3.setHint("Prices");
        }
        else if ("Sucursales".equals(nameExtra)){
            oetCampo1.setHint("Name");
            oetCampo2.setHint("Description");
            oetCampo3.setHint("Address");
            oetCampo3.setInputType(InputType.TYPE_CLASS_TEXT);

        }

        obtnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        FormActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });
        obtnInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    llenarCampos();
                    dbHelper.insertData(sCampo1Insert,sCampo2Insert,sCampo3Insert,imgInsert,nameExtra);
                    Toast.makeText(getApplicationContext(),"Insert Success",Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        obtnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Cursor cursor = dbHelper.getDataById(nameExtra,oetID.getText().toString());
                    while(cursor.moveToNext()){
                        oetCampo1.setText(cursor.getString(1));
                        oetCampo2.setText(cursor.getString(2));
                        oetCampo3.setText(cursor.getString(3));
                        byte[] imgConsultada = cursor.getBlob(4);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imgConsultada,0,imgConsultada.length);
                        oimgForm.setImageBitmap(bitmap);

                    }
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        obtnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    dbHelper.deleteDataById(nameExtra,oetID.getText().toString().trim());
                    limpiarCampos();
                    Snackbar.make(view,"Eliminado",Snackbar.LENGTH_LONG).show();
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        });

        obtnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    llenarCampos();
                    dbHelper.updateProductoById(nameExtra,
                            oetID.getText().toString().trim(),
                            sCampo1Insert,
                            sCampo2Insert,
                            sCampo3Insert,
                            imgInsert);
                    limpiarCampos();
                    Toast.makeText(getApplicationContext(),"Item editado",Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void llenarCampos(){
        sCampo1Insert = oetCampo1.getText().toString().trim();
        sCampo2Insert = oetCampo2.getText().toString().trim();
        sCampo3Insert = oetCampo3.getText().toString().trim();
        imgInsert = imageViewToByte(oimgForm);
    }

    public void limpiarCampos(){
        oetCampo1.setText("");
        oetCampo2.setText("");
        oetCampo3.setText("");
        oimgForm.setImageResource(R.mipmap.ic_launcher_round);
    }


    public byte[] imageViewToByte(ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] byteArray = stream.toByteArray();
        return  byteArray;
    }

                                  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }
            else{
                Toast.makeText(getApplicationContext(),"Sin Permisos",Toast.LENGTH_SHORT).show();
            }

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                oimgForm.setImageBitmap(bitmap);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}