package com.zubov.i.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.igor.z.interfaces.IFeedManagerModel;
import com.igor.z.utils.FeedItem;
import com.igor.z.controllers.FeedManagerController;
import com.zubov.i.tests.utils.ContextMocker;
import com.zubov.i.tests.utils.FlashStub;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import java.util.ArrayList;
import java.util.List;

public class FeedManagerTests {
    private FeedManagerController view;
    private IFeedManagerModel model;

    @Before
    public void setup() {
        model = mock(IFeedManagerModel.class);
    }

    @After
    public void after() {
        view = null;
    }

    @Test
    public void shouldCreateController() {
        List<FeedItem> feeds = new ArrayList<>();
        when(model.getFeedList()).thenReturn(feeds);
        FacesContext context = ContextMocker.mockFacesContext();
        ExternalContext ext = mock(ExternalContext.class);
        Flash flash = new FlashStub();
        when(ext.getFlash()).thenReturn(flash);
        when(context.getExternalContext()).thenReturn(ext);

        view = new FeedManagerController(model);
    }

    @Test
    public void shouldAddNewFeed() {
        List<FeedItem> feeds = new ArrayList<>();
        when(model.getFeedList()).thenReturn(feeds);
        FacesContext context = ContextMocker.mockFacesContext();
        ExternalContext ext = mock(ExternalContext.class);
        Flash flash = new FlashStub();
        when(ext.getFlash()).thenReturn(flash);
        when(context.getExternalContext()).thenReturn(ext);

        view = new FeedManagerController(model);
        String feedName = "new feed";
        view.setFeedName(feedName);
        view.setFeedSource("lal");
        FeedItem feed = new FeedItem("new feed", "lal");
        feeds.add(feed);
        StringBuilder builder = new StringBuilder();
        builder.append("Feed ").append(feedName).append(" added successfully!");
        when(model.addFeed(feed)).thenReturn(builder.toString());
        view.addFeed();
        view = new FeedManagerController(model);
        assertEquals(builder.toString(), view.getResultMessage());
        assertEquals(feeds.size(), view.getFeeds().size());
        verify(model, times(2)).getFeedList();
    }

    @Test
    public void shouldNotReturnSuccessMessage() {
        List<FeedItem> feeds = new ArrayList<>();
        feeds.add(new FeedItem("new feed", "source"));
        when(model.getFeedList()).thenReturn(feeds);
        FacesContext context = ContextMocker.mockFacesContext();
        ExternalContext ext = mock(ExternalContext.class);
        Flash flash = new FlashStub();
        when(ext.getFlash()).thenReturn(flash);
        when(context.getExternalContext()).thenReturn(ext);

        view = new FeedManagerController(model);
        String feedName = "new feed";
        view.setFeedName(feedName);
        view.setFeedSource("source1");
        when(model.addFeed(any( FeedItem.class))).thenReturn("Something wrong!");
        view.addFeed();
        StringBuilder builder = new StringBuilder();
        builder.append("Feed ").append(feedName).append(" added successfully!");
        view = new FeedManagerController(model);
        assertNotEquals(builder.toString(), view.getResultMessage());
        assertEquals(1, view.getFeeds().size());
        verify(model, times(2)).getFeedList();
    }
}
