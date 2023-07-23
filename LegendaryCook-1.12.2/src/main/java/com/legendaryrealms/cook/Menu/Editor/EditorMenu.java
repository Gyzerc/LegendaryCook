package com.legendaryrealms.cook.Menu.Editor;

import com.legendaryrealms.cook.Data.Cook.CookRecipe;
import com.legendaryrealms.cook.Utils.MsgUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditorMenu implements InventoryHolder {
    private Inventory inv;
    private String cook;
    private Editor editor;
    private boolean editintegration;
    public EditorMenu(String cook,boolean editintegration)
    {
        this.cook=cook;
        this.editintegration=editintegration;
        this.inv= Bukkit.createInventory(this,54,"编辑配方："+ MsgUtils.msg("&e")+cook);
        this.editor=new Editor(cook);
    }
    private SettingsFile settingsFile;

    public String getCookId()
    {
        return cook;
    }
    public void LoadMenu()
    {
        if (editintegration)
        {
            ItemStack i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("decorate_5")));
            ItemMeta id=i.getItemMeta();
            id.setDisplayName(" ");
            ArrayList l=new ArrayList();
            i.setItemMeta(id);
            inv.setItem(0,i);
            inv.setItem(1,i);
            inv.setItem(2,i);
            inv.setItem(3,i);
            inv.setItem(4,i);
            inv.setItem(5,i);
            inv.setItem(6,i);
            inv.setItem(7,i);
            inv.setItem(8,i);
            inv.setItem(53,i);
            inv.setItem(52,i);
            inv.setItem(51,i);
            inv.setItem(50,i);
            inv.setItem(48,i);
            inv.setItem(47,i);
            inv.setItem(46,i);
            inv.setItem(45,i);


            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("back")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&c&l返回"));
            i.setItemMeta(id);
            inv.setItem(49,i);
        }
        else {

            ItemStack i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("decorate_1")));
            ItemMeta id=i.getItemMeta();
            id.setDisplayName(" ");
            ArrayList l=new ArrayList();
            i.setItemMeta(id);
            inv.setItem(0,i);
            inv.setItem(1,i);
            inv.setItem(2,i);
            inv.setItem(3,i);
            inv.setItem(4,i);
            inv.setItem(5,i);
            inv.setItem(6,i);
            inv.setItem(7,i);
            inv.setItem(8,i);
            inv.setItem(13,i);
            inv.setItem(16,i);
            inv.setItem(18,i);
            inv.setItem(19,i);
            inv.setItem(20,i);
            inv.setItem(21,i);
            inv.setItem(22,i);
            inv.setItem(23,i);
            inv.setItem(24,i);
            inv.setItem(25,i);
            inv.setItem(26,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("decorate_2")));
            id=i.getItemMeta();
            id.setDisplayName(" ");
            i.setItemMeta(id);
            inv.setItem(27,i);
            inv.setItem(28,i);
            inv.setItem(34,i);
            inv.setItem(35,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("decorate_3")));
            id=i.getItemMeta();
            id.setDisplayName(" ");
            i.setItemMeta(id);
            inv.setItem(36,i);
            inv.setItem(37,i);
            inv.setItem(43,i);
            inv.setItem(44,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("decorate_4")));
            id=i.getItemMeta();
            id.setDisplayName(" ");
            i.setItemMeta(id);
            inv.setItem(45,i);
            inv.setItem(46,i);
            inv.setItem(52,i);
            inv.setItem(53,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("save")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&a&l保存"));
            i.setItemMeta(id);
            inv.setItem(44,i);
        }
    }
    public List<Integer> layout= Arrays.asList(19,20,21,22,23,24,25,
            28,29,30,31,32,33,34);
    public boolean isEditintegration()
    {
        return editintegration;
    }
    public void updataIcons()
    {
        if (editintegration)
        {

            CookRecipe recipe=editor.getRecipe();
            int amount=recipe.getAmounts();
            for (int in=0;in<amount;in++)
            {
                ItemStack i=recipe.getItem(in).clone();
                i.setAmount(recipe.getNeed(in));
                ItemMeta id=i.getItemMeta();
                List<String> lore=id.hasLore() ? id.getLore() : new ArrayList<>();
                lore.add("");
                lore.add(MsgUtils.msg("&b需求火候值: &e"+recipe.getHot(in)));
                lore.add(MsgUtils.msg("&b需求数量: &e"+recipe.getNeed(in)));
                lore.add("");
                lore.add(MsgUtils.msg("&7[ &a左键 &f更改火候值 &7]"));
                lore.add(MsgUtils.msg("&7[ &a右键 &f更改为手持的物品 &7]"));
                lore.add(MsgUtils.msg("&7[ &aShift+左键 &f更改数量 &7]"));

                if (in == amount-1) {
                    lore.add(MsgUtils.msg("&7[ &aShift+右键 &c删除 &7]"));
                }

                id.setLore(lore);
                i.setItemMeta(id);
                inv.setItem(layout.get(in),i);

            }

                ItemStack i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("empty")));
                ItemMeta id=i.getItemMeta();
                id.setDisplayName(MsgUtils.msg("&a无"));
                ArrayList l=new ArrayList();
                l.add(" ");
                l.add(MsgUtils.msg("&6&l> &e将物品拖动到这里并 &a点击 &e以添加配方材料"));
                l.add(MsgUtils.msg("&f"));
                id.setLore(l);
                i.setItemMeta(id);
                if (layout.size() > amount) {
                    inv.setItem(layout.get(amount), i);
                }

        }
        else {

            ItemStack i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("name")));
            ItemMeta id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置该配方的展示名称"));
            ArrayList l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&7当前值: &f"+editor.getCook().getName()));
            l.add(MsgUtils.msg("&8&l[ &2点击更改 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(9,i);

            i=new ItemStack(editor.getCook().getMaterial(),1, (short) editor.getCook().getData());
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置该配方的展示材质"));
            l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &2点击更改为当前手上物品的材质 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(10,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("lore")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置该配方的展示Lore"));
            l=new ArrayList();
            l.add(MsgUtils.msg("&7当前值:"));
            for (String lore:editor.getCook().getLore())
            {
                l.add(lore);
            }
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &a左键新增 &8&l]"));
            l.add(MsgUtils.msg("&8&l[ &c右键删除最后添加的一行 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(11,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("chance")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置该配方的成功几率"));
            l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&c注意:"));
            l.add(MsgUtils.msg("&f - 这里的成功率为当配方所有材料"));
            l.add(MsgUtils.msg("&f - 都投入且顺序和数量正确后烹饪成功的几率"));
            l.add(" ");
            l.add(MsgUtils.msg("&7当前值: &a"+editor.getCook().getChance()));
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &2点击更改 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(12,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("mode")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置该配方的游戏模式"));
            l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&c注意:"));
            l.add(MsgUtils.msg("&f - &6mode1 &f为原版玩法 支持 &d1.12.2 ~ 1.20.1"));
            l.add(MsgUtils.msg("&f - &6mode2 &f为扩展玩法 需 &dItemsAdder &f安装，以及服务端版本高于&d1.16"));
            l.add(" ");
            l.add(MsgUtils.msg("&7当前值: &a"+editor.getCook().getMode()));
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &2点击切换 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(14,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("mode_attribute")));
            id=i.getItemMeta();
            l=new ArrayList();
            if (editor.getCook().getMode().equals("mode1")) {
                id.setDisplayName(MsgUtils.msg("&e设置火候攀升/降低的Tick"));
                l.add(" ");
                l.add(MsgUtils.msg("&c注意:"));
                l.add(MsgUtils.msg("&f - 设置多少Tick攀升/降低1点火候"));
                l.add(" ");
                l.add(MsgUtils.msg("&7当前值: &a" + editor.getCook().getAddHotTick()));
                l.add(" ");
                l.add(MsgUtils.msg("&8&l[ &2点击更改 &8&l]"));
            }
            else {
                id.setDisplayName(MsgUtils.msg("&e设置指针左右摇摆速度"));
                l.add(" ");
                l.add(MsgUtils.msg("&7当前值: &a" + editor.getCook().getSpeed()));
                l.add(" ");
                l.add(MsgUtils.msg("&8&l[ &2点击更改 &8&l]"));
            }
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(15,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("recipes")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置烹饪配方"));
            l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &2点击进入编辑 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(17,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("recipes_experience")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置该配方的最大熟练值"));
            l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&7当前值: &a"+editor.getCook().getMax_recipes_experience()));
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &2点击更改 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(29,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("hot")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置该配方的最大火候值"));
            l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&7当前值: &a"+editor.getCook().getHot()));
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &2点击更改 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(30,i);


            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("time")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置该配方的烹饪时间"));
            l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&7当前值: &a"+editor.getCook().getTime())+"s");
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &2点击更改 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(31,i);


            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("level")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置烹饪等级需求"));
            l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&7当前值: &a"+editor.getCook().getLevel()));
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &2点击更改 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(32,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("result_add_experience")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置烹饪成功给与的 &a烹饪经验"));
            l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&7当前值: &a"+editor.getCook().getResult_add_cook_experience()));
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &2点击更改 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(38,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("result_recipes_experience")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置烹饪成功给与的 &a熟练度"));
            l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&7当前值: &a"+editor.getCook().getResult_add_reicpes_experience()));
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &2点击更改 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(39,i);


            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("result_item")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置烹饪成功给与的奖励物品/战利品"));
            l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&7当前值: &a"+editor.getCook().getResult_item()));
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &2点击更改 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(40,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("result_run")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置烹饪成功执行的命令组"));
            l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&7当前值: "));
            for (String cmd:editor.getCook().getResult_run())
            {
                l.add(cmd);
            }
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &a左键添加 &8&l]"));
            l.add(MsgUtils.msg("&8&l[ &c右键删除最后一条 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(41,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("fail_add_experience")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置烹饪失败给与的 &a烹饪经验"));
            l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&7当前值: &a"+editor.getCook().getFail_add_cook_experience()));
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &2点击更改 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(47,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("fail_recipes_experience")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置烹饪失败给与的 &a熟练度"));
            l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&7当前值: &a"+editor.getCook().getFail_add_reicpes_experience()));
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &2点击更改 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(48,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("fail_item")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置烹饪失败给与的奖励/战利品"));
            l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&7当前值: &a"+editor.getCook().getFail_item()));
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &2点击更改 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(49,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("fail_run")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置烹饪失败执行的命令组"));
            l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&7当前值: "));
            for (String cmd:editor.getCook().getFail_run())
            {
                l.add(cmd);
            }
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &a左键添加 &8&l]"));
            l.add(MsgUtils.msg("&8&l[ &c右键删除最后一条 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(50,i);

            i=new ItemStack(Material.getMaterial(settingsFile.editor_material.get("fail_remove")));
            id=i.getItemMeta();
            id.setDisplayName(MsgUtils.msg("&e设置烹饪失败返回材料消失的种数"));
            l=new ArrayList();
            l.add(" ");
            l.add(MsgUtils.msg("&f当失败时候随机消失指定种材料（非数量）"));
            l.add(MsgUtils.msg("&7当前值: ")+editor.getCook().getRemoveBack());
            l.add(" ");
            l.add(MsgUtils.msg("&8&l[ &2点击更改 &8&l]"));
            id.setLore(l);
            i.setItemMeta(id);
            inv.setItem(51,i);
        }



    }

    public void save()
    {
        editor.save();
    }

    @Override
    public Inventory getInventory() {
        return inv;
    }

    public Editor getEditor()
    {
        return editor;
    }
}
