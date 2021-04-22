package cz.uhk.fim.student.schmid.java.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.Date;
import java.util.List;
import cz.uhk.fim.student.schmid.java.model.BookItem;
import cz.uhk.fim.student.schmid.java.repository.MyLibraryRepository;


public class MyLibraryViewModel extends ViewModel {
    private MutableLiveData<List<BookItem>> books;

    private final MyLibraryRepository repository = MyLibraryRepository.getInstance();

    public void init(){
        if(books != null){
            return;
        }
        books = repository.getBooks();
    }

    public MutableLiveData<List<BookItem>> getBooks() {
        return books;
    }

    public LiveData<Date> extendBookDate(int bookItemId, int daysToAdd){
        return repository.extendBookDate(bookItemId, daysToAdd);
    }

    public LiveData<Boolean> isBookInMyLibrary(int bookItemId){
        return repository.isBookInMyLibrary(bookItemId);
    }

    public LiveData<Boolean> wasExtended(int bookItemId){
        return repository.wasExtended(bookItemId);
    }
}
