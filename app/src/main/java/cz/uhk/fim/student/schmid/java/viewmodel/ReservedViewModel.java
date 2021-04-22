package cz.uhk.fim.student.schmid.java.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import cz.uhk.fim.student.schmid.java.model.BookItem;
import cz.uhk.fim.student.schmid.java.repository.ReservedRepository;

public class ReservedViewModel extends ViewModel {
    private MutableLiveData<List<BookItem>> books;

    private final ReservedRepository repository = ReservedRepository.getInstance();

    public void init(){
        if(books != null){
            return;
        }
        books = repository.getBooks();
    }

    public MutableLiveData<List<BookItem>> getBooks() {
        return books;
    }

    public void addBook(BookItem bookItem){
        repository.addBook(bookItem);
    }

    public void removeBook(int bookItemId){
        repository.removeBook(bookItemId);
    }

    public LiveData<Boolean> isReserved(int bookItemId){
        return repository.isReserved(bookItemId);
    }

}
