package pasa.cbentley.framework.coreui.swing.engine;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.framework.coreui.src4.engine.KeyMapAbstract;
import pasa.cbentley.framework.coreui.src4.tech.IBCodes;

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
            return IBCodes.KEY_DOWN;
         case KeyEvent.VK_UP:
            return IBCodes.KEY_UP;
         //left and right
         case KeyEvent.VK_LEFT:
            return IBCodes.KEY_LEFT;
         case KeyEvent.VK_RIGHT:
            return IBCodes.KEY_RIGHT;
         //
         case KeyEvent.VK_END:
            return IBCodes.KEY_CANCEL;
         //backspace to delete a character
         case KeyEvent.VK_BACK_SPACE:
            return IBCodes.KEY_DELETE_CHAR;
         //home insert
         case KeyEvent.VK_HOME:
            return IBCodes.KEY_BACK;
         //enter
         case KeyEvent.VK_ENTER:
            return IBCodes.KEY_FIRE;
         case KeyEvent.VK_DECIMAL:
            return IBCodes.KEY_PHOTO;
         //numpads
         case KeyEvent.VK_NUMPAD1:
            return (isNumPadInvert()) ? IBCodes.KEY_NUM7 : IBCodes.KEY_NUM1;
         case KeyEvent.VK_NUMPAD2:
            return (isNumPadInvert()) ? IBCodes.KEY_NUM8 : IBCodes.KEY_NUM2;
         case KeyEvent.VK_NUMPAD3:
            return (isNumPadInvert()) ? IBCodes.KEY_NUM9 : IBCodes.KEY_NUM3;
         case KeyEvent.VK_NUMPAD4:
            return (isNumPadInvert()) ? IBCodes.KEY_NUM7 : IBCodes.KEY_NUM4;
         case KeyEvent.VK_NUMPAD5:
            return (isNumPadInvert()) ? IBCodes.KEY_NUM5 : IBCodes.KEY_NUM5;
         case KeyEvent.VK_NUMPAD6:
            return (isNumPadInvert()) ? IBCodes.KEY_NUM6 : IBCodes.KEY_NUM6;
         case KeyEvent.VK_NUMPAD7:
            return (isNumPadInvert()) ? IBCodes.KEY_NUM1 : IBCodes.KEY_NUM7;
         case KeyEvent.VK_NUMPAD8:
            return (isNumPadInvert()) ? IBCodes.KEY_NUM2 : IBCodes.KEY_NUM8;
         case KeyEvent.VK_NUMPAD9:
            return (isNumPadInvert()) ? IBCodes.KEY_NUM3 : IBCodes.KEY_NUM9;
         case KeyEvent.VK_NUMPAD0:
            return (isNumPadInvert()) ? IBCodes.KEY_NUM0 : IBCodes.KEY_NUM0;
         case KeyEvent.VK_MULTIPLY:
            return IBCodes.KEY_STAR;
         case KeyEvent.VK_DIVIDE: //111
            return IBCodes.KEY_POUND;
         //minus and plus
         case KeyEvent.VK_SUBTRACT:
            return IBCodes.KEY_MINUS;
         case KeyEvent.VK_ADD:
            return IBCodes.KEY_PLUS;
         //menu left and right
         case KeyEvent.VK_PAGE_UP:
            return IBCodes.KEY_MENU_LEFT;
         case KeyEvent.VK_PAGE_DOWN:
            return IBCodes.KEY_MENU_RIGHT;
         case KeyEvent.VK_F1:
            return IBCodes.KEY_F1;
         case KeyEvent.VK_F2:
            return IBCodes.KEY_F2;
         case KeyEvent.VK_F3:
            return IBCodes.KEY_F3;
         case KeyEvent.VK_F4:
            return IBCodes.KEY_F4;
         case KeyEvent.VK_F5:
            return IBCodes.KEY_F5;
         case KeyEvent.VK_F6:
            return IBCodes.KEY_F6;
         case KeyEvent.VK_F7:
            return IBCodes.KEY_F7;
         case KeyEvent.VK_F8:
            return IBCodes.KEY_F8;
         case KeyEvent.VK_F9:
            return IBCodes.KEY_F9;
         case KeyEvent.VK_F10:
            return IBCodes.KEY_F10;
         case KeyEvent.VK_F11:
            return IBCodes.KEY_F11;
         case KeyEvent.VK_F12:
            return IBCodes.KEY_F12;
         case KeyEvent.VK_ESCAPE:
            return IBCodes.KEY_ESCAPE;
         case KeyEvent.VK_CONTROL:
            return IBCodes.KEY_CTRL;
         case KeyEvent.VK_ALT:
            return IBCodes.KEY_ALT;
         case KeyEvent.VK_SHIFT:
            return IBCodes.KEY_SHIFT;
         case KeyEvent.VK_INSERT:
            return IBCodes.KEY_INSERT;
         case KeyEvent.VK_CAPS_LOCK:
            return IBCodes.KEY_CAPS_LOCK;
         case KeyEvent.VK_TAB:
            return IBCodes.KEY_TAB;
         case KeyEvent.VK_WINDOWS:
            return IBCodes.KEY_WINDOWS;
         default:
            return jKey;
      }
   }

}
