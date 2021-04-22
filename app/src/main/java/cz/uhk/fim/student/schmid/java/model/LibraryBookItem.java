package cz.uhk.fim.student.schmid.java.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import cz.uhk.fim.student.schmid.java.util.BookItemUtil;

public class LibraryBookItem extends BookItemBase implements Parcelable {

    private final Genre genre;
    private int total;
    private int borrowed;

    public LibraryBookItem(int id, String title, List<String> authors, Genre genre, String description, int total, int borrowed, String imgUrl) {
        super(id, title, authors, description, imgUrl);
        this.genre = genre;
        this.total = total;
        this.borrowed = borrowed;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getTotal() {
        return total;
    }

    public int getBorrowed() {
        return borrowed;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setBorrowed(int borrowed) {
        this.borrowed = borrowed;
    }

    public String getAuthorsFullNameString() { return BookItemUtil.authorsToString(authors, false); }

    public boolean isAvailable() {
        return (total - borrowed) > 0;
    }

    protected LibraryBookItem(Parcel in) {
        super(in.readInt(), in.readString(), in.createStringArrayList(), in.readString(), in.readString());
        genre = (Genre) in.readSerializable();
        total = in.readInt();
        borrowed = in.readInt();
    }

    public static final Creator<LibraryBookItem> CREATOR = new Creator<LibraryBookItem>() {
        @Override
        public LibraryBookItem createFromParcel(Parcel in) {
            return new LibraryBookItem(in);
        }

        @Override
        public LibraryBookItem[] newArray(int size) {
            return new LibraryBookItem[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeStringList(authors);
        dest.writeString(description);
        dest.writeString(imgUrl);
        dest.writeSerializable(genre);
        dest.writeInt(total);
        dest.writeInt(borrowed);
    }

}
