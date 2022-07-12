package com.fptu.android.project.service;

import com.fptu.android.project.interfaces.RestaurantProductDAO;
import com.fptu.android.project.model.Product;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RestaurantProductService implements RestaurantProductDAO {

    private DatabaseReference databaseReference;

    public RestaurantProductService()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Product.class.getSimpleName());

    }

    @Override
    public Task<Void> add(Product pro) {
        return databaseReference.push().setValue(pro);
    }

    @Override
    public Task<Void> update(String key, HashMap<String ,Object> hashMap) {
        return databaseReference.child(key).updateChildren(hashMap);
    }

}
