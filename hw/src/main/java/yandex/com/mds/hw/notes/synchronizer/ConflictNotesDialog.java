package yandex.com.mds.hw.notes.synchronizer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import yandex.com.mds.hw.R;
import yandex.com.mds.hw.models.Note;
import yandex.com.mds.hw.utils.TimeUtils;

public class ConflictNotesDialog extends AlertDialog {
    private static final String TAG = ConflictNotesDialog.class.getName();
    private OnConflictResolvedListener conflictResolvedListener;
    private TextView localDescription, remoteDescription;
    private Button preferLocalButton, preferRemoteButton;

    protected ConflictNotesDialog(@NonNull Context context, ConflictNotes conflictNotes) {
        this(context, 0, conflictNotes);
    }

    public ConflictNotesDialog(@NonNull Context context, @StyleRes int themeResId, final ConflictNotes conflictNotes) {
        super(context, themeResId);
        View root = LayoutInflater.from(context).inflate(R.layout.dialog_conflict_notes, null);
        setView(root);
        localDescription = (TextView) root.findViewById(R.id.text_local);
        localDescription.setText(conflictNotes.getLocal() == null ? context.getString(R.string.deleted)
                : getNotePresentation(conflictNotes.getLocal()));
        remoteDescription = (TextView) root.findViewById(R.id.text_remote);
        remoteDescription.setText(conflictNotes.getRemote() == null ? context.getString(R.string.deleted)
                : getNotePresentation(conflictNotes.getRemote()));

        preferLocalButton = (Button) root.findViewById(R.id.button_prefer_local);
        preferLocalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conflictResolvedListener.onConflictResolved(-1);
                dismiss();
            }
        });
        preferRemoteButton = (Button) root.findViewById(R.id.button_prefer_remote);
        preferRemoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conflictResolvedListener.onConflictResolved(1);
                dismiss();
            }
        });
    }

    public void setOnConflictResolvedListener(OnConflictResolvedListener onConflictResolvedListener) {
        this.conflictResolvedListener = onConflictResolvedListener;
    }

    private String getNotePresentation(Note note) {
        String result = "";
        result += String.format("Title: %s\n", note.getTitle());
        result += String.format("Description: %s\n", note.getDescription());
        result += String.format("Color: %s\n", note.getColor());
        result += String.format("Image: %s\n", note.getImageUrl());
        result += String.format("Created at: %s\n", note.getCreationDate() != null ?
                TimeUtils.IsoDateFormat.format(note.getCreationDate()) : "");
        result += String.format("Edited at: %s\n", note.getLastModificationDate() != null ?
                TimeUtils.IsoDateFormat.format(note.getLastModificationDate()) : "");
        result += String.format("Viewed at: %s\n", note.getLastViewDate() != null ?
                TimeUtils.IsoDateFormat.format(note.getLastViewDate()) : "");
        return result;
    }

    /**
     * Interface definition for a callback to be invoked when notes conflict is resolved
     */
    public interface OnConflictResolvedListener {
        /**
         * Called when conflict if resolved
         *
         * @param result negative integer if preferred note was the local one, positive integer otherwise.
         */
        void onConflictResolved(int result);
    }
}
