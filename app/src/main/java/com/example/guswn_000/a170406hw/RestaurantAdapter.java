package com.example.guswn_000.a170406hw;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
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
    ArrayList<Restaurant> filtereddata = new ArrayList<>();
    Context context;
    Filter listfilter;


    public RestaurantAdapter( Context context,ArrayList<Restaurant> data) {
        this.data = data;
        this.context = context;
        this.filtereddata = data;
    }

    @Override
    public int getCount() {
        return filtereddata.size();
    }

    @Override
    public Object getItem(int position) {
        return filtereddata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            convertView = LayoutInflater.from(context).inflate(R.layout.itemlayout, null);
        }
        TextView t1 = (TextView)convertView.findViewById(R.id.tvname);
        TextView t2 = (TextView)convertView.findViewById(R.id.tvtelnum);
        ImageView imgv = (ImageView)convertView.findViewById(R.id.imageView);
        CheckBox cb = (CheckBox)convertView.findViewById(R.id.checkBox);

        Restaurant one = filtereddata.get(position);
        one.setCheckBox(cb);
        filtereddata.set(position,one);
        data.set(position,one);

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
        if(listfilter == null)
        {
            listfilter = new ListFilter();
        }

        return listfilter;
    }
    //체크박슨어떡해..
    private class ListFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults results = new FilterResults();
            if(constraint == null || constraint.length() == 0)
            {
                results.values = data;
                results.count = data.size();
            }
            else
            {
                ArrayList<Restaurant> itemList = new ArrayList<Restaurant>();
                for(Restaurant item : data)
                {
                    if(item.getName().toUpperCase().contains(constraint.toString().toUpperCase()))
                    {
                        itemList.add(item);
                    }
                }
                results.values = data;
                results.count = data.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            filtereddata = (ArrayList<Restaurant>) results.values;
            if(results.count > 0)
            {
                notifyDataSetChanged();
            }
            else
            {
                notifyDataSetInvalidated();
            }
        }
    }

    public void add(Restaurant restaurant)
    {
        data.add(restaurant);
    }



    public void showcheck()
    {
        for(int i = 0; i< filtereddata.size(); i++)
        {
            filtereddata.get(i).getCheckBox().setVisibility(View.VISIBLE);
        }
        this.notifyDataSetChanged();
    }

    public void delchecked()
    {
        for(int i = 0 ; i < filtereddata.size() ; i++)
        {
            final int pos = i;
            if(filtereddata.get(i).getCheckBox().isChecked())
            {
                AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dlg.setTitle("삭제확인")
                        .setIcon(R.drawable.potato)
                        .setMessage("선택한 맛집을 정말 삭제할까요?")
                        .setNegativeButton("취소",null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                filtereddata.get(pos).getCheckBox().setVisibility(View.INVISIBLE);
                                filtereddata.get(pos).getCheckBox().setChecked(false);
                                filtereddata.remove(pos);
                                notifyDataSetChanged();
                            }
                        })
                        .show();
            }
            else
            {
                filtereddata.get(i).getCheckBox().setVisibility(View.INVISIBLE);
            }
        }
    }

}
