package com.mahui.mhmvp.app;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/7.
 */

public class Page implements Serializable {
    String name;// 页的名字
    Map<String, String> args = new HashMap<String, String>();// 需要的参数
    public Page(){

    }
    public Page(String url) {

        String[] arg = url.split(",");
        for (String str : arg) {
            if(str.equals("direct")){
                name="direct";
            }else if (str.indexOf("tab") != -1) {
                name = "main";
                parseArg(str);
            } else if (str.indexOf("-") != -1) {
                String[] args = str.split("-");
                int len = args.length;
                if(len>0){
                    name = args[0];
                    parseArg(args[1]);
                }

            } else if (str.indexOf("=") != -1) {
                parseArg(str);
            } else {
                name = str;
            }
        }

    }

    private void parseArg(String str) {
        String[] arg = str.split("=");
        if(arg.length>1){
            args.put(arg[0], arg[1]);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getArgs() {
        return args;
    }

    public void setArgs(Map<String, String> args) {
        this.args = args;
    }

}
