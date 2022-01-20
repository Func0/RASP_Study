package com.peppy.RaspDemo;

import java.io.IOException;
import java.lang.instrument.Instrumentation;

public class AgentDemo {
    public static void premain(String featureString, Instrumentation instrumentation) throws IOException {
        System.out.println("===Step in Agent Method1===");
        System.out.println("supportReDefine:" + instrumentation.isRedefineClassesSupported());
        System.out.println("supportReTransform:" + instrumentation.isRetransformClassesSupported());
        System.out.println("featureString:" +  featureString);
        ClassTransformerDemo td = new ClassTransformerDemo();
        instrumentation.addTransformer(td);
    }

    public static void premain(String featureString) {
        System.out.println("===Step in Agent Method2====");
    }
}

