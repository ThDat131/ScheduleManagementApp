package com.dtn.schedulemanagementapp.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.dtn.schedulemanagementapp.R;
import com.dtn.schedulemanagementapp.activity.MainActivity;
import com.dtn.schedulemanagementapp.activity.NewUserActivity;
import com.dtn.schedulemanagementapp.database.UserController;
import com.dtn.schedulemanagementapp.models.User;
import com.dtn.schedulemanagementapp.utils.CalendarUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupInfoFragment extends Fragment {

    UserController uCtrl  ;
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
        edBDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        LocalDate date = LocalDate.of(year, month + 1, dayOfMonth);
                        edBDay.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    }
                } , year, month, day);
                datePickerDialog.show();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uCtrl = new UserController(view.getContext());
                User u = new User(edUser, edPass, edFullName.getText().toString(), CalendarUtils.StringToDate(edBDay.getText().toString(), "dd/MM/yyyy"), edEmail, 0);
                uCtrl.addNewUser(u);
                moveToMainActivity();
            }
        });
        return view;
    }

    private void moveToMainActivity() {
        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
        ((Activity) getActivity()).overridePendingTransition(0, 0);
    }


}