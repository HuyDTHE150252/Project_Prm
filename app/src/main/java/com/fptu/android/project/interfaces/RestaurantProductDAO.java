package com.fptu.android.project.interfaces;

import com.fptu.android.project.model.Product;
import com.google.android.gms.tasks.Task;

import java.util.HashMap;

public interface RestaurantProductDAO {

    public Task<Void> add(Product pro);

    public Task<Void> update(String key, HashMap<String ,Object> hashMap);

}
