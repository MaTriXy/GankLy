package com.gank.gankly.ui.discovered.teamBlog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gank.gankly.R;
import com.gank.gankly.bean.JiandanBean;
import com.gank.gankly.listener.ItemClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by LingYan on 2016-07-20
 * Email:137387869@qq.com
 */
public class TeamBlogAdapter extends RecyclerView.Adapter<TeamBlogAdapter.JiandanHolder> {
    private List<JiandanBean> mList;
    private ItemClick mMeiZiOnClick;
    private Context mContext;

    public TeamBlogAdapter() {
        mList = new ArrayList<>();
    }

    @Override
    public JiandanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_team_blog, parent, false);
        return new JiandanHolder(view);
    }

    @Override
    public void onBindViewHolder(JiandanHolder holder, int position) {
        JiandanBean bean = mList.get(position);
        holder.bean = bean;
        holder.txtTitle.setText(bean.getTitle());

        Glide.with(mContext)
                .load(bean.getImgUrl())
                .into(holder.img);
    }

    public void setListener(ItemClick mMeiZiOnClick) {
        this.mMeiZiOnClick = mMeiZiOnClick;
    }

    public void updateItem(List<JiandanBean> list) {
        int size = mList.size();
        mList.clear();
        notifyItemRangeRemoved(0, size);
        appendItem(list);
    }

    public void appendItem(List<JiandanBean> list) {
        mList.addAll(list);
        notifyItemRangeInserted(mList.size(), list.size());
    }

    @Override
    public void onViewRecycled(JiandanHolder holder) {
        super.onViewRecycled(holder);
        Glide.clear(holder.img);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class JiandanHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.technology_txt_title)
        TextView txtTitle;
        @BindView(R.id.technology_img)
        ImageView img;

        private JiandanBean bean;

        public JiandanHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mMeiZiOnClick != null) {
                mMeiZiOnClick.onClick(getAdapterPosition(), bean);
            }
        }
    }
}
