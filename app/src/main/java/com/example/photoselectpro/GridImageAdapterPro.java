package com.example.photoselectpro;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

public class GridImageAdapterPro extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW = 1;
    private static final int ADD_VIEW = 2;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<LocalMedia> mData = new ArrayList<>();
    private int maxSize = -1;
    private OnClickListener onClickListener;

    private boolean isPreview = false;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;

    }

    public GridImageAdapterPro(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public boolean isPreview() {
        return isPreview;
    }

    public void setPreview(boolean preview) {
        isPreview = preview;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);

    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public void setData(List<LocalMedia> data) {
        this.mData = data;
        notifyDataSetChanged();

    }

    public void addData(LocalMedia data) {
        mData.add(data);
        notifyItemInserted(mData.size() - 1);
        if (mData.size() == maxSize) {
            notifyDataSetChanged();
        }

    }

    public List<LocalMedia> getData() {
        return mData;

    }

    public LocalMedia getItem(int position) {
        return mData.get(position);

    }

    public void setItem(LocalMedia fileBean, int position) {
        mData.set(position, fileBean);
        notifyItemChanged(position);

    }

    public void removeData(ArrayList<LocalMedia> data, int position) {
        this.mData = data;
        notifyItemRemoved(position);

    }

    private boolean isShowAdd(int position) {
        int size = mData.size() == 0 ? 0 : mData.size();
        return position > size;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = mLayoutInflater.inflate(R.layout.item_media, parent, false);
        return new ViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder) holder;


        if (position < mData.size()) {
            vh.cvDel.setVisibility(View.VISIBLE);
            LocalMedia fileBean = mData.get(position);
            String path = fileBean.getPath();
            int placeholder = R.drawable.ic_photo_black;
            int chooseModel = fileBean.getChooseModel();

            if (chooseModel == PictureConfig.TYPE_IMAGE) {
                vh.tvTime.setVisibility(View.GONE);

            } else if (chooseModel == PictureConfig.TYPE_VIDEO) {
                vh.tvTime.setText(String.valueOf(fileBean.getDuration()));
                vh.tvTime.setVisibility(View.VISIBLE);
            } else if (chooseModel == PictureMimeType.ofAudio()) {
                vh.tvTime.setVisibility(View.VISIBLE);
                vh.tvTime.setText(String.valueOf(fileBean.getDuration()));
                vh.tvTime.setTextColor(mContext.getColor(R.color.black));
                path = "";
                placeholder = R.drawable.ic_audio_theme;
            }

            Glide.with(mContext)
                    .load(path)
                    .placeholder(placeholder)
                    .into(vh.ibtnPhoto);
            String finalPath = path;
            vh.ibtnPhoto.setOnClickListener(v ->
                    onClickListener.onPreviewClick(position, finalPath));
        } else {
            if (!isPreview) {


                Glide.with(mContext)
                        .load(mContext.getDrawable(R.drawable.ic_baseline_add_24))
                        .thumbnail(0.1f)
                        .into(vh.ibtnPhoto);
                vh.ibtnPhoto.setBackgroundResource(R.drawable.bg_storke_round);
                vh.ibtnPhoto.setOnClickListener(v -> onClickListener.onAddClick());
            } else {
                vh.cvDel.setVisibility(View.GONE);
            }


        }
        if (isPreview || mData.size() == 0) {
            vh.cvDel.setVisibility(View.GONE);
        }


        vh.cvDel.setOnClickListener(v -> onClickListener.onDelClick(position));
    }

    /**
     * @return 返回个数
     */
    @Override
    public int getItemCount() {

        if (isPreview) {
            return mData.size();
        }
        if (maxSize == -1 || isShowAdd(maxSize)) {
            return mData.size() + 1;
        } else {
            return maxSize;
        }
    }

    /**
     * @param
     * @return
     * @创建人 CZY
     * @创建时间 2019/4/11 11:28
     * @修改人
     * @修改时间
     **/

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ibtnPhoto;
        private final TextView tvTime;
        private final TextView tvProgress;
        private final CardView cvDel;

        public ViewHolder(View v) {
            super(v);
            ibtnPhoto = v.findViewById(R.id.iv_addMediaAdapter_media);
            tvTime = v.findViewById(R.id.tv_addMediaAdapter_time);
            tvProgress = v.findViewById(R.id.tv_addMediaAdapter_progress);
            cvDel = v.findViewById(R.id.cv_addMediaAdapter_delete);
        }
    }

    public interface OnClickListener {
        /**
         * 添加事件
         */
        void onAddClick();

        /**
         * 删除当前
         */
        void onDelClick(int position);

        /**
         * 预览
         *
         * @param position 当前点击的位置
         * @parmar path  路径
         */
        void onPreviewClick(int position, String path);
    }


}
