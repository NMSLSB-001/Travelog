package com.example.travelog.fragement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.travelog.ui.Profile.Display_personal_detailActivity;
import com.example.travelog.ui.Profile.Follow_functionActivity;
import com.example.travelog.R;
import com.example.travelog.ui.Profile.User1;
import com.example.travelog.ui.Profile.change_password_Activity;

import java.io.File;


public class Fragment_Activity3 extends Fragment {

    private TextView tv_text2;
    private Button mBtn_setting;
    private Button img_portrait;
    private Button mBtn_focus;
    private Button display_personal_detail;
    private PopupWindow mPop;
    private ListView listView;

    /**暂时未完成，获取用户名
    RegisterActivity username;
*/

    public String Username,username;//username是唯一的ID

    private Button mFromAlbum; // 从相册选取
    private Button mTakePhotos; // 照相
    private final int FROM_ALBUM = 0;
    private final String FILE_NAME = "tempimg.jpg";
    private final int FROM_TAKE_PHOTOS = 1;
    private File tempFile;
    private Bitmap bitmap;
    private final int PHOTO_CUT = 2;
    private ImageView mImgView;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Activity3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Activity3.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Activity3 newInstance(String param1, String param2) {
        Fragment_Activity3 fragment = new Fragment_Activity3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        initPhotoError();

    }
    private void initPhotoError(){
        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
        拿article里面的内容,暂时实现困难

        username= User.getName();
        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter adapter=new ArrayAdapter<String>(this,R.layout.list_item,list);
        listView.setAdapter(adapter);

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("articles").child(username);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    list.add(snapshot.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        拿article里面的内容，结束
         */

        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment__activity3,container,false);
        mImgView= view.findViewById(R.id.im_portrait);
        img_portrait= view.findViewById(R.id.img_portrait);
        img_portrait.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view=getLayoutInflater().inflate(R.layout.layout_pop,null);
                mPop=new PopupWindow(view,img_portrait.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
                mPop.showAsDropDown(img_portrait);
                mPop.setOutsideTouchable(true);
                mPop.setFocusable(true);
                mPop.update();
                //配置点击事件，因为pop会出现3个选择，所以我们设置3个事件
                TextView textView = view.findViewById(R.id.tv_good);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mPop.dismiss();
                        //这里可以进行设置，比如把性别写进txt文件
                    }
                });
                //从相册选取照片
                mFromAlbum = view.findViewById(R.id.btn_album);
                mFromAlbum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, FROM_ALBUM);
                        mPop.dismiss();
                        //这里可以进行设置，比如把性别写进txt文件
                    }
                });
                mTakePhotos = view.findViewById(R.id.btn_take_photo);
                mTakePhotos.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            // TODO Auto-generated method stub
                        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                        // 判断存储卡是否可以用，可用进行存储
                        if (Environment.getExternalStorageState().equals(
                                Environment.MEDIA_MOUNTED)) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                                    .fromFile(new File(Environment
                                            .getExternalStorageDirectory(), FILE_NAME)));
                        }
                        startActivityForResult(intent, FROM_TAKE_PHOTOS);
                        mPop.dismiss();
                        //这里可以进行设置，比如把性别写进txt文件
                    }
                });
            }
        });
        //跳转到修改密码的activity上
        //设置跳转到MainActivity并销毁WelcomeActivity
        mBtn_setting = view.findViewById(R.id.btn_setting);//找到当前Fragment的Button按钮
        mBtn_setting.animate().rotation(180).setDuration(2000).start();
        mBtn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),change_password_Activity.class);
                startActivity(intent);
                getActivity().finish(); //销毁当前Activity
            }
        });
        //跳转到搜索关注页面
        mBtn_focus=view.findViewById(R.id.btn_focus);
        mBtn_focus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Follow_functionActivity.class);
                startActivity(intent);
                getActivity().finish(); //销毁当前Activity
            }
        });
        //跳转到显示个人信息
        display_personal_detail=view.findViewById(R.id.btn_personal_detail);
        display_personal_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Display_personal_detailActivity.class);
                startActivity(intent);
                getActivity().finish(); //销毁当前Activity
            }
        });
        //获取当前用户名
        Username= User1.getName();
        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        switch (requestCode) {
            case FROM_ALBUM:
                if (data != null) {
                    // 取到照片路径
                    Uri uri = data.getData();
                    editPic(uri);
                }
                break;
            case FROM_TAKE_PHOTOS:
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    tempFile = new File(Environment.getExternalStorageDirectory(),
                            FILE_NAME);
                    editPic(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getActivity(), "No memory card found！", Toast.LENGTH_SHORT).show();
                }
                break;
            case PHOTO_CUT:
                try {
                    //Bundle extras = data.getExtras();
                    bitmap = data.getParcelableExtra("data");
                    mImgView.setImageBitmap(bitmap);
                    //删除缓存图片
                    tempFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /** 编辑选中的照片 */
    public void editPic(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 80);
        intent.putExtra("outputY", 80);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        //intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_CUT);
    }



    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        tv_text2=view.findViewById(R.id.tv_text2);
        tv_text2.setText(Username);
    }



}