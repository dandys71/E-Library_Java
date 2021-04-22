package cz.uhk.fim.student.schmid.java.repository;

import android.os.Build;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import cz.uhk.fim.student.schmid.java.model.BookItem;

import static cz.uhk.fim.student.schmid.java.util.BookItemUtil.addDaysToDate;
import static cz.uhk.fim.student.schmid.java.util.BookItemUtil.getDateFromNow;

public class MyLibraryRepository {
    private static MyLibraryRepository instance;

    public static MyLibraryRepository getInstance() {
        if (instance == null)
            instance = new MyLibraryRepository();
        return instance;
    }

    private final List<BookItem> data;

    public MyLibraryRepository() {
        data = new ArrayList<>();
        createSampleData();
    }

    public MutableLiveData<List<BookItem>> getBooks() {
        MutableLiveData<List<BookItem>> sampleData = new MutableLiveData();
        sampleData.postValue(data);
        return sampleData;
    }

    private void createSampleData() {
        data.add(new BookItem(1, "Kytice", Arrays.asList("Karel Jaromír Erben"), "Kytice je básnická sbírka 13 lyrickoepických skladeb - balad. Všechny mají pochmurný děj, rychlý a dramatický spád a většinou špatný konec. Základním motivem je nějaké provinění, po kterém vždy přichází tvrdý trest, někdy lze vinu odčinit nebo zmírnit pokáním.", "https://www.levneknihy.cz/Document/140/140837/140837.jpg", getDateFromNow(-7), getDateFromNow(24)));
        data.add(new BookItem(4, "Válka světů", Arrays.asList("Herbert George Wells"), "Válka světů je vědeckofantastický román Herberta George Wellse z roku 1898, jenž popisuje invazi mimozemšťanů z Marsu do Anglie. Je to jedna z prvních knih popisujících mimozemšťanskou invazi na Zemi. Několikrát byl podle ní natočen film.", "https://knihydobrovsky.cz/thumbs/book-detail/mod_eshop/produkty/v/valka-svetu-9788075532220.jpg", getDateFromNow(-14), getDateFromNow(17)));
    }

    public LiveData<Date> extendBookDate(int bookItemId, int daysToAdd) {
        MutableLiveData<Date> result = new MutableLiveData<>();
        BookItem bookItem = find(bookItemId);
        if (bookItem != null) {
            bookItem.setDateTo(addDaysToDate(bookItem.getDateTo(), daysToAdd));
            bookItem.setWasExtended();
            result.setValue(bookItem.getDateTo());
        } else {
            result.setValue(new Date());
        }
        return result;
    }

    public LiveData<Boolean> isBookInMyLibrary(int bookItemId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        result.setValue(find(bookItemId) != null);
        return result;
    }

    public LiveData<Boolean> wasExtended(int bookItemId) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();
        BookItem bookItem = find(bookItemId);
        result.setValue((bookItem != null) ? bookItem.getWasExtended() : false);
        return result;
    }

    private BookItem find(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Optional<BookItem> res = data.stream().filter(i -> i.getId() == id).findFirst();
            if (res.isPresent())
                return res.get();
        }
        return null;
    }

}
