package com.tj.tisktaskrecycler.view;

import com.tj.tisktaskrecycler.data.ListItem;

import java.util.List;

/**
 * Created by tj on 7/18/17.
 */

public interface ViewInterface {
    //starts a detail activity
    void startDetailActivity(String dateandTime, String message, int colorResource);

    void setUpAdapterAndView(List<ListItem> listOfData);


}
