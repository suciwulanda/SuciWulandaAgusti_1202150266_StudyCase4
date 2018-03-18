package com.android.suci.suciwulandaagusti_1202150266_studycase4;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String[] item;
    private ListView listView;
    private Button btn_async;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //referencing list nama dan button async
        listView = findViewById(R.id.listview_nama);
        btn_async = findViewById(R.id.btnAsync);
        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        //button onClick
        btn_async.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start class async task
                new ListAsync(MainActivity.this).execute();
            }
        });

        //get resource string array
        item = getResources().getStringArray(R.array.listNama);

        //create array adapter with empty array list
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                new ArrayList<String>()
        );

        //setting adapter
        listView.setAdapter(adapter);
    }

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
            case R.id.actListname :
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                finish();
                return true;
            case R.id.actCariGambar :
                Intent intent2 = new Intent(this, FindGambar.class);
                startActivity(intent2);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public class ListAsync extends AsyncTask<Void,String,Void> { //<parameter, progress, result>
        ArrayAdapter<String> adapter;
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        private int counter=1;

        public ListAsync(MainActivity activity){
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
                    ListAsync.this.cancel(true);
                    dialog.dismiss();
                }
            });
            dialog.show();
            adapter = (ArrayAdapter<String>)listView.getAdapter();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (String name : item){
                publishProgress(name);
                try{
                    Thread.sleep(500); //selang waktu untuk menampilkan list per list
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            adapter.add(values[0]);
            Integer current_status = (int) ((counter/(float)item.length)*100);
            mProgressBar.setProgress(current_status);

            //set progress only working for horizontal loading
            dialog.setProgress(current_status);

            //set message will not working when using horizontal loading
            dialog.setMessage(String.valueOf(current_status+"%"));
            counter++;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
        }
    }
}
