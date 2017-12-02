package com.igor.z.interfaces;

import com.igor.z.nugetImplementations.NugetImplementation;

public interface ISettingsReader {
    String getNuGetExecutablePath();
    String getNuGetConfigPath();
    String getTmpUploadPath();
    NugetImplementation getNuGetImplementation();
}
