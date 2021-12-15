package com.app.retotic.ui.servicios;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.retotic.FormActivity;
import com.app.retotic.R;
import com.app.retotic.adaptadores.ProductoAdapter;
import com.app.retotic.adaptadores.ServicioAdapter;
import com.app.retotic.casos_uso.CasoUsoProducto;
import com.app.retotic.casos_uso.CasoUsoServicio;
import com.app.retotic.databinding.FragmentServiciosBinding;
import com.app.retotic.datos.DBHelper;
import com.app.retotic.modelos.Producto;
import com.app.retotic.modelos.Servicio;

import java.util.ArrayList;

public class ServiciosFragment extends Fragment {

    //Para Reto 2
    //private FragmentServiciosBinding binding;

    //Para Reto 3
    private GridView oGridView;
    private DBHelper dbHelper;
    private ArrayList<Servicio> servicios;
    private String TABLE_NAME = "SERVICIOS";
    private CasoUsoServicio casoUsoServicio;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Para reto 2
        //binding = FragmentServiciosBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();

        //PAra reto 3

        View root = inflater.inflate(R.layout.fragment_servicios,container,false);
        try {
            casoUsoServicio = new CasoUsoServicio();
            dbHelper = new DBHelper(getContext());
            //dbHelper.deleteTable();

            Cursor cursor = dbHelper.getData(TABLE_NAME);
            servicios = casoUsoServicio.llenarListaServicios(cursor);
            oGridView = (GridView) root.findViewById(R.id.gridServicios);
            ServicioAdapter servicioAdapter = new ServicioAdapter(root.getContext(),servicios);
            oGridView.setAdapter(servicioAdapter);

        }
        catch (Exception e){
            Toast.makeText(getContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Para reto 2
        //binding = null;
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
                Intent intent = new Intent(getContext(), FormActivity.class);
                intent.putExtra("nameExtra","Servicios");
                getActivity().startActivity(intent);

                //Toast.makeText(getContext(),"Hola Servicios",Toast.LENGTH_SHORT).show();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}