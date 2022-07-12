package com.fptu.android.project.activity.restaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fptu.android.project.R;
import com.fptu.android.project.interfaces.RestaurantProductDAO;
import com.fptu.android.project.model.Model;
import com.fptu.android.project.model.Product;
import com.fptu.android.project.service.RestaurantProductService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.OnProgressListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class RestaurantCrudActivity extends AppCompatActivity {
    FirebaseDatabase fire;
    private EditText edit_name, edit_price, edit_description, edit_category, edit_quantity, edit_url;
    private Button btnAdd;
    private FirebaseFirestore db;
    private Button btnShow, uploadBtn;
    private String id, name, price, description,category,quantity, rate, url;
    private ImageView imageView;
    private Uri imageUri;
    private ProgressBar progressBar;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("ImageProduct");
    private StorageReference reference = FirebaseStorage.getInstance().getReference("ImgProduct");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        edit_name = findViewById(R.id.edtName);
        edit_price = findViewById(R.id.edtPrice);
        edit_description = findViewById(R.id.edtDescription);
        edit_category = findViewById(R.id.edtCategory);
        edit_quantity = findViewById(R.id.edtQuantity);

        btnAdd = findViewById(R.id.btnConfirmAddProduct);
        btnShow = findViewById(R.id.btnShow);
        uploadBtn = findViewById(R.id.upload_btn);
        imageView = findViewById(R.id.imageViewAdd);
        db = FirebaseFirestore.getInstance();

        Bundle bundle = getIntent().getExtras();
        if (bundle !=null){
            btnAdd.setText("update");

            id = bundle.getString("id");
            name = bundle.getString("name");
            price = bundle.getString("price");
            description = bundle.getString("description");
            quantity = bundle.getString("quantity");
            category = bundle.getString("category");
            rate = bundle.getString("rate");
            url = bundle.getString("url");

            edit_name.setText(name);
            edit_price.setText(price);
            edit_description.setText(description);
            edit_quantity.setText(quantity);
            edit_category.setText(category);




        }else {
            btnAdd.setText("Save");

        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 2);
            }
        });
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null){
                    uploadToFirebase(imageUri);
                }else{
                    Toast.makeText(RestaurantCrudActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RestaurantCrudActivity.this, ShowActivity.class));
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null){
                    uploadToFirebase(imageUri);

                }else{
                    Toast.makeText(RestaurantCrudActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                }

                String name = edit_name.getText().toString();
                String price = edit_price.getText().toString();
                String description = edit_description.getText().toString();
                String quantity = edit_quantity.getText().toString();
                String category = edit_category.getText().toString();
                Float rate = 1.0f;
                String url = imageUri.toString();

                Bundle bundle1 = getIntent().getExtras();
                if(bundle!= null){
                    String Id = id;

                    updateToFireStore(Id,name, price, description,quantity,category,rate,url);


                }else {
                    String id = UUID.randomUUID().toString();

                    saveToFireStore(id,name, price, description,quantity,category,rate,url);}
            }


        });}
    private void updateToFireStore(String id, String name, String price, String description,String quantity,String category, float rate, String url) {
        db.collection("product").document(id).update("name",name,"price",price,"description",description,"quantity",quantity,"category",category,"rate",rate,"url",url)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(RestaurantCrudActivity.this, "Update is sussecful", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RestaurantCrudActivity.this, "Update is fail"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RestaurantCrudActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveToFireStore(String id, String name, String price, String description,String quantity,String category,float rate, String url){
        if(  !name.isEmpty() && !price.isEmpty()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("name", name);
            map.put("price", price);
            map.put("description", description);
            map.put("quantity",quantity);
            map.put("rate",rate);
            map.put("url",url);
            map.put("category",category);






            db.collection("product").document(id).set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RestaurantCrudActivity.this, "Record is inserted", Toast.LENGTH_SHORT).show();
                            }}
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RestaurantCrudActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }else
            Toast.makeText(this, "Empty fields not Allowed", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2 && resultCode == RESULT_OK && data != null){

            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    private void uploadToFirebase(Uri uri){
        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Product pro = new Product(uri.toString());
                        String proId = root.push().getKey();
                        root.child(proId).setValue(pro.getProduct_url());

                        Toast.makeText(RestaurantCrudActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
                    }
                });
            }});

    }



    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }


}