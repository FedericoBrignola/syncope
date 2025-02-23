/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
import groovy.transform.CompileStatic
import java.time.OffsetDateTime
import org.apache.syncope.common.lib.request.AnyUR
import org.apache.syncope.common.lib.types.AuditElements
import org.apache.syncope.core.logic.api.LogicActions
import org.apache.syncope.core.provisioning.api.AuditManager
import org.apache.syncope.core.spring.security.AuthContextUtils
import org.springframework.beans.factory.annotation.Autowired

@CompileStatic
class CustomAuditLogicActions implements LogicActions {

  @Autowired
  AuditManager auditManager;
  
  @Override
  <R extends AnyUR> R beforeUpdate(final R input) {
    auditManager.audit(
      AuthContextUtils.getWho(),
      AuditElements.EventCategoryType.CUSTOM,
      null,
      null,
      "MY_EVENT",
      AuditElements.Result.SUCCESS,
      "before",
      OffsetDateTime.now().toString());
    return input    
  }
}
