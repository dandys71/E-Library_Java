package cz.uhk.fim.student.schmid.java.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import cz.uhk.fim.student.schmid.R;
import cz.uhk.fim.student.schmid.java.adapter.BookItemAdapter;
import cz.uhk.fim.student.schmid.java.viewmodel.ReservedViewModel;

public class ReservedFragment extends Fragment {
    private ReservedViewModel reservedViewModel;
    private RecyclerView recyclerView;
    private TextView noDataTv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        reservedViewModel = new ViewModelProvider(this).get(ReservedViewModel.class);
        reservedViewModel.init();

        View root = inflater.inflate(R.layout.fragment_reserved, container, false);

        recyclerView = root.findViewById(R.id.rv_reserved_book_items);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        recyclerView.setHasFixedSize(true);

        noDataTv = root.findViewById(R.id.reserved_no_data);

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onResume() {
        reservedViewModel.getBooks().observe((LifecycleOwner) this,
                it -> {
                    noDataTv.setVisibility((it.isEmpty()) ? View.VISIBLE : View.GONE);
                    recyclerView.setAdapter(new BookItemAdapter(getContext(), it, BookDetailActivity.FRAGMENT.RESERVED));
                });
        super.onResume();
    }
}
