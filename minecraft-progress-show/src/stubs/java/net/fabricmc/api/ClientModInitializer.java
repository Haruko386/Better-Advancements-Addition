package net.fabricmc.api;

/** Compile-only declaration. Fabric Loader supplies the implementation API. */
public interface ClientModInitializer {
    void onInitializeClient();
}
