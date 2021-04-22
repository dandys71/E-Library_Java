package cz.uhk.fim.student.schmid.java.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;


public class BookItem extends BookItemBase implements Parcelable {

    private Date dateFrom;
    private Date dateTo;
    private Boolean wasExtended = false;

    public BookItem(int id, String title, List<String> authors, String description, String imgUrl, Date dateFrom, Date dateTo) {
        super(id, title, authors, description, imgUrl);
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public Boolean getWasExtended() {
        return wasExtended;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setWasExtended() {
        this.wasExtended = true;
    }

    protected BookItem(Parcel in) {
        super(in.readInt(), in.readString(), in.createStringArrayList(), in.readString(), in.readString());
        dateFrom = (Date) in.readSerializable();
        dateTo = (Date) in.readSerializable();
        byte tmpWasExtended = in.readByte();
        wasExtended = tmpWasExtended == 0 ? null : tmpWasExtended == 1;
    }

    public static final Creator<BookItem> CREATOR = new Creator<BookItem>() {
        @Override
        public BookItem createFromParcel(Parcel in) {
            return new BookItem(in);
        }

        @Override
        public BookItem[] newArray(int size) {
            return new BookItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeStringList(authors);
        dest.writeString(description);
        dest.writeString(imgUrl);
        dest.writeSerializable(dateFrom);
        dest.writeSerializable(dateTo);
        dest.writeByte((byte) (wasExtended == null ? 0 : wasExtended ? 1 : 2));
    }
}
