package cz.uhk.fim.student.schmid.java.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import cz.uhk.fim.student.schmid.R;
import cz.uhk.fim.student.schmid.java.model.BookItem;
import cz.uhk.fim.student.schmid.java.model.Genre;
import cz.uhk.fim.student.schmid.java.model.LibraryBookItem;
import cz.uhk.fim.student.schmid.java.util.BookDetailActivityUtil;
import cz.uhk.fim.student.schmid.java.viewmodel.LibraryViewModel;
import cz.uhk.fim.student.schmid.java.viewmodel.MyLibraryViewModel;
import cz.uhk.fim.student.schmid.java.viewmodel.ReservedViewModel;

import static cz.uhk.fim.student.schmid.java.util.BookItemUtil.getDateFromNow;

public class BookDetailActivity extends AppCompatActivity {

    private TextView tvInfo;
    private Button btn;

    private FRAGMENT type;
    private BookItem book;
    private LibraryBookItem libBook;

    private LibraryViewModel libraryViewModel;
    private MyLibraryViewModel myLibraryViewModel;
    private ReservedViewModel reservedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        tvInfo = findViewById(R.id.d_tv_info);
        btn = findViewById(R.id.d_btn);

        libraryViewModel = new ViewModelProvider(this).get(LibraryViewModel.class);
        myLibraryViewModel = new ViewModelProvider(this).get(MyLibraryViewModel.class);
        reservedViewModel = new ViewModelProvider(this).get(ReservedViewModel.class);

        Intent intent = getIntent();
        type = (FRAGMENT) intent.getSerializableExtra(BOOK_FRAGMENT_TYPE);

        if (type != FRAGMENT.LIBRARY) {
            book = intent.getParcelableExtra(BOOK_ITEM);
            initialize(book.getTitle(), book.authorsToString(), book.getDescription(), book.getImgUrl());
        } else {
            libBook = intent.getParcelableExtra(LIBRARY_BOOK_ITEM);
            initialize(libBook.getTitle(), libBook.authorsToString(), libBook.getDescription(), libBook.getImgUrl());
        }
    }

    private void initialize(String title, String authors, String description, String img) {
        ImageView ivImg = findViewById(R.id.d_iv_book);
        TextView tvTitle = findViewById(R.id.d_tv_title);
        TextView tvAuthors = findViewById(R.id.d_tv_authors);
        TextView tvDescription = findViewById(R.id.d_tv_description);

        Glide
                .with(this)
                .load(img)
                .into(ivImg);

        tvTitle.setText(title);
        tvAuthors.setText(authors);
        tvDescription.setText(description);
        onFragmentChange(type);
    }

    private void onFragmentChange(FRAGMENT type) {
        if (type != FRAGMENT.LIBRARY) {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            enableButton();

            if (type == FRAGMENT.MY_LIBRARY) {
                if(book != null){
                    initializeMyLibraryUi(format);
                }
            } else if (type == FRAGMENT.RESERVED) {
                if (book != null) {
                    initializeReservedUi(format);
                }
            }
        } else {
            if (libBook != null) {
                if (libBook.getTotal() == 0) {
                    final LifecycleOwner lifecycleOwner = this;
                    libraryViewModel.getTotalOfBook(libBook.getId()).observe(lifecycleOwner, total -> {
                        libBook.setTotal(total);
                        libraryViewModel.getTotalOfBorrowed(libBook.getId()).observe(lifecycleOwner, borrowed -> {
                                    libBook.setBorrowed(borrowed);
                                    initializeLibraryUi();
                                }
                        );

                    });
                } else {
                    initializeLibraryUi();
                }

            }
        }
    }

    private void initializeMyLibraryUi(final SimpleDateFormat format){
        btn.setText(getText(R.string.extend));
        myLibraryViewModel.wasExtended(book.getId()).observe(this, it -> {
            if (it) {
                setMyLibraryFragmentInfoText(format, false);
                disableButton();
            } else {
                setMyLibraryFragmentInfoText(format, true);
            }
        });

        btn.setOnClickListener(v -> {
            myLibraryViewModel.extendBookDate(book.getId(), 30).observe(this, it -> {
                Toast.makeText(this, getText(R.string.extend_success), Toast.LENGTH_LONG).show();
                //podmínka na not null není nutná když tak vrací Date()
                book.setDateTo(it);
                setMyLibraryFragmentInfoText(format, false);
                disableButton();
            });

        });
    }

    private void initializeReservedUi(final SimpleDateFormat format){
        if (book.getDateFrom() != null && book.getDateTo() != null)
            tvInfo.setText(String.format(getText(R.string.reserved_book_info).toString(), format.format(book.getDateFrom()), format.format(book.getDateTo())));
        btn.setText(getText(R.string.cancel_reservation));
        btn.setOnClickListener(v -> {
            libBook = new LibraryBookItem(book.getId(), book.getTitle(), book.getAuthors(), Genre.NONE, book.getDescription(), 0, 0, book.getImgUrl());
            reservedViewModel.removeBook(book.getId());
            libraryViewModel.decreaseBorrowed(book.getId());

            Toast.makeText(this, getText(R.string.cancel_reservation_success), Toast.LENGTH_LONG).show();
            onFragmentChange(FRAGMENT.LIBRARY);
        });
    }

    private void initializeLibraryUi() {
        if (libBook.getTotal() != 0) {
            setLibraryFragmentInfoText();
            btn.setText(getText(R.string.reserve));

            btn.setOnClickListener(v -> {
                final LifecycleOwner lifecycleOwner = this;
                reservedViewModel.isReserved(libBook.getId()).observe(lifecycleOwner, isReserved -> {
                    if(!isReserved){
                        myLibraryViewModel.isBookInMyLibrary(libBook.getId()).observe(lifecycleOwner, isInLibrary ->{
                            if(!isInLibrary){
                                book = new BookItem(libBook.getId(), libBook.getTitle(), libBook.getAuthors(), libBook.getDescription(), libBook.getImgUrl(), new Date(), getDateFromNow(7));
                                reservedViewModel.addBook(book);

                                libraryViewModel.increaseBorrowed(libBook.getId());
                                libBook.setBorrowed(libBook.getBorrowed() + 1);
                                setLibraryFragmentInfoText();
                                Toast.makeText(this, getText(R.string.reserved_success), Toast.LENGTH_LONG).show();
                                onFragmentChange(FRAGMENT.RESERVED);
                            }else{
                                Toast.makeText(this, getText(R.string.reserved_failure_in_my_library), Toast.LENGTH_LONG).show();
                            }
                        });
                    }else{
                        Toast.makeText(this, getText(R.string.reserved_failure_in_reserved), Toast.LENGTH_LONG).show();
                    }
                });
            });
        }
    }

    private void disableButton() {
        btn.setEnabled(false);
        btn.setAlpha(.5f);
    }

    private void enableButton() {
        btn.setEnabled(true);
        btn.setAlpha(1f);
    }

    private void setLibraryFragmentInfoText() {
        int total = libBook.getTotal();
        int borrowed = libBook.getBorrowed();
        tvInfo.setText((total - borrowed > 0)
                ? String.format(getText(R.string.library_book_info_available).toString(), getPiecesLeftString(total - borrowed), total - borrowed, getPiecesString(total - borrowed), total)
                : String.format(getText(R.string.library_book_info_unavailable).toString(), total, getPiecesString(total))
        );

        if (total - borrowed <= 0) {
            disableButton();
        }
    }

    private void setMyLibraryFragmentInfoText(SimpleDateFormat format, Boolean canBeExtended) {
        if (book != null) {
            if (book.getDateFrom() != null && book.getDateTo() != null) {
                tvInfo.setText((canBeExtended) ?
                        String.format(getText(R.string.my_library_book_info).toString(), format.format(book.getDateFrom()), format.format(book.getDateTo()))
                        :
                        String.format(getText(R.string.my_library_book_info_can_not_extend).toString(), format.format(book.getDateFrom()), format.format(book.getDateTo()))
                );
            }
        }
    }

    private String getPiecesString(int num) {
        return BookDetailActivityUtil.getPiecesString(this, num);
    }

    private String getPiecesLeftString(int num) {
        return BookDetailActivityUtil.getPiecesLeftString(this, num);
    }

    /**
     * Konstanty sloužící pro předávání údajů skrze intent do této aktivity
     */
    public static String BOOK_FRAGMENT_TYPE = "fragment_type";
    public static String BOOK_ITEM = "book_item";
    public static String LIBRARY_BOOK_ITEM = "my_library_book_item";

    public enum FRAGMENT {
        LIBRARY,
        MY_LIBRARY,
        RESERVED
    }
}