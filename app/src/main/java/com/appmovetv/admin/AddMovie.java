package com.appmovetv.admin;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.appmovetv.R;
import com.appmovetv.model.Movie;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.appmovetv.SplashActivity.listAllMovie;


public class AddMovie extends AppCompatActivity {
    private Button btnXacNhanAdd;
    private ImageView imgphoto;
    private VideoView videoView;
    private Uri imageUri, videoUri;
    MediaController mediaController;
    private EditText edtId, edtName, edtTheLoai, edtDaoDien, edtNamXuatBan, edtMoTa;
    Movie movie = new Movie();
    private List<Movie> movieList = new ArrayList<>();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference("itemmovie");
    private StorageReference reference = FirebaseStorage.getInstance().getReference();

    ProgressBar progressBar;
    private static final int PICK_VIDEO = 1;
    UploadTask uploadTask;

     String fileuri= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmovie);
        anhxa();
        progressBar.setVisibility(View.INVISIBLE);
        imgphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 2);
            }
        });

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("video/*");
                startActivityForResult(intent,PICK_VIDEO);
            }
        });

        btnXacNhanAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageUri != null && videoUri != null) {
                    progressBar.setVisibility(View.VISIBLE);
//                    loadVideo(videoUri);
//                    loadImage(imageUri);
                      updata(videoUri);
                } else if(imageUri == null && videoUri != null){
                    Toast.makeText(AddMovie.this, "Hãy thêm ảnh", Toast.LENGTH_SHORT).show();
                } else if(imageUri != null && videoUri == null){
                    Toast.makeText(AddMovie.this, "Hãy thêm Video", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AddMovie.this, "Hãy thêm ảnh - video", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mediaController = new MediaController(AddMovie.this);
        videoView.setMediaController(mediaController);
        videoView.start();
    }

    private void updata(Uri videoUri){
        movie = new Movie();
        StorageReference fileRefvideo = reference.child("Video/"+System.currentTimeMillis() + "." + getFileExtension(videoUri));
        uploadTask = fileRefvideo.putFile(videoUri);
        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @NonNull
            @NotNull
            @Override
            public Task<Uri> then(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw task.getException();
                }
                return fileRefvideo.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Uri> task) {
                if(task.isSuccessful()) {
                    Uri downloadUrl = task.getResult();
//                    movie.setFileUrl(downloadUrl.toString());
                    fileuri = downloadUrl.toString();
                    if(fileuri != null){
                        loadImage(imageUri);
                    }
                    Toast.makeText(AddMovie.this, "Add thành công Video", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AddMovie.this, "Không add được Video", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void loadVideo(Uri urlVideo){
        StorageReference fileRefvideo = reference.child("Video/"+System.currentTimeMillis() + "." + getFileExtension(urlVideo));
        uploadTask = fileRefvideo.putFile(urlVideo);
        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @NonNull
            @NotNull
            @Override
            public Task<Uri> then(@NonNull @NotNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw task.getException();
                }
                return fileRefvideo.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Uri> task) {
                if(task.isSuccessful()) {
                    Uri downloadUrl = task.getResult();
                    movie.setFileUrl(downloadUrl.toString());

                    Toast.makeText(AddMovie.this, "Add thành công Video", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AddMovie.this, "Không add được Video", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void loadImage(Uri uriImg) {

        int mId = Integer.parseInt(edtId.getText().toString());
        String mTenPhim = edtName.getText().toString();
        String mTheLoai= edtTheLoai.getText().toString();
        String mDaoDien= edtDaoDien.getText().toString();
        String mNamSX= edtNamXuatBan.getText().toString();
        String mMoTa= edtMoTa.getText().toString();

        StorageReference fileRef = reference.child("Image/"+System.currentTimeMillis() + "." + getFileExtension(uriImg));

        fileRef.putFile(uriImg).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(@NonNull @NotNull UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(@NonNull @NotNull Uri uri) {

                        Movie movie = new Movie(mId,mTenPhim,uri.toString(),fileuri,mTheLoai,mDaoDien,mNamSX,mMoTa);
                        progressBar.setVisibility(View.INVISIBLE);
                        movieList.add(movie);
                        root.child("item5").setValue(movieList);
                        Toast.makeText(AddMovie.this, "Uploaded  Successfully", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(AddMovie.this,AdminActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        },1000);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(AddMovie.this, "Upload thấy bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //ghi đè một phương thức có trên kết quả hoạt động
    //sau khi lấy ảnh từ thư viện => hiển thị ảnh ra màn hình
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // kiểm tra điều kiện mã yêu cầu
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            imgphoto.setImageURI(imageUri);
        }

        else if(requestCode == PICK_VIDEO || requestCode == RESULT_OK || data == null || data.getData() == null){
            videoUri = data.getData();
            videoView.setVideoURI(videoUri);
        }
    }


    private String getFileExtension(Uri muri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(muri));
    }

    private void anhxa() {
        imgphoto = findViewById(R.id.img_addimageView);
        videoView = findViewById(R.id.vdv_addvideoview);
        btnXacNhanAdd = findViewById(R.id.btn_dongyaddmovie);
        edtId = findViewById(R.id.edt_idmovie);
        edtName = findViewById(R.id.edt_namemovie);
        edtTheLoai = findViewById(R.id.edt_theloaimovie);
        edtDaoDien = findViewById(R.id.edt_daodien);
        edtNamXuatBan = findViewById(R.id.edt_nsx);
        edtMoTa = findViewById(R.id.edt_mota);
        progressBar = findViewById(R.id.progressBar);
    }
}
