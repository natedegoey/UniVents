package com.example.myapp.ui.education;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapp.Event;
import com.example.myapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class EducationFragment extends Fragment {
    Event[] events;
    DatabaseReference reff;
    private EducationViewModel educationViewModel;
    TextView t1,t2,t3,t4;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        educationViewModel =
                ViewModelProviders.of(this).get(EducationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_education, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        educationViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        reff = FirebaseDatabase.getInstance().getReference().child("Event");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long numEvents = dataSnapshot.getChildrenCount();
                events = new Event[(int)numEvents];
                int counter = 0;
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Event event = new Event();
                    if (dataSnapshot.child(child.getKey()).child("type").getValue().toString() == "Personal") continue;
                    event.setTitle(dataSnapshot.child(child.getKey()).child("title").getValue().toString());
                    event.setDate(dataSnapshot.child(child.getKey()).child("date").getValue().toString());
                    event.setTime(dataSnapshot.child(child.getKey()).child("time").getValue().toString());
                    event.setLocation(dataSnapshot.child(child.getKey()).child("location").getValue().toString());
                    event.setPrice(dataSnapshot.child(child.getKey()).child("price").getValue().toString());
                    event.setType(dataSnapshot.child(child.getKey()).child("type").getValue().toString());
                    events[counter] = event;
                    counter++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return root;
    }

}