package com.example.ethan.gamebacklogmanager;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

//Game model to save all game data and relate them to fields in a database
@Entity (tableName = "game")
public class Game implements Parcelable {

    @PrimaryKey (autoGenerate = true)
    private long id;
    @ColumnInfo (name = "title")
    private String mTitle;
    @ColumnInfo (name = "platform")
    private String mPlatform;
    @ColumnInfo (name = "notes")
    private String mNotes;
    @ColumnInfo (name = "status")
    private int mStatus;
    @ColumnInfo (name = "lastModified")
    private String mLastModified;

    public Game(String mTitle, String mPlatform, String mNotes, int mStatus, String mLastModified) {
        this.mTitle = mTitle;
        this.mPlatform = mPlatform;
        this.mNotes = mNotes;
        this.mStatus = mStatus;
        this.mLastModified = mLastModified;
    }

    protected Game(Parcel in) {
        id = in.readLong();
        mTitle = in.readString();
        mPlatform = in.readString();
        mNotes = in.readString();
        mStatus = in.readInt();
        mLastModified = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public long getId() {
        return id;
    }
    public void setId(long mId) {
        this.id = mId;
    }
    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public String getPlatform() {
        return mPlatform;
    }
    public void setPlatform(String mPlatform) {
        this.mPlatform = mPlatform;
    }
    public String getNotes() {
        return mNotes;
    }
    public void setNotes(String mNotes) {
        this.mNotes = mNotes;
    }
    public int getStatus() {
        return mStatus;
    }
    public void setStatus(int status) {
        this.mStatus = status;
    }
    public String getLastModified() {
        return mLastModified;
    }
    public void setLastModified(String mLastModified) {
        this.mLastModified = mLastModified;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(mTitle);
        dest.writeString(mPlatform);
        dest.writeString(mNotes);
        dest.writeInt(mStatus);
        dest.writeString(mLastModified);
    }
}
