package yandex.com.mds.hw.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import static yandex.com.mds.hw.utils.SerializationUtils.PARCEL_EMPTY_FIELD;

public class Note implements Parcelable {
    private int id;
    private int color;
    private String title;
    private String description;
    @SerializedName("created")
    private Date creationDate;
    @SerializedName("edited")
    private Date lastModificationDate;
    @SerializedName("viewed")
    private Date lastViewDate;
    private String imageUrl;
    private int ownerId;
    private int serverId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    public Date getLastViewDate() {
        return lastViewDate;
    }

    public void setLastViewDate(Date lastViewDate) {
        this.lastViewDate = lastViewDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Note() {
    }

    public Note(int id, int ownerId, int color, String title, String description, Date creationDate, String imageUrl) {
        this.id = id;
        this.color = color;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.imageUrl = imageUrl;
        this.ownerId = ownerId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.color);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeLong(this.creationDate != null ? this.creationDate.getTime() : PARCEL_EMPTY_FIELD);
        dest.writeLong(this.lastModificationDate != null ? this.lastModificationDate.getTime() : PARCEL_EMPTY_FIELD);
        dest.writeLong(this.lastViewDate != null ? this.lastViewDate.getTime() : PARCEL_EMPTY_FIELD);
        dest.writeString(this.imageUrl);
        dest.writeInt(this.ownerId);
        dest.writeInt(this.serverId);
    }

    protected Note(Parcel in) {
        this.id = in.readInt();
        this.color = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        long tmpCreationDate = in.readLong();
        this.creationDate = tmpCreationDate == PARCEL_EMPTY_FIELD ? null : new Date(tmpCreationDate);
        long tmpLastModificationDate = in.readLong();
        this.lastModificationDate = tmpLastModificationDate == PARCEL_EMPTY_FIELD ? null : new Date(tmpLastModificationDate);
        long tmpLastViewDate = in.readLong();
        this.lastViewDate = tmpLastViewDate == PARCEL_EMPTY_FIELD ? null : new Date(tmpLastViewDate);
        this.imageUrl = in.readString();
        this.ownerId = in.readInt();
        this.serverId = in.readInt();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", color=" + color +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", lastModificationDate=" + lastModificationDate +
                ", lastViewDate=" + lastViewDate +
                ", imageUrl='" + imageUrl + '\'' +
                ", ownerId=" + ownerId +
                ", serverId=" + serverId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (id != note.id) return false;
        if (color != note.color) return false;
        if (ownerId != note.ownerId) return false;
        if (serverId != note.serverId) return false;
        if (title != null ? !title.equals(note.title) : note.title != null) return false;
        if (description != null ? !description.equals(note.description) : note.description != null)
            return false;
        if (creationDate != null ? !creationDate.equals(note.creationDate) : note.creationDate != null)
            return false;
        if (lastModificationDate != null ? !lastModificationDate.equals(note.lastModificationDate) : note.lastModificationDate != null)
            return false;
        if (lastViewDate != null ? !lastViewDate.equals(note.lastViewDate) : note.lastViewDate != null)
            return false;
        return imageUrl != null ? imageUrl.equals(note.imageUrl) : note.imageUrl == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + color;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (lastModificationDate != null ? lastModificationDate.hashCode() : 0);
        result = 31 * result + (lastViewDate != null ? lastViewDate.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + ownerId;
        result = 31 * result + serverId;
        return result;
    }
}