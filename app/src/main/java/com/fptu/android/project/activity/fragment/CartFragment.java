package com.fptu.android.project.activity.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fptu.android.project.R;
import com.fptu.android.project.adapter.MyCartAdapter;
import com.fptu.android.project.model.MyCart;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    RecyclerView recyclerView;
    MyCartAdapter cartAdapter;
    List<MyCart> cartList;
    FirebaseAuth auth;
    FirebaseFirestore db;
    MyCart cartViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = view.findViewById(R.id.cart_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(getActivity(), cartList);
//        cartViewModel= new ViewModelProvider(getActivity()).get(MyCart.class);
//        cartAdapter.setData(getListCart());
        recyclerView.setAdapter(cartAdapter);
        db.collection("AddToCart").document(auth.getCurrentUser().getUid())
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                                 cartViewModel=documentSnapshot.toObject(MyCart.class);
                                cartList.add(cartViewModel);
                                cartAdapter.notifyDataSetChanged();
                                Log.d("Write1", documentSnapshot.getId() + " => " + documentSnapshot.getData());
                            }
                        } else {
                            Log.w("Write1", "Error getting documents.", task.getException());
                        }
                    }
                });
                return view;
    }
//    private List<MyCart> getListCart(){
//        cartList.add(new MyCart("a","a","a","b",100));
//        cartList.add(new MyCart("a","a","a","b",200));
//        cartList.add(new MyCart("a","a","a","b",100));
//        cartList.add(new MyCart("a","a","a","b",100));
//        return cartList;
//    }
}