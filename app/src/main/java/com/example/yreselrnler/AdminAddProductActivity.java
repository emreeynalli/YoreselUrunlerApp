package com.example.yreselrnler;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class AdminAddProductActivity extends AppCompatActivity {
    ImageView product_image ;
    EditText product_name,product_cost,product_count;
    Uri imageUri;
    private StorageReference storageReference;
    Button addProduct;
    DatabaseReference mDatabase;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminaddproduct);

        Intent intent = getIntent();
        product_image = findViewById(R.id.product_image);
        product_image.setImageResource(R.drawable.ic_baseline_add_a_photo_24);

        product_name = findViewById(R.id.product_name);
        product_cost = findViewById(R.id.product_cost);
        product_count = findViewById(R.id.product_count);
        addProduct = findViewById(R.id.addProduct);
        addProduct.setOnClickListener(Add);
        product_image.setOnClickListener(addImage);
        mDatabase = FirebaseDatabase.getInstance().getReference();






    }


    public View.OnClickListener Add = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final ProgressDialog progressDialog = new ProgressDialog(AdminAddProductActivity.this);
            progressDialog.setTitle("Ekleme İşlemi");
            progressDialog.show();
            String p_name = product_name.getText().toString();
            String p_cost = product_cost.getText().toString();
            String p_count = product_count.getText().toString();

            storageReference = FirebaseStorage.getInstance().getReference().child(imageUri.getLastPathSegment());
            storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task <Uri> dowloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String t = task.getResult().toString();
                            DatabaseReference newImage = mDatabase.push();



                            mDatabase.child("UserProducts").child(p_name).child("product_image").setValue(task.getResult().toString());
                            mDatabase.child("UserProducts").child(p_name).child("product_name").setValue(p_name);
                            mDatabase.child("UserProducts").child(p_name).child("product_count").setValue(p_count);
                            mDatabase.child("UserProducts").child(p_name).child("product_cost").setValue(p_cost);
                            Products product = new Products(p_name,p_cost,p_count,task.getResult().toString());
                            progressDialog.dismiss();
                            Snackbar.make(findViewById(android.R.id.content),"Ekleme işlemi başarılı",Snackbar.LENGTH_LONG).show();

                        }
                    });

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double state = (100.00 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage("ilerleme: " + (int) state + "%");
                }
            });










                        product_image.setImageResource(R.drawable.ic_baseline_add_a_photo_24);
                        product_cost.setText("");
                        product_count.setText("");
                        product_name.setText("");


        }
    };


    public View.OnClickListener addImage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            ActivityCompat.requestPermissions(AdminAddProductActivity.this,new String [] {Manifest.permission.READ_EXTERNAL_STORAGE},1);

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,2);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK){
            imageUri = data.getData();
            product_image.setImageURI(imageUri);
        }
    }
}