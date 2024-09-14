package com.vladceresna.virtel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;


public class SkinManager {
    private static SkinManager instance;
    private ObjectMap<String, Skin> skins;

    private SkinManager() {
        skins = new ObjectMap<>();
    }

    public static SkinManager getInstance() {
        if (instance == null) {
            instance = new SkinManager();
        }
        return instance;
    }

    public void loadSkin(String skinName, String jsonPath) {
        Skin skin = new Skin(Gdx.files.external(jsonPath));
        skins.put(skinName, skin);
    }

    public Skin getSkin(String skinName) {
        return skins.get(skinName);
    }
}
