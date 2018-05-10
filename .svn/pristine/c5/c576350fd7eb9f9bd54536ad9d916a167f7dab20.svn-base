package com.niveus.oen.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.niveus.oen.R;
import com.niveus.oen.Utils.FontsProvider;

/**
 * Created by Adarsh on 09-Jun-17.
 */

public class ContactUsFragment extends Fragment {

    EditText titleEt, queryEt;

    FontsProvider fontsProvider;

    public ContactUsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        fontsProvider = new FontsProvider(getActivity());

        titleEt = (EditText) view.findViewById(R.id.title_et);
        titleEt.setTypeface(fontsProvider.getRobotoLight());

        queryEt = (EditText) view.findViewById(R.id.query_et);
        queryEt.setTypeface(fontsProvider.getRobotoLight());

        return view;
    }
}
