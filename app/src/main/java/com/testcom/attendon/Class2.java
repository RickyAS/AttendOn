package com.testcom.attendon;

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

import java.util.ArrayList;

public class Class2 extends Fragment {
    private RecyclerView recyclerView;
    private Class2Adapter adapter;
    private ArrayList<EnterData> enterDataArrayList;
    private FloatingActionButton create;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.class2, container,false);
        addData();

        recyclerView = (RecyclerView) view.findViewById(R.id.class2_recycle);

        adapter = new Class2Adapter(enterDataArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.setAdapter(adapter);

        create = (FloatingActionButton) view.findViewById(R.id.class2_create);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), Class2Create.class);
                startActivity(i);
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
