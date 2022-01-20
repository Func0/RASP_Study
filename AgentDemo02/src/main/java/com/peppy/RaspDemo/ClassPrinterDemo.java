package com.peppy.RaspDemo;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

import java.io.IOException;

import static org.objectweb.asm.Opcodes.ASM4;

public class ClassPrinterDemo extends ClassVisitor {
    public ClassPrinterDemo() {
        super(ASM4);
    }
    @Override
    public void visit(int i, int i1, String s, String s1, String s2, String[] strings) {
        super.visit(i, i1, s, s1, s2, strings);
        System.out.println(s+" extends "+s2);
    }

//    public static void main(String[] args) throws IOException {
//        ClassPrinterDemo cp = new ClassPrinterDemo();
//        ClassReader cr = new ClassReader("java.lang.Runnable");
////        ClassReader cr = new ClassReader(Asm01.class.getClassLoader().getResourceAsStream());
//        cr.accept(cp,0);
//    }
}
