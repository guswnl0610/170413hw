package com.example.guswn_000.a170406hw;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
                seldel.setText("삭제");


                break;
        }

    }
    public void setListView()
    {
        listView = (ListView)findViewById(R.id.listview);

        //데이터를 만들고
        //data.add("무언가");

        //어댑터 만듬
//        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,restdata);

        adapter2 = new RestaurantAdapter(this,restlist);
        listView.setAdapter(adapter2);

        //꾹 눌렀을때 삭제
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                //정보를 삭제하는지 묻는 대화상자 나타남
                AlertDialog.Builder dlg = new AlertDialog.Builder(view.getContext());
                dlg.setTitle("삭제확인")
                        .setIcon(R.drawable.potato)
                        .setMessage("선택한 맛집을 정말 삭제하시겠습니까?")
                        .setNegativeButton("취소",null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //삭제 클릭시 아래꺼
                                restlist.remove(position);
                                adapter2.notifyDataSetChanged();
                                Snackbar.make(view,"삭제되었습니다.",2000).show();
                            }
                        })
                        .show();
                return true;
            }
        });

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
