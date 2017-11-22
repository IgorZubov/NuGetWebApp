package com.zubov.i.tests;

import com.igor.z.controllers.FeedEditorController;
import com.igor.z.interfaces.IFeedEditorModel;
import com.igor.z.interfaces.INuGetCommandsWrapper;
import com.igor.z.utils.Consts;
import com.igor.z.utils.FeedItem;
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
    private FeedEditorController view;

    @Before
    public void setup() {
        model = mock(IFeedEditorModel.class);
    }

    @After
    public void after() {
        model = null;
        view = null;
    }

    @Test
    public void shouldCreateFeedEditorController(){
        FacesContext context = ContextMocker.mockFacesContext();
        ExternalContext ext = mock(ExternalContext.class);
        Flash flash = new FlashStub();
        when(ext.getFlash()).thenReturn(flash);
        when(context.getExternalContext()).thenReturn(ext);
        view = new FeedEditorController(model);
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
        FeedItem oldItem = new FeedItem("Name", "Source");
        FeedItem newItem = new FeedItem(newName, newSource);
        flash.put(Consts.FEED, oldItem);
        view = new FeedEditorController(model);
        view.FeedEditor();
        when(model.modifyFeed(eq(oldItem), eq(newItem))).thenReturn("Feed successfully modified.");
        view.submit(newName, newSource);
        view = new FeedEditorController(model);
        view.FeedEditor();
        assertEquals("Feed successfully modified.", view.getResultMessage());
        assertEquals(newItem.getName(), view.getNewName());
        assertEquals(newItem.getSource(), view.getNewSource());
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
        FeedItem oldItem = new FeedItem("Name", "Source");
        FeedItem newItem = new FeedItem(newName, newSource);
        flash.put(Consts.FEED, oldItem);
        view = new FeedEditorController(model);
        view.FeedEditor();
        when(model.modifyFeed(eq(oldItem), eq(newItem))).thenReturn("Something wrong");
        view.submit(newName, newSource);
        view = new FeedEditorController(model);
        view.FeedEditor();
        assertNotEquals("Feed successfully modified.", view.getResultMessage());
        assertEquals(newItem.getName(), view.getNewName());
        assertEquals(newItem.getSource(), view.getNewSource());
    }

}
