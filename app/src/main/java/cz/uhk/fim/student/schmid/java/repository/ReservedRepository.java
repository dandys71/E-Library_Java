package cz.uhk.fim.student.schmid.java.repository;

import android.os.Build;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import cz.uhk.fim.student.schmid.java.model.BookItem;
import cz.uhk.fim.student.schmid.java.util.BookItemUtil;

public class ReservedRepository {
    private static ReservedRepository instance;

    public static ReservedRepository getInstance(){
        if(instance == null)
            instance = new ReservedRepository();
        return instance;
    }

    private final List<BookItem> data = new ArrayList();

    public ReservedRepository() {
        createSampleData();
    }

    public MutableLiveData<List<BookItem>> getBooks(){
        MutableLiveData<List<BookItem>> sampleData = new MutableLiveData();
        sampleData.postValue(data);
        return sampleData;
    }

    private void createSampleData(){
        data.add(new BookItem(3, "Povídky Malostránské", Arrays.asList("Jan Neruda"), "Povídky malostranské je sbírka třinácti povídek, původně vydávaných časopisecky. Děj se vždy odehrává na Malé Straně v 1. ... Převažuje forma povídek či novel, ale je zde využita i forma dopisu, deníku a zápisníku (Figurky). Postavy mluví jazykem lidovým, spisovným i nespisovným." , "https://www.levneknihy.cz/Document/144/144687/144687.jpg", BookItemUtil.getDateFromNow(-1), BookItemUtil.getDateFromNow(6)));
    }

    public void addBook(BookItem bookItem){
        data.add(bookItem);
    }

    public void removeBook(int bookItemId){
        BookItem bookItem = find(bookItemId);
        if(bookItem != null){
            data.remove(bookItem);
        }
    }

    public LiveData<Boolean> isReserved(int bookItemId){
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        result.setValue(find(bookItemId) != null);
        return result;
    }

    private BookItem find(int id){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Optional<BookItem> res = data.stream().filter(i-> i.getId() == id).findFirst();
            if(res.isPresent())
                return res.get();
        }
        return null;
    }
}
