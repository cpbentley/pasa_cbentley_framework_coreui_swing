package pasa.cbentley.framework.coreui.swing.engine;

import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.InputStream;

import javax.imageio.ImageIO;

import pasa.cbentley.framework.coreui.j2se.engine.ImageJ2SE;
import pasa.cbentley.framework.coreui.src4.interfaces.IGraphics;
import pasa.cbentley.framework.coreui.swing.ctx.CoreUiSwingCtx;

/**
 * J2SE bridge class for the {@link mordan.bridge.swing.ui.ImageSwing} class of MIDP 2.0.
 * <br>
 * <br>
 * @author Mordan
 *
 */
public class ImageSwing extends ImageJ2SE {

   /** 
    * Each image object has an associated Graphics object. 
    */
   public GraphicsSwing graphics;

   /** 
    * Reference to an AWT Image.  Most likely to be a BufferedImage instance.
    * <br>
    * This field is never null once the constructor finishes. 
    */
   public BufferedImage image;

   /** 
    * Image mutability flag
    */
   private boolean      isMutable = false;

   /**
    * Creates relevant {@link IGraphics} object.
    * @param bi
    * @param scc
    */
   public ImageSwing(CoreUiSwingCtx scc, BufferedImage bi) {
      super(scc);
      this.image = bi;
      this.isMutable = true;
      graphics = new GraphicsSwing(scc, image.createGraphics());
      graphics.setClip(0, 0, bi.getWidth(), bi.getHeight());
   }

   /**
    * Constructor for creating an Image from an InputStream of image data.  Currently only supports PNG data, using
    * the sixlegs.com library.
    * <br>
    * <br>
    * Creates relevant {@link IGraphics} object.
    */
   public ImageSwing(CoreUiSwingCtx scc, InputStream is) {
      super(scc);
      try {
         this.image = ImageIO.read(is);
      } catch (Exception ex) {
         //#debug
         toLog().pEx("Exception loading image from InputStream.", null, ImageSwing.class, "", ex);
         ex.printStackTrace();
      }
      this.isMutable = false;
      graphics = new GraphicsSwing(scc, image.createGraphics());
   }

   /**
    * Constructor for creating a new Image with a given width and height.
    * <br>
    * Background is filled with opaque white pixels.
    * <br>
    * <br>
    * Creates relevant {@link IGraphics} object.
    * @param w
    * @param h
    */
   public ImageSwing(CoreUiSwingCtx scc, int w, int h) {
      super(scc);
      this.image = new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_ARGB);
      this.isMutable = true;
      graphics = new GraphicsSwing(scc, image.createGraphics());
      //since in J2ME a blank mutable image is filled with opaque white pixels by default
      //our framework does the same for this historical reason
      graphics.setColor(-1);
      graphics.fillRect(0, 0, w, h);
      graphics.setClip(0, 0, w, h);
   }

   /**
    * Constructor for creating a new Image with a given width and height.
    * <br>
    * Creates relevant {@link IGraphics} object.
    * @param w
    * @param h
    * @param scc
    * @param color
    */
   public ImageSwing(CoreUiSwingCtx scc, int w, int h, int color) {
      this(scc, w, h, color, 0);
   }

   /**
    * 
    * {@link IGraphics#IMPL_FLAG_3_TRANS_BACKGROUND}
    * is supported anyways and will be.
    * 
    * Creates relevant {@link IGraphics} object.
    * @param w
    * @param h
    * @param scc
    * @param color
    * @param renderingFlags
    */
   public ImageSwing(CoreUiSwingCtx scc, int w, int h, int color, int renderingFlags) {
      super(scc);
      this.image = new java.awt.image.BufferedImage(w, h, java.awt.image.BufferedImage.TYPE_INT_ARGB);
      this.isMutable = true;
      graphics = new GraphicsSwing(scc, image.createGraphics(), renderingFlags);
      //since in J2ME a blank mutable image is filled with opaque white pixels by default
      int alpha = (color >> 24) & 0xFF;
      if (alpha == 0) {
         //fill graphics with clear pixels
         graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
         graphics.fillRect(0, 0, w, h);
         //reset composite
         graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
      } else {
         graphics.setColor(color);
         graphics.fillRect(0, 0, w, h);
      }
      graphics.setClip(0, 0, w, h);
   }

   /**
    * Returns the {@link IGraphics} object
    */
   public GraphicsSwing getGraphics() {
      if (!isMutable)
         throw new IllegalStateException();
      return graphics;
   }

   public int getHeight() {
      return image.getHeight(null);
   }

   public void getRGB(int[] rgbData, int offset, int scanlength, int x, int y, int width, int height) {

      if (width <= 0 || height <= 0)
         return;
      if (x < 0 || y < 0 || x + width > getWidth() || y + height > getHeight())
         throw new IllegalArgumentException("Specified area exceeds bounds of image");
      if ((scanlength < 0 ? -scanlength : scanlength) < width)
         throw new IllegalArgumentException("abs value of scanlength is less than width");
      if (rgbData == null)
         throw new NullPointerException("null rgbData");
      if (offset < 0 || offset + width > rgbData.length)
         throw new ArrayIndexOutOfBoundsException();
      if (scanlength < 0) {
         if (offset + scanlength * (height - 1) < 0)
            throw new ArrayIndexOutOfBoundsException();
      } else {
         if (offset + scanlength * (height - 1) + width > rgbData.length)
            throw new ArrayIndexOutOfBoundsException();
      }

      try {
         (new PixelGrabber(image, x, y, width, height, rgbData, offset, scanlength)).grabPixels();
      } catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   public int getWidth() {
      return image.getWidth(null);
   }

   public boolean isMutable() {
      return isMutable;
   }

   public void setMutable(boolean b) {
      isMutable = b;
   }
}
