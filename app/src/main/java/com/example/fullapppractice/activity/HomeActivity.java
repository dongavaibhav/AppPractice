package com.example.fullapppractice.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.fullapppractice.R;
import com.example.fullapppractice.fragment.CptoolbarFragment;
import com.example.fullapppractice.fragment.EditFragment;
import com.example.fullapppractice.fragment.GallaryFragment;
import com.example.fullapppractice.fragment.HorizontalRecyclerFragment;
import com.example.fullapppractice.fragment.RecyclerFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.customer_fragment)
    public FrameLayout customerFragment;

    @BindView(R.id.ivRecyclerview)
    public ImageView ivrecycler;

    @BindView(R.id.ivCptoolbar)
    public ImageView ivcptool;

    @BindView(R.id.ivGridAndHori)
    public ImageView ivGridandhori;

    @BindView(R.id.ivCameraGallery)
    public ImageView ivCamAndgallery;

    @BindView(R.id.llBottom)
    public LinearLayout llBottom;

    @BindView(R.id.fab)
    public FloatingActionButton fabs;

    public boolean fromDeal = true;

    private boolean isFromDealSearchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        fabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LocaldbActivity.class);
                startActivity(intent);
                Snackbar.make(view, "Open the Database", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final Intent i = this.getIntent();
        isFromDealSearchResult = i.getBooleanExtra("isFromDealSearchResult", false);
        String resultdeal = i.getStringExtra("dealDetailFromSearchResult");
        if (isFromDealSearchResult) {
            Fragment fragment = new EditFragment();
            Bundle args = new Bundle();
            args.putString("dealDetail", resultdeal);
            args.putBoolean("isFromDealSearchResult", true);
            fragment.setArguments(args);
            openFragmentWithBackStack(R.id.customer_fragment, fragment);
        }
    }


    private void displayView(int position) {
        Log.e("Item Click", "Position : " + position);
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                ivrecycler.setClickable(true);
                fragment = new RecyclerFragment();
                title = getString(R.string.recyler);
                break;
            case 1:
                fragment = new CptoolbarFragment();
                title = getString(R.string.cptool);
                break;
            case 2:
                fragment = new GallaryFragment();
                title = getString(R.string.cam);
                break;
            case 3:
                fragment = new HorizontalRecyclerFragment();
                title = getString(R.string.horizontal);
                break;
            default:
                break;
        }


        if (fragment != null) {
            openFragment(R.id.customer_fragment, fragment);
        }
    }

    protected void openFragment(int container, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container, fragment);
        fragmentTransaction.commit();
    }

    public void openFragmentWithBackStack(int container, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fragmentTransaction.add(container, fragment);
        fragmentTransaction.commit();
    }

    @OnClick({R.id.ivRecyclerview, R.id.ivCptoolbar, R.id.ivGridAndHori, R.id.ivCameraGallery})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivRecyclerview:
                ivrecycler.setClickable(true);
                fromDeal = true;
                displayView(0);
                DealClick();
                break;
            case R.id.ivCptoolbar:
                fromDeal = false;
                displayView(1);
                EventClick();
                break;
            case R.id.ivCameraGallery:
                displayView(2);
                MsgClick();
                break;
            case R.id.ivGridAndHori:
                displayView(3);
                ActivityClick();
                break;
        }
    }

    public void DealClick() {
        ivrecycler.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        ivcptool.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        ivGridandhori.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        ivCamAndgallery.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    public void EventClick() {
        ivrecycler.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        ivcptool.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        ivGridandhori.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        ivCamAndgallery.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    public void MsgClick() {
        ivrecycler.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        ivcptool.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        ivCamAndgallery.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        ivGridandhori.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
    }

    public void ActivityClick() {
        ivrecycler.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        ivcptool.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        ivCamAndgallery.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        ivGridandhori.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("result");

                Fragment fragment = new EditFragment();
                Bundle args = new Bundle();
                args.putString("dealDetail", result);
                fragment.setArguments(args);

                openFragmentWithBackStack(R.id.customer_fragment, fragment);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}