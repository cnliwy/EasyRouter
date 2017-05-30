package com.liwy.easyrouter.router;

import android.content.Context;
import android.content.Intent;

import java.util.HashMap;

/**
 * Created by admin on 2017/5/29.
 */

public class Routers {
    private static HashMap<String,Class> maps = new HashMap<>();

    public static void map(String name,Class clazz){
        maps.put(name,clazz);
    }

    public static void open(Context context,String name){
        if (maps.containsKey(name)){
            Intent intent = new Intent(context,maps.get(name));
            context.startActivity(intent);
        }else{
            System.out.println("404,activity not found");
            return;
        }
    }
}
