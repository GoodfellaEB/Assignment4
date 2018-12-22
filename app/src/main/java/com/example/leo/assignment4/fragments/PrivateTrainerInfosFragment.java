package com.example.leo.assignment4.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leo.assignment4.MyDividerItemDecoration;
import com.example.leo.assignment4.R;
import com.example.leo.assignment4.RecyclerTouchListener;
import com.example.leo.assignment4.database.DatabaseHelper;
import com.example.leo.assignment4.database.models.PrivateTrainerInfos;
import com.example.leo.assignment4.view.PrivateTrainerInfosAdapter;

import java.util.ArrayList;
import java.util.List;

public class PrivateTrainerInfosFragment extends Fragment {

    private PrivateTrainerInfosAdapter mAdapter;
    private View addView;
    private List<PrivateTrainerInfos> infosList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noInfosView;

    private DatabaseHelper db;

    public PrivateTrainerInfosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addView = inflater.inflate(R.layout.fragment_private_trainer_infos, container, false);

        return (addView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        coordinatorLayout = addView.findViewById(R.id.coordinator_layout);
        recyclerView = addView.findViewById(R.id.recycler_view);
        noInfosView = addView.findViewById(R.id.empty_infos_view);

        db = new DatabaseHelper(getActivity());

        infosList.addAll(db.getAllInfos());

        FloatingActionButton fab = (FloatingActionButton) addView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfosDialog(false, null, -1);
            }
        });

        mAdapter = new PrivateTrainerInfosAdapter(getActivity(), infosList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyInfos();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
                showActionsDialog(position);
            }
        }));
    }

    private void createInfo(String info) {
        long id = db.insertInfo(info);

        PrivateTrainerInfos inf = db.getInfo(id);

        if (inf != null) {
            infosList.add(0, inf);

            mAdapter.notifyDataSetChanged();

            toggleEmptyInfos();
        }
    }

    private void updateInfo(String info, int position) {
        PrivateTrainerInfos inf = infosList.get(position);
        inf.setInfos(info);

        db.updateInfo(inf);

        infosList.set(position, inf);
        mAdapter.notifyItemChanged(position);

        toggleEmptyInfos();
    }

    private void deleteInfo(int position) {
        db.deleteInfo(infosList.get(position));

        infosList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyInfos();
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showInfosDialog(true, infosList.get(position), position);
                } else {
                    deleteInfo(position);
                }
            }
        });
        builder.show();
    }

    private void showInfosDialog(final boolean shouldUpdate, final PrivateTrainerInfos info, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity());
        View view = layoutInflaterAndroid.inflate(R.layout.infos_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
        alertDialogBuilderUserInput.setView(view);

        final EditText inputInfo = view.findViewById(R.id.info);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_info_title) : getString(R.string.lbl_edit_info_title));

        if (shouldUpdate && info != null) {
            inputInfo.setText(info.getInfos());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputInfo.getText().toString())) {
                    Toast.makeText(getActivity(), "Enter Info!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                if (shouldUpdate && info != null) {
                    updateInfo(inputInfo.getText().toString(), position);
                } else {
                    createInfo(inputInfo.getText().toString());
                }
            }
        });
    }

    private void toggleEmptyInfos() {

        if (db.getInfosCount() > 0) {
            noInfosView.setVisibility(View.GONE);
        } else {
            noInfosView.setVisibility(View.VISIBLE);
        }
    }
}
