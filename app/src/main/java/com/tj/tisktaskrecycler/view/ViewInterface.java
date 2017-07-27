package com.tj.tisktaskrecycler.view;

import android.view.View;

import com.tj.tisktaskrecycler.data.ListItem;

import java.util.List;

/**
 * Created by tj on 7/18/17.
 */

public interface ViewInterface {
    //starts a detail activity
    void startDetailActivity(String dateandTime, String message, int colorResource, View viewroot );


    void addNewListItemToView(ListItem newItem);

    void deleteListItemAt(int position);

    void showUndoSnackbar();

    void insertListItemAt(int temporaryListItemPosition, ListItem temporaryListItem);



    void setUpAdapterAndView(List<ListItem> listOfData);


}
