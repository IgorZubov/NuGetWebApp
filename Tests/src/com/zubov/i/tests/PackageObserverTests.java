package com.zubov.i.tests;

import com.igor.z.controllers.PackageObserverController;
import com.igor.z.interfaces.IPackageObserverModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PackageObserverTests {
    private IPackageObserverModel model;
    private PackageObserverController view;

    @Before
    public void setup() {
        model = mock(IPackageObserverModel.class);
    }

    @After
    public void after() {
        view = null;
    }

    @Test
    public void shouldCreatePackageObserverController(){

//        view = new PackageObserverController(model);
    }

    @Test
    public void shouldShouldAddPackagesToList(){

//        List<PackageInfo> packages = new ArrayList<>();
//        packages.add(new PackageInfo("Package1", "0.0.1"));
//        packages.add(new PackageInfo("Package2", "1.0.1"));
//        when(model.search(eq(""))).thenReturn(packages);
//        view.setSearchExp("");
//        view.search();
//        view = new PackageObserverController(model);
//        assertArrayEquals(packages.toArray(), view.getPackages().toArray());
    }
}
