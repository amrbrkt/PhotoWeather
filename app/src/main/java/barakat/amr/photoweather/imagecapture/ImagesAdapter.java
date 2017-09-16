package barakat.amr.photoweather.imagecapture;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import barakat.amr.photoweather.Constants;
import barakat.amr.photoweather.ImageFileUtils;
import barakat.amr.photoweather.R;


public class ImagesAdapter extends RecyclerView.Adapter<ImageViewHolder> {
    private Context context;
    private List<String> paths;

    public ImagesAdapter(Context context, List<String> paths) {
        this.context = context;
        this.paths = paths;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        String path = paths.get(position);
        if (path != null) {
            holder.text.setText(path);
            final String fullPath = "file://" + ImageFileUtils.getImageDirectory() + File.separator + path;
            Glide.with(context).asBitmap().load(fullPath).into(holder.imageView);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ImageViewActivity.class);
                    intent.putExtra(Constants.IMAGE_URI, fullPath);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return paths.size();
    }
}
