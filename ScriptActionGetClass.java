package com.itzblaze;

import net.eq2online.macros.scripting.api.*;
import net.eq2online.macros.scripting.parser.ScriptAction;
import net.eq2online.macros.scripting.parser.ScriptContext;

@APIVersion(26)
public class ScriptActionGetClass extends ScriptAction {
    public ScriptActionGetClass() {
        super(ScriptContext.MAIN,"getclass");
    }

    @Override
    public boolean isThreadSafe() {
        return false;
    }


    @Override
    public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
        String classname = provider.expand(macro,params[0],false);
        Class<?> clazz = null;
        try {
            clazz = Class.forName(classname);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(clazz == null) return null;
        Classes.classes.put(Cache.hash(clazz.getName() + clazz.getPackage().getName()),clazz);
        return new ReturnValue(Cache.hash(clazz.getName()));


    }

    @Override
    public void onInit() {
        context.getCore().registerScriptAction(this);
    }
}
