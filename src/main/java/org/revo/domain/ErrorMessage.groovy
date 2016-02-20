package org.revo.domain

import groovy.transform.Canonical

/**
 * Created by revo on 23/01/16.
 */
@Canonical
class ErrorMessage {
    Long Mid
    String Message
    Long Uid
    Long Aid
    Set<String> authorities

    ErrorMessage(String message) {
        Message = message
    }

    ErrorMessage(Long Mid, String message) {
        this.Mid = Mid
        Message = message
    }

    ErrorMessage users(RevoUser revoUser) {
        this.Uid = revoUser.id
        this.Aid = revoUser.admin
        authorities = revoUser.authorities.collect {
            it.authority
        }
        this
    }
}
