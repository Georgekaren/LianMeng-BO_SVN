/*     */ package com.lianmeng.core.framework.bo.utils;
/*     */ 
/*     */ public final class HashCodeUtil
/*     */ {
/*     */   public static int hashCode(int val)
/*     */   {
/*  48 */     return val;
/*     */   }
/*     */ 
/*     */   public static int hashCode(short val)
/*     */   {
/*  58 */     return val;
/*     */   }
/*     */ 
/*     */   public static int hashCode(long val)
/*     */   {
/*  68 */     return (int)val;
/*     */   }
/*     */ 
/*     */   public static int hashCode(double val)
/*     */   {
/*  78 */     return (int)val;
/*     */   }
/*     */ 
/*     */   public static int hashCode(float val)
/*     */   {
/*  88 */     return (int)val;
/*     */   }
/*     */ 
/*     */   public static int hashCode(boolean val)
/*     */   {
/*  98 */     return ((val) ? 1 : 0);
/*     */   }
/*     */ 
/*     */   public static int hashCode(String val)
/*     */   {
/* 108 */     if (val != null) {
/* 109 */       return val.hashCode();
/*     */     }
/* 111 */     return 0;
/*     */   }
/*     */ 
/*     */   public static int hashCode(Short val)
/*     */   {
/* 121 */     if (val == null) {
/* 122 */       return 17;
/*     */     }
/* 124 */     return val.shortValue();
/*     */   }
/*     */ 
/*     */   public static int hashCode(Integer val)
/*     */   {
/* 134 */     if (val == null) {
/* 135 */       return 17;
/*     */     }
/* 137 */     return val.intValue();
/*     */   }
/*     */ 
/*     */   public static int hashCode(Float val)
/*     */   {
/* 147 */     if (val == null) {
/* 148 */       return 17;
/*     */     }
/* 150 */     return val.intValue();
/*     */   }
/*     */ 
/*     */   public static int hashCode(Double val)
/*     */   {
/* 160 */     if (val == null) {
/* 161 */       return 17;
/*     */     }
/* 163 */     return val.intValue();
/*     */   }
/*     */ 
/*     */   public static int hashCode(Long val)
/*     */   {
/* 173 */     if (val == null) {
/* 174 */       return 17;
/*     */     }
/* 176 */     return val.intValue();
/*     */   }
/*     */ 
/*     */   public static int hashCode(Object val)
/*     */   {
/* 186 */     if (val != null) {
/* 187 */       return val.hashCode();
/*     */     }
/*     */ 
/* 190 */     return 17;
/*     */   }
/*     */ 
/*     */   public static int hashCode(Object[] vals)
/*     */   {
/* 202 */     if (ValidateUtil.validateNotEmpty(vals)) {
/* 203 */       int len = vals.length;
/* 204 */       int hashCode = 0;
/*     */ 
/* 206 */       for (int i = 0; i < len; ++i) {
/* 207 */         if (vals[i] != null) {
/* 208 */           hashCode += vals[i].hashCode();
/*     */         }
/*     */       }
/*     */ 
/* 212 */       return hashCode;
/*     */     }
/*     */ 
/* 215 */     return 17;
/*     */   }
/*     */ }

