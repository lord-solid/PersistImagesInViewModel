package com.solid.persistimagesinviewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.util.Supplier;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GenericListAdapter<T> extends RecyclerView.Adapter<GenericListAdapter.GenericListViewHolder> {

    private List<T> dataset;
    private Integer itemLayoutId;
    private Supplier<View> itemViewFactory;
    private BindCallback<T> onBind;

    public GenericListAdapter(List<T> dataset, Integer itemLayoutId, Supplier<View> itemViewFactory, BindCallback<T> onBind) {
        this.dataset = dataset;
        this.itemLayoutId = itemLayoutId;
        this.itemViewFactory = itemViewFactory;
        this.onBind = onBind;
    }

    public static class GenericListViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public GenericListViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }

    @Override
    public GenericListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        if (itemViewFactory != null) {
            itemView = itemViewFactory.get();
        } else if (itemLayoutId != null) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(itemLayoutId, parent, false);
        } else {
            throw new IllegalStateException("Either the layout ID or the view factory need to be non-null");
        }
        return new GenericListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(GenericListViewHolder holder, int position) {
        if (position < 0 || position > dataset.size()) {
            return;
        } else {
            onBind.bind(holder.view, dataset.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}

interface BindCallback<T> {
    void bind(View view, T data, int position);
}
