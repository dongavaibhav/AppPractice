package com.example.fullapppractice.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fullapppractice.R;
import com.example.fullapppractice.adapter.ImageSamplesAdapter;
import com.example.fullapppractice.fragment.GallaryFragment;
import com.google.android.material.snackbar.Snackbar;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;

public class DisplaySelectedImageActivity extends AppCompatActivity {

    ImageSamplesAdapter adapter;
    Context context;
    RelativeLayout coordinatorLayout;
    static ArrayList<String> imageuri;
    String imagename;

    boolean isOpenGallery = true;
    String mCurrentPhotoPath;
    Cursor myCursor;
    ArrayList<String> path1;
    int selectedPos = 0;
    private RecyclerView mImageSampleRecycler;
    private ItemTouchHelper mItemTouchHelper;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            context = this;
            setContentView((int) R.layout.activity_display_selected_image);
            imageuri = new ArrayList<>();
            Intent intent = getIntent();
            imagename = intent.getStringExtra("Images");
            if (imagename.equals("Camera")) {
                imageuri.add(intent.getStringExtra("Capture_Image_Name"));
            } else if (imageuri != null) {
                path1 = intent.getStringArrayListExtra("Gallary_Image");
                StringBuilder sb = new StringBuilder();
                sb.append("onCreate: ");
                sb.append(path1);
                Log.i("fileaa", sb.toString());
                for (int i = 0; i < path1.size(); i++) {
                    imageuri.add(path1.get(i));
                }
            }
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle((CharSequence) "");
            setSupportActionBar(toolbar);
            ((TextView) findViewById(R.id.title_text)).setText("Selected Images");
            toolbar.setNavigationIcon((int) R.drawable.back);
            toolbar.setNavigationOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    onBackPressed();
                }
            });
            coordinatorLayout = (RelativeLayout) findViewById(R.id.display_iamges);
            mImageSampleRecycler = findViewById(R.id.images_sample1);
            ArrayList arrayList = new ArrayList();
            for (int i2 = 0; i2 < 10; i2++) {
                arrayList.add(String.valueOf(i2));
            }
            setupRecycler();
            setupImageSamples();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupRecycler() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mImageSampleRecycler.setLayoutManager(gridLayoutManager);
        mImageSampleRecycler.setNestedScrollingEnabled(true);
        mImageSampleRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
            }

            public void onScrolled(@NonNull RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
            }
        });
    }

    private void setupImageSamples() {
        Context context2 = context;
        if (context2 != null) {
            adapter = new ImageSamplesAdapter(context2, imageuri, new ImageSamplesAdapter.OnImageEdit() {
                public void onImageEdit(int i) {
                    DisplaySelectedImageActivity displaySelectedImageActivity = DisplaySelectedImageActivity.this;
                    displaySelectedImageActivity.selectedPos = i;
                    CropImage.activity(Uri.fromFile(new File(displaySelectedImageActivity.adapter.getmSelectedImages().get(i)))).start(DisplaySelectedImageActivity.this);
                }
            });
            mImageSampleRecycler.setAdapter(adapter);
            mImageSampleRecycler.setHasFixedSize(true);
        }
        mItemTouchHelper.attachToRecyclerView(mImageSampleRecycler);
        StringBuilder sb = new StringBuilder();
        sb.append("setupImageSamples: ");
        sb.append(adapter.getmSelectedImages());
        Log.i("setupImageSamples", sb.toString());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_image, menu);
        return true;
    }

    public void onDestroy() {
        super.onDestroy();
        if (myCursor != null) {
            myCursor.close();
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.action_open) {
            if (this.isOpenGallery) {
                this.isOpenGallery = false;
                ImagePicker.with(DisplaySelectedImageActivity.this)
                        .setFolderMode(true)
                        .setToolbarColor("#008577")
                        .setStatusBarColor("#008577")
                        .setFolderTitle("Album")
                        .setMultipleMode(true)
                        .setSelectedImages(GallaryFragment.images)
                        .setMaxSize(5)
                        .setBackgroundColor("#ffffff")
                        .setAlwaysShowDoneButton(true)
                        .setRequestCode(0)
                        .start();
            }
            return true;
        } else {
            if (itemId == R.id.action_camera) {
                ImagePicker.with(DisplaySelectedImageActivity.this)
                        .setCameraOnly(true)
                        .start();
            }
            return super.onOptionsItemSelected(menuItem);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        StringBuilder sb = new StringBuilder();
        sb.append(isOpenGallery);
        sb.append("");
        Log.e("Called==", sb.toString());
        if (i == 0 && i2 == -1) {
            imageuri.add(mCurrentPhotoPath);
            adapter.notifyDataSetChanged();
        }
        GallaryFragment.images = intent.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
        printImages(GallaryFragment.images);

        if (i == 0 && i2 == -1) {
            String[] strArr = {"_id", "_data"};
            myCursor = getContentResolver().query(Media.EXTERNAL_CONTENT_URI, strArr, null, null, "_id DESC");
            try {
                myCursor.moveToFirst();
                imageuri.add(myCursor.getString(myCursor.getColumnIndexOrThrow("_data")));
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable th) {
                myCursor.close();
                throw th;
            }
            myCursor.close();
        }
    }

    private void printImages(ArrayList<com.nguyenhoanglam.imagepicker.model.Image> list) {
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                imageuri.add(((com.nguyenhoanglam.imagepicker.model.Image) list.get(i)).getPath());
            }
            adapter.notifyDataSetChanged();
        }
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    public void onBackPressed() {
        setResult(0, new Intent());
        super.onBackPressed();
    }

    public void onResume() {
        super.onResume();
        isOpenGallery = true;
    }
}
