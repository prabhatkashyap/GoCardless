package gocardless

import goCardless.GoCardlessPaymentSuccessVO

class PaymentController {
    def goCardlessService

    def payByGoCardless = {
        BigDecimal amount = 12
        String uniqueId = "1234"
        String urlString = goCardlessService.payViaGoCardlessUrl(amount, uniqueId)
        URL url = new URL(urlString)
        redirect(url: url)
    }

    def goCardlessPaymentSuccess = { GoCardlessPaymentSuccessVO paymentSuccessVO ->
        println("--------params----success-------" + params)
        goCardlessService.goCardlessPaymentSuccess(paymentSuccessVO)
        render "success"
    }

    def goCardlessPaymentFailure = { GoCardlessPaymentSuccessVO paymentSuccessVO ->
        println("--------params------failure-----" + params)

        println '-----GO Cardless Failure---------' + params
        //        TODO:Implement this
//                sendMailService.sendPaymentUnSuccessfulMail(loan)
        render "failure"
    }
}
