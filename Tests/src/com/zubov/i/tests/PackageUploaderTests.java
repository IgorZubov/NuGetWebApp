package com.zubov.i.tests;

import com.igor.z.controllers.PackageUploaderController;
import com.igor.z.interfaces.IPackageUploaderModel;
import com.zubov.i.tests.utils.ContextMocker;
import com.zubov.i.tests.utils.FlashStub;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.http.Part;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PackageUploaderTests {

    private PackageUploaderController view;
    private IPackageUploaderModel model;

    @Before
    public void setup() {
        model = mock(IPackageUploaderModel.class);
    }

    @After
    public void after() {
        view = null;
    }

    @Test
    public void shouldCreatePackageObserverController(){
        FacesContext context = ContextMocker.mockFacesContext();
        ExternalContext ext = mock(ExternalContext.class);

        Flash flash = new FlashStub();
        when(ext.getFlash()).thenReturn(flash);
        when(context.getExternalContext()).thenReturn(ext);
        view = new PackageUploaderController(model);
    }

    @Test
    public void shouldUploadPackage(){
        FacesContext context = ContextMocker.mockFacesContext();
        ExternalContext ext = mock(ExternalContext.class);

        Flash flash = new FlashStub();
        when(ext.getFlash()).thenReturn(flash);
        when(context.getExternalContext()).thenReturn(ext);
        view = new PackageUploaderController(model);
        view.setSelectedFeed("source");
        view.setFile(mock(Part.class));
        try {
            when(model.uploadPackageToTempFolder(any(Part.class))).thenReturn("path");
        } catch (IOException e) {
            e.printStackTrace();
        }
        when(model.addPackageToFeed(any(String.class), any(String.class))).thenReturn("Package added");
        view.addPackage();
        view = new PackageUploaderController(model);
        assertEquals("Package added", view.getResultMessage());
    }


    @Test
    public void shouldShowErrorWheIOException(){
        FacesContext context = ContextMocker.mockFacesContext();
        ExternalContext ext = mock(ExternalContext.class);

        Flash flash = new FlashStub();
        when(ext.getFlash()).thenReturn(flash);
        when(context.getExternalContext()).thenReturn(ext);
        view = new PackageUploaderController(model);
        view.setSelectedFeed("source");
        view.setFile(mock(Part.class));
        try {
            when(model.uploadPackageToTempFolder(any(Part.class))).thenThrow(new IOException("..."){ });
        } catch (IOException e) {
            e.printStackTrace();
        }
        view.addPackage();
        view = new PackageUploaderController(model);
        assertEquals("Error occurred in uploading file.", view.getResultMessage());
    }
}
