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
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.permissionx.guolindev.PermissionX;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class PhotoSelectFragment extends Fragment {


    public RecyclerView mPhotoRecyclerView;
    public GridImageAdapterPro gridImageAdapterPro;

    View view;

    private List<LocalMedia> selectList = new ArrayList<>();


    public boolean isPreview = false;



    public PhotoSelectFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void init() {
        Log.d("PhotoSelectFragment", "init");
    }

    public List<LocalMedia> getSelectList() {
        return selectList;
    }

    public void setSelectList(List<LocalMedia> selectList, boolean isPreview) {

        gridImageAdapterPro.setPreview(isPreview);
        setSelectList(selectList);
    }

    public void setSelectList(List<LocalMedia> selectList) {
        this.selectList.clear();
        this.selectList.addAll(selectList);
        gridImageAdapterPro.setData(selectList);
        gridImageAdapterPro.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_photo_select, container, false);
        mPhotoRecyclerView = view.findViewById(R.id.rec_photo_select);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        //通知外部可以传递值过来了
        if (onShowListener != null) {
            onShowListener.notifyShow();
        }
    }

    private void initListener() {
        gridImageAdapterPro.setOnClickListener(new GridImageAdapterPro.OnClickListener() {
            @Override
            public void onAddClick() {
                selectPhoto();
                if (onClickListener != null) {
                    onClickListener.setselect();
                }

            }

            @Override
            public void onDelClick(int position) {

                gridImageAdapterPro.notifyItemRemoved(position);
                selectList.remove(gridImageAdapterPro.getData().get(position));
            }

            @Override
            public void onPreviewClick(int position, String path) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getMimeType();
                    int mediaType = PictureMimeType.getMimeType(pictureType);
                    switch (mediaType) {
                        case 1:
                            PictureSelector.create(getActivity()).themeStyle(R.style.picture_default_style)
                                    .openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(getActivity()).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(getActivity()).externalPictureAudio(media.getPath());
                            break;
                        default:
                            break;
                    }
                }

            }
        });
    }

    public void selectPhoto() {


        selectPhoto(PictureMimeType.ofImage());
    }

    /**
     * 拍照或者选择图片
     */
    public void selectPhoto(int code) {

        //获取写的权限
        PermissionX.init(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .explainReasonBeforeRequest()
                .onExplainRequestReason((scope, deniedList, beforeRequest) -> {
//                                CustomDialog customDialog = new CustomDialog(MainJavaActivity.this, "PermissionX needs following permissions to continue", deniedList);
//                                scope.showRequestReasonDialog(customDialog);
                    scope.showRequestReasonDialog(deniedList, getString(R.string.request_permission), getString(R.string.allow));
                })
                .onForwardToSettings((scope, deniedList) -> scope.showForwardToSettingsDialog(deniedList, getString(R.string.request_permission_setting), "Allow"))
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        for (int i = 0; i < selectList.size(); i++) {
                            selectList.get(i).getMimeType();
                        }

                        //相册
                        PictureSelector.create(this)
                                .openGallery(code)
                                .imageEngine(GlideEngine.createGlideEngine())
                                .maxSelectNum(9)
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .isCompress(true)
                                .synOrAsy(true)
                                .compressQuality(60)
                                .cutOutQuality(60)
                                .selectionData(selectList) // 是否传入已选图片
                                .isMaxSelectEnabledMask(true) //当达到最大选择数量时，列表是否启用遮罩效果
                                .minimumCompressSize(100) // 小于多少kb的图片不压缩
                                .isCamera(true)// 是否显示拍照按钮
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(new OnResultCallbackListener() {
                                    @Override
                                    public void onResult(List result) {
                                        List<LocalMedia> images = result;
                                        selectList.clear();
                                        selectList.addAll(images);
                                        gridImageAdapterPro.setData(selectList);
                                        gridImageAdapterPro.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancel() {

                                    }
                                });
                        //相册
                    } else {
                    Toast.makeText(getContext(), "拒绝", Toast.LENGTH_SHORT).show();

                    }
                });

    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            //文件选择回调
//            //照片回调
//            if (requestCode == Integer.parseInt(getTag())) {
//                // 图片、视频、音频选择结果回调
//                List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
//                selectList.clear();
//                selectList.addAll(images);
//                gridImageAdapterPro.setData(selectList);
//                gridImageAdapterPro.notifyDataSetChanged();
//            }
//        }
//    }

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;

    }

    private OnShowListener onShowListener;

    public void setOnShowListener(OnShowListener onShowListener) {
        this.onShowListener = onShowListener;
    }

    public interface OnClickListener {
        void setselect();


    }

    public interface OnShowListener {
        void notifyShow();
    }

    /**
     * 接收到图片就下发
     *
     * @param selectTag  在xml里的标签
     * @param resultCode
     * @param data
     */
    public static void forwardData(String selectTag, int resultCode, Intent data, List<Fragment> tagList) {
        for (Fragment fragment : tagList) {
            if (selectTag.equals(fragment.getTag())) {
                fragment.onActivityResult(Integer.parseInt(fragment.getTag()), resultCode, data);
            }
        }
    }
}