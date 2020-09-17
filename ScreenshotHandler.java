
import java.awt.image.BufferedImage;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.*;
import net.minecraft.client.shader.*;
import net.minecraft.client.renderer.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.*;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.text.*;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;

public class ScreenshotHandler {
	private static IntBuffer pixelBuffer;
	private static int[] pixelData;

	public static BufferedImage takeScreenshot(int width, int height, final Framebuffer framebuffer) {
		if (OpenGlHelper.isFramebufferEnabled()) {
			width = framebuffer.framebufferWidth;
			height = framebuffer.framebufferHeight;
		}
		final int pixels = width * height;
		if (ScreenshotHandler.pixelBuffer == null || ScreenshotHandler.pixelBuffer.capacity() < pixels) {
			ScreenshotHandler.pixelBuffer = BufferUtils.createIntBuffer(pixels);
			ScreenshotHandler.pixelData = new int[pixels];
		}
		GL11.glPixelStorei(3333, 1);
		GL11.glPixelStorei(3317, 1);
		ScreenshotHandler.pixelBuffer.clear();
		if (OpenGlHelper.isFramebufferEnabled()) {
			GL11.glBindTexture(3553, framebuffer.framebufferTexture);
			GL11.glGetTexImage(3553, 0, 32993, 33639, ScreenshotHandler.pixelBuffer);
		} else {
			GL11.glReadPixels(0, 0, width, height, 32993, 33639, ScreenshotHandler.pixelBuffer);
		}
		ScreenshotHandler.pixelBuffer.get(ScreenshotHandler.pixelData);
		TextureUtil.processPixelValues(ScreenshotHandler.pixelData, width, height);
		final BufferedImage bufferedimage = new BufferedImage(width, height, 1);
		bufferedimage.setRGB(0, 0, width, height, ScreenshotHandler.pixelData, 0, width);
		return bufferedimage;
	}

	public static void processScreenshot() {
		final Minecraft mc = Minecraft.getMinecraft();
		final Thread t = new Thread(new ScreenshotRunnable("imgur",
				takeScreenshot(mc.displayWidth, mc.displayHeight, mc.getFramebuffer())));
		t.start();
	}
}
