package com.app.retotic.casos_uso;

import android.database.Cursor;

import com.app.retotic.modelos.Servicio;
import com.app.retotic.modelos.Sucursal;

import java.util.ArrayList;

public class CasoUsoSucursal {
    public ArrayList<Sucursal> llenarListaSucursal(Cursor cursor){
        ArrayList<Sucursal> list = new ArrayList<>();
        if(cursor.getCount() == 0){
            return list;
        }else{
            while (cursor.moveToNext()){
                Sucursal sucursal = new Sucursal(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getBlob(4)
                );
                list.add(sucursal);
            }
            return list;
        }
    }
}
