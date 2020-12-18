package com.example.photoselectpro;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class PhotoSelectFragment extends Fragment {


    public RecyclerView mPhotoRecyclerView;
    GridImageAdapterPro gridImageAdapterPro;

    View view;
    //添加照片
    public  List<LocalMedia> selectList = new ArrayList<>();

    public PhotoSelectFragment() {
        // Required empty public constructor
    }

    public List<LocalMedia> getSelectList() {
        return selectList;
    }
//

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_photo_select, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPhotoRecyclerView = view.findViewById(R.id.rec_photo_select);
        setRecyclerView();
        initListener();
    }


    /**
     * @description
     * @author WL
     * @date 2020/12/17
     */
    private void setRecyclerView() {

        mPhotoRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mPhotoRecyclerView.getItemAnimator().setChangeDuration(0);
        gridImageAdapterPro = new GridImageAdapterPro(getActivity());
        mPhotoRecyclerView.setAdapter(gridImageAdapterPro);
    }

    private void initListener() {
        gridImageAdapterPro.setOnClickListener(new GridImageAdapterPro.OnClickListener() {
            @Override
            public void OnAddClick() {
                selectPhoto();
                if (onClickListener != null) {
                    onClickListener.setselect();
                }
              Log.d("PhotoSelectFragment", "s");

            }

            @Override
            public void OnDelClick(int position) {
                Log.d("PhotoSelectFragment", "selectList.size():" + selectList.size());
                selectList.remove(position);
                gridImageAdapterPro.notifyItemRemoved(position);
                Log.d("PhotoSelectFragment", "selectList.size():" + selectList.size());
                Toast.makeText(getContext(), "OnPreviewClick", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnPreviewClick(int position, String path) {
                Toast.makeText(getContext(), "OnPreviewClick", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 拍照或者选择图片
     */
    private void selectPhoto() {

        //获取写的权限
        RxPermissions rxPermission = new RxPermissions(getActivity());
        rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(permission -> {
                    if (permission.granted) {// 用户已经同意该权限
                        int countImg = 0;
                        for (int i = 0; i < selectList.size(); i++) {
                            if (selectList.get(i).getMimeType() == 1) {
                                //图片
                                countImg++;
                            }
                        }
                        if (countImg >= 9) {
                            Toast.makeText(getContext(), "图片最多选择9张", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            countImg = 9 - countImg;
                        }

                        //相册
                        PictureSelector.create(getActivity())
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(9)
                                .minSelectNum(1)
                                .imageSpanCount(4)

                                .enableCrop(false)
                                .compress(true)
                                .synOrAsy(true)
                                .minimumCompressSize(200)
                                .cropCompressQuality(60)
                                .previewImage(true)// 是否可预览图片
                                .isCamera(true)// 是否显示拍照按钮
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);


                    } else {
                        Toast.makeText(getContext(), "拒绝", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("111", "进来计次");
        Log.d("PhotoSelectFragment", "我是tag"+getTag());
        if (resultCode == RESULT_OK) {
            //文件选择回调
            //照片回调
            if (requestCode == Integer.parseInt(getTag())) {
                // 图片、视频、音频选择结果回调
                List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
                selectList.addAll(images);
                gridImageAdapterPro.setData(selectList);
                gridImageAdapterPro.notifyDataSetChanged();
            }
        }
    }
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;

    }

    public interface OnClickListener {
        void setselect();


    }



}