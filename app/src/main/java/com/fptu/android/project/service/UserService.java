package com.fptu.android.project.service;

import android.util.Log;

import androidx.annotation.NonNull;

import com.fptu.android.project.interfaces.IUserDao;
import com.fptu.android.project.model.Category;
import com.fptu.android.project.model.Product;
import com.fptu.android.project.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserService implements IUserDao {


    @Override
    public void insert(User u) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, ArrayList<Product>> products = new HashMap<>();
        ArrayList<Category> categories = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            categories.add(new Category(i,"Cate name " + i));
        }

        ArrayList<Product> listProducts1 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            listProducts1.add(new Product(i,"Product name " + i, "Product address " + i, "" + i,"Active", categories));
        }
        products.put("products", listProducts1);
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("a", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("a", "Error adding document", e);
                    }
                });
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("a", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("a", "Error getting documents.", task.getException());
                        }
                    }
                });

// Add a new document with a generated ID
        db.collection("products")
                .add(products)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        System.out.println(documentReference.getId());
                        Log.d("AddUser", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Log.d("Add1", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Add1", "Error adding document", e);
                    }
                });

        db.collection("products")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Write1", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("Write1", "Error getting documents.", task.getException());
                        }
                    }
                });
    }


    public void sendMessage(String text) {
        DatabaseReference myRef = FirebaseDatabase.getInstance("https://projectprm-392-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("message1");
        String userId = myRef.push().getKey();
        myRef.child(userId).setValue(text);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                Log.d("Huy", "Value is: " + map);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Huy", "Failed to read value.", error.toException());
            }
        });
    }

    public void getMessage() {

   }
}
