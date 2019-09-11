package yongfa365.WebClient.controller;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;

@RestController
public class WebClientController {

    private static final String URI = "/test";

    @GetMapping("webClientTest")
    public Object getTest(Object object,HttpServletRequest request){
        Mono<String> stringMono = WebClient.builder()
                .baseUrl(getRequestSchemeHost(request) + URI + "?object=" + object)
                .build()
                .get()
                .retrieve()
                .bodyToMono(String.class);
        return stringMono;
    }

    @PostMapping("webClientTest")
    public Object postTest(Object object,HttpServletRequest request){
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("object",object);
        Mono<String> stringMono = WebClient.builder()
                .baseUrl(getRequestSchemeHost(request) + URI)
                .build()
                .post()
                .syncBody(paramMap)
                .retrieve()
                .bodyToMono(String.class);
        return stringMono;
    }

    @DeleteMapping("webClientTest")
    public Object deleteTest(Object object,HttpServletRequest request){
        String block = WebClient.builder()
                .baseUrl(getRequestSchemeHost(request) + URI + "?object=" + object)
                .build()
                .delete()
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return "delete:"+block;
    }

    @PutMapping("webClientTest")
    public Object putTest(Object object,HttpServletRequest request){
        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("object",object);
        Mono<String> stringMono = WebClient.builder()
                .baseUrl(getRequestSchemeHost(request) + URI)
                .build()
                .put()
                .syncBody(paramMap)
                .retrieve()
                .bodyToMono(String.class);
        return stringMono;
    }

    /**
     * 获取uri前部分
     * @param request
     * @return
     */
    public static String getRequestSchemeHost(HttpServletRequest request) {
        StringBuffer url = new StringBuffer();
        String scheme = request.getScheme();
        int port = request.getServerPort();
        if (port < 0) {
            port = 80;
        }

        url.append(scheme);
        url.append("://");
        url.append(request.getServerName());
        if (scheme.equals("http") && port != 80 || scheme.equals("https") && port != 443) {
            url.append(':');
            url.append(port);
        }
        return url.toString();
    }
}
