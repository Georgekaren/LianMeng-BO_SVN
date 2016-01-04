/*     */ package com.lianmeng.core.framework.bo.utils;
/*     */ 
/*     */ import java.sql.Date;
/*     */ 
/*     */ public final class Constants
/*     */ {
/*     */   public static final String OCS_WEB_HOME = "OCS_HOME";
/*     */   public static final String ORACLE_DIALECT = "Oracle";
/*     */   public static final int ORACLE_DIALECT_INT = 1;
/*     */   public static final String INFORMIX_DIALECT = "Informix";
/*     */   public static final int INFORMIX_DIALECT_INT = 2;
/*     */   public static final String CURRENT_DATE_KEY_ORACLE = "SYSDATE";
/*     */   public static final String CURRENT_DATE_KEY_INFORMIX = "CURRENT";
/*     */   public static int DB_DIALECT_INT;
/*     */   public static final String TRUE = "T";
/*     */   public static final String FALSE = "F";
/*     */   public static final String WEB_SER_WEBLOGIC = "WEBLOGIC";
/*     */   public static final String WEB_SER_WEBSPHERE = "WEBSPHERE";
/*     */   public static final int WEB_SERV_WEBLOGIC_INT = 1;
/*     */   public static final int WEB_SERV_WEBSPHERE_INT = 2;
/*     */   public static int WEB_SERV_INSTANCE_INT;
/*     */   public static final String GBK_ENCODING = "GBK";
/*     */   public static final String GB2312_ENCODING = "GB2312";
/*     */   public static final String ISO_8859_1_ENCODING = "ISO-8859-1";
/*     */   public static final String UTF_8_ENCODING = "UTF-8";
/*     */   public static final short ALONE_ENCODE_MODE = 1;
/*     */   public static final short FROM_TO_ENCOD_MODE = 2;
/*     */   public static final int DEF_ACCT_ITME_GROUP_ID = -1;
/*     */   public static final int DEF_PAYMENT_RULE = -1;
/*     */   public static final int NULL_INT = -2147483648;
/*     */   public static final float NULL_FLOAT = 1.4E-45F;
/*     */   public static final double NULL_DOUBLE = 4.9E-324D;
/*     */   public static final long NULL_LONG = -9999999999999998L;
/*     */   public static final String NULL_STRING = "";
/*     */   public static final int ARRAY_INIT_SIZE = 512;
/*     */   public static final int SUCCESS = 1;
/*     */   public static final int FAILED = 0;
/*     */   public static final int MOVE_FROM_P_TO_C = 1;
/*     */   public static final int MOVE_TO_OTHER = 2;
/*     */   public static final int DEF_HASH_CODE = 17;
/*     */   public static final String SERV_STATE_EFF = "2HA";
/*     */   public static final int DEFAULT_CATEGORY_ID = 0;
/*     */   public static final int DEF_FOREIEN_KEY = -1;
/*     */   public static final String SIMPLE_TRUE = "T";
/*     */   public static final String SIMPLE_FALSE = "F";
/*     */   public static final int SHORT_INT_TYPE = 20;
/*     */   public static final int SHORT_INT_LE_TYPE = 21;
/*     */   public static final int INT_LE_TYPE = 22;
/*     */   public static final int LONG_LE_TYPE = 23;
/*     */   public static final int INT_TYPE = 0;
/*     */   public static final int FLOAT_TYPE = 1;
/*     */   public static final int LONG_TYPE = 2;
/*     */   public static final int STRING_TYPE = 3;
/*     */   public static final int DATE_TYPE = 4;
/*     */   public static final int OBJECT_TYPE = 5;
/*     */   public static final int ARRAY_TYPE = 6;
/*     */   public static final int INT_ARRAY_TYPE = 7;
/*     */   public static final int STRING_ARRAY_TYPE = 8;
/*     */   public static final int DATE_ARRAY_TYPE = 9;
/*     */   public static final int LONG_ARRAY_TYPE = 10;
/*     */   public static final int OBJECT_ARRAY_TYPE = 11;
/*     */   public static final int C_TIME_TYPE = 12;
/*     */   public static final int REF_OBJECT_TYPE = 13;
/*     */   public static final int SINGLE_INT_TYPE = 14;
/*     */   public static final String EXP_DATE = "2030-01-01 00:00:00";
/*     */   public static final String EXP_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
/* 174 */   public static final Date EXP_DATE_DATE_TYPE = DateUtilBase.string2SQLDate("2030-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss");
/*     */   public static final String STATE_EFF = "00A";
/*     */   public static final String STATE_EXP = "00X";
/*     */   public static final String STATE_ARCHIVE = "00H";
/*     */   public static final String STATE_SHOULD_VALIDATE = "0EA";
/*     */   public static final String STATE_SHOULD_INVALIDATE = "0DA";
/*     */   public static final String STATE_UNKNOWN = "000";
/*     */   public static final String DOT_PRODUCT_INSTANCE = "80A";
/*     */   public static final String DOT_PRODUCT_BUNDLE_INSTANCE = "80B";
/*     */   public static final String DOT_PRODUCT_OFFER_INSTANCE = "80C";
/*     */   public static final String DOT_SERV_PRODUCT_INSTANCE = "80D";
/*     */   public static final String DOT_EVENT = "80H";
/*     */   public static final String DOT_CUST = "80I";
/*     */   public static final String DOT_ACCOUNT = "80J";
/*     */   public static final String DOT_PRODUCT_INSTANCE_COLL = "80P";
/*     */   public static final String DOT_PRODUCT_BUNDLE_INSTANCE_COLL = "80Q";
/*     */   public static final String DOT_CUST_COLL = "80R";
/*     */   public static final String DET_PRODUCT = "10A";
/*     */   public static final String DET_PRODUCT_BUNDLE = "10B";
/*     */   public static final String DET_PRODUCT_OFFER = "10C";
/*     */   public static final String DET_PRICE_PLAN = "10D";
/*     */   public static final String DOT_PRICE_INCLUDE_OBJECT = "80U";
/*     */   public static final int PAYMENT_METHOD_CASH = 210;
/*     */   public static final int PAYMENT_METHOD_CHECK = 212;
/*     */   public static final int PAYMENT_METHOD_CREDIT_CARD = 213;
/*     */   public static final int PAYMENT_METHOD_REPLACE_CASH_TICKET = 214;
/*     */   public static final int PAYMENT_METHOD_CONSIGN = 220;
/*     */   public static final int PAYMENT_METHOD_DEDUCT = 221;
/*     */   public static final int PAYMENT_METHOD_SUPERSEDE = 230;
/*     */   public static final int PAYMENT_METHOD_FREE_OF_CHARGE = 299;
/*     */   public static final int PAYMENT_METHOD_PREPAID = 70;
/*     */   public static final String ACTION_ADD = "1";
/*     */   public static final String ACTION_MOD = "2";
/*     */   public static final String ACTION_DEL = "3";
/*     */   public static final String REQUIRE_FLAG = "1";
/*     */   public static final String OPTIONAL_FLAG = "2";
/*     */   public static final String NOREQUIRE_FLAG = "0";
/*     */   public static final String ALLOW_CUSTOMIZED_FLAG = "1";
/*     */   public static final String NO_ALLOW_CUSTOMIZED_FLAG = "0";
/*     */   public static final String ATTR_TYPE_INTEGER = "40A";
/*     */   public static final String ATTR_TYPE_DATE = "40C";
/*     */   public static final String ATTR_TYPE_STRING = "40B";
/*     */   public static final String DEFAULT_VALUE_FLAG = "2";
/*     */   public static final String AGREEMENT_TYPE_PRODUCT_ORDER = "26A";
/*     */   public static final String AGREEMENT_TYPE_INFO_CHANGE = "26B";
/*     */   public static final String AGREEMENT_TYPE_SERVICE_CANCEL = "26C";
/*     */   public static final String SERV_STATE_SINGLE_STOP = "2HG";
/*     */   public static final String SERV_STATE_DOUBLE_STOP = "2HC";
/*     */   public static final String SERV_STATE_STOP_BEFORE_REMOVE = "2HJ";
/*     */   public static final String SERV_STATE_STOP_KEEP_NUM = "2HI";
/*     */   public static final String SERV_STATE_VALIDATE = "2HA";
/*     */   public static final String SERV_STATE_LOGOUT = "2HB";
/*     */   public static final String SERV_STATE_ARCHIVE = "2HF";
/*     */   public static final String SERV_STATE_UNKNOW = "000";
/*     */   public static final String SUM_EVENT_TYPE_CUST_ALTERNATE = "48A";
/*     */   public static final String SUM_EVENT_TYPE_USE_log = "48A";
/*     */   public static final String SUM_EVENT_TYPE_OPERATION = "48A";
/*     */   public static final String ACCT_GROUP_MAIN = "53A";
/*     */   public static final String ACCT_GROUP_IMPLICATIVE = "53B";
/*     */   public static final int ENLARGE_MONEY_MULTIPLE = 10000;
/*     */   public static final String PRODUCT_TYPE_MAIN = "10A";
/*     */   public static final String PRODUCT_TYPE_ANNEX = "10B";
/*     */   public static final int DEF_INVOICE_REQUIRE_ID = -1;
/*     */   public static final int DEF_BILL_REQUIRE_ID = -1;
/*     */   public static final String BANK_ACCT_TYPE_CONSIGN = "5DA";
/*     */   public static final String BANK_ACCT_TYPE_TRANSFER = "5DB";
/*     */   public static final int BILLING_MODE_PREPAID = 2;
/*     */   public static final int BILLING_MODE_AFTER_PAY = 1;
/*     */   public static final String DEF_ACCT_ITEM_GROUP_STR = "-1";
/*     */   public static final int DEF_ACCT_ITEM_GROUP_INT = -1;
/*     */   public static final String COUNTY_TYPE_CITY = "0";
/*     */   public static final String COUNTY_TYPE_VILLAGE = "1";
/*     */   public static final String RESID_FLAG_HOME = "0";
/*     */   public static final String RESID_FLAG_OFFICE = "1";
/*     */   public static final String ACCT_ITEM_SD_STATE_OUT = "5JE";
/*     */   public static final String ACCT_ITEM_SD_STATE_CREATE = "5JA";
/*     */   public static final String BATCH_STATE_FINISH = "4";
/*     */   public static final int ACCT_ITEM_ADD_STATE_CREATE = 0;
/*     */   public static final int ACCT_ITEM_ADD_STATE_DEAL = 1;
/*     */   public static final int ACCT_ITEM_ADD_STATE_CANCEL = 2;
/* 344 */   public static int TELE_SYSTEM_STAFF_ID = -2;
/*     */ 
/* 346 */   public static String TELE_SYSTEM_STAFF_CODE = "-2";
/*     */ 
/* 348 */   public static String BILLCYCLE_STATE_AVAIL = "50E";
/*     */ 
/* 350 */   public static String BILLCYCLE_STATE_STRIKE = "50F";
/*     */ 
/* 352 */   public static String BILLCYCLE_STATE_DURATION = "50A";
/*     */ 
/* 354 */   public static String BILLCYCLE_STATE_COUNT = "50D";
/*     */   public static final String EFF_TYPE_A = "82A";
/*     */   public static final String EFF_TYPE_B = "82B";
/* 360 */   public static String DEFAULT_PASSWD = "000000";
/*     */ 
/* 362 */   public static String ACTION_INSTALL = "A1";
/*     */ 
/* 364 */   public static String ACTION_REMOVE = "A5";
/*     */ 
/* 366 */   public static String ACTION_DISMANTLE = "A8";
/*     */ 
/* 368 */   public static String ACTION_CHANGE_ACCT_RELATION = "B1";
/*     */ 
/* 370 */   public static String ACTION_TRANSFER = "B2";
/*     */ 
/* 372 */   public static String ACTION_STOP = "B3";
/*     */ 
/* 374 */   public static String ACTION_RECOVER = "B5";
/*     */ 
/* 376 */   public static String ACTION_CHANGE_SERV_PRODUCT = "B6";
/*     */ 
/* 378 */   public static String ACTION_CHANGE_PRODUCT_OFFER = "B7";
/*     */ 
/* 380 */   public static String ACTION_CHANGE_INFO = "B9";
/*     */ 
/* 382 */   public static String ACTION_STOP_AND_KEEP_NUM = "C2";
/*     */ 
/* 384 */   public static String ACTION_STOP_BEFORE_REMOVE = "C5";
/*     */ 
/* 386 */   public static String AGREEMENT_STATE_COMPLETED = "27A";
/*     */ 
/* 388 */   public static String AGREEMENT_STATE_EXECUTING = "27B";
/*     */ 
/* 390 */   public static String SOURCE_CUST_APPLY = "0";
/*     */ 
/* 392 */   public static String SOURCE_STAFF_OPERATE = "1";
/*     */ 
/* 394 */   public static String OBJ_TYPE_ACCT = "5BA";
/*     */ 
/* 396 */   public static String OBJ_TYPE_CUST = "5BB";
/*     */ 
/* 398 */   public static String OBJ_TYPE_SERV = "5BC";
/*     */ 
/* 400 */   public static String SINGLE_CHOICE_INPUT = "98A";
/*     */ 
/* 402 */   public static String MUTI_CHOICE_INPUT = "98B";
/*     */ 
/* 404 */   public static String TEXT_INPUT = "98C";
/*     */ 
/* 406 */   public static String PRODUCT_CATALOG_TYPE = "12A";
/*     */   public static final int AUTO_BATCH = 1;
/* 410 */   public static String SERV_GRADE_A = "01";
/*     */ 
/* 412 */   public static String SERV_GRADE_B = "03";
/*     */ 
/* 414 */   public static String SERV_GRADE_C = "04";
/*     */ 
/* 416 */   public static String SERV_GRADE_D = "02";
/*     */ 
/* 418 */   public static String UPDATE_FLAG_0 = "0";
/*     */ 
/* 420 */   public static String UPDATE_FLAG_1 = "1";
/*     */ 
/* 422 */   public static String REGION_TYPE_C = "3";
/*     */ 
/* 424 */   public static String REGION_TYPE_E = "7";
/*     */   public static final String SHMDB_OPERATE_TYPE_A = "0";
/*     */   public static final String SHMDB_OPERATE_TYPE_B = "1";
/*     */   public static final String SHMDB_OPERATE_TYPE_C = "2";
/*     */   public static final String EDUCATION_LEVEL_GRADE_SCHOOL = "2AA";
/*     */   public static final String EDUCATION_LEVEL_HIGH_SCHOOL = "2AB";
/*     */   public static final String EDUCATION_LEVEL_COLLEGE = "2AC";
/*     */   public static final String EDUCATION_LEVEL_COLLEGE_UPWARDS = "2AD";
/*     */   public static final String GENDER_MALE = "T";
/*     */   public static final String GENDER_FEMALE = "F";
/*     */   public static final String RELIGION_BUDDHISM = "2EA";
/*     */   public static final String RELIGION_TAOISM = "2EB";
/*     */   public static final String RELIGION_ISIAM = "2EC";
/*     */   public static final String RELIGION_CHRISTIANISM = "2ED";
/*     */   public static final String RELIGION_CATHOLICISM = "2EF";
/*     */   public static final String RELIGION_ORTHODOX_CHURCH = "2EH";
/*     */   public static final String RELIGION_NONE = "2EG";
/*     */   public static final String MARRY_STATUS_YES = "T";
/*     */   public static final String MARRY_STATUS_NO = "F";
/*     */   public static final String OCCUPATION_WORKER = "2CA";
/*     */   public static final String OCCUPATION_FARMER = "2CB";
/*     */   public static final String OCCUPATION_TEACHER = "2CC";
/*     */   public static final String OCCUPATION_ENGINEER = "2CD";
/*     */   public static final String CERTIFICATE_TYPE_ID = "2BA";
/*     */   public static final String CERTIFICATE_TYPE_DRIVING_LICENCE = "2BB";
/*     */   public static final String CERTIFICATE_TYPE_OFFICER = "2BC";
/*     */   public static final String CERTIFICATE_TYPE_CORPORATION_PATENT = "2BD";
/*     */   public static final String BATCHBILL_CREATEFILE = "00";
/*     */   public static final String BATCHBILL_BILL = "01";
/*     */   public static final String GROUP_BATCHBILL_CREATEFILE = "02";
/*     */   public static final String GROUP_BATCHBILL_BILL = "03";
/*     */   public static final String SSO_APP_USER_NAME = "SSO_APP_USER_NAME";
/*     */ }

