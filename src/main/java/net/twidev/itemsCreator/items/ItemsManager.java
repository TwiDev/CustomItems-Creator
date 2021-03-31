package net.twidev.itemsCreator.items;

import net.twidev.CustomItems.ItemsBuilder;
import net.twidev.itemsCreator.Creator;

import java.util.ArrayList;
import java.util.List;

public class ItemsManager {

    private final Creator creator;

    private final List<ItemsBuilder> items = new ArrayList<>();

    public ItemsManager(Creator creator) {
        this.creator = creator;
    }

    public void loadConfigItems() {

    }

}
