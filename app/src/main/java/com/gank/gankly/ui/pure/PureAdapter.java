package com.gank.gankly.ui.pure;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.gank.gankly.R;
import com.gank.gankly.bean.GiftBean;
import com.gank.gankly.listener.ItemClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by LingYan on 2016-04-25
 */
public class PureAdapter extends RecyclerView.Adapter<PureAdapter.GankViewHolder> {
    private List<GiftBean> mResults;
    private ItemClick mMeiZiOnClick;
    private Context mContext;

    public PureAdapter(Context context) {
        setHasStableIds(true);
        mResults = new ArrayList<>();
        mContext = context;
    }

    @Override
    public GankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_gift, parent, false);
        return new GankViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GankViewHolder holder, int position) {
        final GiftBean bean = mResults.get(holder.getAdapterPosition());
        holder.mGiftBean = bean;
        holder.position = position;
        holder.txtDesc.setText(bean.getTitle());
        holder.txtAuthor.setText(bean.getTime());
        GlideUrl glideUrl = new GlideUrl(bean.getImgUrl(), new LazyHeaders.Builder()
                .addHeader("referer", "http://www.mzitu.com/mm/")
                .build());
        Glide.with(mContext)
                .asBitmap()
                .load(glideUrl)
                .apply(new RequestOptions()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onViewRecycled(GankViewHolder holder) {
        super.onViewRecycled(holder);
        Glide.get(mContext).clearMemory();
    }

    public void refillItems(List<GiftBean> getResults) {
        int size = mResults.size();
        mResults.clear();
        notifyItemRangeRemoved(0, size);
        appedItems(getResults);
    }

    public void appedItems(List<GiftBean> getResults) {
        mResults.addAll(getResults);
        notifyItemRangeInserted(mResults.size(), getResults.size());
    }

    public void clear() {
        if (mResults != null) {
            mResults.clear();
        }
    }

    public void setOnItemClickListener(ItemClick onItemClickListener) {
        mMeiZiOnClick = onItemClickListener;
    }

    class GankViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.goods_txt_title)
        TextView txtDesc;
        @BindView(R.id.goods_img_bg)
        ImageView mImageView;
        @BindView(R.id.video_txt_author)
        TextView txtAuthor;
        int position;
        GiftBean mGiftBean;

        public GankViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            if (mMeiZiOnClick != null) {
                mMeiZiOnClick.onClick(position, mGiftBean);
            }
        }
    }
}
