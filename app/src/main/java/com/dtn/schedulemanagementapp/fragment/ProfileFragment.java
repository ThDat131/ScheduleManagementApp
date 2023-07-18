package com.dtn.schedulemanagementapp.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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

    Button btnProfileLogin;
    ImageView imgProfile;
    TextView tvProfileName;
    Button btnUserInfo;
    Button btnScheduleStats;
    Button btnAdmin;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private boolean f;

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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        btnProfileLogin = (Button) v.findViewById(R.id.btnProfileLogin);
        tvProfileName = (TextView) v.findViewById(R.id.tvProfileName);
        btnProfileLogin.setText("login");
//        Intent intent = getActivity().getIntent();
//        Bundle bundle = getActivity().getIntent().getExtras();
//        mbtnProfileLogin.setText((CharSequence) bundle.getSerializable("key"));


        SharedPreferences prefs = requireActivity().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        f = prefs.getBoolean("logged in", false);
        if (f) {
            btnProfileLogin.setText("logout");
            String un = prefs.getString("key_username", "User name");
            tvProfileName.setText(un);


        }

        btnProfileLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnProfileLogin.getText().toString().equals("login")) {
                    Intent intent = new Intent(ProfileFragment.this.getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    editor.clear();
                    editor.apply();
                    btnProfileLogin.setText("login");
                    Toast.makeText(getContext(), "See you again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnUserInfo = v.findViewById(R.id.btnUserInfo);
        btnAdmin = v.findViewById(R.id.btnAdmin);

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToAdmin = new Intent(getActivity(), AdminActivity.class);
                startActivity(intentToAdmin);
//                Toast.makeText(getActivity(), "Button Clicked", Toast.LENGTH_SHORT).show();

            }
        });
        return v;
    }
}