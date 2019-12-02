package pasa.cbentley.framework.coreui.swing.engine;

import java.awt.FontMetrics;
import java.awt.Toolkit;

import pasa.cbentley.framework.coreui.j2se.engine.FontJ2SE;
import pasa.cbentley.framework.coreui.swing.ctx.CoreUiSwingCtx;

/**
 * J2SE bridge class for the {@link mordan.bridge.swing.ui.FontSwing} class of MIDP 2.0 <br>
 * 
 * Q:how to provide antialiased font drawing in this bridge?
 * A: Anti-aliasing is done at the Graphics level with Hints.
 * 
 * @author Charles-Philip Bentley
 *
 */
public class FontSwing extends FontJ2SE {

   /** 
    * Reference to an AWT Font object, created based on the MIDP font properties requested
    */
   private java.awt.Font font;

   /** 
    * FontMetrics object does most of the font width and height stuff for us
    */
   private FontMetrics   fontMetrics;

   /**
    * Constructor.  Take the MIDP font parameters, and determine an appropriate AWT font to use.
    * <br>
    * <br>
    * Font face is divided into 2 big families
    * <li> monospaced
    * <li> not monospaced
    * <br>
    * <br>
    * How do you built the system for hot loading of all fonts with a new face and updated base size?
    * <br>
    * This here is Swing (platform specific). The user action must invalidate all layouts. 
    * All StringDrawable Stringer are accessed and font resetted.
    * <br>
    * On a system that supports font size. negative point size are correctly mapped. on J2ME such sizes
    * must be mapped to small/medium/large.
    * <br>
    * <br>
    * 
    */
   public FontSwing(CoreUiSwingCtx scc, int face, int style, int size) {
      super(scc, face, style, size);
      int awt_style = java.awt.Font.PLAIN;

      switch (style) {
         case STYLE_BOLD:
            awt_style = java.awt.Font.BOLD;
            break;
         case STYLE_ITALIC:
            awt_style = java.awt.Font.ITALIC;
            break;
         // Doesn't seem to be any underlined font support in plain old AWT, never mind.
      }

      this.font = new java.awt.Font(font_name, awt_style, points);
      this.fontMetrics = Toolkit.getDefaultToolkit().getFontMetrics(this.font);
   }
   
   public int getAscent() {
      return fontMetrics.getAscent();
   }
   
   public int getDescent() {
      return fontMetrics.getDescent();
   }
   
   public java.awt.Font getFontAWT() {
      return font;
   }

   public int charWidth(char c) {
      return fontMetrics.charWidth(c);
   }

   public int charsWidth(char[] c, int ofs, int len) {
      return fontMetrics.charsWidth(c, ofs, len);
   }

   public int getBaselinePosition() {
      return fontMetrics.getAscent();
   }

   public int getHeight() {
      return fontMetrics.getHeight();
   }

   public int stringWidth(String s) {
      return fontMetrics.stringWidth(s);
   }

   public int substringWidth(String s, int offset, int length) {
      return fontMetrics.stringWidth(s.substring(offset, offset + length));
   }

   public int getFace() {
      return face;
   }

   public int getSize() {
      return size;
   }

   public int getStyle() {
      return style;
   }

   public boolean isSupported(int flag) {
      return true;
   }

   public boolean isBold() {
      return (style & STYLE_BOLD) != 0;
   }

   public boolean isUnderlined() {
      return (style & STYLE_UNDERLINED) != 0;
   }

   public boolean isItalic() {
      return (style & STYLE_ITALIC) != 0;
   }

   public boolean isPLAIN() {
      return style == 0;
   }

}
