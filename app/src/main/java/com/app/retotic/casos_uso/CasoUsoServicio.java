package com.app.retotic.casos_uso;

import android.database.Cursor;

import com.app.retotic.modelos.Producto;
import com.app.retotic.modelos.Servicio;

import java.util.ArrayList;

public class CasoUsoServicio {
    public ArrayList<Servicio> llenarListaServicios(Cursor cursor){
        ArrayList<Servicio> list = new ArrayList<>();
        if(cursor.getCount() == 0){
            return list;
        }else{
            while (cursor.moveToNext()){
                Servicio servicio = new Servicio(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getBlob(4)
                );
                list.add(servicio);
            }
            return list;
        }
    }
}
