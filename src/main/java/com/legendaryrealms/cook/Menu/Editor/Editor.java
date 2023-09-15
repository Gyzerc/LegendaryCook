package com.legendaryrealms.cook.Menu.Editor;

import com.legendaryrealms.cook.Data.Cook.Cook;
import com.legendaryrealms.cook.Data.Cook.CookRecipe;
import com.legendaryrealms.cook.Manager.MessageManager;
import com.legendaryrealms.cook.Utils.MsgUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Editor {

    private String id;

    private Cook cook;

    private CookRecipe recipe;

    public Editor(String id)
    {
        this.id=id;
        this.cook=Cook.getCook(id) != null ? Cook.getCook(id) : new Cook(id);
        this.recipe=CookRecipe.getCookRecipe(id) != null ? CookRecipe.getCookRecipe(id) :new CookRecipe(id);
    }

    public Cook getCook()
    {
        return cook;
    }

    public CookRecipe getRecipe()
    {
        return recipe;
    }



    public void save()
    {
        File file=new File("./plugins/LegendaryCook/Cooks",id+".yml");
        YamlConfiguration yml=new YamlConfiguration();

        yml.set("display.material",cook.getMaterial().name());
        yml.set("display.data",cook.getData());
        yml.set("display.model",cook.getModel());
        yml.set("display.name",cook.getName());
        yml.set("display.lore",cook.getLore());

        yml.set("settings.mode",cook.getMode());
        yml.set("settings.game.mode1.addHotTick",cook.getAddHotTick());
        yml.set("settings.game.mode2.speed",cook.getSpeed());

        yml.set("settings.limit.level",cook.getLevel());
        yml.set("settings.limit.hot",cook.getHot());
        yml.set("settings.limit.time",cook.getTime());

        yml.set("settings.chance",cook.getChance());
        yml.set("settings.max_recipes_experience",cook.getMax_recipes_experience());

        yml.set("settings.result.item",cook.getResult_item());
        yml.set("settings.result.add_cook_experience",cook.getResult_add_cook_experience());
        yml.set("settings.result.add_recipes_experince",cook.getResult_add_reicpes_experience());
        yml.set("settings.result.run",cook.getResult_run());

        yml.set("settings.fail.item",cook.getFail_item());
        yml.set("settings.fail.add_cook_experience",cook.getFail_add_cook_experience());
        yml.set("settings.fail.add_recipes_experince",cook.getFail_add_reicpes_experience());
        yml.set("settings.fail.run",cook.getFail_run());
        yml.set("settings.fail.remove",cook.getRemoveBack());

        for (int in=0;in<recipe.getAmounts();in++)
        {
            if (recipe.getItem(in)!=null)
            {
                yml.set("recipes."+in+".hot",recipe.getHot(in));
                yml.set("recipes."+in+".item",recipe.getItem(in));
                yml.set("recipes."+in+".amount",recipe.getNeed(in));
            }
        }

        try {
            yml.save(file);
            Bukkit.getConsoleSender().sendMessage(MessageManager.plugin+"成功保存烹饪配方 "+ MsgUtils.msg("&e"+id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
