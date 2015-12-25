package ckmah.mangoreader.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.william.mangoreader.R;

import ckmah.mangoreader.activity.MangaViewerActivity;
import ckmah.mangoreader.parse.MangaEden;

public class MangaImageFragment extends Fragment {
    private static final String IMAGE_DATA_EXTRA = "item_index";
    private int itemIndex;

    public MangaImageFragment() {
        // required empty constructor
    }

    public static MangaImageFragment newInstance(int index) {
        Bundle args = new Bundle();
        args.putInt(IMAGE_DATA_EXTRA, index);
        MangaImageFragment f = new MangaImageFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemIndex = getArguments() != null ? getArguments().getInt(IMAGE_DATA_EXTRA) : 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.manga_image_fragment, container, false);
        ImageView imageView = (ImageView) root.findViewById(R.id.manga_image_view);
        MangaViewerActivity activity = (MangaViewerActivity) getActivity();
        String imageUrl = activity.getImages().get(itemIndex).getUrl();

        MangaEden.setMangaImage(imageUrl, activity, imageView);
        return root;
    }
}
