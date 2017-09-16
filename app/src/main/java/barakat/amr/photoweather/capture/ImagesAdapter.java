package barakat.amr.photoweather.capture;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

import barakat.amr.photoweather.R;

import static barakat.amr.photoweather.capture.ImageUtils.getImageDirectory;

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
            String fullPath = "file://" + getImageDirectory() + File.separator + path;
            Glide.with(context).asBitmap().load(fullPath).into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return paths.size();
    }
}
