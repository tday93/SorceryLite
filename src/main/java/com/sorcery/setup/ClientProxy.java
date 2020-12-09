package com.sorcery.setup;

import com.sorcery.block.ModBlock;
import com.sorcery.entity.ModEntity;
import com.sorcery.entity.SpellCarrierEntity;
import com.sorcery.keybinding.KeyBindings;
import com.sorcery.keybinding.KeyInputHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy implements IProxy
{

    @Override
    public void init()
    {
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        KeyBindings.init();

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        RenderingRegistry.registerEntityRenderingHandler(ModEntity.SPELL_PROJECTILE, (manager) -> new SpriteRenderer<>(manager, itemRenderer));
        RenderingRegistry.registerEntityRenderingHandler(ModEntity.FIREBOLT, (manager) -> new SpriteRenderer<>(manager, itemRenderer));
        RenderingRegistry.registerEntityRenderingHandler(ModEntity.MAGIC_MISSILE, (manager) -> new SpriteRenderer<>(manager, itemRenderer));
        RenderingRegistry.registerEntityRenderingHandler(ModEntity.SPELL_CARRIER, EmptyRenderer::new);
        RenderTypeLookup.setRenderLayer(ModBlock.RUNEWOOD_SAPLING.get(), RenderType.getCutout());
    }

    private static class EmptyRenderer extends EntityRenderer<SpellCarrierEntity>
    {
        protected EmptyRenderer(EntityRendererManager renderManager)
        {
            super(renderManager);
        }

        @Override
        public boolean shouldRender(SpellCarrierEntity entity, ClippingHelper camera, double camX, double camY, double camZ)
        {
            return false;
        }

        @Override
        public ResourceLocation getEntityTexture(SpellCarrierEntity entity)
        {
            return null;
        }
    }
    @Override
    public World getClientWorld()
    {
        return Minecraft.getInstance().world;
    }

    @Override
    public PlayerEntity getClientPlayer()
    {
        return Minecraft.getInstance().player;
    }
}
