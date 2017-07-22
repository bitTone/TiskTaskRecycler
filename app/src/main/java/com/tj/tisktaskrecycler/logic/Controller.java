package com.tj.tisktaskrecycler.logic;

import com.tj.tisktaskrecycler.data.DataSourceInterface;
import com.tj.tisktaskrecycler.data.ListItem;
import com.tj.tisktaskrecycler.view.ViewInterface;

/**
 * Created by tj on 7/18/17.
 */

public class Controller {

    private ViewInterface view;

    private DataSourceInterface datasource;

    public Controller(ViewInterface view, DataSourceInterface datasource) {
        this.view = view;
        this.datasource = datasource;
    }


    public void getListFromDataSource() {

        view.setUpAdapterAndView(datasource.getListData());

    }

    public void onListItemClick(ListItem testItem){

        view.startDetailActivity(testItem.getDateAndTime(),
                testItem.getMessage(),
                testItem.getColorResource());

    }

}
