package com.example.owner.quizquest;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
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
    Context context;
    private String nickname;
    public ClassAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Class> objects){
        super(context, resource, objects);
        this.classes = objects;
        this.context = context;
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
            viewHolder.edit = convertView.findViewById(R.id.imgBtnEditClass);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SharedPreferences settings = context.getSharedPreferences(MainActivity.PREF_NAME, 0);
        if(settings.contains(aClass.getName())){
            viewHolder.nickName.setText(settings.getString(aClass.getName(), ""));
        }

        viewHolder.className.setText(aClass.getName());
        viewHolder.teacherName.setText(aClass.getTeacher());


        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v, aClass);
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
        ImageButton edit;

    }

    public void showPopup(View anchorView, final Class aClass){
        nickname = null;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View popupView  = inflater.inflate(R.layout.layout_edit_class, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        final EditText classNickname = popupView.findViewById(R.id.etClassNickname);
        Button cancel = popupView.findViewById(R.id.btnNicknameCancel);
        Button addNickname = popupView.findViewById(R.id.btnAddNickname);


        popupWindow.setFocusable(true);
        popupWindow.update();


        int strokeWidth = 5; // 5px not dp
        int roundRadius = 15; // 15px not dp
        int strokeColor = Color.parseColor("#2E3135");
        int fillColor = getPrimaryColor();

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(fillColor);
        gd.setCornerRadius(roundRadius);
        gd.setStroke(strokeWidth, strokeColor);

        popupWindow.setBackgroundDrawable(gd);

        int location[] = new int[2];
        anchorView.getLocationOnScreen(location);

        popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, location[0], location[1] + anchorView.getHeight());

        addNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test", "Add class was clicked");
                nickname = classNickname.getText().toString();
                aClass.setNickName(nickname);
                ClassAdapter.this.notifyDataSetChanged();
                SharedPreferences settings = context.getSharedPreferences(MainActivity.PREF_NAME,0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(aClass.getName(), nickname);
                editor.apply();
                popupWindow.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test", "Cancel was clicked");
                popupWindow.dismiss();
            }
        });
    }

    private int getPrimaryColor(){
        TypedValue typedValue = new TypedValue();

        TypedArray a = getContext().obtainStyledAttributes(typedValue.data, new int[] {R.attr.colorPrimary});
        int color = a.getColor(0,0);
        a.recycle();
        return color;
    }




}
