package com.dtn.schedulemanagementapp.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.activity.AdminActivity;
import com.dtn.schedulemanagementapp.activity.AdminUserActivity;
import com.dtn.schedulemanagementapp.activity.ChangeUserInfo;
import com.dtn.schedulemanagementapp.activity.LoginActivity;
import com.dtn.schedulemanagementapp.activity.SoundActivity;
import com.dtn.schedulemanagementapp.database.DBHelper;
import com.dtn.schedulemanagementapp.database.UserController;
import com.dtn.schedulemanagementapp.models.User;

import java.io.Serializable;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements Serializable {

    Button btnProfileLogin, btnSound;
    ImageView imgProfile;
    TextView tvProfileName;
    Button btnUserInfo;
    Button btnScheduleStats;
    Button btnAdmin;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private boolean logIn, admin;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        btnProfileLogin = v.findViewById(R.id.btnProfileLogin);
        tvProfileName = v.findViewById(R.id.tvProfileName);
        btnSound = v.findViewById(R.id.btnSound);
        btnUserInfo = v.findViewById(R.id.btnUserInfo);
        btnAdmin = v.findViewById(R.id.btnAdmin);

        UserController userController = new UserController(getContext());

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileFragment.this.requireActivity());

        DBHelper dbHelper = DBHelper.getInstance(ProfileFragment.this.getContext());

        SharedPreferences prefs = requireActivity().getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        logIn = prefs.getBoolean("logged in", false);
        admin = prefs.getBoolean("admin_user", false);
        String un = prefs.getString("key_username", "User name");

        if (logIn) {
            btnProfileLogin.setText("logout");
            tvProfileName.setText(un);


        }
        else
            btnProfileLogin.setText("login");
        if(!admin)
            btnAdmin.setVisibility(View.GONE);
        else
            btnAdmin.setVisibility(View.VISIBLE);
        btnProfileLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnProfileLogin.getText().toString().equals("login")) {
                    Intent intentToLogin = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intentToLogin);
                }
                else {
                    editor.clear();
                    editor.apply();
                    btnProfileLogin.setText("login");
                    tvProfileName.setText("User name");
                    Toast.makeText(getContext(), "See you again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
        btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("User info");

                User user = userController.getUserByUsername(un);
                String fullname = user.getFullName();
                String birthday = user.getBirthDate().toString();
                String email = user.getEmail();

                builder.setMessage("Username: " + un +
                        "\nFull name: " + fullname +
                        "\nBirthday: " + birthday +
                        "\nEmail: " + email);
                builder.setPositiveButton("Change info", (dialogInterface, i) -> {
                    AlertDialog.Builder confirmPassword = new AlertDialog.Builder(ProfileFragment.this.requireActivity());
                    EditText pass = new EditText(getContext());
                    pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    confirmPassword.setTitle("Confirm password")
                            .setView(pass)
                            .setPositiveButton("Enter", (dialogInterface1, i1) -> {
                                if(user.getPassword().equals(pass.getText().toString())){
                                    Toast.makeText(getContext(), "Password correct", Toast.LENGTH_SHORT).show();
                                    Intent intentToChangeInfo = new Intent(getContext(), ChangeUserInfo.class);
                                    startActivity(intentToChangeInfo);
                                }else
                                    Toast.makeText(getContext(), "Password incorrect", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("Cancel", null);
                    confirmPassword.create().show();
                });
                builder.setNegativeButton("OK", null);
                builder.create().show();
            }
        });

        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentToSound = new Intent(getActivity(), SoundActivity.class);
                startActivity(intentToSound);
            }
        });
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