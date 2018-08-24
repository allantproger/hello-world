package kz.btsd.wikibot.domain

import org.json.JSONArray
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URLEncoder

@Service
class Parser {

    suspend fun urlFinder(query: String): String{
        val encoding = "UTF-8"
        var query = URLEncoder.encode(query.replace(" ", "_"), encoding)
        val query_url = "https://en.wikipedia.org/w/api.php?action=opensearch&format=json&search=$query"
        println(query_url)
        return query_url
    }

    @Throws(Exception::class)
    suspend fun outputer(query_url: String): String {

        var output: String

        val restTemplate = RestTemplate()
        val response = restTemplate.getForObject(query_url, String::class.java)
        val jsonarray = JSONArray(response)

        val tags = jsonarray.getJSONArray(1)
        val descr = jsonarray.getJSONArray(2)
        val links = jsonarray.getJSONArray(3)

        var description = "No brief description is provided"

        try{

            if (descr.get(0).toString().contains("may refer to") or descr.get(0).toString().contains("primarily refer to")) {
                description = "Your query has more than one meanings, closest meaning is: \n"
                description = description + "\n" + descr.get(1).toString()

            }else if (descr.get(0).toString() != "") {
                description = descr.get(0).toString()

            }
            output = tags.get(0).toString() + "\n" + "\n" + description + "\n" + "\n" + "To read more follow the link" + "\n" + links.get(0).toString()

        }catch (e: Exception) {

            output = "No result"

        }

        return output
    }

    @Throws(Exception::class)
    suspend fun moreOutputer(query_url: String): String {

        var output = arrayOf("", "", "")

        var big_output: String = ""

        val restTemplate = RestTemplate()
        val response = restTemplate.getForObject(query_url, String::class.java)
        val jsonarray = JSONArray(response)

        val tags = jsonarray.getJSONArray(1)
        val descr = jsonarray.getJSONArray(2)
        val links = jsonarray.getJSONArray(3)

        var description = arrayOf("", "", "")

        try{

            for(i in 0..2){

                if (descr.get(i+1).toString() != "") {
                    description[i] = descr.get(i+1).toString()
                }else{
                    description[i] = "No brief description is provdied"
                }

                output[i] = tags.get(i+1).toString() + "\n" + "\n" + description[i] + "\n" +
                            "\n" + "To read more follow the link" + "\n" + links.get(i+1).toString()
            }

            big_output = "1. " + output[0] + "\n \n" + "2. " + output[1] + "\n \n" + "3. " +  output[2]

            return big_output

        }catch (e: Exception) {

            big_output = "No result"

        }

        return big_output
    }
}