package com.fptu.android.project;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.fptu.android.project.model.Category;
import com.fptu.android.project.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class DatabaseTest {

    public static final String TAG_NAME = "HUY DAN`";
    FirebaseFirestore database = FirebaseFirestore.getInstance();

    public void Test() {

        Map<String, Category> user = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            user.put(i+""
                    ,new Category(i, "Beer"));

        }

        database.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG_NAME, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG_NAME, "Error adding document", e);
                    }
                });
    }

    public void getData() {
        database.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG_NAME, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG_NAME, "Error getting documents.", task.getException());
                        }
                    }
                });
    }
}

