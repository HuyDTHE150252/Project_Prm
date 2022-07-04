package com.fptu.android.project.activity.restaurant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.UUID;

public class RestaurantCrudActivity extends AppCompatActivity {
    FirebaseDatabase fire;
    private EditText edit_img, edit_price, edit_address;
    private Button btnAdd;
    private FirebaseFirestore db;
    private Button btnShow;
    private String pid, pimg, pprice, pname;
//    private ImageView imageView;
//    private Uri imageUri;
   //private ProgressBar progressBar;
//    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
//    private StorageReference reference = FirebaseStorage.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
         edit_img = findViewById(R.id.edtImg);
         edit_price = findViewById(R.id.edtPrice);
         edit_address = findViewById(R.id.edtAddress);
         btnAdd = findViewById(R.id.btnConfirmAddProduct);
         btnShow = findViewById(R.id.btnShow);

         db = FirebaseFirestore.getInstance();

         Bundle bundle = getIntent().getExtras();
         if (bundle !=null){
             btnAdd.setText("update");
             pimg = bundle.getString("pimg");
             pid = bundle.getString("pid");
             pprice = bundle.getString("pprice");
             pname = bundle.getString("pname");
             edit_img.setText(pimg);
             edit_price.setText(pprice);
             edit_address.setText(pname);


         }else {
             btnAdd.setText("Save");

         }
//        imageView = findViewById(R.id.imageViewAdd);
//         imageView.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View v) {
//                 Intent galleryIntent = new Intent();
//                 galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
//                 galleryIntent.setType("image/*");
//                 startActivityForResult(galleryIntent, 2);
//             }
//         });

//        uploadBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (imageUri != null){
//                    uploadToFirebase(imageUri);
//                }else{
//                    Toast.makeText(MainActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }


         btnShow.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(RestaurantCrudActivity.this, ShowActivity.class));
             }
         });

         btnAdd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
//                 if (imageUri != null){
//                    uploadToFirebase(imageUri);
//                }else{
//                    Toast.makeText(RestaurantCrudActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
//                }
                 String img = edit_img.getText().toString();
                 String name = edit_address.getText().toString();
                 String price = edit_price.getText().toString();

                 Bundle bundle1 = getIntent().getExtras();
                 if(bundle!= null){
                     String id = pid;
                     updateToFireStore(id,img, name, price);


                 }else {
                 String id = UUID.randomUUID().toString();

                 saveToFireStore(id,img, name, price);}
             }


         });}
    private void updateToFireStore(String id, String img, String name, String price) {
        db.collection("restautantProduct").document(id).update("img",img,"name",name,"price",price)
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
    
    private void saveToFireStore(String id, String img, String name, String price){
        if(  !name.isEmpty() && !price.isEmpty()){
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("img", img);
                map.put("name", name);
                map.put("price", price);

                db.collection("restautantProduct").document(id).set(map)
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
//        RestaurantProductService service = new RestaurantProductService();
//        btn.setOnClickListener(v-> {
//            Product pro = new Product(edit_img.getId(),edit_price.getText().toString(),edit_address.getText().toString());
//            service.add(pro).addOnSuccessListener(suc-> {
//                Toast.makeText(this,"Record is inserted",Toast.LENGTH_SHORT).show();
//            }).addOnFailureListener(er-> {
//                Toast.makeText(this,""+er.getMessage(),Toast.LENGTH_SHORT).show();
//            });


            // THIS IS UPDATE :)))))))
//            HashMap<String,Object> hashMap = new HashMap<>();
//            hashMap.put("product_image",edit_img.getId());
//            hashMap.put("product_name",edit_address.getText().toString());
//            hashMap.put("product_price",edit_price.getText().toString());
//            service.update("-N5oKYEMGjPinqA4CFMC",hashMap).addOnSuccessListener(suc-> {
//                Toast.makeText(this,"Record is updated",Toast.LENGTH_SHORT).show();
//            }).addOnFailureListener(er-> {
//                Toast.makeText(this,""+er.getMessage(),Toast.LENGTH_SHORT).show();
//            });

            //THIS IS DELETE:)))

//






    }

//    private void uploadToFirebase(Uri uri){
//
//        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
//        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                    @Override
//                    public void onSuccess(Uri uri) {
//
//                        Model model = new Model(uri.toString());
//                        String modelId = root.push().getKey();
//                        root.child(modelId).setValue(model);
//                        progressBar.setVisibility(View.INVISIBLE);
//                        Toast.makeText(MainActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                        imageView.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
//                    }
//                });
//            }
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                progressBar.setVisibility(View.VISIBLE);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progressBar.setVisibility(View.INVISIBLE);
//                Toast.makeText(MainActivity.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private String getFileExtension(Uri mUri){
//
//        ContentResolver cr = getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cr.getType(mUri));
//
//    }
}