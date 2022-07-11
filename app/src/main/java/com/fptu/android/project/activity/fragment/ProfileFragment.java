package com.fptu.android.project.activity.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.fptu.android.project.R;
import com.fptu.android.project.activity.user.EditProflieActivity;
import com.fptu.android.project.activity.user.LoginActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProfileFragment extends Fragment {
    TextView fullName, email, phone, editProfile, verifyMsg, verified, resetPass, tvlogout;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button resendCode;
    FirebaseUser user;
    StorageReference storageReference;
    ImageView imgUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        phone = view.findViewById(R.id.profilePhone);
        fullName = view.findViewById(R.id.profileName);
        email = view.findViewById(R.id.profileEmail);
        imgUser = view.findViewById(R.id.imgUser);

        editProfile = view.findViewById(R.id.tveditProfile);
        editProfile.setOnClickListener(this::editProfile);
        resetPass = view.findViewById(R.id.resetPassword);
        resetPass.setOnClickListener(this::resetPassword);
        tvlogout = view.findViewById(R.id.tvLogout);
        tvlogout.setOnClickListener(this::logout);

        resendCode = view.findViewById(R.id.resendCode);
        verifyMsg = view.findViewById(R.id.verifyMsg);
        verified = view.findViewById(R.id.verified);

        // Email Verification
        if (!user.isEmailVerified()) {
            verifyMsg.setVisibility(View.VISIBLE);
            resendCode.setVisibility(View.VISIBLE);

            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(v.getContext(), "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag", "onFailure: Email not sent " + e.getMessage());
                        }
                    });
                }
            });
        } else {
            verified.setVisibility(View.VISIBLE);
        }
        // Get user's avatar
        StorageReference profileRef = storageReference.child("users/" + fAuth.getCurrentUser().getUid() + "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imgUser);
            }
        });

        // Get user's information
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
        fetchingStaticDataForUser(view);
    }

    private LineChart lineChart;

    private void fetchingStaticDataForUser(View view) {
        lineChart = view.findViewById(R.id.user_chart_activities);
        System.out.println(userId);
        fStore.collection("Order").whereEqualTo("userId", userId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            HashMap<Integer, Integer> staticData = new HashMap<>();
                            for (int i = 0; i < 12; i++) {
                                staticData.put(i + 1, 0);
                            }
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                String currentDate = documentSnapshot.getString("currentDateOrder");
                                String month = currentDate.split(" ")[1];
                                if (!month.contains(",")) {
                                    staticData.put(Integer.parseInt(month), staticData.get(Integer.parseInt(month)) + 1);
                                }

                            }
                            List<Entry> entries = new ArrayList<Entry>();
                            for (Integer item : staticData.keySet()) {
                                int month = item;
                                int amount = staticData.get(month);
                                entries.add(new Entry(month, amount));
                            }
                            LineDataSet dataSet = new LineDataSet(entries, "Orders"); // add entries to dataset
                            dataSet.setColor(ColorTemplate.COLORFUL_COLORS[0]);
                            LineData lineData = new LineData(dataSet);
                            lineChart.setData(lineData);
                            lineChart.setDescription(null);
                            lineChart.setScaleEnabled(false);
                            lineChart.invalidate(); // refresh
                        } else {
                            Log.w("err", "Error getting documents.", task.getException());
                        }
                    }
                });


    }


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

    private void resetPassword(View view) {
        final EditText resetPassword = new EditText(view.getContext());

        final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
        passwordResetDialog.setTitle("Reset Password ?");
        passwordResetDialog.setMessage("Enter New Password >= 6 Characters long.");
        passwordResetDialog.setView(resetPassword);

        passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // extract the email and send reset link
                String newPassword = resetPassword.getText().toString();
                user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getActivity(), "Password Reset Successfully.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Password Reset Failed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // close
            }
        });

        passwordResetDialog.create().show();

    }
}



