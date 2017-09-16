package barakat.amr.photoweather.imagecapture;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import barakat.amr.photoweather.R;
import butterknife.BindView;
import butterknife.ButterKnife;

class ImageViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.item_image)
    public ImageView imageView;
    @BindView(R.id.item_text)
    public TextView text;

    public ImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
