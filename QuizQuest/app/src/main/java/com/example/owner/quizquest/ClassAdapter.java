package com.example.owner.quizquest;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/*
Optional Group 1- Christopher Bratti and Katherine Bratti
Homework 07
 */

public class ClassAdapter extends ArrayAdapter<Class> {
    private List<Class> classes;
    public ClassAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Class> objects){
        super(context, resource, objects);
        this.classes = objects;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        final Class aClass = getItem(position);

        ViewHolder viewHolder;

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_view, parent, false);
            viewHolder = new ViewHolder();

            //Sets the template attributes
            viewHolder.nickName = convertView.findViewById(R.id.tvClassName);
            viewHolder.teacherName = convertView.findViewById(R.id.tvTeacherName);
            viewHolder.className = convertView.findViewById(R.id.tvClassNumber);
            viewHolder.delete = convertView.findViewById(R.id.imgBtnDelete);
            viewHolder.edit = convertView.findViewById(R.id.imgBtnEditClass);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nickName.setText(aClass.getNickName());
        viewHolder.className.setText(aClass.getName());
        viewHolder.teacherName.setText(aClass.getTeacher());

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classes.remove(aClass);
                ClassAdapter.this.notifyDataSetChanged();
            }
        });

        return convertView;
    }

    /*
     * Defines the ViewHolder's attributes
     */
    private static class ViewHolder{
        TextView className;
        TextView teacherName;
        TextView nickName;
        ImageButton delete;
        ImageButton edit;

    }





}
