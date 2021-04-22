package cz.uhk.fim.student.schmid.java.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import cz.uhk.fim.student.schmid.R;
import cz.uhk.fim.student.schmid.java.model.BookItem;
import cz.uhk.fim.student.schmid.java.ui.BookDetailActivity;
import static cz.uhk.fim.student.schmid.java.ui.BookDetailActivity.BOOK_FRAGMENT_TYPE;
import static cz.uhk.fim.student.schmid.java.ui.BookDetailActivity.BOOK_ITEM;

public class BookItemAdapter extends RecyclerView.Adapter<BookItemAdapter.BookItemViewHolder>{
    private final Context context;
    private final List<BookItem> bookItemList;
    private final BookDetailActivity.FRAGMENT fragment;

    public BookItemAdapter(Context context, List<BookItem> bookItemList, BookDetailActivity.FRAGMENT fragment) {
        this.context = context;
        this.bookItemList = bookItemList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public BookItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View bookItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_library_book_item, parent, false);
        return new BookItemViewHolder(bookItem);
    }

    @Override
    public void onBindViewHolder(@NonNull BookItemViewHolder holder, int position) {
        BookItem item = bookItemList.get(position);

        Glide
                .with(holder.image.getContext())
                .load(item.getImgUrl())
                .into(holder.image);

        holder.tvTitle.setText(item.getTitle());
        holder.tvAuthors.setText(item.authorsToString());
        holder.btnDetail.setOnClickListener(v -> {
            Intent intent = new Intent(context, BookDetailActivity.class);
            intent.putExtra(BOOK_ITEM, item);
            intent.putExtra(BOOK_FRAGMENT_TYPE, fragment);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bookItemList.size();
    }

    static class BookItemViewHolder extends RecyclerView.ViewHolder {
        final private ImageView image;
        final private TextView tvTitle;
        final private TextView tvAuthors;
        final private Button btnDetail;

        public BookItemViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_book);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvAuthors = itemView.findViewById(R.id.tvAuthors);
            btnDetail = itemView.findViewById(R.id.btnDetail);
        }
    }

}
