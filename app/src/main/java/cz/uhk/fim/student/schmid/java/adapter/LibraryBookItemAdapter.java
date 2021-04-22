package cz.uhk.fim.student.schmid.java.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;
import cz.uhk.fim.student.schmid.R;
import cz.uhk.fim.student.schmid.java.model.LibraryBookItem;
import cz.uhk.fim.student.schmid.java.ui.BookDetailActivity;
import static cz.uhk.fim.student.schmid.java.ui.BookDetailActivity.BOOK_FRAGMENT_TYPE;
import static cz.uhk.fim.student.schmid.java.ui.BookDetailActivity.FRAGMENT.LIBRARY;
import static cz.uhk.fim.student.schmid.java.ui.BookDetailActivity.LIBRARY_BOOK_ITEM;

public class LibraryBookItemAdapter extends RecyclerView.Adapter<LibraryBookItemAdapter.LibraryBookItemViewHolder> implements Filterable {

    private final List<LibraryBookItem> libraryBookListFiltered;
    private final Context context;
    private final List<LibraryBookItem> libraryBookList;
    private final TextView noResult;

    public LibraryBookItemAdapter(Context context, List<LibraryBookItem> libraryBookList, TextView noResultTv){
        this.context = context;
        this.libraryBookList = libraryBookList;
        this.noResult = noResultTv;
        this.libraryBookListFiltered = new ArrayList<>(libraryBookList);
    }

    @NonNull
    @Override
    public LibraryBookItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View bookItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new LibraryBookItemViewHolder(bookItem);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryBookItemViewHolder holder, int position) {
        LibraryBookItem item = libraryBookListFiltered.get(position);

        holder.itemView.setOnClickListener( it ->
        {
            Intent intent = new Intent(context, BookDetailActivity.class);
            intent.putExtra(LIBRARY_BOOK_ITEM, item);
            intent.putExtra(BOOK_FRAGMENT_TYPE, LIBRARY);

            context.startActivity(intent);
        });

        Glide
                .with(holder.image.getContext())
                .load(item.getImgUrl())
                .into(holder.image);

        holder.tvTitle.setText(item.getTitle());
        holder.tvAuthors.setText(item.authorsToString());
        holder.tvGenre.setText(item.getGenre().getGenreName());
        holder.tvDescription.setText(item.getDescription());
        holder.ivAvailability.setImageResource((item.isAvailable()) ? R.drawable.available : R.drawable.unavailable);
    }

    @Override
    public int getItemCount() {
        return libraryBookListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    final private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<LibraryBookItem> filtered = new ArrayList<>();
            if(constraint.toString().isEmpty()){
                filtered.addAll(libraryBookList);
            }else{
                for(LibraryBookItem bookItem : libraryBookList){
                    if(bookItem.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filtered.add(bookItem);
                    }else if(bookItem.getAuthorsFullNameString().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filtered.add(bookItem);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filtered;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            libraryBookListFiltered.clear();
            if(results != null)
                libraryBookListFiltered.addAll((List<LibraryBookItem>) results.values);
            noResult.setVisibility((libraryBookListFiltered.isEmpty()) ? View.VISIBLE : View.GONE);
            notifyDataSetChanged();
        }
    };

    static class LibraryBookItemViewHolder extends RecyclerView.ViewHolder {

        final private ImageView image;
        final private TextView tvTitle;
        final private TextView tvAuthors;
        final private TextView tvGenre;
        final private TextView tvDescription;
        final private ImageView ivAvailability;

        public LibraryBookItemViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_book);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthors = itemView.findViewById(R.id.tvAuthors);
            tvGenre = itemView.findViewById(R.id.tvGenre);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivAvailability = itemView.findViewById(R.id.iv_isAvailable);
        }
    }



}
