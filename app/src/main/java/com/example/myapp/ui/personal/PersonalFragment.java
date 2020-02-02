package com.example.myapp.ui.personal;

import android.content.Intent;
import android.net.Uri;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PersonalFragment extends Fragment {
    Event[] events;
    DatabaseReference reff;
    private PersonalViewModel personalViewModel;

    TextView title;
    TextView date;
    TextView time;
    TextView loco;
    FloatingActionButton arrow;
    FloatingActionButton location;
    int i=0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_personal, container, false);

        title = (TextView) root.findViewById(R.id.textView);
        loco = (TextView) root.findViewById(R.id.textView2);
        date = (TextView) root.findViewById(R.id.textView3);
        time = (TextView) root.findViewById(R.id.textView4);
        arrow = (FloatingActionButton) root.findViewById(R.id.floatingActionButton2);
        location = (FloatingActionButton) root.findViewById(R.id.floatingActionButton3);


        reff = FirebaseDatabase.getInstance().getReference().child("Event");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final long numEvents = dataSnapshot.getChildrenCount();
                events = new Event[(int)numEvents];
                int counter = 0;
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Event event = new Event();
                    if (dataSnapshot.child(child.getKey()).child("type").getValue().toString() == "Educational") continue;
                    event.setTitle(dataSnapshot.child(child.getKey()).child("title").getValue().toString());
                    event.setDate(dataSnapshot.child(child.getKey()).child("date").getValue().toString());
                    event.setTime(dataSnapshot.child(child.getKey()).child("time").getValue().toString());
                    event.setLocation(dataSnapshot.child(child.getKey()).child("location").getValue().toString());
                    event.setPrice(dataSnapshot.child(child.getKey()).child("price").getValue().toString());
                    event.setType(dataSnapshot.child(child.getKey()).child("type").getValue().toString());
                    events[counter] = event;
                    counter++;
                }


                title.setText(events[i].getTitle());
                date.setText("Date: "+events[i].getDate());
                time.setText("Time: "+events[i].getTime());
                loco.setText("Location: "+events[i].getLocation());

                arrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (i<(events.length-1))
                            i += 1;
                        else
                            i = 0;

                        title.setText(events[i].getTitle());
                        date.setText("Date: "+events[i].getDate());
                        time.setText("Time: "+events[i].getTime());
                        loco.setText("Location: "+events[i].getLocation());
                    }
                });

                location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("geo:0,0?q="+events[i].getLocation()));
                        startActivity(intent);

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return root;
    }
}