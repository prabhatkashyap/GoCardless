package goCardless

import org.codehaus.groovy.grails.validation.Validateable


@Validateable
class GoCardlessPaymentSuccessVO {

    String resource_id
    String resource_type
    String state
    String resource_uri
    String signature
}
