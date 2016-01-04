/**************************************************************************************** 
 Copyright © 2015-2020 LianMeng Corporation. All rights reserved. Reproduction or <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.lianmeng.core.framework.bo.server;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lianmeng.core.framework.bo.utils.BOUtils;
import com.lianmeng.core.framework.bo.utils.DateUtilBase;
import com.lianmeng.core.framework.bo.utils.FileUtil;
import com.lianmeng.core.framework.bo.utils.ValidateUtil;
import com.lianmeng.core.framework.exceptions.AppException;
import com.lianmeng.core.framework.exceptions.ExceptionHandler;

/** 
 * Description: <br> 
 *  
 * @author XXX<br>
 * @version 8.0<br>
 * @taskId <br>
 * @CreateDate 2015-12-20 <br>
 * @since V8<br>
 * @see com.lianmeng.core.framework.bo.server <br>
 */
public class DynamicDict implements Serializable {
    /**
     * logger <br>
     */
    private static final Logger logger = Logger.getLogger(DynamicDict.class);

    /**
     * serialVersionUID <br>
     */
    private static final long serialVersionUID = 7996383561512782942L;

    /**
     * serviceName <br>
     */
    public String serviceName;

    /**
     * valueMap <br>
     */
    public HashMap<String, Object> valueMap;

    /**
     * nullValueKeyList <br>
     */
    public List<String> nullValueKeyList = null;

    /**
     * visitFlag <br>
     */
    private int visitFlag = 0;

    /**
     * isBOMode <br>
     */
    private boolean isBOMode = false;

    /**
     * isSuccess <br>
     */
    private boolean isSuccess;

    /**
     * msg <br>
     */
    private String msg;

    /**
     * msgCode <br>
     */
    private String msgCode;

    /**
     * type <br>
     */
    private String type;

    /**
     * channel <br>
     */
    private String channel;

    /**
     * DEFAULT_ENCODING <br>
     */
    private static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * KEY_IS_EMPTY_STATE <br>
     */
    public static final int KEY_IS_EMPTY_STATE = -1;

    /**
     * VALUE_EXIST_IS_NOT_NULL_STATE <br>
     */
    public static final int VALUE_EXIST_IS_NOT_NULL_STATE = 1;

    /**
     * VALUE_EXIST_IS_NULL_STATE <br>
     */
    public static final int VALUE_EXIST_IS_NULL_STATE = 2;

    /**
     * VALUE_NOT_EXIST_STATE <br>
     */
    public static final int VALUE_NOT_EXIST_STATE = 3;

    public String getChannel() {
        return this.channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /** 
     *  DynamicDict
     */ 
    public DynamicDict() {
        this.valueMap = new HashMap();
        this.isBOMode = true;
        this.nullValueKeyList = new ArrayList();
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br> <br>
     */ 
    public void destroy() {
        if (this.valueMap != null) {
            this.valueMap.clear();
            this.valueMap = null;
        }
        if (this.nullValueKeyList != null) {
            this.nullValueKeyList.clear();
            this.nullValueKeyList = null;
        }
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br> <br>
     */ 
    public void clear() {
        if (this.valueMap != null) {
            this.valueMap.clear();
        }
        if (this.nullValueKeyList != null) {
            this.nullValueKeyList.clear();
        }
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @return String<br>
     */ 
    public String toString() {
        return this.valueMap.toString();
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @return <br>
     */ 
    public String asMapString() {
        StringBuilder content = new StringBuilder();
        content.append("serviceName = ").append(this.serviceName).append(",");
        content.append("values = ").append(this.valueMap);
        return content.toString();
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param encoding String
     * @return String
     * @throws AppException <br>
     */ 
    public String asXML(String encoding) throws AppException {
        if ((encoding == null) || (encoding.trim().length() == 0)) {
            encoding = "UTF-8";
        }
        return BOUtils.bo2xml(this, encoding);
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @return String
     * @throws AppException <br>
     */ 
    public String asXML() throws AppException {
        return asXML("UTF-8");
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param key String
     * @return 0<br>
     */ 
    public int getStateOfValue(String key) {
        if (!(ValidateUtil.validateNotEmpty(key))) {
            return -1;
        }

        boolean isExist = this.valueMap.containsKey(key);
        if (isExist) {
            return ((this.valueMap.get(key) == null) ? 2 : 1);
        }

        for (int i = 0; i < this.nullValueKeyList.size(); ++i) {
            String nullValueKey = (String) this.nullValueKeyList.get(i);
            if (nullValueKey.equals(key)) {
                return 2;
            }
        }
        return 3;
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param key String
     * @return boolean<br>
     */ 
    public boolean existValueIsNull(String key) {
        return (getStateOfValue(key) == 2);
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param name String
     * @param object  Object
     * @param insert int
     * @throws AppException <br>
     */ 
    public void setValueByName(String name, Object object, int insert) throws AppException {
        setValueByNameInnger(name, object, insert, true);
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param name String
     * @param object Object
     * @param insert int
     * @param trimStr boolean
     * @throws AppException <br>
     */ 
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setValueByNameInnger(String name, Object object, int insert, boolean trimStr) throws AppException {
        if (object == null) {
            if (name != null) {
                this.nullValueKeyList.add(name);
            }
            return;
        }
        if ((object != null) && (object instanceof ResultSet) && (this.visitFlag == 1)) {
            try {
                ArrayList arr = new ArrayList();
                ResultSet rs = (ResultSet) object;
                ResultSetMetaData rsmd = rs.getMetaData();
                int cols = rsmd.getColumnCount();
                String[] arr1 = new String[cols];
                for (int i = 1; i <= cols; ++i) {
                    arr1[(i - 1)] = rsmd.getColumnName(i);
                }

                while (rs.next()) {
                    Map fields = new HashMap();
                    for (int i = 0; i < cols; ++i) {
                        if (rs.getString(arr1[i]) != null) {
                            fields.put(arr1[i], rs.getString(arr1[i]));
                        }
                    }
                    arr.add(fields);
                }
                if (arr.size() > 0) {
                    object = arr;
                }
                else {
                    object = null;
                }
            }
            catch (SQLException sqle) {
                ExceptionHandler.publish("S-SYS-00006");
            }

        }

        Object obj = this.valueMap.get(name);
        if (object != null) {
            if (object instanceof String) {
                if (trimStr) {
                    object = ((String) object).trim();
                }
            }
            else if (object instanceof java.util.Date) {
                object = new java.sql.Date(((java.util.Date) object).getTime());
            }
            else if (object instanceof DynamicDict) {
                this.isBOMode = true;
                if (obj == null) {
                    obj = new ArrayList();
                }
            }
        }

        switch (insert) {
            case 0:
                ExceptionHandler.publish("S-SYS-00007", null, name);
            case 1:
                if ("java.util.ArrayList".equals(obj.getClass().getName())) {
                    ArrayList alObj = (ArrayList) obj;
                    alObj.add(object);
                    this.valueMap.remove(name);
                    this.valueMap.put(name, alObj);
                    return;
                }
                ArrayList alObj = new ArrayList();
                alObj.add(obj);
                alObj.add(object);
                this.valueMap.remove(name);
                this.valueMap.put(name, alObj);

                break;
            case 2:
                this.valueMap.remove(name);
                this.valueMap.put(name, object);
                break;
            default:
                ExceptionHandler.publish("S-SYS-00008");
                this.valueMap.put(name, object);
                return;

        }
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param name String
     * @param object Object
     * @throws AppException <br>
     */ 
    public void setValueByName(String name, Object object) throws AppException {
        setValueByName(name, object, 2);
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param name String
     * @param str String
     * @param isTrim boolean
     * @throws AppException <br>
     */ 
    @Deprecated
    public void setValueByName(String name, String str, boolean isTrim) throws AppException {
        if (!(ValidateUtil.validateNotEmpty(str))) {
            return;
        }
        if (isTrim) {
            str = str.trim();
        }
        setValueByName(name, str, 2);
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param name String
     * @param object Object
     * @throws AppException <br>
     */ 
    public void addValueByName(String name, Object object) throws AppException {
        setValueByName(name, object, 1);
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param name String
     * @param object Object
     * @param trimStr boolean
     * @throws AppException <br>
     */ 
    public void addValueByName(String name, Object object, boolean trimStr) throws AppException {
        setValueByNameInnger(name, object, 1, trimStr);
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param name String
     * @param seq int
     * @param isThrow boolean
     * @param strDefault String
     * @return Object
     * @throws AppException <br>
     */ 
    @SuppressWarnings("rawtypes")
    public Object getValueByName(String name, int seq, boolean isThrow, String strDefault) throws AppException {
        Object obj = this.valueMap.get(name);
        if (obj == null) {
            if (isThrow) {
                ExceptionHandler.publish("S-SYS-00009", null, name);
            }
            else {
                obj = strDefault;
            }

        }
        else if ((seq >= 0) && (this.visitFlag != 1) && ("java.util.ArrayList".equals(obj.getClass().getName()))) {
            ArrayList alObj = (ArrayList) obj;
            if ((alObj.size() > 0) && (seq < alObj.size())) {
                obj = alObj.get(seq);
            }
            else {
                obj = null;
            }
            if ((obj == null) && (isThrow)) {
                ExceptionHandler.publish("S-SYS-00009", null, name);
            }
        }

        return obj;
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param name String
     * @param strDefault String
     * @return Object
     * @throws AppException <br>
     */ 
    public Object getValueByName(String name, String strDefault) throws AppException {
        return getValue(name, 0, false, strDefault);
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param name String
     * @param isThrow boolean
     * @return Object
     * @throws AppException <br>
     */ 
    public Object getValueByName(String name, boolean isThrow) throws AppException {
        if (name.indexOf(".") > 0) {
            String[] strs = name.split("(\\.)");
            DynamicDict obj = getBOByName(strs[0], isThrow);
            for (int i = 1; i < strs.length - 1; ++i) {
                obj = (DynamicDict) obj.getValueByName(strs[i]);
            }
            return obj.getValueByName(strs[(strs.length - 1)], 0, isThrow, null);
        }

        return getValueByName(name, 0, isThrow, null);
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param name String
     * @return Object
     * @throws AppException <br>
     */ 
    public Object getValueByName(String name) throws AppException {
        if (name.indexOf(".") > 0) {
            String[] strs = name.split("(\\.)");
            DynamicDict obj = (DynamicDict) getValueByName(strs[0]);
            for (int i = 1; i < strs.length - 1; ++i) {
                obj = (DynamicDict) obj.getValueByName(strs[i]);
            }
            return obj.getValueByName(strs[(strs.length - 1)], 0, true, null);
        }

        return getValueByName(name, 0, true, null);
    }

    public Object getValueByName(String name, int seq) throws AppException {
        return getValueByName(name, seq, true, null);
    }

    public Object getValueByName(String name, int seq, String defaultValue) throws AppException {
        return getValueByName(name, seq, false, defaultValue);
    }

    public DynamicDict getBOByName(String aName) throws AppException {
        return getBO(aName, 0, true);
    }

    public DynamicDict getBOByName(String aName, int aSeq) throws AppException {
        return getBO(aName, aSeq, true);
    }

    public DynamicDict getBOByName(String aName, boolean isThrow) throws AppException {
        return getBO(aName, 0, isThrow);
    }

    private DynamicDict getBO(String name, int seq, boolean isThrow) throws AppException {
        if (name.indexOf(".") > 0) {
            String[] strs = name.split("(\\.)");
            DynamicDict obj = getBOByName(strs[0], isThrow);
            if (obj == null) {
                if (isThrow) {
                    ExceptionHandler.publish("S-SYS-00009", null, name);
                }
                else {
                    return null;
                }
            }
            for (int i = 1; i < strs.length - 1; ++i) {
                obj = (DynamicDict) obj.getValueByName(strs[i]);
            }
            return obj.getBOByName(strs[(strs.length - 1)], seq, isThrow);
        }

        return getBOByName(name, seq, isThrow);
    }

    public DynamicDict getBOByName(String name, int seq, boolean isThrow) throws AppException {
        DynamicDict dict = null;
        Object obj = null;

        obj = this.valueMap.get(name);
        if (obj == null) {
            if (isThrow) {
                ExceptionHandler.publish("S-SYS-00009", null, name);
            }

            return null;
        }

        if (obj.getClass().getName().indexOf("ArrayList") != -1) {
            ArrayList al = (ArrayList) obj;
            if ((seq >= 0) && (seq < al.size())) {
                dict = (DynamicDict) al.get(seq);
            }
            else {
                throw new AppException("S-SYS-00010");
            }
        }
        else {
            if (obj.getClass().getName().indexOf("DynamicDict") >= 0) {
                return ((DynamicDict) obj);
            }

            ExceptionHandler.publish("S-SYS-00011", null, name);
        }

        label132:
        return dict;
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param name String
     * @return <br>
     */ 
    public int getCountByName(String name) {
        if (name.indexOf(".") > 0) {
            String[] strs = name.split("(\\.)");
            DynamicDict obj = new DynamicDict();
            try {
                obj = getBOByName(strs[0], false);

                for (int i = 1; i < strs.length - 1; ++i) {
                    if (obj == null) {
                        return 0;
                    }
                    obj = (DynamicDict) obj.getValueByName(strs[i]);
                }
            }
            catch (AppException e) {
                logger.error(e);
            }
            if (obj == null) {
                return 0;
            }
            return obj.getCountByName(strs[(strs.length - 1)], true);
        }

        return getCountByName(name, true);
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param name String
     * @param isSkipArray  boolean
     * @return int<br>
     */ 
    @SuppressWarnings("rawtypes")
    public int getCountByName(String name, boolean isSkipArray) {
        Object obj = this.valueMap.get(name);
        int iObj;
        if (obj == null) {
            iObj = 0;
        }
        else if ((isSkipArray) && ("java.util.ArrayList".equals(obj.getClass().getName()))) {
            ArrayList alObj = (ArrayList) obj;
            iObj = alObj.size();
        }
        else {
            iObj = 1;
        }

        return iObj;
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param format String
     * @return String
     * @throws AppException <br>
     */ 
    @SuppressWarnings("unused")
    public String format(String format) throws AppException {
        String result = "";

        if ((format == null) || (format.length() == 0)) {
            result = "";
            return result;
        }

        if ((format.indexOf("${") < 0) || (format.indexOf("}") < 0)) {
            result = format;
            return result;
        }

        int INIT = 0;
        int LEFT = 1;
        int RIGHT = 2;
        int index = 0;
        int rightIndex = 0;
        int flag = 0;
        String tmp = format;
        result = "";
        String variable = null;
        String tran = null;
        while (true) {
            if ((flag != 2) && ((index = tmp.indexOf("}")) >= 0)) {
                flag = 2;
            }
            else {
                if ((flag == 1) || ((index = tmp.substring(0, index).lastIndexOf("${")) < 0))
                    break;
                flag = 1;
            }

            switch (flag) {
                case 2:
                    rightIndex = index;
                    break;
                case 1:
                    variable = tmp.substring(index + 2, rightIndex);
                    tran = (String) getValueByName(variable);

                    if (tran == null) {
                        tran = "";
                    }
                    tmp = tmp.substring(0, index) + tran + tmp.substring(rightIndex + 1);
                    continue;
            }

        }

        result = tmp;
        return result;
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param conn Connection
     * @throws AppException <br>
     */ 
    public void setConnection(Connection conn) throws AppException {
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isBOMode() {
        return this.isBOMode;
    }

    public void setBOMode(boolean isBOMode) {
        this.isBOMode = isBOMode;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgCode() {
        return this.msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param key <br>
     */ 
    public void remove(String key) {
        this.valueMap.remove(key);
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param name Object
     * @return <br>
     */ 
    public int getCount(String name) {
        return getCountByName(name);
    }

    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param name String
     * @param obj Object
     * @throws AppException <br>
     */ 
    public void set(String name, Object obj) throws AppException {
        setValueByName(name, obj);
    }

    public void set(String name, Object obj, boolean trim) throws AppException {
        setValueByNameInnger(name, obj, 2, trim);
    }

    public void set(String name, DynamicDict obj) throws AppException {
        remove(name);
        setValueByName(name, obj, 2);
    }

    public void set(String name, int obj) throws AppException {
        add(name, obj);
    }

    public void set(String name, long obj) throws AppException {
        Long value = new Long(obj);
        setValueByName(name, value);
    }

    public void set(String name, short obj) throws AppException {
        Long value = new Long(obj);
        setValueByName(name, value);
    }

    public void set(String name, ArrayList array) throws AppException {
        this.valueMap.remove(name);
        this.valueMap.put(name, array);
    }

    public void add(String name, Object obj) throws AppException {
        addValueByName(name, obj);
    }

    public void add(String name, Object obj, boolean trimStr) throws AppException {
        addValueByName(name, obj, trimStr);
    }

    public void addString(String name, String value, boolean trim) throws AppException {
        setValueByNameInnger(name, value, 1, trim);
    }

    public void add(String name, int obj) throws AppException {
        Long value = new Long(obj);
        setValueByName(name, value);
    }

    public void add(String name, long obj) throws AppException {
        Long value = new Long(obj);
        setValueByName(name, value);
    }

    public void add(String name, short obj) throws AppException {
        Long value = new Long(obj);
        setValueByName(name, value);
    }

    private Object getValue(String name, int seq, boolean isThrow, String defaultValue) throws AppException {
        if (name.indexOf(".") > 0) {
            String[] strs = name.split("(\\.)");
            DynamicDict obj = (DynamicDict) getValueByName(strs[0], isThrow);
            if (obj == null) {
                return defaultValue;
            }
            for (int i = 1; i < strs.length - 1; ++i) {
                obj = (DynamicDict) obj.getValueByName(strs[i], isThrow);
                if (obj == null) {
                    return defaultValue;
                }
            }
            return obj.getValueByName(strs[(strs.length - 1)], seq, isThrow, defaultValue);
        }

        return getValueByName(name, seq, isThrow, defaultValue);
    }

    public Long getLong(String name) throws AppException {
        Object obj = getValue(name, 0, false, null);
        if (obj == null) {
            return null;
        }
        if (obj instanceof Long) {
            return ((Long) obj);
        }
        if (obj instanceof Double) {
            return Long.valueOf(Math.round(((Double) obj).doubleValue()));
        }

        if ("".equals(obj.toString())) {
            return null;
        }
        return new Long(obj.toString());
    }

    public Long getLong(String name, boolean isThrow) throws AppException {
        Object obj = getValue(name, 0, isThrow, null);
        if (obj == null) {
            return null;
        }

        if (obj instanceof Long) {
            return ((Long) obj);
        }
        if (obj instanceof Double) {
            return Long.valueOf(Math.round(((Double) obj).doubleValue()));
        }

        if ("".equals(obj.toString())) {
            return null;
        }
        return new Long(obj.toString());
    }

    public Long getLong(String name, int seq) throws AppException {
        Object obj = getValue(name, seq, true, null);
        if (obj == null) {
            return null;
        }

        if (obj instanceof Long) {
            return ((Long) obj);
        }
        if (obj instanceof Double) {
            return Long.valueOf(Math.round(((Double) obj).doubleValue()));
        }

        if ("".equals(obj.toString())) {
            return null;
        }
        return new Long(obj.toString());
    }

    public String getString(String name) throws AppException {
        return getString(name, false);
    }

    public String getString(String name, boolean isThrow) throws AppException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Object obj = getValue(name, 0, isThrow, null);

        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return ((String) obj);
        }
        if ((obj instanceof java.sql.Date) || (obj instanceof Timestamp)) {
            return sdf.format(obj);
        }

        return obj.toString();
    }

    public String getString(String name, int seq) throws AppException {
        Object obj = getValue(name, seq, false, null);
        if (obj instanceof String) {
            return ((String) obj);
        }

        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public DynamicDict getBO(String name) throws AppException {
        return getBO(name, 0, false);
    }

    public DynamicDict getBO(String name, int seq) throws AppException {
        return getBO(name, seq, true);
    }

    public DynamicDict getBO(String aName, boolean isThrow) throws AppException {
        return getBO(aName, 0, isThrow);
    }

    public List getList(String name) throws AppException {
        Object obj = this.valueMap.get(name);
        List retList = null;
        if (obj != null) {
            if (obj instanceof List) {
                retList = (List) obj;
            }
            else {
                retList = new ArrayList();
                retList.add(obj);
            }
        }
        else {
            retList = new ArrayList();
        }
        return retList;
    }

    public java.sql.Date getDate(String name) throws AppException {
        Object obj = getValue(name, 0, false, null);
        if (obj == null) {
            return null;
        }

        if (obj instanceof String) {
            if (!("".equals(obj.toString()))) {
                return DateUtilBase.string2SQLDate(obj.toString());
            }

            return null;
        }

        if (obj instanceof Timestamp) {
            return new java.sql.Date(((Timestamp) obj).getTime());
        }
        if (obj instanceof java.util.Date) {
            return new java.sql.Date(((java.util.Date) obj).getTime());
        }

        return ((java.sql.Date) obj);
    }

    public java.sql.Date getDate(String name, boolean isThrow) throws AppException {
        Object obj = getValue(name, 0, isThrow, null);
        if (obj == null) {
            return null;
        }

        if (obj instanceof String) {
            if (!("".equals(obj.toString()))) {
                return DateUtilBase.string2SQLDate(obj.toString());
            }

            return null;
        }

        if (obj instanceof Timestamp) {
            return new java.sql.Date(((Timestamp) obj).getTime());
        }

        return ((java.sql.Date) obj);
    }

    public java.sql.Date getDate(String name, int seq) throws AppException {
        Object obj = getValue(name, seq, false, null);
        if (obj == null) {
            return null;
        }

        if (obj instanceof String) {
            if (!("".equals(obj.toString()))) {
                return DateUtilBase.string2SQLDate(obj.toString());
            }

            return null;
        }

        if (obj instanceof Timestamp) {
            return new java.sql.Date(((Timestamp) obj).getTime());
        }

        return ((java.sql.Date) obj);
    }

    public Object get(String name, int seq) throws AppException {
        return getValue(name, seq, false, null);
    }

    public Object get(String name) throws AppException {
        return getValue(name, 0, false, null);
    }

    private Object getOriginal(String name) throws AppException {
        return getValue(name, -1, false, null);
    }

    public Object get(String name, String defaultValue) throws AppException {
        return getValue(name, 0, false, defaultValue);
    }

    public Object get(String name, boolean isThrow) throws AppException {
        return getValue(name, 0, isThrow, null);
    }

    public Boolean getBoolean(String name) throws AppException {
        Object obj = getValue(name, 0, false, null);
        if (obj instanceof Boolean) {
            return ((Boolean) obj);
        }

        if (obj == null) {
            return null;
        }
        return new Boolean(obj.toString());
    }

    public Boolean getBoolean(String name, boolean isThrow) throws AppException {
        Object obj = getValue(name, 0, isThrow, null);
        if (obj instanceof Boolean) {
            return ((Boolean) obj);
        }

        if (obj == null) {
            return null;
        }
        return new Boolean(obj.toString());
    }

    public Boolean getBoolean(String name, int seq) throws AppException {
        Object obj = getValue(name, seq, false, null);
        if (obj instanceof Boolean) {
            return ((Boolean) obj);
        }

        if (obj == null) {
            return null;
        }
        return new Boolean(obj.toString());
    }

    public void putAll(DynamicDict dict) throws AppException {
        for (Iterator iter = dict.valueMap.keySet().iterator(); iter.hasNext();) {
            String key = (String) iter.next();
            set(key, dict.getOriginal(key));
        }
    }

    public void printToFile(String fname, boolean mode) throws IOException, AppException {
        FileUtil.writeTxtFile(fname, asXML() + "\r\n", mode);
    }
}
