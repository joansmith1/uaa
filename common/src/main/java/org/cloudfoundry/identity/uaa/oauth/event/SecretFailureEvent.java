/*******************************************************************************
 *     Cloud Foundry 
 *     Copyright (c) [2009-2014] Pivotal Software, Inc. All Rights Reserved.
 *
 *     This product is licensed to you under the Apache License, Version 2.0 (the "License").
 *     You may not use this product except in compliance with the License.
 *
 *     This product includes a number of subcomponents with
 *     separate copyright notices and license terms. Your use of these
 *     subcomponents is subject to the terms and conditions of the
 *     subcomponent's license, as noted in the LICENSE file.
 *******************************************************************************/

package org.cloudfoundry.identity.uaa.oauth.event;

import java.security.Principal;

import org.cloudfoundry.identity.uaa.audit.AuditEvent;
import org.cloudfoundry.identity.uaa.audit.AuditEventType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * @author Dave Syer
 * 
 */
public class SecretFailureEvent extends AbstractClientAdminEvent {

    private String message;

    public SecretFailureEvent(String message, Authentication principal) {
        this(message, null, principal);
    }

    public SecretFailureEvent(String message, ClientDetails client, Authentication principal) {
        super(client, principal);
        this.message = message;
    }

    @Override
    public AuditEvent getAuditEvent() {
        ClientDetails client = getClient();
        if (client == null) {
            return createAuditRecord(getPrincipal().getName(), AuditEventType.SecretChangeFailure,
                            getOrigin(getPrincipal()), message);
        }
        else {
            return createAuditRecord(client.getClientId(), AuditEventType.SecretChangeFailure,
                            getOrigin(getPrincipal()), message);
        }
    }

}
