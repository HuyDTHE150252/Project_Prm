package com.fptu.android.project.activity.restaurant;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fptu.android.project.R;
import com.fptu.android.project.model.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class RestaurantCrudActivity extends AppCompatActivity {
    FirebaseDatabase fire;
    private EditText edit_name, edit_price, edit_description, edit_category, edit_quantity, edit_rate, edit_url;
    private Button btnAdd;
    private FirebaseFirestore db;
    private Button btnShow, uploadBtn;
    private String id, name, price, description,category,quantity, rate, url;
    private ImageView imageView;
    private Uri imageUri;
   private ProgressBar progressBar;
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
         edit_name = findViewById(R.id.edtName);
         edit_price = findViewById(R.id.edtPrice);
        edit_description = findViewById(R.id.edtDescription);
         edit_category = findViewById(R.id.edtCategory);
         edit_quantity = findViewById(R.id.edtQuantity);
         //edit_rate = findViewById(R.id.edtRate);
         edit_url = findViewById(R.id.edtImg);
         btnAdd = findViewById(R.id.btnConfirmAddProduct);
         btnShow = findViewById(R.id.btnShow);
        //progressBar = findViewById(R.id.progressBar);
       // progressBar.setVisibility(View.INVISIBLE);
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
             edit_rate.setText(rate);
             edit_url.setText(url);


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
//                 if (imageUri != null){
//                    uploadToFirebase(imageUri);
//                }else{
//                    Toast.makeText(RestaurantCrudActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
//                }
                 String name = edit_name.getText().toString();
                 String price = edit_price.getText().toString();
                 String description = edit_description.getText().toString();
                 String quantity = edit_quantity.getText().toString();
                 String category = edit_category.getText().toString();
                 Float rate = 1.0f;
                 String url = edit_url.getText().toString();

                 Bundle bundle1 = getIntent().getExtras();
                 if(bundle!= null){
                     String Id = id;

                     updateToFireStore(Id,name, price, description,quantity,category,rate,url,9);


                 }else {
                 String id = UUID.randomUUID().toString();

                 saveToFireStore(id,name, price, description,quantity,category,rate,url,9);}
             }


         });}
    private void updateToFireStore(String id, String name, String price, String description,String quantity,String category, float rate, String url,int resid) {
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
    
    private void saveToFireStore(String id, String name, String price, String description,String quantity,String category,float rate, String url,int resid){
        if(  !name.isEmpty() && !price.isEmpty()){
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("name", name);
                map.put("price", price);
                map.put("description", description);
                map.put("quantity",quantity);
                map.put("rate",rate);
                map.put("url",url);
                map.put("resid", resid);
                String ctg = category.toLowerCase(Locale.ROOT);
            switch (ctg) {
                // Case 1
                case "food":
                    map.put("category", "cate_1");
                    break;
                // Case 2
                case "drink":
                    map.put("category", "cate_2");
                    break;
                // Case 3
                case "fastFood":
                    map.put("category", "cate_3");
                    break;
                case "rawFood":
                    map.put("category", "cate_4");
                    break;
                case "fruit":
                    map.put("category", "cate_5");
                    break;
                case "iceCream":
                    map.put("category", "cate_6");
                    break;
                case "cake":
                    map.put("category", "cate_7");
                    break;
                case "beer":
                    map.put("category", "cate_8");
                    break;
                default:
                    map.put("category", "cate_1");
            }
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

                        Model model = new Model(uri.toString());
                        String modelId = root.push().getKey();
                        root.child(modelId).setValue(model);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(RestaurantCrudActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
                    }
                });
            }});
//        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                progressBar.setVisibility(View.VISIBLE);
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                progressBar.setVisibility(View.INVISIBLE);
//               Toast.makeText(RestaurantCrudActivity.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
//            }
//        });
    }



    private String getFileExtension(Uri mUri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));

    }


}