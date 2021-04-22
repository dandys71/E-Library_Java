package cz.uhk.fim.student.schmid.java.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import cz.uhk.fim.student.schmid.java.model.LibraryBookItem;
import cz.uhk.fim.student.schmid.java.repository.LibraryRepository;

public class LibraryViewModel extends ViewModel {
    private MutableLiveData<List<LibraryBookItem>> books;

    private final LibraryRepository repository = LibraryRepository.getInstance();

    public void init(){
        if(books != null){
            return;
        }
        books = repository.getBooks();
    }

    public MutableLiveData<List<LibraryBookItem>> getBooks() {
        return books;
    }

    public void increaseBorrowed(int bookItemId){
        repository.increaseBorrowed(bookItemId);
    }

    public void decreaseBorrowed(int bookItemId){
        repository.decreaseBorrowed(bookItemId);
    }

    public LiveData<Integer> getTotalOfBook(int bookItemId){
        return repository.getTotalOfBook(bookItemId);
    }

    public LiveData<Integer> getTotalOfBorrowed(int bookItemId){
        return repository.getTotalOfBorrowed(bookItemId);
    }

}
