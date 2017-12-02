package com.zubov.i.tests;

import com.igor.z.interfaces.IFeedEditorModel;
import com.igor.z.modelAttributes.FeedItem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FeedEditorTests {

    private IFeedEditorModel model;

    @Before
    public void setup() {
        model = mock(IFeedEditorModel.class);
    }

    @After
    public void after() {
        model = null;
    }

    @Test
    public void shouldCreateFeedEditorController(){

    }

    @Test
    public void shouldSubmitChanges(){

        String newName = "newName";
        String newSource = "newSource";
        FeedItem oldItem = new FeedItem();
        oldItem.setFeedName("Name");
        oldItem.setFeedSource("Source");
        FeedItem newItem = new FeedItem();
        oldItem.setFeedName(newName);
        oldItem.setFeedSource(newSource);
//        flash.put(Consts.FEED, oldItem);
//        view = new FeedEditorController(model);
//        view.FeedEditor();
//        when(model.modifyFeed(eq(oldItem), )).thenReturn("Feed successfully modified.");
//        view.submit(newName, newSource);
//        view = new FeedEditorController(model);
//        view.FeedEditor();
//        assertEquals("Feed successfully modified.", view.getResultMessage());
//        assertEquals(newItem.getName(), view.getNewName());
//        assertEquals(newItem.getSource(), view.getNewSource());
    }

    @Test
    public void shouldNotSubmitChanges(){
        String newName = "newName";
        String newSource = "newSource";
        FeedItem oldItem = new FeedItem();
        oldItem.setFeedName("Name");
        oldItem.setFeedSource("Source");
        FeedItem newItem = new FeedItem();
        oldItem.setFeedName(newName);
        oldItem.setFeedSource(newSource);
//        flash.put(Consts.FEED, oldItem);
//        view = new FeedEditorController(model);
//        view.FeedEditor();
//        when(model.modifyFeed(eq(oldItem), )).thenReturn("Something wrong");
//        view.submit(newName, newSource);
//        view = new FeedEditorController(model);
//        view.FeedEditor();
//        assertNotEquals("Feed successfully modified.", view.getResultMessage());
//        assertEquals(newItem.getName(), view.getNewName());
//        assertEquals(newItem.getSource(), view.getNewSource());
    }

}
