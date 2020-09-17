package exodusclient.screenshotuploader;

import java.util.*;
import java.awt.image.*;

public abstract class ImageHost
{
    public final String hostName;
    public static final HashMap<String, ImageHost> imageHosts;
    public static ImageHost currentSelectedHost;
    
    public ImageHost(final String name) {
        if (ImageHost.imageHosts.containsKey(name)) {
            throw new IllegalArgumentException("Image host " + name + " is already taken!");
        }
        this.hostName = name;
        ImageHost.imageHosts.put(name, this);
    }
    
	public abstract boolean upload(final BufferedImage p0, final UPLOAD_METHOD p1, final String... p2);
    
    public abstract boolean deleteLast();
    
    public abstract String getLink();
    
    public boolean canUploadAnon() {
        return false;
    }
    
    public boolean canUploadAccount() {
        return false;
    }
    
    static {
        imageHosts = new HashMap<String, ImageHost>();
    }
    
    public enum UPLOAD_METHOD
    {
        ANON, 
        ACCOUNT, 
        CUSTOM;
    }
}
