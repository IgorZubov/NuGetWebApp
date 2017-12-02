package com.zubov.i.tests;

import com.igor.z.interfaces.IFeedEditorModel;
import com.igor.z.modelAttributes.FeedItem;
import com.igor.z.utils.Consts;
import com.zubov.i.tests.utils.ContextMocker;
import com.zubov.i.tests.utils.FlashStub;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

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
        FacesContext context = ContextMocker.mockFacesContext();
        ExternalContext ext = mock(ExternalContext.class);
        Flash flash = new FlashStub();
        when(ext.getFlash()).thenReturn(flash);
        when(context.getExternalContext()).thenReturn(ext);
    }

    @Test
    public void shouldSubmitChanges(){
        FacesContext context = ContextMocker.mockFacesContext();
        ExternalContext ext = mock(ExternalContext.class);
        Flash flash = new FlashStub();
        when(ext.getFlash()).thenReturn(flash);
        when(context.getExternalContext()).thenReturn(ext);

        String newName = "newName";
        String newSource = "newSource";
        FeedItem oldItem = new FeedItem();
        oldItem.setFeedName("Name");
        oldItem.setFeedSource("Source");
        FeedItem newItem = new FeedItem();
        oldItem.setFeedName(newName);
        oldItem.setFeedSource(newSource);
        flash.put(Consts.FEED, oldItem);
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
        FacesContext context = ContextMocker.mockFacesContext();
        ExternalContext ext = mock(ExternalContext.class);
        Flash flash = new FlashStub();
        when(ext.getFlash()).thenReturn(flash);
        when(context.getExternalContext()).thenReturn(ext);

        String newName = "newName";
        String newSource = "newSource";
        FeedItem oldItem = new FeedItem();
        oldItem.setFeedName("Name");
        oldItem.setFeedSource("Source");
        FeedItem newItem = new FeedItem();
        oldItem.setFeedName(newName);
        oldItem.setFeedSource(newSource);
        flash.put(Consts.FEED, oldItem);
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
