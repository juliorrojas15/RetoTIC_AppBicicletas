package com.app.retotic.adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.retotic.R;
import com.app.retotic.modelos.Producto;

import java.util.ArrayList;

public class ProductoAdapter extends BaseAdapter {

    Context context;
    ArrayList<Producto> productos;
    LayoutInflater inflater;

    public ProductoAdapter(Context context, ArrayList<Producto> productos) {
        this.context = context;
        this.productos = productos;
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.grid_item,null);
        }

        ImageView imageView = convertView.findViewById(R.id.imagenItem);
        TextView textView = convertView.findViewById(R.id.textoItem);

        Producto producto = productos.get(position);


        byte[] image = producto.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
        imageView.setImageBitmap(bitmap);


        String sTexto = producto.getId() + ")" + producto.getName()
                        + "\n" + producto.getDescription()
                        + "\n" + producto.getPrice();
        textView.setText(sTexto);


        return convertView;
    }
}
