package com.xiaofangfang.materialdesigndemo.RecyclerViewAdapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public abstract class MyCommandAdapter<T> extends RecyclerView.Adapter<MyCommandAdapter.MyViewHolder> {

    private ArrayList<T> data;
    private int resLayout;
    private Context context;
    MyViewHolder myViewHolder;

    public MyCommandAdapter(Context context, int resLayout, ArrayList<T> data) {
        this.context = context;
        this.resLayout = resLayout;
        this.data = data;
    }

    public abstract <T> void bind(MyViewHolder holder, T t);


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(resLayout, parent, false);

        myViewHolder = new MyViewHolder(view) {

            @Override
            public <T> void bind(MyViewHolder holder, T t) {
                MyCommandAdapter.this.bind(holder, t);
            }
        };

        myViewHolder.view = view;

        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(holder, data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static abstract class MyViewHolder extends RecyclerView.ViewHolder {

        View view = null;

        public MyViewHolder(View itemView) {
            super(itemView);
        }

        public abstract <T> void bind(MyViewHolder holder, T t);


        public void setText(int viewId, String text) {

            View viewText = this.view.findViewById(viewId);
            if (viewText instanceof TextView) {
                ((TextView) viewText).setText(text);
            }
        }

    }


    public static class Person {

        private String name;
        private String address;

        public Person(String name, String address) {
            this.name = name;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }


}