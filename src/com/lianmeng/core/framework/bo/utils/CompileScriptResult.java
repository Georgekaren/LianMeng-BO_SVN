/*    */ package com.lianmeng.core.framework.bo.utils;
/*    */ 
/*    */ public class CompileScriptResult
/*    */ {
/*    */   public static final int PYTHON_TYPE = 1;
/*    */   public static final int JYTHON_TYPE = 2;
/*    */   public static final int UNKNOWN_TYPE = 0;
/*    */   public static final int SUCC_COMPILE = 1;
/*    */   public static final int FAILURE_COMPILE = 0;
/*    */   public static final int NON_COMPILE = -1;
/*    */   public static final int UNKNOWN_COMPILE_ERROR = -2;
/*    */   private int cResult;
/*    */   private String eInfo;
/*    */   private int scriptType;
/*    */ 
/*    */   public CompileScriptResult()
/*    */   {
/* 33 */     this.cResult = -1;
/*    */ 
/* 36 */     this.eInfo = "";
/*    */ 
/* 39 */     this.scriptType = 0; }
/*    */ 
/*    */   public int getScriptType() {
/* 42 */     return this.scriptType;
/*    */   }
/*    */ 
/*    */   public void setScriptType(int scriptType) {
/* 46 */     this.scriptType = scriptType;
/*    */   }
/*    */ 
/*    */   public int getCResult() {
/* 50 */     return this.cResult;
/*    */   }
/*    */ 
/*    */   public void setCResult(int result) {
/* 54 */     this.cResult = result;
/*    */   }
/*    */ 
/*    */   public String getEInfo() {
/* 58 */     return this.eInfo;
/*    */   }
/*    */ 
/*    */   public void setEInfo(String info) {
/* 62 */     this.eInfo = info;
/*    */   }
/*    */ }
