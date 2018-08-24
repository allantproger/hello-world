package kz.btsd.wikibot.config

import org.apache.http.conn.ssl.*
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContextBuilder
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import javax.net.ssl.*
import java.io.IOException
import java.security.KeyManagementException
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException

/**
 * This example demonstrates how to ignore certificate errors.
 * These errors include self signed certificate errors and hostname verification errors.
 */
object HttpClientAcceptSelfSignedCertificate {

    @JvmStatic
    fun free() {

        try {
            createAcceptSelfSignedCertificateClient().use { httpclient ->
                var get = RestTemplate().getForObject<String>("https://tengrinews.kz/news.rss")
                println(get!!)
            }
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        } catch (e: KeyStoreException) {
            throw RuntimeException(e)
        } catch (e: KeyManagementException) {
            throw RuntimeException(e)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    }

    @Throws(KeyManagementException::class, NoSuchAlgorithmException::class, KeyStoreException::class)
    fun createAcceptSelfSignedCertificateClient(): CloseableHttpClient {

        // use the TrustSelfSignedStrategy to allow Self Signed Certificates
        val sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(TrustSelfSignedStrategy())
                .build()

        // we can optionally disable hostname verification.
        // if you don't want to further weaken the security, you don't have to include this.
        val allowAllHosts = NoopHostnameVerifier()

        // create an SSL Socket Factory to use the SSLContext with the trust self signed certificate strategy
        // and allow all hosts verifier.
        val connectionFactory = SSLConnectionSocketFactory(sslContext, allowAllHosts)

        // finally create the HttpClient using HttpClient factory methods and assign the ssl socket factory
        return HttpClients
                .custom()
                .setSSLSocketFactory(connectionFactory)
                .build()
    }
}
