package com.tj.tisktaskrecycler;

import com.tj.tisktaskrecycler.data.DataSourceInterface;
import com.tj.tisktaskrecycler.data.ListItem;
import com.tj.tisktaskrecycler.logic.Controller;
import com.tj.tisktaskrecycler.view.ViewInterface;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(MockitoJUnitRunner.class)
public class ControllerUnitTest {

    /**
     *
     * Test Double
     * Specifically a "Mock"
     */
    @Mock
    DataSourceInterface dataSource;

    @Mock
    ViewInterface view;


    Controller controller;

    private static final ListItem testItem= new ListItem(
      "6:30AM 07/18/17",
       "Check out my milly rock",
            R.color.RED
    );

    @Before
    public void setUpTest (){
        //this actually setups all init mocks
        MockitoAnnotations.initMocks(this);
        controller = new Controller(view,dataSource);


    }



    @Test
    public void onGetListDataSuccessful() throws Exception {

        //set up data for test

        ArrayList<ListItem> listOfData = new ArrayList<>();
        listOfData.add(testItem);
        //sets up mock data to return the data we want
        Mockito.when(dataSource.getListData()).thenReturn(listOfData);
        //Call the method(Unit) we are testing
        controller.getListFromDataSource();
        //Check how the Tested Class Responds to the data it receives
       Mockito.verify(view).setUpAdapterAndView(listOfData);





    }
}