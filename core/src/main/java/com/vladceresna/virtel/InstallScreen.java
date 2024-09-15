package com.vladceresna.virtel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import jdk.internal.net.http.HttpRequestImpl;

/** First screen of the application. Displayed after the application is created. */
public class InstallScreen implements Screen {
    Skin skin;
    Stage stage;
    SpriteBatch batch;
    final Main game;

    public InstallScreen(final Main game){
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.

        skin = new Skin();

        // Generate a 1x1 white texture and store it in the skin named "white".
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));


        // Store the default libGDX font under the name "default".
        skin.add("default", new BitmapFont());



        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        skin.add("default", labelStyle);
        // Create a label with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        Label label = new Label("Enter your path where need to install Virtel!", skin,"default");

        // Add a listener to the label. ChangeListener is fired when the label's checked state changes, eg when clicked,
        // Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
        // ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
        // revert the checked state.



        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = skin.getFont("default");
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.background = skin.newDrawable("white", Color.GRAY);
        textFieldStyle.cursor = skin.newDrawable("white", Color.WHITE);
        textFieldStyle.selection = skin.newDrawable("white", Color.LIGHT_GRAY);
        skin.add("default",textFieldStyle);

        TextField textField = new TextField("/your-storage/Virtel/",skin, "default");

        // Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.down = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.GRAY);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);
        TextButton textButton = new TextButton("Install", skin,"default");
        textButton.pad(10);
        textButton.addListener(new ChangeListener() {
            @SuppressWarnings("NewApi")
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                String path = textField.getText();

                File launcherPath = new File(path+"virtel/apps/vladceresna.virtel.launcher/bin/");
                File launcherFile = new File(path+"virtel/apps/vladceresna.virtel.launcher/bin/start.steps");

                //make virtel system
                launcherPath.mkdirs();
                try {
                    launcherFile.createNewFile();
                    InputStream in = new URL("https://drive.usercontent.google.com/uc?id=1xPqJuiJO1E9C7GP91E0c5cYPe5xnDCOb").openStream();
                    Files.copy(in, Paths.get(launcherFile.toURI()), StandardCopyOption.REPLACE_EXISTING);
                    in.close();
                } catch (IOException e) {
                    System.out.println("Input isn`t normal");
                }

                //make config file
                File config = new File(Gdx.files.getExternalStoragePath() + ".virtel/config.txt");
                File configFolder = new File(Gdx.files.getExternalStoragePath() + ".virtel/");
                if (!config.exists()) {
                    try {
                        configFolder.mkdirs();
                        boolean newFile = config.createNewFile();
                        FileWriter writer = new FileWriter(config, true);
                        writer.write(path+"virtel/");
                        writer.close();
                        game.setScreen(new StartupScreen(game));
                        dispose();
                    } catch (IOException e) {
                        System.out.println("Thats wrong: "+e.getMessage());
                    }
                }

            }
        });

        Actor actor = new Actor();
        actor.setSize(1,5);

        Image image = new Image(new Texture(Gdx.files.internal("logo.png")));
        image.setSize(50,50);


        // Add an image actor. Have to set the size, else it would be the size of the drawable (which is the 1x1 texture)
        table.add(image).size(100);
        table.row();
        table.add(label);
        table.row();
        table.add(actor);
        table.row();
        table.add(textField).size(200,30);
        table.row();
        table.add(actor);
        table.row();
        table.add(textButton);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.2f, 0.2f, 0.2f, 1);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        stage.dispose();
        skin.dispose();
    }

}
