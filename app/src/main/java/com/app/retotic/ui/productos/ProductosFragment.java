package com.app.retotic.ui.productos;

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
import com.app.retotic.casos_uso.CasoUsoProducto;
import com.app.retotic.databinding.FragmentProductosBinding;
import com.app.retotic.datos.DBHelper;
import com.app.retotic.modelos.Producto;

import java.util.ArrayList;

public class ProductosFragment extends Fragment {

    //Para Reto 2
    //private FragmentProductosBinding binding;

    //Para Reto 3
    private GridView oGridView;
    private DBHelper dbHelper;
    private ArrayList<Producto> productos;
    private String TABLE_NAME = "PRODUCTOS";
    private CasoUsoProducto casoUsoProducto;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Para reto 2
        //binding = FragmentProductosBinding.inflate(inflater, container, false);
        //View root = binding.getRoot();

        //PAra reto 3

        View root = inflater.inflate(R.layout.fragment_productos,container,false);
        try {
            casoUsoProducto = new CasoUsoProducto();
            dbHelper = new DBHelper(getContext());
            //dbHelper.deleteTable();

            Cursor cursor = dbHelper.getData(TABLE_NAME);
            productos = casoUsoProducto.llenarListaProductos(cursor);
            oGridView = (GridView) root.findViewById(R.id.gridProductos);
            ProductoAdapter productoAdapter = new ProductoAdapter(root.getContext(),productos);
            oGridView.setAdapter(productoAdapter);

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
        // binding = null;
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
                intent.putExtra("nameExtra","Productos");
                getActivity().startActivity(intent);

                //Toast.makeText(getContext(),"Hola Productos",Toast.LENGTH_SHORT).show();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
}