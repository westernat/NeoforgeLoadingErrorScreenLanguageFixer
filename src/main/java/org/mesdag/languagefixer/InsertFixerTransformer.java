package org.mesdag.languagefixer;

import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.api.ITransformerVotingContext;
import cpw.mods.modlauncher.api.TargetType;
import cpw.mods.modlauncher.api.TransformerVoteResult;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.util.Set;

class InsertFixerTransformer implements ITransformer<ClassNode>, Opcodes {
    @Override
    public ClassNode transform(ClassNode input, ITransformerVotingContext context) {
        addFixMethod(input);
        invokeFixMethod(input);
        return input;
    }

    private static void invokeFixMethod(ClassNode input) {
        for (MethodNode method : input.methods) {
            if (!"loadBuiltinLanguages".equals(method.name)) continue;
            for (AbstractInsnNode insn : method.instructions) {
                if (insn.getOpcode() != Opcodes.INVOKESTATIC) continue;
                MethodInsnNode mInsn = (MethodInsnNode) insn;
                if (!"injectTranslations".equals(mInsn.name)) continue;
                InsnList toInsert = new InsnList();
                toInsert.add(new FieldInsnNode(Opcodes.GETSTATIC, "net/neoforged/neoforge/server/LanguageHook", "modTable", "Ljava/util/Map;"));
                toInsert.add(new MethodInsnNode(Opcodes.INVOKESTATIC, "net/neoforged/neoforge/server/LanguageHook", "confluence$fix", "(Ljava/util/Map;)V"));
                method.instructions.insertBefore(insn, toInsert);
                break;
            }
        }
    }

    private static void addFixMethod(ClassNode input) {
        MethodVisitor methodVisitor = input.visitMethod(ACC_PRIVATE | ACC_STATIC, "confluence$fix", "(Ljava/util/Map;)V", "(Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;)V", null);
        methodVisitor.visitCode();
        Label label0 = new Label();
        Label label1 = new Label();
        Label label2 = new Label();
        methodVisitor.visitTryCatchBlock(label0, label1, label2, "java/lang/Throwable");
        Label label3 = new Label();
        Label label4 = new Label();
        Label label5 = new Label();
        methodVisitor.visitTryCatchBlock(label3, label4, label5, "java/lang/Throwable");
        Label label6 = new Label();
        Label label7 = new Label();
        Label label8 = new Label();
        methodVisitor.visitTryCatchBlock(label6, label7, label8, "java/io/IOException");
        Label label9 = new Label();
        methodVisitor.visitLabel(label9);
        methodVisitor.visitLineNumber(20, label9);
        methodVisitor.visitMethodInsn(INVOKESTATIC, "net/minecraft/client/Minecraft", "getInstance", "()Lnet/minecraft/client/Minecraft;", false);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/Minecraft", "getLanguageManager", "()Lnet/minecraft/client/resources/language/LanguageManager;", false);
        methodVisitor.visitVarInsn(ASTORE, 1);
        Label label10 = new Label();
        methodVisitor.visitLabel(label10);
        methodVisitor.visitLineNumber(21, label10);
        methodVisitor.visitVarInsn(ALOAD, 1);
        Label label11 = new Label();
        methodVisitor.visitJumpInsn(IFNULL, label11);
        Label label12 = new Label();
        methodVisitor.visitLabel(label12);
        methodVisitor.visitLineNumber(22, label12);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/resources/language/LanguageManager", "getSelected", "()Ljava/lang/String;", false);
        methodVisitor.visitVarInsn(ASTORE, 2);
        Label label13 = new Label();
        methodVisitor.visitLabel(label13);
        methodVisitor.visitLineNumber(23, label13);
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/client/resources/language/LanguageManager", "getLanguage", "(Ljava/lang/String;)Lnet/minecraft/client/resources/language/LanguageInfo;", false);
        methodVisitor.visitVarInsn(ASTORE, 3);
        Label label14 = new Label();
        methodVisitor.visitLabel(label14);
        methodVisitor.visitLineNumber(24, label14);
        methodVisitor.visitVarInsn(ALOAD, 3);
        methodVisitor.visitJumpInsn(IFNULL, label11);
        methodVisitor.visitLdcInsn("en_us");
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/String", "equals", "(Ljava/lang/Object;)Z", false);
        methodVisitor.visitJumpInsn(IFNE, label11);
        Label label15 = new Label();
        methodVisitor.visitLabel(label15);
        methodVisitor.visitLineNumber(25, label15);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitMethodInsn(INVOKESTATIC, "net/neoforged/fml/i18n/I18nManager", "loadTranslations", "(Ljava/lang/String;)Ljava/util/Map;", false);
        methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "putAll", "(Ljava/util/Map;)V", true);
        Label label16 = new Label();
        methodVisitor.visitLabel(label16);
        methodVisitor.visitLineNumber(26, label16);
        methodVisitor.visitLdcInsn(Type.getType("Lnet/neoforged/neoforge/common/NeoForge;"));
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitInvokeDynamicInsn("makeConcatWithConstants", "(Ljava/lang/String;)Ljava/lang/String;", new Handle(Opcodes.H_INVOKESTATIC, "java/lang/invoke/StringConcatFactory", "makeConcatWithConstants", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;", false), new Object[]{"/assets/neoforge/lang/\u0001.json"});
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getResourceAsStream", "(Ljava/lang/String;)Ljava/io/InputStream;", false);
        methodVisitor.visitVarInsn(ASTORE, 4);
        Label label17 = new Label();
        methodVisitor.visitLabel(label17);
        methodVisitor.visitLineNumber(27, label17);
        methodVisitor.visitVarInsn(ALOAD, 4);
        Label label18 = new Label();
        methodVisitor.visitJumpInsn(IFNULL, label18);
        methodVisitor.visitLabel(label6);
        methodVisitor.visitLineNumber(28, label6);
        methodVisitor.visitTypeInsn(NEW, "java/io/InputStreamReader");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitVarInsn(ALOAD, 4);
        methodVisitor.visitFieldInsn(GETSTATIC, "java/nio/charset/StandardCharsets", "UTF_8", "Ljava/nio/charset/Charset;");
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/io/InputStreamReader", "<init>", "(Ljava/io/InputStream;Ljava/nio/charset/Charset;)V", false);
        methodVisitor.visitVarInsn(ASTORE, 5);
        methodVisitor.visitLabel(label0);
        methodVisitor.visitLineNumber(29, label0);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitTypeInsn(NEW, "com/google/gson/Gson");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "com/google/gson/Gson", "<init>", "()V", false);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitTypeInsn(NEW, "org/mesdag/languagefixer/StringStringMapType");
        methodVisitor.visitInsn(DUP);
        methodVisitor.visitMethodInsn(INVOKESPECIAL, "org/mesdag/languagefixer/StringStringMapType", "<init>", "()V", false);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "com/google/gson/Gson", "fromJson", "(Ljava/io/Reader;Lcom/google/gson/reflect/TypeToken;)Ljava/lang/Object;", false);
        methodVisitor.visitTypeInsn(CHECKCAST, "java/util/Map");
        methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "putAll", "(Ljava/util/Map;)V", true);
        methodVisitor.visitLabel(label1);
        methodVisitor.visitLineNumber(30, label1);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/InputStreamReader", "close", "()V", false);
        methodVisitor.visitJumpInsn(GOTO, label7);
        methodVisitor.visitLabel(label2);
        methodVisitor.visitLineNumber(28, label2);
        methodVisitor.visitFrame(Opcodes.F_FULL, 6, new Object[] {"java/util/Map", "net/minecraft/client/resources/language/LanguageManager", "java/lang/String", "net/minecraft/client/resources/language/LanguageInfo", "java/io/InputStream", "java/io/InputStreamReader"}, 1, new Object[] {"java/lang/Throwable"});
        methodVisitor.visitVarInsn(ASTORE, 6);
        methodVisitor.visitLabel(label3);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/io/InputStreamReader", "close", "()V", false);
        methodVisitor.visitLabel(label4);
        Label label19 = new Label();
        methodVisitor.visitJumpInsn(GOTO, label19);
        methodVisitor.visitLabel(label5);
        methodVisitor.visitFrame(Opcodes.F_FULL, 7, new Object[] {"java/util/Map", "net/minecraft/client/resources/language/LanguageManager", "java/lang/String", "net/minecraft/client/resources/language/LanguageInfo", "java/io/InputStream", "java/io/InputStreamReader", "java/lang/Throwable"}, 1, new Object[] {"java/lang/Throwable"});
        methodVisitor.visitVarInsn(ASTORE, 7);
        methodVisitor.visitVarInsn(ALOAD, 6);
        methodVisitor.visitVarInsn(ALOAD, 7);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Throwable", "addSuppressed", "(Ljava/lang/Throwable;)V", false);
        methodVisitor.visitLabel(label19);
        methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        methodVisitor.visitVarInsn(ALOAD, 6);
        methodVisitor.visitInsn(ATHROW);
        methodVisitor.visitLabel(label7);
        methodVisitor.visitLineNumber(30, label7);
        methodVisitor.visitFrame(Opcodes.F_CHOP,2, null, 0, null);
        methodVisitor.visitJumpInsn(GOTO, label18);
        methodVisitor.visitLabel(label8);
        methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] {"java/io/IOException"});
        methodVisitor.visitVarInsn(ASTORE, 5);
        methodVisitor.visitLabel(label18);
        methodVisitor.visitLineNumber(32, label18);
        methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitLdcInsn("fml.loadingerrorscreen.errorheader");
        methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;", true);
        methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/String");
        methodVisitor.visitVarInsn(ASTORE, 5);
        Label label20 = new Label();
        methodVisitor.visitLabel(label20);
        methodVisitor.visitLineNumber(33, label20);
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitJumpInsn(IFNULL, label11);
        Label label21 = new Label();
        methodVisitor.visitLabel(label21);
        methodVisitor.visitLineNumber(34, label21);
        methodVisitor.visitLdcInsn("zh_cn");
        methodVisitor.visitLdcInsn("\u8be5\u754c\u9762\u7684\u672c\u5730\u5316\u529f\u80fd\u7531\u6c47\u6d41\u6765\u4e16\u4fee\u590d");
        methodVisitor.visitMethodInsn(INVOKESTATIC, "java/util/Map", "of", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/util/Map;", true);
        methodVisitor.visitVarInsn(ASTORE, 6);
        Label label22 = new Label();
        methodVisitor.visitLabel(label22);
        methodVisitor.visitLineNumber(37, label22);
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitLdcInsn("fml.loadingerrorscreen.errorheader");
        methodVisitor.visitVarInsn(ALOAD, 5);
        methodVisitor.visitVarInsn(ALOAD, 6);
        methodVisitor.visitVarInsn(ALOAD, 2);
        methodVisitor.visitLdcInsn("The i18n of this screen is fixed by Confluence Otherworld");
        methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "getOrDefault", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", true);
        methodVisitor.visitTypeInsn(CHECKCAST, "java/lang/String");
        methodVisitor.visitInvokeDynamicInsn("makeConcatWithConstants", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", new Handle(Opcodes.H_INVOKESTATIC, "java/lang/invoke/StringConcatFactory", "makeConcatWithConstants", "(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;", false), "\u0001\n\u0001");
        methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/util/Map", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", true);
        methodVisitor.visitInsn(POP);
        methodVisitor.visitLabel(label11);
        methodVisitor.visitLineNumber(41, label11);
        methodVisitor.visitFrame(Opcodes.F_CHOP,3, null, 0, null);
        methodVisitor.visitInsn(RETURN);
        Label label23 = new Label();
        methodVisitor.visitLabel(label23);
        methodVisitor.visitLocalVariable("reader", "Ljava/io/InputStreamReader;", null, label0, label7, 5);
        methodVisitor.visitLocalVariable("author", "Ljava/util/Map;", "Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;", label22, label11, 6);
        methodVisitor.visitLocalVariable("stream", "Ljava/io/InputStream;", null, label17, label11, 4);
        methodVisitor.visitLocalVariable("s", "Ljava/lang/String;", null, label20, label11, 5);
        methodVisitor.visitLocalVariable("selected", "Ljava/lang/String;", null, label13, label11, 2);
        methodVisitor.visitLocalVariable("language", "Lnet/minecraft/client/resources/language/LanguageInfo;", null, label14, label11, 3);
        methodVisitor.visitLocalVariable("modTable", "Ljava/util/Map;", "Ljava/util/Map<Ljava/lang/String;Ljava/lang/String;>;", label9, label23, 0);
        methodVisitor.visitLocalVariable("languageManager", "Lnet/minecraft/client/resources/language/LanguageManager;", null, label10, label23, 1);
        methodVisitor.visitMaxs(6, 8);
        methodVisitor.visitEnd();
    }

    @Override
    public TransformerVoteResult castVote(ITransformerVotingContext context) {
        return TransformerVoteResult.YES;
    }

    @Override
    public Set<Target<ClassNode>> targets() {
        return Set.of(Target.targetClass("net.neoforged.neoforge.server.LanguageHook"));
    }

    @Override
    public TargetType<ClassNode> getTargetType() {
        return TargetType.CLASS;
    }
}
