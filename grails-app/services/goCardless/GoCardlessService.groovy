package goCardless


import gocardless.GoCardless
import gocardless.connect.Bill
import gocardless.connect.Resource
import gocardless.connect.Subscription
import grails.util.Environment
import org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib

class GoCardlessService {

    def grailsApplication

    String payViaGoCardlessUrl(BigDecimal amount, String uniqueId) {
        try {
            GoCardless.accountDetails.appId = grailsApplication.config.gocardless.appIdentifier
            GoCardless.accountDetails.appSecret = grailsApplication.config.gocardless.appSecret
            GoCardless.accountDetails.accessToken = grailsApplication.config.gocardless.merchantAccessToken
            GoCardless.accountDetails.merchantId = grailsApplication.config.gocardless.merchantId
            if (Environment.current == Environment.DEVELOPMENT) {
                GoCardless.environment = GoCardless.Environment.SANDBOX;
            } else {
                GoCardless.environment = GoCardless.Environment.PRODUCTION;
            }
            Bill bill = new Bill(grailsApplication.config.gocardless.merchantId, amount);
            ApplicationTagLib tagLib = new ApplicationTagLib()
            String successUrl = "http://127.0.0.1:8085/credio/checkout/gocardlessCallback"
            String failureUrl = "http://127.0.0.1:8085/credio/checkout/payViaGoCardless"
            // TODO:change on the setting developers ur setting
            println '----------successUrl---------------' + successUrl
            println '----------failureUrl-------------' + failureUrl

            String newUri = GoCardless.connect.newBillUrl(bill, successUrl, failureUrl, uniqueId);
            println '---------newUri--------------' + newUri
            return newUri
        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    def goCardlessPaymentSuccess(GoCardlessPaymentSuccessVO paymentSuccessVO) {

        try {
            GoCardless.accountDetails.appId = grailsApplication.config.gocardless.appIdentifier
            GoCardless.accountDetails.appSecret = grailsApplication.config.gocardless.appSecret
            GoCardless.accountDetails.accessToken = grailsApplication.config.gocardless.merchantAccessToken
            GoCardless.accountDetails.merchantId = grailsApplication.config.gocardless.merchantId
            if (Environment.current == Environment.DEVELOPMENT) {
                GoCardless.environment = GoCardless.Environment.SANDBOX;
            } else {
                GoCardless.environment = GoCardless.Environment.PRODUCTION;
            }
            Resource resource = new Resource();
            resource.setResourceId(paymentSuccessVO.resource_id);
            resource.setResourceType(paymentSuccessVO.resource_type);
            resource.setResourceUri(paymentSuccessVO.resource_uri);
            resource.setState(paymentSuccessVO.state);
            resource.setSignature(paymentSuccessVO.signature);
            GoCardless.connect.confirm(resource);
        } catch (Exception e) {
            e.printStackTrace()
        }
    }


    String createSubscriptionUrl(BigDecimal monthlyAmount, Integer durationInMonth, Date endDate, String uniqueId) {
        println("----------------")
        try {
            String merchantId = grailsApplication.config.gocardless.merchantId
            Subscription subscription = new Subscription();
            subscription.amount = monthlyAmount
            subscription.intervalLength = durationInMonth
            subscription.intervalUnit = "month"
            subscription.name = "Subscription_of+${monthlyAmount}_for_${durationInMonth}"
            String description = "Monthly installment of ${monthlyAmount} \nDuration ${durationInMonth} months"
            subscription.description = description
            subscription.expiresAt = endDate
            subscription.merchantId = merchantId

            String appId = grailsApplication.config.gocardless.appIdentifier
            String appSecret = grailsApplication.config.gocardless.appSecret
            String accessToken = grailsApplication.config.gocardless.merchantAccessToken
            GoCardless.accountDetails.appId = appId
            GoCardless.accountDetails.appSecret = appSecret
            GoCardless.accountDetails.accessToken = accessToken
            GoCardless.accountDetails.merchantId = merchantId
            if (Environment.current == Environment.DEVELOPMENT) {
                GoCardless.environment = GoCardless.Environment.SANDBOX;
            } else {
                GoCardless.environment = GoCardless.Environment.PRODUCTION;
            }
            ApplicationTagLib tagLib = new ApplicationTagLib()
            String successUrl = "${tagLib.createLink(controller: 'payment', action: 'subscriptionGoCardlessSuccess', absolute: true)}"
            String failureUrl = "${tagLib.createLink(controller: 'payment', action: 'subscriptionGoCardlessFailure', absolute: true)}"

            String urlString = GoCardless.connect.newSubscriptionUrl(subscription, successUrl, failureUrl, uniqueId)
            return urlString

        } catch (Exception e) {
            e.printStackTrace()
        }
    }

    def goCardlessSubScriptionSuccess(GoCardlessPaymentSuccessVO paymentSuccessVO) {
        println("----------------")
        String merchantId = grailsApplication.config.gocardless.merchantId
        String appId = grailsApplication.config.gocardless.appIdentifier
        String appSecret = grailsApplication.config.gocardless.appSecret
        String accessToken = grailsApplication.config.gocardless.merchantAccessToken
        GoCardless.accountDetails.appId = appId
        GoCardless.accountDetails.appSecret = appSecret
        GoCardless.accountDetails.accessToken = accessToken
        GoCardless.accountDetails.merchantId = merchantId
        if (Environment.current == Environment.DEVELOPMENT) {
            GoCardless.environment = GoCardless.Environment.SANDBOX;
        } else {
            GoCardless.environment = GoCardless.Environment.PRODUCTION;
        }
        Resource resource = new Resource();
        resource.setResourceId(paymentSuccessVO.resource_id);
        resource.setResourceType(paymentSuccessVO.resource_type);
        resource.setResourceUri(paymentSuccessVO.resource_uri);
        resource.setState(paymentSuccessVO.state);
        resource.setSignature(paymentSuccessVO.signature);
        GoCardless.connect.confirm(resource);
    }
}