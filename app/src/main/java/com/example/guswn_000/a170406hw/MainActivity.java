package com.example.guswn_000.a170406hw;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    RestaurantAdapter adapter2;

    ArrayList<Restaurant> restlist = new ArrayList<Restaurant>();
    ListView listView;
    final int REST_INFO = 21;
    final int NEW_REST = 22;
    Button seldel;
    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("나의 맛집");
        setListView();
        seldel = (Button)findViewById(R.id.btnselect);
        et = (EditText)findViewById(R.id.editText);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String filterText = s.toString();
                if(filterText.length() > 0)
                {
                    listView.setFilterText(filterText);
                }
                else
                {
                    listView.clearTextFilter();
                }
            }
        });
    }


    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.plus:
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivityForResult(intent,NEW_REST);
                break;
            case R.id.btnnamesort:
                adapter2.setsort(RestaurantAdapter.NAME_ASC);
                break;
            case R.id.btncat:
                adapter2.setsort(RestaurantAdapter.CATE_ASC);
                break;
            case R.id.btnselect:
                if(seldel.getText().toString().equals("선택"))
                {
                    seldel.setText("삭제");
                    adapter2.showcheck();
                }
                else
                {
                    seldel.setText("선택");
                    adapter2.delchecked();
                }



                break;
        }

    }
    public void setListView()
    {
        listView = (ListView)findViewById(R.id.listview);
        adapter2 = new RestaurantAdapter(this,restlist);
        listView.setAdapter(adapter2);


        //클릭시 상세정보가 나타남
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(MainActivity.this,Main3Activity.class);
                Restaurant res = restlist.get(position);
                intent.putExtra("restinfo",res);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == NEW_REST)
        {
            if(resultCode == RESULT_OK)
            {
                Restaurant res = data.getParcelableExtra("newrest"); //새 레스토랑 받아옴
                restlist.add(res);
                adapter2.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
