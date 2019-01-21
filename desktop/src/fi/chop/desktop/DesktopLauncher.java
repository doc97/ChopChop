package fi.chop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import fi.chop.Chop;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DesktopLauncher {
    public static void main (String[] arg) {
        packTextureAtlas("textures/unpacked/", "textures/packed", "Chop");

        System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;
        config.resizable = false;
        new LwjglApplication(new Chop(), config);
    }

    private static void packTextureAtlas(String inputDir, String outputDir, String atlasName) {
        Path path = Paths.get(inputDir);
        if (!Files.exists(path))
            return;

        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 2048;
        settings.maxHeight = 2048;

        TexturePacker.processIfModified(settings, inputDir, outputDir, atlasName);
    }
}
