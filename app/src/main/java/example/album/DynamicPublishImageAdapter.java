package example.album;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dmcbig.mediapicker.entity.Media;
import com.style.base.BaseRecyclerViewAdapter;
import com.style.framework.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.style.manager.ImageLoadManager;

public class DynamicPublishImageAdapter extends BaseRecyclerViewAdapter<Media> {

    private OnDeleteClickListener listener;

    public DynamicPublishImageAdapter(Context context, ArrayList<Media> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.adapter_publish_dynamic_picture, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final int index = position;
        ViewHolder holder = (ViewHolder) viewHolder;
        Media media = getData(position);
        if (position != getItemCount() - 1) {
            holder.ivDelete.setVisibility(View.VISIBLE);
            ImageLoadManager.loadNormalPicture(mContext, holder.ivActiveImages, media.path);

        } else {
            holder.ivDelete.setVisibility(View.GONE);
            holder.ivActiveImages.setImageResource(R.mipmap.ic_add_photo);
        }
        super.setOnItemClickListener(holder, position);

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClickDelete(index);
                }
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iv_active_images)
        ImageView ivActiveImages;
        @Bind(R.id.iv_delete)
        ImageView ivDelete;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        if (listener != null) {
            this.listener = listener;
        }
    }

    public interface OnDeleteClickListener {
        void onItemClickDelete(int position);
    }

}