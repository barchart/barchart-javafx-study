/*    */ package javafx.embed.swt;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import org.eclipse.swt.dnd.ByteArrayTransfer;
/*    */ import org.eclipse.swt.dnd.DND;
/*    */ import org.eclipse.swt.dnd.TransferData;
/*    */ 
/*    */ public class CustomTransfer extends ByteArrayTransfer
/*    */ {
/*    */   private String name;
/*    */   private String mime;
/*    */ 
/*    */   public CustomTransfer(String paramString1, String paramString2)
/*    */   {
/* 38 */     this.name = paramString1;
/* 39 */     this.mime = paramString2;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 43 */     return this.name;
/*    */   }
/*    */ 
/*    */   public String getMime() {
/* 47 */     return this.mime;
/*    */   }
/*    */ 
/*    */   public void javaToNative(Object paramObject, TransferData paramTransferData) {
/* 51 */     if ((!checkCustom(paramObject)) || (!isSupportedType(paramTransferData))) {
/* 52 */       DND.error(2003);
/*    */     }
/* 54 */     byte[] arrayOfByte = null;
/* 55 */     if ((paramObject instanceof ByteBuffer)) {
/* 56 */       arrayOfByte = ((ByteBuffer)paramObject).array();
/*    */     }
/* 58 */     else if ((paramObject instanceof byte[])) arrayOfByte = (byte[])paramObject;
/*    */ 
/* 60 */     if (arrayOfByte == null) DND.error(2003);
/* 61 */     super.javaToNative(arrayOfByte, paramTransferData);
/*    */   }
/*    */ 
/*    */   public Object nativeToJava(TransferData paramTransferData) {
/* 65 */     if (isSupportedType(paramTransferData)) {
/* 66 */       return super.nativeToJava(paramTransferData);
/*    */     }
/* 68 */     return null;
/*    */   }
/*    */ 
/*    */   protected String[] getTypeNames() {
/* 72 */     return new String[] { this.name };
/*    */   }
/*    */ 
/*    */   protected int[] getTypeIds() {
/* 76 */     return new int[] { registerType(this.name) };
/*    */   }
/*    */ 
/*    */   boolean checkByteArray(Object paramObject) {
/* 80 */     return (paramObject != null) && ((paramObject instanceof byte[])) && (((byte[])paramObject).length > 0);
/*    */   }
/*    */ 
/*    */   boolean checkByteBuffer(Object paramObject) {
/* 84 */     return (paramObject != null) && ((paramObject instanceof ByteBuffer)) && (((ByteBuffer)paramObject).limit() > 0);
/*    */   }
/*    */ 
/*    */   boolean checkCustom(Object paramObject) {
/* 88 */     return (checkByteArray(paramObject)) || (checkByteBuffer(paramObject));
/*    */   }
/*    */ 
/*    */   protected boolean validate(Object paramObject) {
/* 92 */     return checkCustom(paramObject);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.embed.swt.CustomTransfer
 * JD-Core Version:    0.6.2
 */