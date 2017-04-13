package com.example.guswn_000.a170406hw;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by guswn_000 on 2017-04-13.
 */

public class RestaurantAdapter extends BaseAdapter implements Filterable
{
    ArrayList<Restaurant> data = new ArrayList<>();
    Context context;


    public RestaurantAdapter( Context context,ArrayList<Restaurant> data) {
        this.data = data;
    data.r
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    ViewGroup p;
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        p=parent;
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.itemlayout, null);
        }
        TextView t1 = (TextView)convertView.findViewById(R.id.tvname);
        TextView t2 = (TextView)convertView.findViewById(R.id.tvtelnum);
        ImageView imgv = (ImageView)convertView.findViewById(R.id.imageView);

        Restaurant one = data.get(position);
        t1.setText(one.getName());
        t2.setText(one.getTel());
        if(one.getCategorynum() == 1)//치킨=1,피자=2,햄버거=3
        {
            imgv.setImageResource(R.drawable.chicken);
        }
        else if(one.getCategorynum() == 2)
        {
            imgv.setImageResource(R.drawable.pizza);
        }
        else
        {
            imgv.setImageResource(R.drawable.hamburger);
        }
        return convertView;
    }

    Comparator<Restaurant> nameAsc = new Comparator<Restaurant>() {
        @Override
        public int compare(Restaurant o1, Restaurant o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };

    Comparator<Restaurant> cateAsc = new Comparator<Restaurant>() {
        @Override
        public int compare(Restaurant o1, Restaurant o2) {
            if(o1.getCategorynum() < o2.getCategorynum())
                return -1;
            else if (o1.getCategorynum() == o2.getCategorynum())
                return 0;
            else
                return 1;
        }
    };

    final static int NAME_ASC = 0;
    final static int CATE_ASC = 1;

    public void setsort(int sorttype)
    {
        if (sorttype == NAME_ASC)
        {
            Collections.sort(data,nameAsc);
            this.notifyDataSetChanged(); // 얘의 this는 어댑터
        }
        else if (sorttype == CATE_ASC)
        {
            Collections.sort(data,cateAsc);
            this.notifyDataSetChanged(); // 얘의 this는 어댑터
        }
    }

    //검색하는것도 만들어야함

    @Override
    public Filter getFilter()
    {

        return null;
    }
    //체크박슨어떡해..

    public void check()
    {
//        for
        p.getChildAt(0).findViewById(R.id.checkBox);
        //포문 돌려서 일단 invisible인거 visible로 바꾸기
        //체크된거 확인해서 data에서 remove하고 notifyㅙ주개ㄹㅇㅎㄹㅇㄴ
    }


}
