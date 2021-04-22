package cz.uhk.fim.student.schmid.java.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cz.uhk.fim.student.schmid.R;
import cz.uhk.fim.student.schmid.java.adapter.LibraryBookItemAdapter;
import cz.uhk.fim.student.schmid.java.viewmodel.LibraryViewModel;

public class LibraryFragment extends Fragment {

    private LibraryViewModel libraryViewModel;
    private RecyclerView recyclerView;
    private TextView noResultTv;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        libraryViewModel = new ViewModelProvider(this).get(LibraryViewModel.class);
        libraryViewModel.init();

        View root = inflater.inflate(R.layout.fragment_library, container, false);

        recyclerView = root.findViewById(R.id.rv_library_book_items);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setHasFixedSize(true);

        noResultTv = root.findViewById(R.id.library_no_result);

        setHasOptionsMenu(true);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        libraryViewModel.getBooks().observe((LifecycleOwner) this,
                it -> recyclerView.setAdapter(new LibraryBookItemAdapter(getContext(), it, noResultTv)));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint(getResources().getText(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ((LibraryBookItemAdapter) recyclerView.getAdapter()).getFilter().filter(newText);
                return false;
            }
        });

    }
}
