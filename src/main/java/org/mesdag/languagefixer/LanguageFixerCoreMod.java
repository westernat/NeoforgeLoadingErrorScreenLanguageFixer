package org.mesdag.languagefixer;

import cpw.mods.modlauncher.api.ITransformer;
import net.neoforged.neoforgespi.coremod.ICoreMod;

import java.util.List;

public class LanguageFixerCoreMod implements ICoreMod {
    @Override
    public Iterable<? extends ITransformer<?>> getTransformers() {
        return List.of(new InsertFixerTransformer());
    }
}
