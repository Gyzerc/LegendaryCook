package com.legendaryrealms.cook.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MsgUtils {
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})");


    public  static  String msg(String msg)
    {

        return msg.replace("&","§");

    }

    public static List<String> msg(List<String> msg)
    {
        List<String> lore=new ArrayList<>();
        lore.replaceAll(x->x.replace("&","§"));
        return lore;
    }


}
