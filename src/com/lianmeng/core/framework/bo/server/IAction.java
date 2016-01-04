/**************************************************************************************** 
 Copyright © 2015-2020 LianMeng Corporation. All rights reserved. Reproduction or <br>
 transmission in whole or in part, in any form or by any means, electronic, mechanical <br>
 or otherwise, is prohibited without the prior written consent of the copyright owner. <br>
 ****************************************************************************************/
package com.lianmeng.core.framework.bo.server;

import com.lianmeng.core.framework.exceptions.AppException;

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
public abstract interface IAction {
    /**
     * Description: <br> 
     *  
     * @author XXX<br>
     * @taskId <br>
     * @param paramDynamicDict DynamicDict
     * @return 0
     * @throws AppException <br>
     */ 
    public abstract int perform(DynamicDict paramDynamicDict) throws AppException;
}
