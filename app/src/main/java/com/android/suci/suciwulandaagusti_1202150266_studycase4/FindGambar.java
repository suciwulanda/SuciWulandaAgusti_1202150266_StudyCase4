package com.android.suci.suciwulandaagusti_1202150266_studycase4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;


public class FindGambar extends AppCompatActivity {

    EditText inputUrl;
    Button btnGambar;
    ImageView imgGambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_gambar);

        //referencing
        inputUrl = findViewById(R.id.inputGambar);
        btnGambar = findViewById(R.id.btnGambar);
        imgGambar = findViewById(R.id.imgGambar);

        btnGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = inputUrl.getText().toString();
                new GambarAsync(FindGambar.this).execute(url);
            }
        });
    }

    //create option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        //inflate menu
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.actListname:
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.actCariGambar:
                Intent intent2 = new Intent(this, FindGambar.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public class GambarAsync extends AsyncTask<String, String, Bitmap>{
        ProgressDialog dialog;
        public GambarAsync(FindGambar activity) {
            dialog = new ProgressDialog(activity);
        }

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Loading Data");
            dialog.setCancelable(false);
            dialog.setProgress(0);
            dialog.setMax(100);
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Progress", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    GambarAsync.this.cancel(true);
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try{
                URL url = new URL(strings[0]);
                bitmap = BitmapFactory.decodeStream((InputStream) url.getContent());
            }catch(Exception e){
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imgGambar.setImageBitmap(bitmap);
            dialog.dismiss();
        }
    }
}
