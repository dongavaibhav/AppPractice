package com.example.fullapppractice.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fullapppractice.MyApplication;
import com.example.fullapppractice.R;
import com.example.fullapppractice.SQLiteHelper;
import com.example.fullapppractice.model.DataModel;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocaldbActivity extends AppCompatActivity {

    @BindView(R.id.btnAddNewRecord)
    Button btnInsertRecord;

    SQLiteHelper sQLiteHelper;

    @BindView(R.id.parentLayout)
    LinearLayout parentLayout;

    @BindView(R.id.showRecord)
    LinearLayout DisplayPerson;

    @BindView(R.id.tvNoRecordsFound)
    TextView tvNoData;

    private String rowID = null;

    @Inject
    DataModel dataModel;

    public static final int ADD_RECORD = 0;
    public static final int UPDATE_RECORD = 1;
    public static final String DML_TYPE = "DML_TYPE";
    public static final String UPDATE = "Update";
    public static final String INSERT = "Insert";
    public static final String DELETE = "Delete";
    public static final String FIRST_NAME = "Firstname";
    public static final String LAST_NAME = "Lastname";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localdb);
        ButterKnife.bind(this);
        MyApplication.app().appComponent().inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(LocaldbActivity.this)
                        .setTitle("Exit")
                        .setMessage("Are you sure you want to close this activity?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(LocaldbActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        btnInsertRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocaldbActivity.this, TableActivity.class);
                intent.putExtra(DML_TYPE, INSERT);
                startActivityForResult(intent, ADD_RECORD);
            }
        });

        sQLiteHelper = new SQLiteHelper(LocaldbActivity.this);
        showAllData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String firstname = data.getStringExtra(FIRST_NAME);
            String lastname = data.getStringExtra(LAST_NAME);

            DataModel contact = new DataModel();
            contact.setFirstName(firstname);
            contact.setLastName(lastname);

            if (requestCode == ADD_RECORD) {
                sQLiteHelper.insertRecord(contact);
            } else if (requestCode == UPDATE_RECORD) {
                contact.setID(rowID);
                sQLiteHelper.updateRecord(contact);
            }
            showAllData();
        }
    }

    private void onUpdateData(String firstname, String lastname) {
        Intent intent = new Intent(LocaldbActivity.this, TableActivity.class);
        intent.putExtra(FIRST_NAME, firstname);
        intent.putExtra(LAST_NAME, lastname);
        intent.putExtra(DML_TYPE, UPDATE);
        startActivityForResult(intent, UPDATE_RECORD);
    }

    private void showAllData() {
        LinearLayout inflateParentView;
        parentLayout.removeAllViews();
        ArrayList<DataModel> contacts = sQLiteHelper.getAllRecords();

        if (contacts.size() > 0) {
            tvNoData.setVisibility(View.GONE);
            for (int i = 0; i < contacts.size(); i++) {
                dataModel = contacts.get(i);

                final Holder holder = new Holder();
                final View view = LayoutInflater.from(this).inflate(R.layout.show_record, null);
                inflateParentView = (LinearLayout) view.findViewById(R.id.inflateParentView);
                holder.tvFullName = (TextView) view.findViewById(R.id.tvFullName);
                view.setTag(dataModel.getID());
                holder.firstname = dataModel.getFirstName();
                holder.lastname = dataModel.getLastName();
                String personName = holder.firstname + " " + holder.lastname;
                holder.tvFullName.setText(personName);

                final CharSequence[] items = {UPDATE, DELETE};
                inflateParentView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(LocaldbActivity.this);
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    rowID = view.getTag().toString();
                                    onUpdateData(holder.firstname, holder.lastname.toString());
                                } else {
                                    AlertDialog.Builder deleteDialogOk = new AlertDialog.Builder(LocaldbActivity.this);
                                    deleteDialogOk.setTitle("Delete Record?");
                                    deleteDialogOk.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    DataModel contact = new DataModel();
                                                    contact.setID(view.getTag().toString());
                                                    sQLiteHelper.deleteRecord(contact);
                                                    showAllData();
                                                }
                                            }
                                    );
                                    deleteDialogOk.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    deleteDialogOk.show();
                                }
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        return true;
                    }
                });
                parentLayout.addView(view);
            }
        } else {
            tvNoData.setVisibility(View.VISIBLE);
        }
    }

    private class Holder {
        TextView tvFullName;
        String firstname;
        String lastname;
    }
}
