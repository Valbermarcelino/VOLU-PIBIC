package com.example.valbermarcelino.pibic;

/**
 * Created by valbermarcelino on 16/01/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

public class Tab1 extends Fragment {

        private EditText editPesquisa;
        private ListView lstEventos;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);

        editPesquisa = (EditText)rootView.findViewById(R.id.editPesquisa);
        lstEventos = (ListView)rootView.findViewById(R.id.lstEventos);


        return rootView;
    }

}
