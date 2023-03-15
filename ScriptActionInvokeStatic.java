package com.itzblaze;

import net.eq2online.macros.scripting.api.*;
import net.eq2online.macros.scripting.parser.ScriptAction;
import net.eq2online.macros.scripting.parser.ScriptContext;
import net.eq2online.macros.scripting.parser.ScriptCore;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@APIVersion(26)
public class ScriptActionInvokeStatic extends ScriptAction {
    public ScriptActionInvokeStatic() {
        super(ScriptContext.MAIN,"invokestatic");
    }

    @Override
    public boolean isThreadSafe() {
        return false;
    }

    @Override
    public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
        String method = provider.expand(macro,params[0],false);
        ArrayList<String> laterParams = new ArrayList<>(Arrays.asList(params));
        ArrayList<Object> objs = new ArrayList<>();
        for(String param0 : laterParams) {
            String param = provider.expand(macro,param0,false);
            boolean isInt = true;
            boolean isString = true;
            boolean isBoolean = true;

            if(isInt) {
                objs.add(Integer.parseInt(param));
            } else {
                if(param.toLowerCase().contains("true") || param.toLowerCase().contains("false")) {
                    isBoolean = true;
                } else {
                    isString = true;
                    // TODO: boolean isObject = false;
                    for(char c : param.toCharArray()) {
                        if(!Character.isDigit(c)) isInt = false; break;
                    }
                }
            }



        }
        Object returnV = null;
        Method m = Classes.methods.get(method);
        try {
            returnV = m.invoke(null,objs.toArray());
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        if(returnV instanceof String) return new ReturnValue((String) returnV);

        if(returnV instanceof Integer) return new ReturnValue((Integer) returnV);

        if(returnV instanceof Boolean) return new ReturnValue((Boolean) returnV);

        return new ReturnValue(false);
    }

    @Override
    public void onInit() {
        context.getCore().registerScriptAction(this);
    }
}
