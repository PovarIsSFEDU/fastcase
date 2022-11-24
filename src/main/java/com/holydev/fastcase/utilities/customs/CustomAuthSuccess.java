package com.holydev.fastcase.utilities.customs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthSuccess extends SavedRequestAwareAuthenticationSuccessHandler {
    //TODO Logger logics
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        //TODO Logger logics
        Map<String, Object> data = new HashMap<>();
        data.put(
                "timestamp",
                Calendar.getInstance().getTime());
        data.put(
                "status", "success");

        //TODO - Logger logic
        response.setStatus(HttpStatus.OK.value());

        authentication.setAuthenticated(true);

        response.getOutputStream()
                .println(objectMapper.writeValueAsString(data));

//        super.onAuthenticationSuccess(request, response, authentication);
    }

}
