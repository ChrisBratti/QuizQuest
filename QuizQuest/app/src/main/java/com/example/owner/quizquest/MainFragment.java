package com.example.owner.quizquest;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    ArrayList<Class> classes;
    TextView nameDisplay;
    Button logOut;
    FirebaseUser currentUser;
    private DatabaseReference mDatabase;
    private ListView listView;
    private ClassAdapter adapter;
    ValueEventListener postListener;
    Class newClass;
    FloatingActionButton addClass;
    EditText classCode;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public void onStart() {
        super.onStart();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        classes = new ArrayList<Class>();

        postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot snapshot = dataSnapshot.child(currentUser.getUid()).child("Classes");
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    classes.add(postSnapshot.getValue(Class.class));
                }

                listView = getActivity().findViewById(R.id.list_view);

                adapter = new ClassAdapter(getContext(), R.layout.layout_view, classes);
                listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabase.addListenerForSingleValueEvent(postListener);

    }


    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        nameDisplay = getActivity().findViewById(R.id.tvNameMain);
        logOut = getActivity().findViewById(R.id.btnLogout);
        nameDisplay.setText(currentUser.getDisplayName());
        addClass = getActivity().findViewById(R.id.btnAddClass);
        classes = new ArrayList<Class>();


        TextView nameDisplay = getActivity().findViewById(R.id.tvNameProfile);

        HashMap<String, String> questions = new HashMap<String,String>();
        addQuestions(questions);


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(getActivity())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "Successfully logged out", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                            }
                        });
            }
        });

        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

    }

    public void showPopup(View anchorView){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.layout_add_class, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        classCode = popupView.findViewById(R.id.etClassCode);
        EditText classNickname = popupView.findViewById(R.id.etClassNickname);
        Button cancel = popupView.findViewById(R.id.btnCancelAddClass);
        Button addClass = popupView.findViewById(R.id.btnAddNewClass);


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

        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test", "Add class was clicked");
                final String code = classCode.getText().toString();

                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Class mClass = dataSnapshot.child("Classes").child(code).getValue(Class.class);
                        if(mClass == null){
                            Toast.makeText(getContext(), "That class code does not exist", Toast.LENGTH_SHORT).show();
                        }else{
                            mDatabase.child(currentUser.getUid()).child("Classes").child(code).setValue(mClass);
                            classes.add(mClass);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                mDatabase.addListenerForSingleValueEvent(postListener);
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

    private HashMap<String, String> addQuestions(HashMap<String,String> questions){
        questions.put("In 2003, which U.S. state was officially declared the birthplace of aviation?" , "North Carolina");
        questions.put("The Scarlet Letter is a historical fiction novel written by which American author?", "Nathaniel Hawthorne");
        questions.put("What 'King' of golf lent his name to a mixture of iced tea and lemonade?", "Arnold Palmer");
        questions.put("Which actor played Freddie Mercury in the 2018 film Bohemian Rhapsody?", "Rami Malek");
        questions.put("What two sisters faced each other in the finals of the French Open, Wimbledon, and US Open in 2002?", "Venus and Serena Williams");
        questions.put("In which Asian country is the city of Chiang Mai located?", "Thailand");
        questions.put("What is the chemical equation for hydrogen peroxide?", "H2O2");
        return questions;
    }

    private ArrayList<Class> addClasses(ArrayList<Class> classes){
        classes.add(new Class("Software Development Projects", "ITCS-4155", "Bojan Cukic", "0000"));
        classes.add(new Class("Mobile App Development", "ITCS- 4180", "Enas Al Kawasmi", "0001"));
        classes.add(new Class("Artificial Intelligence in Computer Games", "ITCS-4236", "Danny Jugan", "0011"));
        classes.add(new Class("Visualization and Visual Communication", "ITCS-4123", "Aidong Lu", "0111"));
        return classes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
