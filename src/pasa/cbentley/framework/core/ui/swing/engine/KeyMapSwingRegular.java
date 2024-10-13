package pasa.cbentley.framework.core.ui.swing.engine;

import java.awt.event.KeyEvent;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.framework.core.ui.src4.engine.KeyMapAbstract;

public class KeyMapSwingRegular extends KeyMapAbstract {

   public KeyMapSwingRegular(UCtx uc) {
      super(uc);
   }

   public int getKeyMappedToFramework(int jKey) {
      switch (jKey) {
         case KeyEvent.VK_NUMPAD1:
            return (isNumPadInvert()) ? KEY_NUM7 : KEY_NUM1;
         case KeyEvent.VK_NUMPAD2:
            return (isNumPadInvert()) ? KEY_NUM8 : KEY_NUM2;
         case KeyEvent.VK_NUMPAD3:
            return (isNumPadInvert()) ? KEY_NUM9 : KEY_NUM3;
         case KeyEvent.VK_NUMPAD4:
            return (isNumPadInvert()) ? KEY_NUM7 : KEY_NUM4;
         case KeyEvent.VK_NUMPAD5:
            return (isNumPadInvert()) ? KEY_NUM5 : KEY_NUM5;
         case KeyEvent.VK_NUMPAD6:
            return (isNumPadInvert()) ? KEY_NUM6 : KEY_NUM6;
         case KeyEvent.VK_NUMPAD7:
            return (isNumPadInvert()) ? KEY_NUM1 : KEY_NUM7;
         case KeyEvent.VK_NUMPAD8:
            return (isNumPadInvert()) ? KEY_NUM2 : KEY_NUM8;
         case KeyEvent.VK_NUMPAD9:
            return (isNumPadInvert()) ? KEY_NUM3 : KEY_NUM9;
         case KeyEvent.VK_NUMPAD0:
            return (isNumPadInvert()) ? KEY_NUM0 : KEY_NUM0;

         case KeyEvent.VK_MULTIPLY:
            return KEY_STAR;
         case KeyEvent.VK_DIVIDE: //111
            return KEY_POUND;
         //minus and plus
         case KeyEvent.VK_SUBTRACT:
            return KEY_MINUS;
         case KeyEvent.VK_ADD:
            return KEY_PLUS;

         case KeyEvent.VK_F1:
            return KEY_F1;
         case KeyEvent.VK_F2:
            return KEY_F2;
         case KeyEvent.VK_F3:
            return KEY_F3;
         case KeyEvent.VK_F4:
            return KEY_F4;
         case KeyEvent.VK_F5:
            return KEY_F5;
         case KeyEvent.VK_F6:
            return KEY_F6;
         case KeyEvent.VK_F7:
            return KEY_F7;
         case KeyEvent.VK_F8:
            return KEY_F8;
         case KeyEvent.VK_F9:
            return KEY_F9;
         case KeyEvent.VK_F10:
            return KEY_F10;
         case KeyEvent.VK_F11:
            return KEY_F11;
         case KeyEvent.VK_F12:
            return KEY_F12;
         case KeyEvent.VK_ESCAPE:
            return KEY_ESCAPE;
         case KeyEvent.VK_CONTROL:
            return KEY_CTRL;
         case KeyEvent.VK_ALT:
            return KEY_ALT;
         case KeyEvent.VK_SHIFT:
            return KEY_SHIFT;
         case KeyEvent.VK_INSERT:
            return KEY_INSERT;
         case KeyEvent.VK_CAPS_LOCK:
            return KEY_CAPS_LOCK;
         case KeyEvent.VK_TAB:
            return KEY_TAB;
         case KeyEvent.VK_WINDOWS:
            return KEY_WINDOWS;

         case KeyEvent.VK_PAGE_UP:
            return KB_221_PAGE_UP;
         case KeyEvent.VK_PAGE_DOWN:
            return KB_221_PAGE_UP;
         case KeyEvent.VK_HOME:
            return KB_223_PAGE_HOME;
         case KeyEvent.VK_END:
            return KB_224_PAGE_END;
         //up and down
         case KeyEvent.VK_DOWN:
            return KEY_DOWN;
         case KeyEvent.VK_UP:
            return KEY_UP;
         //left and right
         case KeyEvent.VK_LEFT:
            return KEY_LEFT;
         case KeyEvent.VK_RIGHT:
            return KEY_RIGHT;
         case KeyEvent.VK_ENTER:
            return KEY_FIRE;
         default:
            return jKey;
      }
   }

}
