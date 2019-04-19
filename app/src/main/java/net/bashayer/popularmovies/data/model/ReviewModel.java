package net.bashayer.popularmovies.data.model;

import android.os.Parcel;
import android.os.Parcelable;


public class ReviewModel implements Parcelable {

    private String content;
    private String author;

    public ReviewModel(String content, String author) {
        this.content = content;
        this.author = author;
    }

    protected ReviewModel(Parcel in) {
        content = in.readString();
        author = in.readString();
    }

    public static final Creator<ReviewModel> CREATOR = new Creator<ReviewModel>() {
        @Override
        public ReviewModel createFromParcel(Parcel in) {
            return new ReviewModel(in);
        }

        @Override
        public ReviewModel[] newArray(int size) {
            return new ReviewModel[size];
        }
    };

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(content);
        parcel.writeString(author);
    }

    @Override
    public String toString() {
        return content + "\n" +
                "By " + author + "\n---\n";
    }
}

