package tw.tcnr03.frisign01;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Frilist extends ListActivity {
    private TextView count_t,tvTitle;
    private Button b001, b002, b003, b004;
    private EditText e001, e002, e003, e004;
    private FriendDbHelper dbHper;
    private static final String DB_FILE = "friends.db";
    private static final String DB_TABLE = "member";
    private static final int DBversion = 1;
    private String tname;
    private String tgrp;
    private String taddress;
    private TextView t001;
    private List<Map<String, Object>> mList;
    private ArrayList<String> recSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frilist);
        setupViewComponent();
        initDB();
//        count_t.setText("共計:" + Integer.toString(dbHper.RecCount()) + "筆");
    }
    private void setupViewComponent() {
        initDB();
        tvTitle = (TextView) findViewById(R.id.tvIdTitle);
        tvTitle.setTextColor(ContextCompat.getColor(this, R.color.Navy));
        tvTitle.setBackgroundResource(R.color.Aqua);
        tvTitle.setText("顯示資料： 共 " + recSet.size() + " 筆");
        //===========取SQLite 資料=============
        mList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < recSet.size(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            String[] fld = recSet.get(i).split("#");
//            item.put("imgView", R.drawable.userconfig);
            item.put("txtView",  getString(R.string.fri_i001) + fld[1] + "\n"
                    + getString(R.string.fri_i002) + fld[2] );
            mList.add(item);
        }
        //=========設定listview========
        SimpleAdapter adapter = new SimpleAdapter(this,
                mList, R.layout.fri_list_item,
                new String[]{"txtView"},
                new int[]{ R.id.txtView} );
        setListAdapter(adapter);
        //----------------------------------
        ListView listview = getListView();
        listview.setTextFilterEnabled(true);
        listview.setOnItemClickListener(listviewOnItemClkLis);
    }
    private ListView.OnItemClickListener listviewOnItemClkLis = new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String s = "你按下第 "  + Integer.toString(position +1)   + "筆\n"
                    + ((TextView) view.findViewById(R.id.txtView)).getText()   .toString();
            tvTitle.setText(s);
        } };
    private void initDB() {
        if(dbHper==null){
            dbHper = new FriendDbHelper(this, DB_FILE, null, DBversion);
            recSet = dbHper.getRecSet();
        }
    }
//--------------------生命週期------------------------
    @Override
    protected void onPause() {
        super.onPause();
        if (dbHper != null) {
            dbHper.close();
            dbHper = null;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (dbHper == null)
            dbHper = new FriendDbHelper(this, DB_FILE, null, DBversion);
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
}//-----------END