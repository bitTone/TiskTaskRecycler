package com.tj.tisktaskrecycler.data;

import java.util.List;

/**
 * A Contract between classes that
 * Created by tj on 7/18/17.
 */

public interface DataSourceInterface  {

    List<ListItem> getListData();

    ListItem createNewListItem();

    void deleteListItem(ListItem listItem);

    void insertListItem(ListItem temporaryListItem);

}
