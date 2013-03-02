/*    */ package javafx.embed.swt;
/*    */ 
/*    */ import com.sun.javafx.cursor.CursorFrame;
/*    */ import com.sun.javafx.cursor.ImageCursorFrame;
/*    */ import org.eclipse.swt.graphics.Cursor;
/*    */ import org.eclipse.swt.widgets.Display;
/*    */ 
/*    */ class SWTCursors
/*    */ {
/*    */   private static Cursor createCustomCursor(ImageCursorFrame paramImageCursorFrame)
/*    */   {
/* 64 */     return null;
/*    */   }
/*    */ 
/*    */   static Cursor embedCursorToCursor(CursorFrame paramCursorFrame) {
/* 68 */     int i = 0;
/* 69 */     switch (1.$SwitchMap$com$sun$javafx$cursor$CursorType[paramCursorFrame.getCursorType().ordinal()]) { case 1:
/* 70 */       i = 0; break;
/*    */     case 2:
/* 71 */       i = 2; break;
/*    */     case 3:
/* 72 */       i = 19; break;
/*    */     case 4:
/* 73 */       i = 1; break;
/*    */     case 5:
/* 74 */       i = 16; break;
/*    */     case 6:
/* 75 */       i = 15; break;
/*    */     case 7:
/* 76 */       i = 17; break;
/*    */     case 8:
/* 77 */       i = 14; break;
/*    */     case 9:
/* 78 */       i = 10; break;
/*    */     case 10:
/* 79 */       i = 11; break;
/*    */     case 11:
/* 80 */       i = 13; break;
/*    */     case 12:
/* 81 */       i = 12; break;
/*    */     case 13:
/*    */     case 14:
/*    */     case 15:
/* 84 */       i = 21; break;
/*    */     case 16:
/* 85 */       i = 5; break;
/*    */     case 17:
/* 88 */       break;
/*    */     case 18:
/* 89 */       i = 9; break;
/*    */     case 19:
/* 90 */       i = 7; break;
/*    */     case 20:
/* 92 */       return null;
/*    */     case 21:
/*    */     }
/*    */ 
/* 97 */     Display localDisplay = Display.getCurrent();
/* 98 */     return localDisplay != null ? localDisplay.getSystemCursor(i) : null;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.embed.swt.SWTCursors
 * JD-Core Version:    0.6.2
 */