package tw.tcnr03.frisign01;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Frisign01 extends AppCompatActivity implements View.OnClickListener{

    private MediaPlayer starmusic;
    private Button Mainb001,Mainb002,Mainb003,Mainb004,edit_btn;
    private Intent intent=new Intent();
    private Uri uri;
    private Intent it;
    private Button del_btn,list_btn;
    private ArrayList<Map<String, Object>> mList;
    private ArrayList<String> recSet;
    private FriendDbHelper dbHper;
    private String msg = null;
    private static final String DB_FILE = "friends.db";
    private static final int DBversion = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frisign01);
        setupViewComponent();
    }
    private void setupViewComponent() {
        //-----開啟片頭音樂---------
        starmusic = MediaPlayer.create(getApplication(), R.raw.openmusic);
        starmusic.start();
        //---------------------------------------------------------
        edit_btn = (Button) findViewById(R.id.fri_b000); //新增頁面
        edit_btn.setOnClickListener(this);
        del_btn = (Button) findViewById(R.id.fri_del001);
        del_btn.setOnClickListener(this);

        list_btn = (Button) findViewById(R.id.fri_list);
        list_btn.setOnClickListener(this);
        initDB();
    }
    private void initDB() {
        if(dbHper==null){
            dbHper = new FriendDbHelper(this, DB_FILE, null, DBversion);
            recSet = dbHper.getRecSet();
        }
    }
    //------------------連接各個class---------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fri_b000:  //連接新增頁面
                intent.setClass(Frisign01.this, Friedit.class);
                startActivity(intent);  //執行
                break;

            case R.id.fri_del001:
                MyAlertDialog aldDial = new MyAlertDialog(Frisign01.this);
                aldDial.setTitle("刪除資料");
                aldDial.setMessage("資料刪除無法復原\n確定將資料刪除嗎?");
                aldDial.setIcon(android.R.drawable.ic_dialog_info);
                aldDial.setCancelable(false); //返回鍵關閉
                aldDial.setButton(BUTTON_POSITIVE, "確定刪除", aldBtListener);
                aldDial.setButton(BUTTON_NEGATIVE, "取消刪除", aldBtListener);
                aldDial.show();
                break;

            case R.id.fri_list:
                intent.setClass(Frisign01.this, Frilist.class);
                startActivity(intent);  //執行
                break;
        }
    }
    //-------------------------------------------------
    private DialogInterface.OnClickListener aldBtListener =new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case BUTTON_POSITIVE:
                    int rowsAffected = dbHper.clearRec();   //--- 刪除所有資料
                    msg = "資料表已空 !共刪除" + rowsAffected + " 筆";
                    break;
                case BUTTON_NEGATIVE:
                    msg = "放棄刪除所有資料 !";
                    break;
            }
            Toast.makeText(Frisign01.this, msg, Toast.LENGTH_SHORT).show();
        }
    };
    //-----------------------選單---------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}