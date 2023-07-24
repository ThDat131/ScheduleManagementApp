package com.dtn.schedulemanagementapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dtn.schedulemanagementapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupFragment extends Fragment {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SignupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
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
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        Button btnNext = view.findViewById(R.id.buttonNext);
        EditText edUsername = view.findViewById(R.id.editTextUsername);
        EditText edPassword = view.findViewById(R.id.editTextPassword);
        EditText edRepass = view.findViewById(R.id.editTextRePassword);
        EditText edEmail = view.findViewById(R.id.editTextEmail);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new SignupInfoFragment();
                Bundle bundle = new Bundle();
                FragmentManager fragManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragTransact = fragManager.beginTransaction();
                bundle.putString("user",edUsername.getText().toString());
                bundle.putString("pass",edPassword.getText().toString());
                bundle.putString("email",edEmail.getText().toString());
                fragment.setArguments(bundle);
                fragTransact.replace(R.id.layoutSignup, fragment).commit();
            }
        });
        return view;

    }
}