package cz.uhk.fim.student.schmid.java.repository;

import android.os.Build;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import cz.uhk.fim.student.schmid.java.model.Genre;
import cz.uhk.fim.student.schmid.java.model.LibraryBookItem;

public class LibraryRepository {
    private static LibraryRepository instance;

    public static LibraryRepository getInstance(){
        if(instance == null)
            instance = new LibraryRepository();
        return instance;
    }

    private List<LibraryBookItem> data = new ArrayList();

    public LibraryRepository() {
        createSampleData();
    }

    private void createSampleData(){
        data.add(new LibraryBookItem(1, "Kytice", Arrays.asList("Karel Jaromír Erben"), Genre.POEZIE, "Kytice je básnická sbírka 13 lyrickoepických skladeb - balad. Všechny mají pochmurný děj, rychlý a dramatický spád a většinou špatný konec. Základním motivem je nějaké provinění, po kterém vždy přichází tvrdý trest, někdy lze vinu odčinit nebo zmírnit pokáním." ,10, 6, "https://www.levneknihy.cz/Document/140/140837/140837.jpg"));
        data.add(new LibraryBookItem(2, "Babička", Arrays.asList("Božena Němcová"), Genre.ROMAN, "Babička Magdaléna Novotná – Moudrá, prostá, pracovitá, obětavá a laskavá žena, která okolí rozdává lásku. Je dobrou katoličkou a ostatní si jí váží. Dodržuje tradice. ... Kvůli nešťastné lásce se zblázní.", 7, 6, "https://www.levneknihy.cz/Document/106/106812/106812.jpg"));
        data.add(new LibraryBookItem(3, "Povídky Malostránské", Arrays.asList("Jan Neruda"), Genre.POVIDKA, "Povídky malostranské je sbírka třinácti povídek, původně vydávaných časopisecky. Děj se vždy odehrává na Malé Straně v 1. ... Převažuje forma povídek či novel, ale je zde využita i forma dopisu, deníku a zápisníku (Figurky). Postavy mluví jazykem lidovým, spisovným i nespisovným.", 4, 2, "https://www.levneknihy.cz/Document/144/144687/144687.jpg"));
        data.add(new LibraryBookItem(4, "Válka světů", Arrays.asList("Herbert George Wells"), Genre.SCI_FI, "Válka světů je vědeckofantastický román Herberta George Wellse z roku 1898, jenž popisuje invazi mimozemšťanů z Marsu do Anglie. Je to jedna z prvních knih popisujících mimozemšťanskou invazi na Zemi. Několikrát byl podle ní natočen film.", 3, 3, "https://knihydobrovsky.cz/thumbs/book-detail/mod_eshop/produkty/v/valka-svetu-9788075532220.jpg"));
    }

    public MutableLiveData<List<LibraryBookItem>> getBooks(){
        MutableLiveData<List<LibraryBookItem>> sampleData = new MutableLiveData();
        sampleData.postValue(data);
        return sampleData;
    }


    public Boolean increaseBorrowed(int bookItemId){
        LibraryBookItem item = find(bookItemId);
        if(item != null){
            if(item.getBorrowed() + 1 <= item.getTotal()){
                item.setBorrowed(item.getBorrowed() + 1);
                return true;
            }
        }
        return false;
    }

    public Boolean decreaseBorrowed(int bookItemId){
        LibraryBookItem item = find(bookItemId);
        if(item != null){
            if(item.getBorrowed() - 1 >= 0){
                item.setBorrowed(item.getBorrowed() - 1);
                return true;
            }
        }
        return false;
    }

    public LiveData<Integer> getTotalOfBook(int bookItemId){
        MutableLiveData<Integer> result = new MutableLiveData<>();
        LibraryBookItem item = find(bookItemId);
        result.setValue((item != null) ? item.getTotal() : 0);
        return result;
    }

    public LiveData<Integer> getTotalOfBorrowed(int bookItemId){
        MutableLiveData<Integer> result = new MutableLiveData<>();
        LibraryBookItem item = find(bookItemId);
        result.setValue((item != null) ? item.getBorrowed() : 0);
        return result;
    }


    private LibraryBookItem find(int id){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Optional<LibraryBookItem> res = data.stream().filter(i-> i.getId() == id).findFirst();
            if(res.isPresent())
                return res.get();
        }
        return null;
    }
}
