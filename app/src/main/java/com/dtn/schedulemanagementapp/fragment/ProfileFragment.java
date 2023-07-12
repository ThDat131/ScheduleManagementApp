package com.dtn.schedulemanagementapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
<<<<<<< HEAD
=======
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
>>>>>>> origin/dat

import com.dtn.schedulemanagementapp.activity.LoginActivity;
import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.activity.AdminActivity;
import com.dtn.schedulemanagementapp.activity.AdminUserActivity;
import com.dtn.schedulemanagementapp.activity.MainActivity;

import java.io.Serializable;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements Serializable {

<<<<<<< HEAD
    Button mbtnProfileLogin;
=======
    ImageView imgProfile;
    TextView tvProfileName;
    Button btnUserInfo;
    Button btnScheduleStats;
    Button btnAdmin;


>>>>>>> origin/dat
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
    }
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
=======
>>>>>>> origin/dat

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
<<<<<<< HEAD
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        mbtnProfileLogin = (Button) v.findViewById(R.id.btnProfileLogin);
        mbtnProfileLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileFragment.this.getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return v;
=======
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        btnUserInfo = view.findViewById(R.id.btnUserInfo);
        btnAdmin = view.findViewById(R.id.btnAdmin);

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToAdmin = new Intent(getActivity(), AdminActivity.class);
                startActivity(intentToAdmin);
//                Toast.makeText(getActivity(), "Button Clicked", Toast.LENGTH_SHORT).show();

            }
        });
        return view;
>>>>>>> origin/dat
    }
}