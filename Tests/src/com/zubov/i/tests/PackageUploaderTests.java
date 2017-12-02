package com.zubov.i.tests;

import com.igor.z.interfaces.IPackageUploaderModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PackageUploaderTests {

    private IPackageUploaderModel model;

    @Before
    public void setup() {
        model = mock(IPackageUploaderModel.class);
    }

    @After
    public void after() {
    }

    @Test
    public void shouldCreatePackageObserverController(){

    }

    @Test
    public void shouldUploadPackage(){

//        view = new PackageUploaderController(model);
//        view.setSelectedFeed("source");
//        view.setFile(mock(Part.class));
//        try {
//            when(model.uploadPackageToTempFolder(any(Part.class))).thenReturn("path");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        when(model.addPackageToFeed(any(String.class), any(String.class))).thenReturn("Package added");
//        view.addPackage();
//        view = new PackageUploaderController(model);
//        assertEquals("Package added", view.getResultMessage());
    }


    @Test
    public void shouldShowErrorWheIOException(){

//        view = new PackageUploaderController(model);
//        view.setSelectedFeed("source");
//        view.setFile(mock(Part.class));
//        try {
//            when(model.uploadPackageToTempFolder(any(Part.class))).thenThrow(new IOException("..."){ });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        view.addPackage();
//        view = new PackageUploaderController(model);
//        assertEquals("Error occurred in uploading file.", view.getResultMessage());
    }
}
