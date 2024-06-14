package com.example.todolist.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.todolist.R;
import com.example.todolist.adapter.ThingAdapter;
import com.example.todolist.db.dao.ThingDao;
import com.example.todolist.db.domain.Thing;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView list_view;
    private EditText et_des;
    private EditText et_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initUI();
        initData();
    }

    private void initData() {
        ThingDao thingDao = ThingDao.getInstance(getApplicationContext());
        List<Thing> things = thingDao.findAll();

        list_view.setAdapter(new ThingAdapter(getApplicationContext(), things));
    }

    private void initUI() {
        list_view = findViewById(R.id.list_view);
        findViewById(R.id.add).setOnClickListener(this);
        // 长按事件
//         TODO：短按
        list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("onItemLongClickPosition",position+"");
                Log.e("onItemLongClickId",id+"");
                showDeleteDialog(id);
                return false;
            }
        });
    }

    private void showDeleteDialog(long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alertDialog = builder.create();
        View view = View.inflate(getApplicationContext(), R.layout.dialog_delete, null);
        alertDialog.setView(view);
        alertDialog.show();

        view.findViewById(R.id.cancel_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        view.findViewById(R.id.confirm_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThingDao thingDao = ThingDao.getInstance(getApplicationContext());
                thingDao.delete(id);
                initData();
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Log.d("aaaaaaa",v.getId()+"");
        if (v.getId() == R.id.add) {
            showAlertDialog();
        }
    }

    private void showAlertDialog() {
        // 实例化弹窗的构造者对象
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // 自定义弹窗内容，所以需要调用builder的create和dialog的setview
        AlertDialog alertDialog = builder.create();
        View view = View.inflate(getApplicationContext(), R.layout.dialog_add, null);
        alertDialog.setView(view);
        alertDialog.show();

        et_title = view.findViewById(R.id.et_title);
        et_des = view.findViewById(R.id.et_des);
        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = et_title.getText().toString();
                String des = et_des.getText().toString();
                if (title.isEmpty() || des.isEmpty()){
                    Toast.makeText(getApplicationContext(),"不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                save2db(title,des);
                alertDialog.dismiss();
                Toast.makeText(getApplicationContext(), "添加成功",Toast.LENGTH_SHORT).show();
            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    private void save2db(String title, String des) {
        ThingDao thingDao = ThingDao.getInstance(getApplicationContext());
        Thing thing = new Thing(title, des);
        thingDao.insert(thing);
        initData();
    }
}