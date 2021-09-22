package tw.tcnr03.frisign01;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Friedit extends AppCompatActivity implements View.OnClickListener {
    private Button cancel_btn, ok_btn;
    private Intent intent = new Intent();
    private TextView count_t;
    private EditText b_name;
    private String msg= null;
    private FriendDbHelper dbHper;
    private static final String DB_FILE = "friends.db";
    private static final String DB_TABLE = "member";
    private static final int DBversion = 1;
    private TextView tvTitle;
    private ArrayList<String> recSet;
    private String tdate;
    private DatePicker b_date;
//    private String t_date;
    private TextView tdatea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friedit);
        setupViewComponent();

    }

    private void setupViewComponent() {
        cancel_btn = (Button) findViewById(R.id.friedit_b001);
        ok_btn = (Button) findViewById(R.id.friedit_b002);

        b_name = (EditText) findViewById(R.id.friedit_e001);
        b_date = (DatePicker)findViewById(R.id.friedit_date01);
        //------------------------------------------
        tdatea = (TextView)findViewById(R.id.friedit_date02);  //隱藏顯示日期  為了轉換成字串
        count_t = (TextView) findViewById(R.id.fri_count);  //顯示幾筆
        //----------------------------------------------
        cancel_btn.setOnClickListener(this);
        ok_btn.setOnClickListener(this);

        //------------------------------------------------------
        initDB();
        //tvTitle.setTextColor(ContextCompat.getColor(this, R.color.Navy));
        //-------------------------------------------------------
    }
    private void initDB() {
        if (dbHper == null)
            dbHper = new FriendDbHelper(this, DB_FILE, null, DBversion);
        recSet = dbHper.getRecSet();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.friedit_b001:
                this.finish();
               // startActivity(intent);  //執行
                break;
            case R.id.friedit_b002:   //新增
                // 查詢name跟在e001上打得是否有有此筆資料
                String tname = b_name.getText().toString().trim();

                 tdatea.setText(b_date.getYear() + "/"+
                         (b_date.getMonth()+1) + "/"+
                         b_date.getDayOfMonth());

                String tdate = tdatea.getText().toString().trim();
                //String taddress = e003.getText().toString().trim();
                if (tname.equals("") ) {
                    Toast.makeText(Friedit.this, "資料空白無法新增 !", Toast.LENGTH_SHORT).show();
                    return;
                }
                msg = null;
                long rowID = dbHper.insertRec(tname,tdate);

                if (rowID != -1) {

                    msg = "新增記錄  成功 ! \n" + "目前資料表共有 " + dbHper.RecCount() + " 筆記錄 !";
                    //*************************************
                    setupViewComponent();
                    u_insert();
                    //ctlLast();  //成功跳到最後一筆
                } else {
                    msg = "新增記錄  失敗 !";
                }
                Toast.makeText(Friedit.this, msg, Toast.LENGTH_SHORT).show();
//                count_t.setText("共計:" + Integer.toString(dbHper.RecCount()) + "筆");
                break;
        }
    }
    private void u_insert() {
        b_name.setText("");
    }
    //-----Item表單-----------
    @Override
    public void onBackPressed() {
    }
    //-------------------END-------------------------
}