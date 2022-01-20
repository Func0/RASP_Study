package com.peppy.RaspDemo;

import org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import static org.objectweb.asm.Opcodes.*;

public class ClassTransformerDemo implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
       className = className.replace("/",".");
       if (className.contains("HelloController")){
           ClassReader  cr  = new ClassReader(classfileBuffer);
           ClassWriter  cw  = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
           ClassVisitor cv = new ClassVisitor(ASM5,cw) {
               @Override
               public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
                   MethodVisitor mv = super.visitMethod(i,s,s1,s2,strings);
                   return new MethodVisitor(ASM5,mv) {
                       @Override
                       public void visitCode() {
                           if (mv != null && !s.equals("<init>")&&s.equals("hello01")) {
//                            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//                               visitFieldInsn(INVOKESTATIC, "HookFilter", "XSS", "()V");
//                               mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
//                               mv.visitLdcInsn("Method Enter...");
                               mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                               mv.visitVarInsn(ALOAD, 1);
                               mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                               //System.out.println();

                               mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                               mv.visitVarInsn(ILOAD, 2);
                               mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
//                               mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                           }
                       }
                   };
               }
           };
           cr.accept(cv,ClassReader.EXPAND_FRAMES);
           classfileBuffer = cw.toByteArray();
           try{
               String path = (String)System.getProperties().get("user.dir")+"/com/peppy/NewClass/";
               File f = new File(path);
               System.out.println(path);
               f.mkdirs();
               FileOutputStream fo = new FileOutputStream(new File(path +"Filter3.class"));
               fo.write(classfileBuffer);
               fo.flush();
               fo.close();
           }catch (IOException e){
               e.printStackTrace();
           }

       }
        return classfileBuffer;
    }

//
        public static void MyTransformer() throws IOException {
        ClassReader cr = new ClassReader(ClassPrinterDemo.class.getClassLoader().getResourceAsStream("com/example/springdemo01/api/HelloController.class"));
        ClassWriter cw = new ClassWriter(0);//创建新的写入
        ClassVisitor cv = new ClassVisitor(ASM5,cw) {
            @Override
            public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
                MethodVisitor mv = super.visitMethod(i,s,s1,s2,strings);
                return new MethodVisitor(ASM5,mv) {
                    @Override
                    public void visitCode() {
                        if (mv != null && !s.equals("<init>")) {
//                            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                            visitFieldInsn(INVOKESTATIC, "HookFilter", "XSS", "()V");
                        }
                    }
                };
            }
        };
        cr.accept(cv,0);
        byte[] b = cw.toByteArray();
        String path = (String)System.getProperties().get("user.dir")+"/com/peppy/asmDemo/NewClass/";
        File f = new File(path);
        System.out.println(path);
        f.mkdirs();
        FileOutputStream fo = new FileOutputStream(new File(path +"Filter3.class"));
        fo.write(b);
        fo.flush();
        fo.close();
    }
}
