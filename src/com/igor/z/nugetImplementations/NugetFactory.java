package com.igor.z.nugetImplementations;

public class NugetFactory {
    public Nuget getNugetImplementation(NugetImplementation implementation){
        switch (implementation){
            case EMPTY_NUGET:
                return new EmptyNuget();
            case LOCAL_NUGET:
                return new LocalNuget();
            default:
                return new EmptyNuget();
        }
    }
}
