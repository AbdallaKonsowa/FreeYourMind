package com.alaaelshazly.freeyourmind.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.alaaelshazly.freeyourmind.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DilogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DilogFragment extends DialogFragment {


    public DilogFragment() {
        // Required empty public constructor
    }

    public static DilogFragment newInstance(String param1, String param2) {
        DilogFragment fragment = new DilogFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog dialog;
        builder.setTitle("Login please")
                .setMessage("U need to Login to open the pdf")
                .setCancelable(false)
                .setPositiveButton("Go To LogIn", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        getActivity().finish();
                        startActivity(intent);
                    }
                });
        dialog = builder.create();

        return dialog;
    }
}