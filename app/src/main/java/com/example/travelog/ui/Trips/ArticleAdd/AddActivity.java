package com.example.travelog.ui.Trips.ArticleAdd;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.travelog.MainActivity;
import com.example.travelog.R;
import com.example.travelog.ui.Profile.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

//import com.example.travelog.D.DiscoverActivity;

public class AddActivity extends Activity implements
        MyDialog.OnButtonClickListener, OnItemClickListener {
    private MyDialog dialog;// Picture selection dialog box
    public static final int NONE = 0;
    public static final int CAMERA = 1;// Camera
    public static final int IMAGE_OPEN = 2; // Open gallery
    public static final int PHOTO_ZOOM = 3;// Photo zoom
    public static final String IMAGE_UNSPECIFIED = "image/*";

    private GridView gridView; // 网格显示缩略图
    private String pathImage; // 选择图片路径
    private Bitmap bmp;
    private ArrayList<HashMap<String, Bitmap>> imageItem;
    private ArrayList<byte[]> photoData;

    private SimpleAdapter simpleAdapter; // Adapter

    private int articleId;
    private String userId;
    private String location;
    private String title;
    private String content;
    private String time;
    private String urlJson;

    public ArrayList<String> imageUrls;
    public int index;
    public String isUrl;

    private ImageView topBarImage_1;
    private EditText etTitle;
    private EditText etContent;
    private ImageView ivLocation;
    private EditText etLocation;
    private Button buttonSubmit;
    private ProgressBar progressBar;


    private StorageReference mStorageRef;
    private DatabaseReference mArticleRef;
    private DatabaseReference mImageRef;
    private DatabaseReference mImageRef2;
    private DatabaseReference mArticleRef2;

    public static final int LOCATION_CODE = 301;
    private LocationManager locationManager;
    private String locationProvider = null;
    // 定位的经纬度
    private String locationStr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*
         * 防止键盘挡住输入框 不希望遮挡设置activity属性 android:windowSoftInputMode="adjustPan"
         * 希望动态调整高度 android:windowSoftInputMode="adjustResize"
         */
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        // lock screen
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_add);
        //add permission for android 6.0 to open camera
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //Verify permission is granted
            for (String str : permissions) {
                if (this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //Apply for permission
                    this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                }
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1001);
                builder.detectFileUriExposure();
            }
        }
        //
        init();
        initData();
        add();
    }


    private void add() {
        topBarImage_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddActivity.this.finish();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });

        ivLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                getAddressAndWeather();
            }
        });
    }

    private void verify() {

        String unVerifyTitle = etTitle.getText().toString().trim();
        String unVerifyContent = etContent.getText().toString().trim();
        String unVerifyLocation = etLocation.getText().toString().trim();
        if (TextUtils.isEmpty(unVerifyTitle)) {
            Toast.makeText(AddActivity.this, "Please enter a title", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(unVerifyContent)) {
            Toast.makeText(AddActivity.this, "Please enter some contents", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(unVerifyLocation)) {
            Toast.makeText(AddActivity.this, "Please enter a location", Toast.LENGTH_SHORT).show();
        } else {
            uploadImage();

            uploadInfo();

            AddActivity.this.finish();
            startActivity(new Intent(AddActivity.this, MainActivity.class));

        }
    }

    private void uploadImage() {
        String path;
        for (int i = 1; i < photoData.size(); i++) {
            index = 1;
            if (photoData.get(i) != null) {
                isUrl = String.valueOf(index);
                path = "photo/" + UUID.randomUUID() + ".png";
                String finalPath = path.substring(6);
                buttonSubmit.setEnabled(false);
                topBarImage_1.setEnabled(false);
                StorageReference Ref = mStorageRef.child(path);

                StorageTask uploadTask = Ref.putBytes(photoData.get(i)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //progressBar.setProgress(0);
                            }
                        }, 500);

                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful());
                        Uri downloadUrl = urlTask.getResult();
                        Upload upload = new Upload(String.valueOf(articleId) + index, finalPath, downloadUrl.toString());
                        Gson gson = new Gson();
                        mImageRef.child(String.valueOf(articleId) + index).setValue(upload);
                        String imJson = gson.toJson(upload);
                        mImageRef2.child(String.valueOf(articleId) + index).setValue(imJson);

                        Toast.makeText(AddActivity.this, "Upload successful!", Toast.LENGTH_LONG).show();
                        index++;
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                //progressBar.setProgress((int) progress);
                            }
                        });
            }
        }
    }

    private void uploadInfo() {
        title = etTitle.getText().toString().trim();
        content = etContent.getText().toString().trim();
        location = etLocation.getText().toString().trim();

        userId = User.getName();
        time = getDate();
        //Toast.makeText(AddActivity.this, sp.getString("1" , ""),Toast.LENGTH_LONG).show();
        ArticleInfo articleInfo = new ArticleInfo(articleId, userId, location, title, content, time, isUrl);
        mArticleRef.child(String.valueOf(articleId)).setValue(articleInfo);
        Gson gson = new Gson();
        String userJson = gson.toJson(articleInfo);
        mArticleRef2.child(String.valueOf(articleId)).setValue(userJson);
    }

    private int getArticleId() {
        long l;
        int i = 0;
        l = System.currentTimeMillis();
        i = (int) (l % 900000) + 100000;
        return i;
    }

    private String getDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));

        String year = String.valueOf(cal.get(Calendar.YEAR));
        String month = String.valueOf(cal.get(Calendar.MONTH)) + 1;
        String day = String.valueOf(cal.get(Calendar.DATE));
        String hour;
        String minute;
        String second;
        String my_time_1;
        String my_time_2;
        if (cal.get(Calendar.AM_PM) == Calendar.AM)
            hour = String.valueOf(cal.get(Calendar.HOUR));
        else
            hour = String.valueOf(cal.get(Calendar.HOUR) + 12);
        minute = String.valueOf(cal.get(Calendar.MINUTE));
        second = String.valueOf(cal.get(Calendar.SECOND));

        my_time_1 = year + "-" + month + "-" + day;
        my_time_2 = hour + ":" + minute + ":" + second;
        return my_time_1 + " " + my_time_2;
    }

    private void init() {
        topBarImage_1 = findViewById(R.id.top_bar_image_1);
        etTitle = findViewById(R.id.et_title);
        etContent = findViewById(R.id.et_content);
        ivLocation = findViewById(R.id.iv_location);
        etLocation = findViewById(R.id.et_location);
        buttonSubmit = findViewById(R.id.bt_submit);
        //progressBar = findViewById(R.id.progress_bar);

        gridView = (GridView) findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);
        dialog = new MyDialog(this);
        dialog.setOnButtonClickListener(this);
        // activity中调用其他activity中组件的方法
        LayoutInflater layout = this.getLayoutInflater();
        layout.inflate(R.layout.add_layout_select_photo, null);
    }

    private void initData() {
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mArticleRef = FirebaseDatabase.getInstance().getReference("articles");
        mImageRef = FirebaseDatabase.getInstance().getReference("images");
        mImageRef2 = FirebaseDatabase.getInstance().getReference("imagesJson");
        mArticleRef2 = FirebaseDatabase.getInstance().getReference("articlesJson");

        articleId = getArticleId();
        imageUrls = new ArrayList<>();
        isUrl = "NULL";
        /*
         * 载入默认图片添加图片加号
         */
        // 导入临时图片
        bmp = BitmapFactory.decodeResource(getResources(),
                R.drawable.gridview_addpic); // 加号

        photoData = new ArrayList<>();
        imageItem = new ArrayList<>();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, out);
        photoData.add(out.toByteArray());
        HashMap<String, Bitmap> map = new HashMap<>();
        map.put("itemImage", bmp);
        imageItem.add(map);
        simpleAdapter = new SimpleAdapter(this, imageItem,
                R.layout.add_griditem_addpic, new String[]{"itemImage"},
                new int[]{R.id.image_gridview});
        simpleAdapter.setViewBinder(new ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data,
                                        String textRepresentation) {
                // TODO Auto-generated method stub
                if (view instanceof ImageView && data instanceof Bitmap) {
                    ImageView i = (ImageView) view;
                    i.setImageBitmap((Bitmap) data);
                    return true;
                }
                return false;
            }
        });
        gridView.setAdapter(simpleAdapter);
    }


    @Override
    public void camera() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
                Environment.getExternalStorageDirectory(), "temp.jpg")));
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void gallery() {
        // TODO Auto-generated method stub
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_OPEN);
    }

    @Override
    public void cancel() {
        // TODO Auto-generated method stub
        dialog.cancel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == NONE)
            return;

        // 拍照
        if (requestCode == CAMERA) {
            // 设置文件保存路径这里放在跟目录下
            File picture = new File(Environment.getExternalStorageDirectory()
                    + "/temp.jpg");
            startPhotoZoom(Uri.fromFile(picture));
        }
        // 打开图片
        if (resultCode == RESULT_OK && requestCode == IMAGE_OPEN) {
            startPhotoZoom(data.getData());
        }

        if (data == null)
            return;

        // 处理结果
        if (requestCode == PHOTO_ZOOM) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap photo = extras.getParcelable("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 75, stream);// (0-100)压缩文件
                photoData.add(stream.toByteArray());
                // 将图片放入gridview中
                HashMap<String, Bitmap> map = new HashMap<>();
                map.put("itemImage", photo);
                imageItem.add(map);
                simpleAdapter = new SimpleAdapter(this, imageItem,
                        R.layout.add_griditem_addpic, new String[]{"itemImage"},
                        new int[]{R.id.image_gridview});
                simpleAdapter.setViewBinder(new ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Object data,
                                                String textRepresentation) {
                        // TODO Auto-generated method stub
                        if (view instanceof ImageView && data instanceof Bitmap) {
                            ImageView i = (ImageView) view;
                            i.setImageBitmap((Bitmap) data);
                            return true;
                        }
                        return false;
                    }
                });
                gridView.setAdapter(simpleAdapter);
                simpleAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (!TextUtils.isEmpty(pathImage)) {
            Bitmap addbmp = BitmapFactory.decodeFile(pathImage);
            ByteArrayOutputStream outPut = new ByteArrayOutputStream();
            addbmp.compress(Bitmap.CompressFormat.PNG, 75, outPut);// (0-100)压缩文件
            photoData.add(outPut.toByteArray());
            HashMap<String, Bitmap> map = new HashMap<>();
            map.put("itemImage", addbmp);
            imageItem.add(map);
            simpleAdapter = new SimpleAdapter(this, imageItem,
                    R.layout.add_griditem_addpic, new String[]{"itemImage"},
                    new int[]{R.id.image_gridview});
            simpleAdapter.setViewBinder(new ViewBinder() {
                @Override
                public boolean setViewValue(View view, Object data,
                                            String textRepresentation) {
                    // TODO Auto-generated method stub
                    if (view instanceof ImageView && data instanceof Bitmap) {
                        ImageView i = (ImageView) view;
                        i.setImageBitmap((Bitmap) data);
                        return true;
                    }
                    return false;
                }
            });
            gridView.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
            // 刷新后释放防止手机休眠后自动添加
            pathImage = null;
            dialog.dismiss();
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        // TODO Auto-generated method stub
        if (imageItem.size() == 10) { // 第一张为默认图片

            if (position != 0) {
                dialog(position);
            } else {
                Toast.makeText(AddActivity.this, "Maximum 9 photos allowed",
                        Toast.LENGTH_SHORT).show();
            }

        } else if (position == 0) { // 点击图片位置为+ 0对应0张图片
            // 选择图片
            dialog.show();
        } else {
            dialog(position);
        }

    }

    /*
     * Dialog对话框提示用户删除操作 position为删除图片位置
     */
    protected void dialog(final int position) {
        Builder builder = new Builder(AddActivity.this);
        builder.setMessage("Are you sure to remove this photo?");
        builder.setTitle("Notice");
        builder.setPositiveButton("YES", (dialog, which) -> {
            dialog.dismiss();
            imageItem.remove(position);
            photoData.remove(position);
            simpleAdapter.notifyDataSetChanged();
        });
        builder.setNegativeButton("NO", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_ZOOM);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x0001:
                    Toast.makeText(AddActivity.this, "Get location successful!", Toast.LENGTH_SHORT).show();
                    JSONObject res = (JSONObject) msg.obj;
                    try {
                        JSONObject basic = res.getJSONObject("basic");
                        String address = basic.getString("location");
                        String parentCity = basic.getString("admin_area");

                        JSONObject now = res.getJSONObject("now");

                        String condTxt = now.getString("cond_txt");
                        String windDir = now.getString("wind_dir");
                        String tmp = now.getString("tmp");

                        String toMsg = parentCity + " " + address + " " + condTxt + " " + windDir + " " + tmp + "℃\n";
                        etLocation.setText(toMsg);

                    } catch (Exception e) {
                        Toast.makeText(AddActivity.this, "Wrong： " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                    break;
                case 0x0002:
                    Toast.makeText(AddActivity.this, "Wrong： " + (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    private void getAddressAndWeather() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                OkHttpClient client = new OkHttpClient();

                String url = "https://free-api.heweather.net/s6/weather/now?key=7bed08b4386e4bfd8dc621e30d8190cb&location=" + locationStr;
                Log.d("urlMsg", url);
                // 获取地址和天气
                Request request = new Request.Builder()
                        .url(url)
                        .get()  //默认为GET请求，可以不写
                        .build();
                try {
                    Response execute = client.newCall(request).execute();
                    JSONObject jsonObject = new JSONObject(execute.body().string());
                    JSONObject res = jsonObject.getJSONArray("HeWeather6").getJSONObject(0);

                    Message msg = new Message();
                    msg.what = 0x0001;
                    msg.obj = res;
                    mHandler.sendMessage(msg);

                } catch (Exception e) {
                    Message msg = new Message();
                    msg.what = 0x0002;
                    msg.obj = e.getMessage();
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }

    private void getLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //获取权限（如果没有开启权限，会弹出对话框，询问是否开启权限）
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
            } else {

                List<String> providers = locationManager.getProviders(true);
                if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                    //如果是Network
                    locationProvider = LocationManager.NETWORK_PROVIDER;
                }else if (providers.contains(LocationManager.GPS_PROVIDER)) {
                    //如果是GPS
                    locationProvider = LocationManager.GPS_PROVIDER;
                }

                //监视地理位置变化
                locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(locationProvider);
                if (location != null) {
                    locationStr = location.getLongitude() + "," + location.getLatitude();
                    locationManager.removeUpdates(locationListener);
                }

            }
        } else {
            List<String> providers = locationManager.getProviders(true);
            if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                //如果是Network
                locationProvider = LocationManager.NETWORK_PROVIDER;
            }else if (providers.contains(LocationManager.GPS_PROVIDER)) {
                //如果是GPS
                locationProvider = LocationManager.GPS_PROVIDER;
            }

            //监视地理位置变化
            locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
            Location location = locationManager.getLastKnownLocation(locationProvider);
            if (location != null) {
                locationStr = location.getLongitude() + "," + location.getLatitude();
                locationManager.removeUpdates(locationListener);
            }
        }
    }

    public LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                //不为空
                locationStr = location.getLongitude() + "," + location.getLatitude();
                locationManager.removeUpdates(locationListener);
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Request permission", Toast.LENGTH_LONG).show();
                    try {
                        List<String> providers = locationManager.getProviders(true);
                        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                            //如果是Network
                            locationProvider = LocationManager.NETWORK_PROVIDER;
                        }else if (providers.contains(LocationManager.GPS_PROVIDER)) {
                            //如果是GPS
                            locationProvider = LocationManager.GPS_PROVIDER;
                        }

                        //监视地理位置变化
                        locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
                        Location location = locationManager.getLastKnownLocation(locationProvider);
                        if (location != null) {
                            locationStr = location.getLongitude() + "," + location.getLatitude();
                            locationManager.removeUpdates(locationListener);
                        }
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(this, "The lack of permissions", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

}
