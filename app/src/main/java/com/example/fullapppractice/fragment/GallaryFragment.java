package com.example.fullapppractice.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.fullapppractice.R;
import com.example.fullapppractice.activity.DisplaySelectedImageActivity;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.List;

public class GallaryFragment extends Fragment {

    Button gallerys, cameras;
    String mCurrentPhotoPath;
    Cursor myCursor;
    List<String> path;
    public static ArrayList<com.nguyenhoanglam.imagepicker.model.Image> images = new ArrayList<>();

    public GallaryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallary, container, false);
        gallerys = view.findViewById(R.id.gallery);
        cameras = view.findViewById(R.id.camera);

        gallerys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(GallaryFragment.this)
                        .setFolderMode(true)
                        .setToolbarColor("#008577")
                        .setStatusBarColor("#008577")
                        .setFolderTitle("Album")
                        .setMultipleMode(true)
                        .setSelectedImages(images)
                        .setMaxSize(5)
                        .setBackgroundColor("#ffffff")
                        .setAlwaysShowDoneButton(true)
                        .setRequestCode(0)
                        .start();
            }
        });

        cameras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(GallaryFragment.this)
                        .setCameraOnly(true)
                        .start();
            }
        });
        return view;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == 100 && i2 == -1) {
            try {
                Intent intent2 = new Intent(getActivity(), DisplaySelectedImageActivity.class);
                intent2.putExtra("Capture_Image_Name", this.mCurrentPhotoPath);
                intent2.putExtra("Images", "Camera");
                startActivityForResult(intent2, 0);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return;
            }
        }
        if (i == 0 && i2 == -1 && intent != null) {
            try {
                String[] strArr = {"_id", "_data"};
                myCursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, strArr, null, null, "_id DESC");
                String str = "";
                try {
                    myCursor.moveToFirst();
                    String string = myCursor.getString(myCursor.getColumnIndexOrThrow("_data"));
                    myCursor.close();
                    str = string;
                } catch (Exception e2) {
                    e2.printStackTrace();
                    myCursor.close();
                }
                Intent intent3 = new Intent(getActivity(), DisplaySelectedImageActivity.class);
                intent3.putExtra("Capture_Image_Name", str);
                intent3.putExtra("Images", "Camera");
                startActivityForResult(intent3, 0);
            } catch (IllegalArgumentException e3) {
                e3.printStackTrace();
            } catch (Throwable th) {
                myCursor.close();
                throw th;
            }
        }
        Intent intent4 = new Intent(getActivity(), DisplaySelectedImageActivity.class);
        intent4.putExtra("Images", "Album");
        images = intent.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
        path = new ArrayList();
        printImages(images);
        intent4.putStringArrayListExtra("Gallary_Image", (ArrayList) path);
        startActivityForResult(intent4, 0);
        super.onActivityResult(i, i2, intent);
    }

    public void printImages(List<com.nguyenhoanglam.imagepicker.model.Image> list) {
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(((com.nguyenhoanglam.imagepicker.model.Image) list.get(i)).getPath());
                Log.d("image", sb.toString());
                path.add(((Image) list.get(i)).getPath());
            }
        }
    }
}
