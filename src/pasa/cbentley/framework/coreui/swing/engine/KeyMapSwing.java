package pasa.cbentley.framework.coreui.swing.engine;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.framework.coreui.src4.engine.KeyMapAbstract;
import pasa.cbentley.framework.coreui.src4.tech.ITechCodes;

public class KeyMapSwing extends KeyMapAbstract {

   public KeyMapSwing(UCtx uc) {
      super(uc);
   }

   public boolean isMajOn() {
      return Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
   }

   public void setMajOn(boolean b) {
      Toolkit.getDefaultToolkit().setLockingKeyState(KeyEvent.VK_CAPS_LOCK, b);
   }

   public int getKeyMappedToFramework(int jKey) {
      switch (jKey) {
         //up and down
         case KeyEvent.VK_DOWN:
            return ITechCodes.KEY_DOWN;
         case KeyEvent.VK_UP:
            return ITechCodes.KEY_UP;
         //left and right
         case KeyEvent.VK_LEFT:
            return ITechCodes.KEY_LEFT;
         case KeyEvent.VK_RIGHT:
            return ITechCodes.KEY_RIGHT;
         //
         case KeyEvent.VK_END:
            return ITechCodes.KEY_CANCEL;
         //backspace to delete a character
         case KeyEvent.VK_BACK_SPACE:
            return ITechCodes.KEY_DELETE_CHAR;
         //home insert
         case KeyEvent.VK_HOME:
            return ITechCodes.KEY_BACK;
         //enter
         case KeyEvent.VK_ENTER:
            return ITechCodes.KEY_FIRE;
         case KeyEvent.VK_DECIMAL:
            return ITechCodes.KEY_PHOTO;
         //numpads
         case KeyEvent.VK_NUMPAD1:
            return (isNumPadInvert()) ? ITechCodes.KEY_NUM7 : ITechCodes.KEY_NUM1;
         case KeyEvent.VK_NUMPAD2:
            return (isNumPadInvert()) ? ITechCodes.KEY_NUM8 : ITechCodes.KEY_NUM2;
         case KeyEvent.VK_NUMPAD3:
            return (isNumPadInvert()) ? ITechCodes.KEY_NUM9 : ITechCodes.KEY_NUM3;
         case KeyEvent.VK_NUMPAD4:
            return (isNumPadInvert()) ? ITechCodes.KEY_NUM7 : ITechCodes.KEY_NUM4;
         case KeyEvent.VK_NUMPAD5:
            return (isNumPadInvert()) ? ITechCodes.KEY_NUM5 : ITechCodes.KEY_NUM5;
         case KeyEvent.VK_NUMPAD6:
            return (isNumPadInvert()) ? ITechCodes.KEY_NUM6 : ITechCodes.KEY_NUM6;
         case KeyEvent.VK_NUMPAD7:
            return (isNumPadInvert()) ? ITechCodes.KEY_NUM1 : ITechCodes.KEY_NUM7;
         case KeyEvent.VK_NUMPAD8:
            return (isNumPadInvert()) ? ITechCodes.KEY_NUM2 : ITechCodes.KEY_NUM8;
         case KeyEvent.VK_NUMPAD9:
            return (isNumPadInvert()) ? ITechCodes.KEY_NUM3 : ITechCodes.KEY_NUM9;
         case KeyEvent.VK_NUMPAD0:
            return (isNumPadInvert()) ? ITechCodes.KEY_NUM0 : ITechCodes.KEY_NUM0;
         case KeyEvent.VK_MULTIPLY:
            return ITechCodes.KEY_STAR;
         case KeyEvent.VK_DIVIDE: //111
            return ITechCodes.KEY_POUND;
         //minus and plus
         case KeyEvent.VK_SUBTRACT:
            return ITechCodes.KEY_MINUS;
         case KeyEvent.VK_ADD:
            return ITechCodes.KEY_PLUS;
         //menu left and right
         case KeyEvent.VK_PAGE_UP:
            return ITechCodes.KEY_MENU_LEFT;
         case KeyEvent.VK_PAGE_DOWN:
            return ITechCodes.KEY_MENU_RIGHT;
         case KeyEvent.VK_F1:
            return ITechCodes.KEY_F1;
         case KeyEvent.VK_F2:
            return ITechCodes.KEY_F2;
         case KeyEvent.VK_F3:
            return ITechCodes.KEY_F3;
         case KeyEvent.VK_F4:
            return ITechCodes.KEY_F4;
         case KeyEvent.VK_F5:
            return ITechCodes.KEY_F5;
         case KeyEvent.VK_F6:
            return ITechCodes.KEY_F6;
         case KeyEvent.VK_F7:
            return ITechCodes.KEY_F7;
         case KeyEvent.VK_F8:
            return ITechCodes.KEY_F8;
         case KeyEvent.VK_F9:
            return ITechCodes.KEY_F9;
         case KeyEvent.VK_F10:
            return ITechCodes.KEY_F10;
         case KeyEvent.VK_F11:
            return ITechCodes.KEY_F11;
         case KeyEvent.VK_F12:
            return ITechCodes.KEY_F12;
         case KeyEvent.VK_ESCAPE:
            return ITechCodes.KEY_ESCAPE;
         case KeyEvent.VK_CONTROL:
            return ITechCodes.KEY_CTRL;
         case KeyEvent.VK_ALT:
            return ITechCodes.KEY_ALT;
         case KeyEvent.VK_SHIFT:
            return ITechCodes.KEY_SHIFT;
         case KeyEvent.VK_INSERT:
            return ITechCodes.KEY_INSERT;
         case KeyEvent.VK_CAPS_LOCK:
            return ITechCodes.KEY_CAPS_LOCK;
         case KeyEvent.VK_TAB:
            return ITechCodes.KEY_TAB;
         case KeyEvent.VK_WINDOWS:
            return ITechCodes.KEY_WINDOWS;
         default:
            return jKey;
      }
   }

}
