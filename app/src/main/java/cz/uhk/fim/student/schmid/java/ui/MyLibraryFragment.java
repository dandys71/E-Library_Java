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
import cz.uhk.fim.student.schmid.java.viewmodel.MyLibraryViewModel;

public class MyLibraryFragment extends Fragment {

    private MyLibraryViewModel myLibraryViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myLibraryViewModel = new ViewModelProvider(this).get(MyLibraryViewModel.class);
        myLibraryViewModel.init();

        View root = inflater.inflate(R.layout.fragment_my_library, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.rv_my_library_book_items);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);

        TextView noDataTv = root.findViewById(R.id.my_library_no_data);

        myLibraryViewModel.getBooks().observe((LifecycleOwner) this,
                it -> {
                    noDataTv.setVisibility((it.isEmpty()) ? View.VISIBLE : View.GONE);
                    recyclerView.setAdapter(new BookItemAdapter(getContext(), it, BookDetailActivity.FRAGMENT.MY_LIBRARY));
                });
        setHasOptionsMenu(true);

        return root;
    }
}
