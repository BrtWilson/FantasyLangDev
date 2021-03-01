package edu.byu.cs.tweeter.view.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatDialogFragment;

import edu.byu.cs.tweeter.R;

public class StatusDialog extends AppCompatDialogFragment {
    private EditText editTextMessage;
    private SDListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflator = getActivity().getLayoutInflater();
        View view = inflator.inflate(R.layout.status_dialogue, null);

        builder.setView(view)
                .setTitle("Post a Status")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Post", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String message = editTextMessage.getText().toString();
                        listener.saveMessage(message);
                    }
                });
        editTextMessage = view.findViewById(R.id.status_dialogue_entermessage);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (SDListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + ": Need to implement StatusDialogListener");
        }

    }

    public interface SDListener{
        void saveMessage(String message);
    }

}