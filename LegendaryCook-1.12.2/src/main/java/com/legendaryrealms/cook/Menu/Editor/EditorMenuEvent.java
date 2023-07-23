package com.legendaryrealms.cook.Menu.Editor;

import com.legendaryrealms.cook.LegendaryCook;
import com.legendaryrealms.cook.Manager.MessageManager;
import com.legendaryrealms.cook.Utils.MsgUtils;
import com.legendaryrealms.cook.Utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditorMenuEvent implements Listener {

    private HashMap<Player,Long> cooldown=new HashMap<>();
    @EventHandler
    public void onEditorMenuEdit(InventoryClickEvent e)
    {
        Player p=(Player) e.getWhoClicked();
        EditorMenu menu=getMenu(e.getInventory());


        if (menu!=null) {

            if (!menu.isEditintegration()) {
                e.setCancelled(true);
                if (e.getRawSlot() == 10) {
                    ItemStack i = p.getInventory().getItemInMainHand();
                    if (i != null && !i.getType().equals(Material.AIR)) {
                        menu.getEditor().getCook().setMaterial(i.getType());
                        p.sendMessage(MessageManager.plugin + "更改成功！");
                        menu.updataIcons();
                        p.openInventory(menu.getInventory());
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
                        return;
                    }
                    p.sendMessage(MessageManager.plugin + "请手持物品！");
                } else if (e.getRawSlot() == 14) {
                    p.sendMessage(MessageManager.plugin + "当前版本不支持！");
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
                    return;
                } else if (e.getRawSlot()==17) {

                    menu=new EditorMenu(menu.getCookId(),true);
                    menu.LoadMenu();
                    menu.updataIcons();
                    p.openInventory(menu.getInventory());
                    return;

                } else if (e.getRawSlot()==44) {

                    menu.getEditor().save();
                    p.closeInventory();
                    p.sendMessage(MessageManager.plugin + "成功保存. "+MsgUtils.msg("&e请输入 /cook admin reload 以使该配方生效."));
                    return;

                }
                HashMap<Integer,String> fuction_String=new HashMap<>();
                fuction_String.put(9,"name");
                fuction_String.put(40,"result_item");
                fuction_String.put(49,"fail_item");

                HashMap<Integer,String> fuction_List=new HashMap<>();
                fuction_List.put(11,"lore");
                fuction_List.put(41,"result_run");
                fuction_List.put(50,"fail_run");

                HashMap<Integer,String> fuction_Double=new HashMap<>();
                fuction_Double.put(12,"chance");
                fuction_Double.put(29,"repice_experience");
                fuction_Double.put(38,"result_cook_experience");
                fuction_Double.put(39,"result_recipes_experience");
                fuction_Double.put(47,"result_cook_experience");
                fuction_Double.put(48,"result_recipes_experience");

                HashMap<Integer,String> fuction_Int=new HashMap<>();
                fuction_Int.put(15,"mode_attribute");
                fuction_Int.put(30,"hot");
                fuction_Int.put(31,"time");
                fuction_Int.put(32,"level");
                fuction_Int.put(51,"remove");

                if (fuction_Int.containsKey(e.getRawSlot()))
                {
                    editorMenu.put(p, menu);
                    edit.put(p, fuction_Int.get(e.getRawSlot()));
                    p.sendMessage(MessageManager.plugin + "接下来发送"+e.getCurrentItem().getItemMeta().getDisplayName() +",且必须为整数." + MsgUtils.msg(" &7(输入'cancel'即可取消)"));
                    p.closeInventory();
                    return;
                }

               else if (fuction_Double.containsKey(e.getRawSlot()))
                {
                    editorMenu.put(p, menu);
                    edit.put(p, fuction_Double.get(e.getRawSlot()));
                    p.sendMessage(MessageManager.plugin + "接下来发送"+e.getCurrentItem().getItemMeta().getDisplayName() +"." + MsgUtils.msg(" &7(输入'cancel'即可取消)"));
                    p.closeInventory();
                    return;
                }
                else if (fuction_List.containsKey(e.getRawSlot()))
                {
                    String tag=fuction_List.get(e.getRawSlot());
                    if (e.isLeftClick())
                    {
                        editorMenu.put(p, menu);
                        edit.put(p,tag );
                        p.sendMessage(MessageManager.plugin + "接下来发送需要添加的值" + MsgUtils.msg(" &7(输入'cancel'即可取消)"));
                        p.closeInventory();
                        return;
                    }
                    else if (e.isRightClick())
                    {
                        if (tag.equals("lore"))
                        {
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
                           List<String> lore=menu.getEditor().getCook().getLore();
                           lore.remove(lore.size()-1);
                           menu.getEditor().getCook().setLore(lore);
                           menu.updataIcons();
                           p.openInventory(menu.getInventory());
                           p.sendMessage(MessageManager.plugin + "成功删去最后一条.");
                           p.closeInventory();
                           return;
                        }
                        if (tag.equals("result_run"))
                        {
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
                            List<String> lore=menu.getEditor().getCook().getResult_run();
                            lore.remove(lore.size()-1);
                            menu.getEditor().getCook().setResult_run(lore);
                            menu.updataIcons();
                            p.openInventory(menu.getInventory());
                            p.sendMessage(MessageManager.plugin + "成功删去最后一条.");
                            p.closeInventory();
                            return;
                        }
                        if (tag.equals("fail_run"))
                        {
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
                            List<String> lore=menu.getEditor().getCook().getFail_run();
                            lore.remove(lore.size()-1);
                            menu.getEditor().getCook().setFail_run(lore);
                            menu.updataIcons();
                            p.openInventory(menu.getInventory());
                            p.sendMessage(MessageManager.plugin + "成功删去最后一条.");
                            p.closeInventory();
                            return;
                        }
                    }

                }
                else if (fuction_String.containsKey(e.getRawSlot()))
                {
                    editorMenu.put(p, menu);
                    edit.put(p, fuction_String.get(e.getRawSlot()));
                    List<String> msg=new ArrayList<>();
                    msg.add(MsgUtils.msg("&7#ni;id;数量 —— 使用NeigeItems的物品"));
                    msg.add(MsgUtils.msg("&7#mi;type;id;数量 —— 使用MMOItems的物品"));
                    msg.add(MsgUtils.msg("&7#default;ID;DATA;数量 —— 使用原版物品"));
                    msg.add(MsgUtils.msg("&7#loot;ID —— 使用战利品预设配置"));
                    msg.add(MsgUtils.msg("&7#save;ID;数量 —— 使用插件内保存的物品"));
                    msg.add(MsgUtils.msg("&7#设置为''则不给予任何物品"));
                    if (fuction_String.get(e.getRawSlot()).equals("result_item") || fuction_String.get(e.getRawSlot()).equals("fail_item"))
                    {
                        for (String message:msg)
                        {
                            p.sendMessage(message);
                        }
                    }
                    p.sendMessage(MessageManager.plugin + "接下来发送"+e.getCurrentItem().getItemMeta().getDisplayName() +"." + MsgUtils.msg(" &7(输入'cancel'即可取消)"));
                    p.closeInventory();
                    return;
                }
            }
            else {
                if (e.getRawSlot() >=0 && e.getRawSlot() <=53) {
                    e.setCancelled(true);
                    if (e.getRawSlot() == 49)
                    {
                        menu=new EditorMenu(menu.getCookId(),false);
                        menu.LoadMenu();
                        menu.updataIcons();
                        p.openInventory(menu.getInventory());
                        return;
                    }
                    if (menu.layout.contains(e.getRawSlot())) {
                        if (e.getCurrentItem() != null)
                        {
                            int in=menu.layout.indexOf(e.getRawSlot());
                            if (menu.getEditor().getRecipe().getAmounts() == in)
                            {
                                if (e.getCursor()!=null&&!e.getCursor().getType().equals(Material.AIR))
                                {
                                    ItemStack i=e.getCursor().clone();
                                    i.setAmount(1);
                                    menu.getEditor().getRecipe().add(1,i,1);
                                    menu.updataIcons();
                                    e.setCursor(null);
                                    p.openInventory(menu.getInventory());
                                    p.sendMessage(MessageManager.plugin+"成功添加！");
                                    return;
                                }
                            }
                            else {
                                if (e.isLeftClick())
                                {
                                    if (e.isShiftClick())
                                    {
                                        edit.put(p,"recipe_amount");
                                        editorMenu.put(p,menu);
                                        edit_recipe_in.put(p,in);
                                        p.sendMessage(MessageManager.plugin+"接下来发送需要更改的数量 "+MsgUtils.msg("&7(输入'cancel'即可取消)"));
                                        p.closeInventory();
                                        return;
                                    }
                                    edit.put(p,"recipe_hot");
                                    editorMenu.put(p,menu);
                                    edit_recipe_in.put(p,in);
                                    p.sendMessage(MessageManager.plugin+"接下来发送需要更改的火候值 "+MsgUtils.msg("&7(输入'cancel'即可取消)"));
                                    p.closeInventory();
                                    return;
                                } else if (e.isRightClick()) {
                                    if (e.isShiftClick())
                                    {
                                        if (in == menu.getEditor().getRecipe().getAmounts()-1) {
                                            menu.getEditor().getRecipe().remove(in);
                                            menu=new EditorMenu(menu.getCookId(),true);
                                            menu.LoadMenu();
                                            menu.updataIcons();
                                            p.openInventory(menu.getInventory());
                                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                                            p.sendMessage(MessageManager.plugin + "删除成功！");
                                        }
                                        return;
                                    }
                                    if (p.getInventory().getItemInMainHand() != null &&! p.getInventory().getItemInMainHand().getType().equals(Material.AIR))
                                    {
                                        ItemStack i=p.getInventory().getItemInMainHand();
                                        menu.getEditor().getRecipe().setItem(in,i);
                                        menu.updataIcons();
                                        p.openInventory(menu.getInventory());
                                        p.sendMessage(MessageManager.plugin+"成功更改为手上物品");
                                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
                                        return;
                                    }
                                    p.sendMessage(MessageManager.plugin+"你的手上没有物品");
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void clearMap()
    {
        edit.clear();
        editorMenu.clear();
        edit_recipe_in.clear();
    }
    private HashMap<Player,String> edit=new HashMap<>();
    private HashMap<Player,EditorMenu> editorMenu=new HashMap<>();
    private HashMap<Player,Integer> edit_recipe_in=new HashMap<>();

    @EventHandler (priority = EventPriority.LOWEST,ignoreCancelled = true)
    public void onEditSend(AsyncPlayerChatEvent e)
    {
        Player p=e.getPlayer();
        if (edit.containsKey(p))
        {
            e.setCancelled(true);
            EditorMenu menu=editorMenu.get(p);
            if (e.getMessage().equalsIgnoreCase("cancel"))
            {
                p.sendMessage(MessageManager.plugin+"取消本次操作.");
                openInv(p,menu);
                return;
            }

            String tag=edit.get(p);
            Editor editor=menu.getEditor();

            if (tag.equals("name"))
            {
                editor.getCook().setName(e.getMessage());
                menu.updataIcons();
                openInv(p,menu);
                p.sendMessage(MessageManager.plugin+"成功更改烹饪展示名为："+e.getMessage());
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
                return;
            }
            if (tag.equals("recipe_hot"))
            {
                if (StringUtils.isNum(e.getMessage())) {
                    int max = editor.getCook().getHot();
                    int hot= Integer.parseInt(e.getMessage());
                    if (hot > max)
                    {
                        p.sendMessage(MessageManager.plugin+"不可超出该配方的最大火候值："+max);
                        return;
                    }
                    editor.getRecipe().setHot(edit_recipe_in.get(p), Integer.parseInt(e.getMessage()));
                    menu.updataIcons();
                    openInv(p,menu);
                    p.sendMessage(MessageManager.plugin+"成功更改火候为："+e.getMessage());
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
                    return;
                }
                p.sendMessage(MessageManager.plugin+"请输入正确的数字！");
                return;
            }
            if (tag.equals("recipe_amount"))
            {
                if (StringUtils.isNum(e.getMessage())) {
                    editor.getRecipe().setNeed(edit_recipe_in.get(p), Integer.parseInt(e.getMessage()));
                    menu.updataIcons();
                    openInv(p,menu);
                    p.sendMessage(MessageManager.plugin+"成功更改数量为："+e.getMessage());
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
                    return;
                }
                p.sendMessage(MessageManager.plugin+"请输入正确的数字！");
                return;
            }
            if (tag.equals("lore"))
            {
                List<String> lore=editor.getCook().getLore();
                lore.add(MsgUtils.msg(e.getMessage()));
                editor.getCook().setLore(lore);
                menu.updataIcons();
                openInv(p,menu);
                p.sendMessage(MessageManager.plugin+"成功添加lore："+MsgUtils.msg(e.getMessage()));
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
                return;
            }
            if (tag.equals("result_run"))
            {
                List<String> lore=editor.getCook().getResult_run();
                lore.add(MsgUtils.msg(e.getMessage()));
                editor.getCook().setResult_run(lore);
                menu.updataIcons();
                openInv(p,menu);
                p.sendMessage(MessageManager.plugin+"成功添加命令执行组："+MsgUtils.msg(e.getMessage()));
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
                return;
            }
            if (tag.equals("fail_run"))
            {
                List<String> lore=editor.getCook().getFail_run();
                lore.add(MsgUtils.msg(e.getMessage()));
                editor.getCook().setFail_run(lore);
                menu.updataIcons();
                openInv(p,menu);
                p.sendMessage(MessageManager.plugin+"成功添加命令执行组："+MsgUtils.msg(e.getMessage()));
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
                return;
            }
            if (tag.equals("chance"))
            {
                if (StringUtils.isDouble(e.getMessage())) {
                    double chance=Double.parseDouble(e.getMessage());
                    if (chance > 1.0 || chance < 0.0)
                    {
                        p.sendMessage(MessageManager.plugin + "请输入一个大于0.0且小于1.0的值");
                        return;
                    }
                    editor.getCook().setChance(chance);
                    menu.updataIcons();
                    openInv(p, menu);
                    p.sendMessage(MessageManager.plugin + "成功更改几率为：" + e.getMessage());
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP,1,1);
                    return;
                }
                p.sendMessage(MessageManager.plugin + "请输入一个正确的数字！");
                return;
            }
            if (tag.equals("mode_attribute"))
            {
                if (!StringUtils.isNum(e.getMessage())) {
                    p.sendMessage(MessageManager.plugin + "请输入一个正确的整数！");
                    return;
                }
                String mode=menu.getEditor().getCook().getMode();
                if (mode.equals("mode1"))
                {
                    editor.getCook().setAddHotTick(Integer.parseInt(e.getMessage()));
                    menu.updataIcons();
                    openInv(p, menu);
                    p.sendMessage(MessageManager.plugin + "成功更改火候攀升/下降速度为：" + e.getMessage() + "tick/点");
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    return;
                }
                editor.getCook().setSpeed(Integer.parseInt(e.getMessage()));
                menu.updataIcons();
                openInv(p, menu);
                p.sendMessage(MessageManager.plugin + "成功更改指针左右摇摆速度为：" + e.getMessage());
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                return;
            }
            if (tag.equals("repice_experience"))
            {
                if (StringUtils.isDouble(e.getMessage()) || StringUtils.isNum(e.getMessage()))
                {
                    editor.getCook().setMax_recipes_experience(Double.parseDouble(e.getMessage()));
                    menu.updataIcons();
                    openInv(p, menu);
                    p.sendMessage(MessageManager.plugin + "成功更改该配方的最大熟练度为：" + e.getMessage());
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    return;
                }
                p.sendMessage(MessageManager.plugin + "请输入一个正确的数字！");
                return;
            }
            if (tag.equals("hot"))
            {
                if (!StringUtils.isNum(e.getMessage()))
                {
                    p.sendMessage(MessageManager.plugin + "请输入一个正确的数字！");
                    return;
                }
                editor.getCook().setHot(Integer.parseInt(e.getMessage()));
                menu.updataIcons();
                openInv(p, menu);
                p.sendMessage(MessageManager.plugin + "成功更改该配方的最大火候值为：" + e.getMessage());
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                return;
            }
            if (tag.equals("time"))
            {
                if (!StringUtils.isNum(e.getMessage()))
                {
                    p.sendMessage(MessageManager.plugin + "请输入一个正确的数字！");
                    return;
                }
                editor.getCook().setTime(Integer.parseInt(e.getMessage()));
                menu.updataIcons();
                openInv(p, menu);
                p.sendMessage(MessageManager.plugin + "成功更改该配方的烹饪时间限制：" + e.getMessage()+"s");
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                return;
            }
            if (tag.equals("level"))
            {
                if (!StringUtils.isNum(e.getMessage()))
                {
                    p.sendMessage(MessageManager.plugin + "请输入一个正确的数字！");
                    return;
                }
                editor.getCook().setLevel(Integer.parseInt(e.getMessage()));
                menu.updataIcons();
                openInv(p, menu);
                p.sendMessage(MessageManager.plugin + "成功更改该配方的烹饪等级需求：" + e.getMessage());
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                return;
            }
            if (tag.equals("result_cook_experience"))
            {
                if (StringUtils.isDouble(e.getMessage()) || StringUtils.isNum(e.getMessage()))
                {
                    editor.getCook().setResult_add_cook_experience(Double.parseDouble(e.getMessage()));
                    menu.updataIcons();
                    openInv(p, menu);
                    p.sendMessage(MessageManager.plugin + "成功更改该配方烹饪成功后给与的烹饪经验为：" + e.getMessage());
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    return;
                }
                p.sendMessage(MessageManager.plugin + "请输入一个正确的数字！");
                return;
            }
            if (tag.equals("result_recipes_experience"))
            {
                if (StringUtils.isDouble(e.getMessage()) || StringUtils.isNum(e.getMessage()))
                {
                    editor.getCook().setResult_add_reicpes_experience(Double.parseDouble(e.getMessage()));
                    menu.updataIcons();
                    openInv(p, menu);
                    p.sendMessage(MessageManager.plugin + "成功更改该配方烹饪成功后给与的配方熟练度为：" + e.getMessage());
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    return;
                }
                p.sendMessage(MessageManager.plugin + "请输入一个正确的数字！");
                return;
            }
            if (tag.equals("fail_cook_experience"))
            {
                if (StringUtils.isDouble(e.getMessage()) || StringUtils.isNum(e.getMessage()))
                {
                    editor.getCook().setFail_add_cook_experience(Double.parseDouble(e.getMessage()));
                    menu.updataIcons();
                    openInv(p, menu);
                    p.sendMessage(MessageManager.plugin + "成功更改该配方烹饪失败后给与的烹饪经验为：" + e.getMessage());
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    return;
                }
                p.sendMessage(MessageManager.plugin + "请输入一个正确的数字！");
                return;
            }
            if (tag.equals("result_recipes_experience"))
            {
                if (StringUtils.isDouble(e.getMessage()) || StringUtils.isNum(e.getMessage()))
                {
                    editor.getCook().setFail_add_reicpes_experience(Double.parseDouble(e.getMessage()));
                    menu.updataIcons();
                    openInv(p, menu);
                    p.sendMessage(MessageManager.plugin + "成功更改该配方烹饪失败后给与的配方熟练度为：" + e.getMessage());
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    return;
                }
                p.sendMessage(MessageManager.plugin + "请输入一个正确的数字！");
                return;
            }
            if (tag.equals("result_item"))
            {
                if(LegendaryCook.getInstance().getItemUtils().checkIsItemArg(p,e.getMessage()))
                {
                    editor.getCook().setResult_item(e.getMessage());
                    menu.updataIcons();
                    openInv(p, menu);
                    p.sendMessage(MessageManager.plugin + "成功更改烹饪成功后给与的物品预设为：" + e.getMessage());
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    return;
                }
                return;
            }
            if (tag.equals("fail_item"))
            {
                if(LegendaryCook.getInstance().getItemUtils().checkIsItemArg(p,e.getMessage()))
                {
                    editor.getCook().setFail_item(e.getMessage());
                    menu.updataIcons();
                    openInv(p, menu);
                    p.sendMessage(MessageManager.plugin + "成功更改烹饪失败后给与的物品预设为：" + e.getMessage());
                    p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    return;
                }
                return;
            }
            if (tag.equals("remove"))
            {
                if (!StringUtils.isNum(e.getMessage()))
                {
                    p.sendMessage(MessageManager.plugin + "请输入一个正确的数字！");
                    return;
                }
                editor.getCook().setRemoveBack(Integer.parseInt(e.getMessage()));
                menu.updataIcons();
                openInv(p, menu);
                p.sendMessage(MessageManager.plugin + "成功更改该配方的失败后随机消失种数：" + e.getMessage()+"s");
                p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                return;
            }
        }

    }

    public void openInv(Player p,EditorMenu menu)
    {
        Bukkit.getScheduler().runTask(LegendaryCook.getInstance(),()-> {
            p.openInventory(menu.getInventory());
            editorMenu.remove(p);
            edit.remove(p);
            edit_recipe_in.remove(p);
        });
    }
    public boolean isInCooldown(Player p)
    {
        if (cooldown.containsKey(p))
        {
            if (cooldown.get(p) + 500 > System.currentTimeMillis())
            {
                return false;
            }
        }
        cooldown.put(p,System.currentTimeMillis());
        return true;
    }


    public EditorMenu getMenu(Inventory inv)
    {
        InventoryHolder holder=inv.getHolder();
        if (holder instanceof EditorMenu)
        {
            return (EditorMenu) holder;
        }
        return null;
    }
}
