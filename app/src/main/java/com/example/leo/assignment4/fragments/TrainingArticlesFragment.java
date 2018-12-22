package com.example.leo.assignment4.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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
import com.example.leo.assignment4.database.models.TrainingArticles;
import com.example.leo.assignment4.view.PrivateTrainerInfosAdapter;
import com.example.leo.assignment4.view.TrainingArticlesAdapter;

import java.util.ArrayList;
import java.util.List;


public class TrainingArticlesFragment extends Fragment {

    private TrainingArticlesAdapter mAdapter;
    private View addView;
    private List<TrainingArticles> articlesList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private TextView noArticlesView;

    private DatabaseHelper db;
    public TrainingArticlesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        addView = inflater.inflate(R.layout.fragment_training_articles, container, false);

        return (addView);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        coordinatorLayout = addView.findViewById(R.id.coordinator_layout);
        recyclerView = addView.findViewById(R.id.recycler_view);
        noArticlesView = addView.findViewById(R.id.empty_articles_view);

        db = new DatabaseHelper(getActivity());

        articlesList.addAll(db.getAllArticles());

        FloatingActionButton fab = (FloatingActionButton) addView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showArticlesDialog(false, null, -1);
            }
        });

        mAdapter = new TrainingArticlesAdapter(getActivity(), articlesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyArticles();

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

    private void createArticle(String article) {
        long id = db.insertArticle(article);

        TrainingArticles art = db.getArticle(id);

        if (art != null) {

            articlesList.add(0, art);

            mAdapter.notifyDataSetChanged();

            toggleEmptyArticles();
        }
    }

    private void updateArticle(String article, int position) {
        TrainingArticles art = articlesList.get(position);
        art.setArticles(article);

        db.updateArticle(art);

        articlesList.set(position, art);
        mAdapter.notifyItemChanged(position);

        toggleEmptyArticles();
    }

    private void deleteArticles(int position) {
        db.deleteArticle(articlesList.get(position));

        articlesList.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyArticles();
    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose option");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showArticlesDialog(true, articlesList.get(position), position);
                } else {
                    deleteArticles(position);
                }
            }
        });
        builder.show();
    }

    private void showArticlesDialog(final boolean shouldUpdate, final TrainingArticles article, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity());
        View view = layoutInflaterAndroid.inflate(R.layout.articles_dialog, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
        alertDialogBuilderUserInput.setView(view);

        final EditText inputArticle = view.findViewById(R.id.article);
        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? getString(R.string.lbl_new_article_title) : getString(R.string.lbl_edit_article_title));

        if (shouldUpdate && article != null) {
            inputArticle.setText(article.getArticles());
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
                if (TextUtils.isEmpty(inputArticle.getText().toString())) {
                    Toast.makeText(getActivity(), "Enter Article!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }

                if (shouldUpdate && article != null) {
                    updateArticle(inputArticle.getText().toString(), position);
                } else {
                    createArticle(inputArticle.getText().toString());
                }
            }
        });
    }

    private void toggleEmptyArticles() {

        if (db.getArticlesCount() > 0) {
            noArticlesView.setVisibility(View.GONE);
        } else {
            noArticlesView.setVisibility(View.VISIBLE);
        }
    }
}
