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
package org.apache.syncope.core.persistence.jpa.dao.repo;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.apache.syncope.common.lib.types.AnyTypeKind;
import org.apache.syncope.core.persistence.api.dao.ExternalResourceDAO;
import org.apache.syncope.core.persistence.api.entity.Any;
import org.apache.syncope.core.persistence.api.entity.AnyUtilsFactory;
import org.apache.syncope.core.persistence.api.entity.PlainAttr;
import org.apache.syncope.core.persistence.api.entity.PlainSchema;
import org.apache.syncope.core.persistence.api.entity.anyobject.APlainAttr;
import org.apache.syncope.core.persistence.api.entity.group.GPlainAttr;

abstract class AbstractPlainSchemaRepoExtJSON extends PlainSchemaRepoExtImpl {

    protected AbstractPlainSchemaRepoExtJSON(
            final AnyUtilsFactory anyUtilsFactory,
            final ExternalResourceDAO resourceDAO,
            final EntityManager entityManager) {

        super(anyUtilsFactory, resourceDAO, entityManager);
    }

    @Override
    public <T extends PlainAttr<?>> List<T> findAttrs(final PlainSchema schema, final Class<T> reference) {
        // not possible
        return List.of();
    }

    protected <T extends PlainAttr<?>> AnyTypeKind getAnyTypeKind(final Class<T> plainAttrClass) {
        if (GPlainAttr.class.isAssignableFrom(plainAttrClass)) {
            return AnyTypeKind.GROUP;
        }
        if (APlainAttr.class.isAssignableFrom(plainAttrClass)) {
            return AnyTypeKind.ANY_OBJECT;
        }

        return AnyTypeKind.USER;
    }

    @Override
    protected void deleteAttrs(final PlainSchema schema) {
        // nothing to do
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends PlainAttr<?>> void delete(final T plainAttr) {
        if (plainAttr.getOwner() != null) {
            ((Any<T>) plainAttr.getOwner()).remove(plainAttr);
        }
    }
}
