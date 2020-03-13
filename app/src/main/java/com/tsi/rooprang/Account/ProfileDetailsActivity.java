package com.tsi.rooprang.Account;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;
import com.tsi.rooprang.DTO.profile.Update_Profile_DTO;
import com.tsi.rooprang.DTO.profile.Update_image_DTO;
import com.tsi.rooprang.DTO.profile.ViewProfile_DTO;
import com.tsi.rooprang.DTO.profile.ViewProfile_Detail_DTO;
import com.tsi.rooprang.R;
import com.tsi.rooprang.Session.SessionManager;
import com.tsi.rooprang.UserProfile.ChangePasswordActivity;
import com.tsi.rooprang.retrofit.ApiInterface;
import com.tsi.rooprang.retrofit.NewApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileDetailsActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView tv_user_name, tv_user_phone, tv_user_email, tv_user_password;
    ImageView imageView, imageup;
    private static final String IMAGE_DIRECTORY = "/demonuts";
    private int GALLERY = 1, CAMERA = 2;
    private String vName, vPhone, vEmail;
    ProgressDialog mProgressDialog;
    ApiInterface apiService;
    private Context context;
    Bitmap bitmap;
    private Button btn_update_profile;
    String user_id = "";
    String mediaPath;
    ProgressBar pbbanner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);


        context = this;
        apiService = NewApiClient.getClient().create(ApiInterface.class);
        mProgressDialog = new ProgressDialog(ProfileDetailsActivity.this);

        pbbanner = findViewById(R.id.pbbanner);


        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_user_name = findViewById(R.id.tv_user_name);
        tv_user_phone = findViewById(R.id.tv_user_phone);
        tv_user_email = findViewById(R.id.tv_user_email);
        tv_user_password = findViewById(R.id.tv_user_password);
        imageView = findViewById(R.id.userimage);
        imageup = findViewById(R.id.imageupload);
        btn_update_profile = findViewById(R.id.btn_update_profile);
        user_id = SessionManager.getInstance(this).get_data_from_session(SessionManager.KEY_USERID);

        getProfileDetail();
        requestMultiplePermissions();


        tv_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_user_name.setCursorVisible(true);
                tv_user_name.setFocusableInTouchMode(true);
                tv_user_name.setInputType(InputType.TYPE_CLASS_TEXT);
                tv_user_name.requestFocus();
            }
        });

        tv_user_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_user_phone.setCursorVisible(true);
                tv_user_phone.setFocusableInTouchMode(true);
                tv_user_phone.setInputType(InputType.TYPE_CLASS_PHONE);
                tv_user_phone.requestFocus();
            }
        });

        tv_user_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_user_email.setCursorVisible(true);
                tv_user_email.setFocusableInTouchMode(true);
                tv_user_email.setInputType(InputType.TYPE_CLASS_TEXT);
                tv_user_email.requestFocus();
            }
        });

        tv_user_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vName = tv_user_name.getText().toString();
                vEmail = tv_user_email.getText().toString();
                vPhone = tv_user_phone.getText().toString();

                if (vName.isEmpty()) {
                    tv_user_name.setError("Name Can't be empty");
                    tv_user_name.requestFocus();
                } else if (vEmail.isEmpty()) {
                    tv_user_email.setError("Email Can't be empty");
                    tv_user_email.requestFocus();
                } else if (vPhone.isEmpty()) {
                    tv_user_phone.setError("Phone Can't be empty");
                    tv_user_phone.requestFocus();
                } else if (vPhone.length() != 10) {
                    tv_user_phone.setError("Mobile Number should be 10 digit");
                    tv_user_phone.requestFocus();
                } else {
                    updateProfile();
                }

            }
        });

        imageup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });


    }


    private void api_update_profile_image(File mfile) {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        MultipartBody.Part imagePart;

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), mfile);
// RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), mfile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("profile_img", mfile.getName(), requestBody);


        MultipartBody.Part vId = MultipartBody.Part.createFormData("id", user_id);

// RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
// MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
// RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        final Call<Update_image_DTO> update_image_dtoCall = apiService.api_update_profile_photo(body, vId);
        update_image_dtoCall.enqueue(new Callback<Update_image_DTO>() {
            @Override
            public void onResponse(Call<Update_image_DTO> call, Response<Update_image_DTO> response) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Update_image_DTO update_image_dto = null;
                try {
                    update_image_dto = response.body();
                    if (update_image_dto.getMessage().equals("success")) {
                        Log.e("success............", "success" + update_image_dto.getMessage() + "");

                    } else {
                        Toast.makeText(context, update_image_dto.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<Update_image_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("failure", t + "");
                Toast.makeText(context, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getProfileDetail() {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();


        final Call<ViewProfile_DTO> viewProfile_dtoCall = apiService.api_viewprofile(user_id);
        viewProfile_dtoCall.enqueue(new Callback<ViewProfile_DTO>() {
            @Override
            public void onResponse(Call<ViewProfile_DTO> call, Response<ViewProfile_DTO> response) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                ViewProfile_DTO viewProfile_dto = response.body();
                if (viewProfile_dto.getResult().equals("success")) {
                    Log.e("success", viewProfile_dto.getResult() + "");
                    List<ViewProfile_Detail_DTO> viewProfile_detail_dto;
                    viewProfile_detail_dto = viewProfile_dto.getData();
                    tv_user_name.setText(viewProfile_detail_dto.get(0).getName());
                    tv_user_email.setText(viewProfile_detail_dto.get(0).getEmail());
                    tv_user_phone.setText(viewProfile_detail_dto.get(0).getPhone());

                    if (viewProfile_detail_dto.get(0).getProfileImg() != null) {
                        //Glide.with(context).load(getString(R.string.BaseUrl) + viewProfile_detail_dto.get(0).getProfileImg()).into(imageView);

                        Picasso.with(ProfileDetailsActivity.this).load(getString(R.string.BaseUrl) +
                                viewProfile_detail_dto.get(0).getProfileImg()).placeholder(R.drawable.no_image).fit().into(imageView,
                                new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                pbbanner.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                pbbanner.setVisibility(View.GONE);
                            }
                        });

                    }


                    // Glide.with(context).load("http://thelittleshoppers.com/API/images/user/share_image_1583501645209.png").into(imageView);

                    System.out.println(viewProfile_detail_dto.get(0).getProfileImg() + "=djfhd");


                }
            }

            @Override
            public void onFailure(Call<ViewProfile_DTO> call, Throwable t) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                Log.e("profilefailure", t.getMessage() + "");
            }
        });

    }

    private void updateProfile() {
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        Call<Update_Profile_DTO> update_profile_dtoCall = apiService.api_updateUserProfile(user_id,
                vName, vEmail, vPhone, "", "", "", "", "", "");
        update_profile_dtoCall.enqueue(new Callback<Update_Profile_DTO>() {

            @Override
            public void onResponse(Call<Update_Profile_DTO> call, Response<Update_Profile_DTO> response) {

                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
                Update_Profile_DTO update_profile_dto = response.body();
                Log.e("Success_update_profile", update_profile_dto.getResult() + "");
                if (update_profile_dto.getResult().equals("success")) {
                    Log.e("Success_update_profile", update_profile_dto.getResult() + "");
                    Toast.makeText(ProfileDetailsActivity.this, "Profile Updated Succesfully ", Toast.LENGTH_SHORT).show();
                }
                Log.e("Success_update_profile", update_profile_dto.getResult() + "");
            }

            @Override
            public void onFailure(Call<Update_Profile_DTO> call, Throwable t) {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();

                }
                Log.e("failuer", "onFailure: ", t);
            }
        });
    }


    private void showPictureDialog() {

        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Choose Image",
                "Camera"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri contentURI = data.getData();
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == GALLERY) {
            if (data != null) {

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);

                    Uri xxx = getLocalBitmapUri(bitmap);
                    File mfile = new File(xxx.getPath());
                    System.out.println("zzzzzzzzzz" + mfile);

                    Glide.with(ProfileDetailsActivity.this).load(xxx).into(imageView);

// String path = saveImage(bitmap);
// imageView.setImageBitmap(bitmap);
// ByteArrayOutputStream baos = new ByteArrayOutputStream();
// bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
// byte[] b = baos.toByteArray();

                    api_update_profile_image(mfile);

// System.out.println(path + "=userphoto");

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            Uri xxx = getLocalBitmapUri(bitmap);
            File mfile = new File(xxx.getPath());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);

            saveImage(bitmap);
            api_update_profile_image(mfile);


        }
    }

    public static Uri getLocalBitmapUri(Bitmap bmp) {
// Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 40, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 40, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
// have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());
            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    private void requestMultiplePermissions() {
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
// check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
//Toast.makeText(getActivity(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

// check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
// show alert dialog navigating to Settings
//openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(ProfileDetailsActivity.this, "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }


}