package com.itzblaze;

import net.eq2online.macros.scripting.api.*;
import net.eq2online.macros.scripting.parser.ScriptAction;
import net.eq2online.macros.scripting.parser.ScriptContext;
import sun.reflect.generics.scope.ClassScope;

import java.lang.reflect.Method;
import java.util.Arrays;

@APIVersion(26)
public class ScriptActionGetStaticMethod extends ScriptAction {
    public ScriptActionGetStaticMethod() {
        super(ScriptContext.MAIN,"getstaticmethod");
    }

    @Override
    public boolean isThreadSafe() {
        return false;
    }

    @Override
    public IReturnValue execute(IScriptActionProvider provider, IMacro macro, IMacroAction instance, String rawParams, String[] params) {
        Class<?> clazz = Classes.classes.get(provider.expand(macro,params[0],false));
        if(clazz == null) return null;
        try {
            Method m = clazz.getDeclaredMethod(provider.expand(macro,params[1],false));
            String hash = Cache.hash(clazz.getName() + m.getName() + m.getParameterCount() + Arrays.toString(m.getParameters()) + m.getGenericReturnType().getTypeName());
            Classes.methods.put(hash,m);
            return new ReturnValue(hash);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onInit() {
        context.getCore().registerScriptAction(this);
    }
}
