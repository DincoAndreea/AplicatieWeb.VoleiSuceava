package ro.marcc.server.filter;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor
public class CertificateFilter implements Filter {
    private static long numarCereriGet = 0;
    private static long numarCereriPost = 0;
    private static long numarCereriPut = 0;
    private static long numarCereriDelete = 0;

    @Override
    public void doFilter(ServletRequest cerereServlet, ServletResponse raspunsServlet, FilterChain lantDeFiltre) throws IOException, ServletException {
        HttpServletRequest cerere = (HttpServletRequest) cerereServlet;
        String tipCerere = cerere.getMethod();

        switch (tipCerere) {
            case "GET" -> {
                numarCereriGet++;
                tipCerere = String.format("[%d]", numarCereriGet) + tipCerere;
            }
            case "POST" -> {
                numarCereriPost++;
                tipCerere = String.format("[%d]", numarCereriPost) + tipCerere;
            }
            case "PUT" -> {
                numarCereriPut++;
                tipCerere = String.format("[%d]", numarCereriPut) + tipCerere;
            }
            case "DELETE" -> {
                numarCereriDelete++;
                tipCerere = String.format("[%d]", numarCereriDelete) + tipCerere;
            }
            default -> tipCerere = "[?]" + tipCerere;
        }

        log.info(tipCerere +"."+ cerere.getRemoteUser()+"("+cerere.getRemoteAddr()+")");

        lantDeFiltre.doFilter(cerereServlet,raspunsServlet);

    }
}