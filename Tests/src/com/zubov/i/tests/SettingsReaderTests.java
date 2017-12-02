package com.zubov.i.tests;

import com.igor.z.interfaces.ISettingsReader;
import com.igor.z.nugetImplementations.NugetImplementation;
import com.igor.z.utils.SettingsReader;
import com.zubov.i.tests.utils.ContextMocker;
import com.zubov.i.tests.utils.UnDisposableStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SettingsReaderTests {
    private ISettingsReader reader;

    @Before
    public void setup() {
//        UnDisposableStream content = new UnDisposableStream( "resources/settings.xml");
//        FacesContext context = ContextMocker.mockFacesContext();
//        ExternalContext ext = mock(ExternalContext.class);
//        when(context.getExternalContext()).thenReturn(ext);
//        when(ext.getResourceAsStream(any(String.class))).thenReturn(content);
        reader = new SettingsReader();
    }

    @After
    public void after() {
        reader = null;
    }

    @Test
    public void shouldGetUploadPath(){
        assertEquals("C:/uploads", reader.getTmpUploadPath());
    }

    @Test
    public void shouldGetExePath(){
        assertEquals("F:/NuGet/nuget.exe", reader.getNuGetExecutablePath());
    }

    @Test
    public void shouldGetConfigPath(){
        assertEquals("C:/NuGet/my.Config", reader.getNuGetConfigPath());
    }

    @Test
    public void shouldGetNugetTmpl(){
        assertEquals(NugetImplementation.SERVER_NUGET, reader.getNuGetImplementation());
    }
}
