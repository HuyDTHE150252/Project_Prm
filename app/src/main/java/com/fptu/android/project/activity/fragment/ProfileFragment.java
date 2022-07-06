package com.fptu.android.project.activity.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.HomePageActivity;
import com.fptu.android.project.activity.user.EditProflieActivity;
import com.fptu.android.project.activity.user.LoginActivity;
import com.fptu.android.project.activity.user.SignupActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;
import java.util.concurrent.Executor;

//import com.squareup.picasso.Picasso;
public class ProfileFragment extends Fragment {
    TextView fullName, email, phone, editProfile, verifyMsg;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button resendCode;
    Button resetPassLocal, changeProfileImage;
    FirebaseUser user;
    ImageView logout,iveditProfile, profileImage;
    StorageReference storageReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        phone = view.findViewById(R.id.profilePhone);
        fullName = view.findViewById(R.id.profileName);
        email = view.findViewById(R.id.profileEmail);
        //resetPassLocal = view.findViewById(R.id.resetPasswordLocal);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        logout = view.findViewById(R.id.logout);
        logout.setOnClickListener(this::logout);
        editProfile = view.findViewById(R.id.tveditProfile);
        editProfile.setOnClickListener(this::editProfile);
        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Picasso.get().load(uri).into(profileImage);
            }
        });


//        resendCode = findViewById(R.id.resendCode);
//        verifyMsg = findViewById(R.id.verifyMsg);


        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    phone.setText(documentSnapshot.getString("phone"));
                    fullName.setText(documentSnapshot.getString("fName"));
                    email.setText(documentSnapshot.getString("email"));

                } else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });
    }

    //    if(!user.isEmailVerified()){
//        verifyMsg.setVisibility(View.VISIBLE);
//        resendCode.setVisibility(View.VISIBLE);
//
//        resendCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//
//                user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        Toast.makeText(v.getContext(), "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d("tag", "onFailure: Email not sent " + e.getMessage());
//                    }
//                });
//            }
//        });
//    }
    private void editProfile(View view) {
        Intent i = new Intent(view.getContext(), EditProflieActivity.class);
        i.putExtra("fullName", fullName.getText().toString());
        i.putExtra("email", email.getText().toString());
        i.putExtra("phone", phone.getText().toString());
        startActivity(i);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getActivity(), LoginActivity.class));

    }

}
