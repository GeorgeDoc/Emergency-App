package com.example.emergencyapp;

import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;

public class EmergencyTypeFragment extends Fragment implements View.OnClickListener {     // 1.

    LocationManager locationManager;                                                                // 3. Initiate locationManager class to get lat, long, etc
    TextView tvTop;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_emergency_type, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView fire, traffic, violence, medical;                                                  // HOOKS & onClickListeners
        fire = (CardView) view.findViewById(R.id.cardView_fire);
        fire.setOnClickListener(this);

        traffic = (CardView) view.findViewById(R.id.cardView_traffic);
        traffic.setOnClickListener(this);

        violence = (CardView) view.findViewById(R.id.cardView_violence);
        violence.setOnClickListener(this);

        medical = (CardView) view.findViewById(R.id.cardView_medical);
        medical.setOnClickListener(this);

        tvTop = (TextView) view.findViewById(R.id.textview_first);

        view.findViewById(R.id.logout_first).setOnClickListener(new View.OnClickListener() {        // LOG OUT
            @Override
            public void onClick(View view) {
                        FirebaseAuth.getInstance().signOut();                                       //sign out and
                        startActivity(new Intent(getActivity(), Login.class));                      //navigate to login
            }
        });

        view.findViewById(R.id.profile_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               NavHostFragment.findNavController(EmergencyTypeFragment.this)                       //navigate to profile frag
                       .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

        view.findViewById(R.id.bForm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), Form.class));
            }
        });
    }

    public void onClick(View view) {                                                                // EMERGENCY CLICKS
        Bundle bundle = new Bundle();
        String type = "";

        try {
            switch (view.getId()) {                                                                 // EMERGENCY TYPE SELECTION
                case R.id.cardView_fire:
                    type = "Φωτιά";
                    break;
                case R.id.cardView_traffic:
                    type = "Τροχαίο";
                    break;
                case R.id.cardView_violence:
                    type = "Περιστατικό βίας";
                    break;
                case R.id.cardView_medical:
                    type = "Ιατρικό";
                    break;
            }
            bundle.putString("type", type);
            Intent in = new Intent(view.getContext(), Form.class);
            in.putExtras(bundle);
            startActivity(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}