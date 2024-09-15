package com.vladceresna.virtel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class StartupScreen implements Screen {

    Skin skin;
    Stage stage;
    SpriteBatch batch;
    final Main game;
    public StartupScreen(final Main game){
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
        BitmapFont font = new BitmapFont();
        skin.add("default", font);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = skin.getFont("default");
        skin.add("default", labelStyle);
        // Create a label with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".
        Label label = new Label("Virtel", skin,"default");


        Actor actor = new Actor();
        actor.setSize(1,5);

        Image image = new Image(new Texture(Gdx.files.internal("logo.png")));
        image.setSize(50,50);

        table.add(image).size(100);
        table.row();
        table.add(actor);
        table.row();
        table.add(label);
        File config = new File(Gdx.files.getExternalStoragePath()+".virtel/config.txt");
        if(config.exists()){
            //TODO:load all needed tools
            try {
                FileReader reader = new FileReader(config);
                Scanner scan = new Scanner(reader);
                int i = 1;
                String virtelPath = "";
                while (scan.hasNextLine()) {
                    virtelPath += scan.nextLine();
                    i++;
                }
                scan.close();
                reader.close();
                if(!new File(virtelPath).exists()){
                    game.setScreen(new InstallScreen(game));
                    dispose();
                } else {
                    //System.out.println(config.getPath());
                    //System.out.println(virtelPath);
                    game.setScreen(new AppScreen(game, "vladceresna.virtel.launcher", virtelPath));
                    dispose();
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            game.setScreen(new InstallScreen(game));
            dispose();
        }
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

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
