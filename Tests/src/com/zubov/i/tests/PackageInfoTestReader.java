package com.zubov.i.tests;

import com.igor.z.springutils.NuGetPackageInfo;
import com.igor.z.springutils.PackageInfoReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PackageInfoTestReader {

    private PackageInfoReader reader;
    private URL packageUrl;

    @Before
    public void setup(){
        reader = new PackageInfoReader();
        ClassLoader classLoader = getClass().getClassLoader();
        packageUrl = classLoader.getResource("resources/log4net.2.0.3.nupkg");
    }

    @After
    public void after() {

    }

    @Test
    public void shouldReadMetaDataFromPackage(){
        NuGetPackageInfo result = reader.readPackage(packageUrl.getPath().substring(1));
        assertNotNull(result);
        assertEquals("log4net", result.getId());
        assertEquals("2.0.3", result.getVersion());
        assertEquals("logging log tracing logfiles", result.getTags());
    }

}
