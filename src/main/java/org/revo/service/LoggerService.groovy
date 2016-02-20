package org.revo.service

import org.revo.domain.ErrorMessage
import org.slf4j.Logger

/**
 * Created by revo on 23/01/16.
 */
interface LoggerService {
    def Error(Logger logger, ErrorMessage em)
}