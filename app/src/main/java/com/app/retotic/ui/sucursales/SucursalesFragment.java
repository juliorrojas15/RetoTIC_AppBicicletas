package com.app.retotic.ui.sucursales;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.app.retotic.R;
import com.app.retotic.casos_uso.CasoUsoSucursal;
import com.app.retotic.datos.ApiOracle;
import com.app.retotic.FormMapsActivity;
import com.app.retotic.modelos.Sucursal;

import java.util.ArrayList;


public class SucursalesFragment extends Fragment {

    private GridView oGridView;
    //private DBHelper dbHelper;
    private ApiOracle apiOracle;
    private ArrayList<Sucursal> sucursal;
    private String TABLE_NAME = "SUCURSALES";
    private CasoUsoSucursal casoUsoSucursal;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_sucursales,container,false);
        try {
            casoUsoSucursal = new CasoUsoSucursal();
            oGridView = (GridView) root.findViewById(R.id.gridSucursales);

            // ****************** SQL ORACLE ******************
            apiOracle = new ApiOracle(root.getContext());
            apiOracle.getAllSucursales(oGridView);


            // ****************** SQLite ******************
            //dbHelper = new DBHelper(getContext());
            //Cursor cursor = dbHelper.getData(TABLE_NAME);
            //sucursal = casoUsoSucursal.llenarListaSucursal(cursor);
            //SucursalAdapter sucursalAdapter = new SucursalAdapter(root.getContext(),sucursal);
            //oGridView.setAdapter(sucursalAdapter);

        }
        catch (Exception e){
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_opcion1:
                Intent intent = new Intent(getContext(), FormMapsActivity.class);
                intent.putExtra("nameExtra","Sucursales");
                getActivity().startActivity(intent);
                //Toast.makeText(getContext(),"Hola Sucursales",Toast.LENGTH_SHORT).show();
                return true;
            default: return super.onOptionsItemSelected(item);
            //comentario
        }

    }
}


//Esto es original, pero lo ideal es manejar todos los fragments iguales
/*
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SucursalesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SucursalesFragment.
     */
    // TODO: Rename and change types and number of parameters
  /*  public static SucursalesFragment newInstance(String param1, String param2) {
        SucursalesFragment fragment = new SucursalesFragment();
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
        return inflater.inflate(R.layout.fragment_sucursales, container, false);
    }
}
*/