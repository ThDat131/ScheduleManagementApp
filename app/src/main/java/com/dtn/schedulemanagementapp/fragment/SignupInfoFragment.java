package com.dtn.schedulemanagementapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.activity.MainActivity;
import com.dtn.schedulemanagementapp.activity.SignupActivity;
import com.dtn.schedulemanagementapp.database.UserController;
import com.dtn.schedulemanagementapp.models.User;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupInfoFragment extends Fragment {

    UserController uCtrl;
    String edUser;
    String edPass;
    String edEmail;
    EditText edFullName;
    EditText edBDay;
    EditText edCalName;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupInfoFragment newInstance(String param1, String param2) {
        SignupInfoFragment fragment = new SignupInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            edUser = bundle.getString("user");
            edPass = bundle.getString("pass");
            edEmail = bundle.getString("email");
        }
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_info, container, false);
        Button btnSignUp = view.findViewById(R.id.buttonFinish);
        edFullName = view.findViewById(R.id.editTextFullName);
        edBDay = view.findViewById(R.id.editTextBirthDate);
        edCalName = view.findViewById(R.id.editTextCalName);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                User u = new User(edUser, edPass, edFullName.toString(), date, edEmail, 1);
                uCtrl.add(u);
                moveToMainActivity();
            }
        });
        return view;
    }

    private void moveToMainActivity() {
        Intent i = new Intent(getActivity(), MainActivity.class);
//        i.putExtra("User", edUser);
//        i.putExtra("Pass", edPass);
//        i.putExtra("Email", edEmail);
//        i.putExtra("fullName", edFullName.toString());
//        i.putExtra("birthDay", edBDay.toString());
//        i.putExtra("calName", edCalName.toString());
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);
    }


}