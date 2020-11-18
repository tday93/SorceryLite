package com.sorcery.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.Locale;

public class RGBAParticleData implements IParticleData
{
    public final float r;
    public final float g;
    public final float b;
    public final float a;
    public final int t;
    public final boolean q;
    public final boolean c;
    public final float m;
    public final float d;
    public final boolean l;
    public ParticleType<RGBAParticleData> particleType;

    public RGBAParticleData()
    {
        this.r = 1;
        this.g = 1;
        this.b = 1;
        this.a = 1;
        this.t = 40;
        this.q = true;
        this.c = true;
        this.m = 0;
        this.d = 1;
        this.l = false;
        this.particleType = ModParticle.RGBA_SPARK;

    }

    public RGBAParticleData(float r, float g, float b, float a)
    {

        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.t = 40;
        this.q = true;
        this.c = true;
        this.m = 0;
        this.d = 1;
        this.l = false;
        this.particleType = ModParticle.RGBA_SPARK;
    }

    public RGBAParticleData(float r, float g, float b, float a, int t)
    {

        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.t = t;
        this.q = true;
        this.c = true;
        this.m = 0;
        this.d = 1;
        this.l = false;
        this.particleType = ModParticle.RGBA_SPARK;
    }

    public RGBAParticleData(float r, float g, float b, float a, int t, boolean q)
    {

        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.t = t;
        this.q = q;
        this.c = true;
        this.m = 0;
        this.d = 1;
        this.l = false;
        this.particleType = ModParticle.RGBA_SPARK;
    }

    public RGBAParticleData(ParticleType<RGBAParticleData> particleType, float r, float g, float b, float a)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.t = 40;
        this.q = true;
        this.c = true;
        this.m = 0;
        this.d = 1;
        this.l = false;
        this.particleType = particleType;
    }

    public RGBAParticleData(ParticleType<RGBAParticleData> particleType, float r, float g, float b, float a, int t)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.t = t;
        this.q = true;
        this.c = true;
        this.m = 0;
        this.d = 1;
        this.l = false;
        this.particleType = particleType;
    }

    public RGBAParticleData(ParticleType<RGBAParticleData> particleType, float r, float g, float b, float a, int t, boolean q)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.t = t;
        this.q = q;
        this.c = true;
        this.m = 0;
        this.d = 1;
        this.l = false;
        this.particleType = particleType;
    }

    public RGBAParticleData(ParticleType<RGBAParticleData> particleType, float r, float g, float b, float a, int t, boolean q, boolean c)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.t = t;
        this.q = q;
        this.c = c;
        this.m = 0;
        this.d = 1;
        this.l = false;
        this.particleType = particleType;
    }

    public RGBAParticleData(ParticleType<RGBAParticleData> particleType, float r, float g, float b, float a, int t, boolean q, boolean c, float m)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.t = t;
        this.q = q;
        this.c = c;
        this.m = m;
        this.d = 1;
        this.l = false;
        this.particleType = particleType;
    }

    public RGBAParticleData(ParticleType<RGBAParticleData> particleType, float r, float g, float b, float a, int t, boolean q, boolean c, float m, float d)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.t = t;
        this.q = q;
        this.c = c;
        this.m = m;
        this.d = d;
        this.l = false;
        this.particleType = particleType;
    }

    public RGBAParticleData(ParticleType<RGBAParticleData> particleType, float r, float g, float b, float a, int t, boolean q, boolean c, float m, float d, boolean l)
    {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
        this.t = t;
        this.q = q;
        this.c = c;
        this.m = m;
        this.d = d;
        this.l = l;
        this.particleType = particleType;
    }

    @Override
    public ParticleType<RGBAParticleData> getType()
    {
        return this.particleType;
    }

    @Override
    public void write(PacketBuffer buf)
    {
        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);
        buf.writeFloat(a);
        buf.writeInt(t);
        buf.writeBoolean(q);
        buf.writeBoolean(c);
        buf.writeFloat(m);
        buf.writeFloat(d);
        buf.writeBoolean(l);

    }

    @Override
    public String getParameters()
    {
        String params = String.format(Locale.ROOT, "%s %f %f %f %f %d %b %b %f %f %b", this.getType().getRegistryName(), this.r, this.g, this.b, this.a, this.t, this.q, this.c, this.m, this.d, this.l);
        return params;
    }

    public static final IDeserializer<RGBAParticleData> DESERIALIZER = new IDeserializer<RGBAParticleData>() {
        @Nonnull
        @Override
        public RGBAParticleData deserialize(@Nonnull ParticleType<RGBAParticleData> type, @Nonnull StringReader reader) throws CommandSyntaxException
        {
            String regName = reader.readStringUntil(' ');
            IForgeRegistryEntry regParticle = GameRegistry.findRegistry(ParticleType.class).getValue(new ResourceLocation(regName));
            float r = reader.readFloat();
            reader.expect(' ');
            float g = reader.readFloat();
            reader.expect(' ');
            float b = reader.readFloat();
            reader.expect(' ');
            float a = reader.readFloat();
            reader.expect(' ');
            int t = reader.readInt();
            reader.expect(' ');
            boolean q = reader.readBoolean();
            reader.expect(' ');
            boolean c = reader.readBoolean();
            reader.expect(' ');
            float m = reader.readFloat();
            reader.expect(' ');
            float d = reader.readFloat();
            reader.expect(' ');
            boolean l = reader.readBoolean();

            return new RGBAParticleData((ParticleType)regParticle, r, g, b, a, t, q, c, m, d, l);
        }

        @Override
        public RGBAParticleData read(@Nonnull ParticleType<RGBAParticleData> type, PacketBuffer buf) {
            return new RGBAParticleData(type, buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readBoolean(), buf.readBoolean(), buf.readFloat(), buf.readFloat(), buf.readBoolean());
        }
    };
}
