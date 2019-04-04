package com.testcom.attendon;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import java.util.ArrayList;

public class Class1 extends Fragment {
    private RecyclerView recyclerView;
    private Class1Adapter adapter;
    private ArrayList<EnterData> enterDataArrayList;
    private FloatingActionButton join;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class1, container,false);
        addData();

        recyclerView = (RecyclerView) view.findViewById(R.id.class1_recycle);

        adapter = new Class1Adapter(enterDataArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(adapter);

        join = (FloatingActionButton) view.findViewById(R.id.class1_join);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog di = new Dialog(v.getContext());
                di.requestWindowFeature(Window.FEATURE_NO_TITLE);
                di.setContentView(R.layout.dialog_join);

                Button btn1 = (Button) di.findViewById(R.id.join_cancel);
                Button btn2 = (Button) di.findViewById(R.id.join_join);

                btn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        di.dismiss();
                    }
                });

                btn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
                di.show();
            }
        });

        return view;
    }




    void addData(){
        enterDataArrayList = new ArrayList<>();
        enterDataArrayList.add(new EnterData("Bahasa Indonesia", "09.00-10.00", "25-4-2019"));
        enterDataArrayList.add(new EnterData("Matematika", "09.00-10.00", "26-4-2019"));
        enterDataArrayList.add(new EnterData("Sains", "09.00-10.00", "27-4-2019"));
        enterDataArrayList.add(new EnterData("Bahasa Inggris", "09.00-10.00", "28-4-2019"));
        enterDataArrayList.add(new EnterData("Bahasa Indonesia", "09.00-10.00", "29-4-2019"));
        enterDataArrayList.add(new EnterData("Matematika", "09.00-10.00", "30-4-2019"));
    }
}
