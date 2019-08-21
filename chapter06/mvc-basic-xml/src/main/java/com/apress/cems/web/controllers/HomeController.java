/*
Freeware License, some rights reserved

Copyright (c) 2019 Iuliana Cosmina

Permission is hereby granted, free of charge, to anyone obtaining a copy 
of this software and associated documentation files (the "Software"), 
to work with the Software within the limits of freeware distribution and fair use. 
This includes the rights to use, copy, and modify the Software for personal use. 
Users are also allowed and encouraged to submit corrections and modifications 
to the Software for the benefit of other users.

It is not allowed to reuse,  modify, or redistribute the Software for 
commercial use in any way, or for a user's educational materials such as books 
or blog articles without prior permission from the copyright holder. 

The above copyright notice and this permission notice need to be included 
in all copies or substantial portions of the software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS OR APRESS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package com.apress.cems.web.controllers;

import com.apress.cems.web.problem.WebException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    //http://localhost:8080/mvc-basic-xml/home/
    @GetMapping
    public String home(Model model) {
        model.addAttribute("message", "Spring MVC JSP Example!!");
        return "home/root";
    }

    // matches http://localhost:8080/mvc-basic-xml/home/today
    @RequestMapping(value = "/today")
    public String today(Model model){
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM uuuu");
        model.addAttribute("today", today.format(formatter));
        return "home/today";
    }

    // matches http://localhost:8080/mvc-basic-xml/home/hello?name=Bub
    @RequestMapping(value = "/hello")
    public String hello(@RequestParam("name") String name, Model model){
        model.addAttribute("name", name);
        return "home/hello";
    }

    @GetMapping("/verifyRedirection")
    public String redirectData(final RedirectAttributes redirectAttributes) {
        List<String> dataList = new ArrayList<>();
        dataList.add("Data from HomeController.redirectData");
        redirectAttributes.addFlashAttribute("dataList", dataList);
        return "redirect:/home/data-list";
    }

    /**
     * Method which is called via "redirect:" from the HomeController.redirectData(..)
     * @param content
     * @return
     */
    @GetMapping("/data-list")
    public String listData(@ModelAttribute("dataList") ArrayList<String> content) {
        return "home/sandbox";
    }

    /**
     * Instructions: Refresh the page after you see the first error, so that the Cache-Control header is initialized.
     * @param host
     * @param cacheControl
     * @param userAgent
     * @param model
     * @return
     * @throws WebException
     */
    @GetMapping("/headers")
    public String headers(@RequestHeader(value="Host") String host,
                          @RequestHeader(value="Cache-Control", required=false) String cacheControl,
                          @RequestHeader(value="User-Agent") String userAgent,
                          Model model) throws WebException {
        try {
            List<String> dataList = new ArrayList<>();
            dataList.add("These are the headers for this request: ");
            dataList.add("Host: ".concat(host));
            dataList.add("Cache-Control: ".concat(cacheControl));
            dataList.add("User-Agent:".concat(userAgent));
            model.addAttribute("dataList", dataList);
        } catch (Exception e){
            throw new WebException("Something wrong happened while processing headers. ", e);
        }
        return "home/sandbox";
    }

    @GetMapping("/request")
    public String webRequest(WebRequest webRequest, Model model){
        List<String> dataList = new ArrayList<>();
        dataList.add("These are the details of this request: ");
        dataList.add(webRequest.getContextPath());
        webRequest.getHeaderNames().forEachRemaining(dataList:: add);
        dataList.add(webRequest.getDescription(true));
        model.addAttribute("dataList", dataList);
        return "home/sandbox";
    }

    @GetMapping("/response")
    public String webResponse(HttpServletResponse response, Model model){
        List<String> dataList = new ArrayList<>();
        dataList.add("Response was modified. Check the cookies.");
        model.addAttribute("dataList", dataList);
        response.addCookie(new Cookie("SANDBOX_COOKIE", "Delicious"));
        return "home/sandbox";
    }

    //75;g=1;u=3
   /* @GetMapping("/building")
    public String matrix(@PathVariable String buildingId, @MatrixVariable int g, @MatrixVariable int u, Model model){
        List<String> dataList = new ArrayList<>();
        dataList.add("building number: ".concat(buildingId));
        dataList.add("ground floor flat number: "+ g);
        dataList.add("uppler floor flat number: " + u);
        model.addAttribute("dataList", dataList);
        return "home/sandbox";
    }*/

    @ResponseBody
    @GetMapping("/building/{buildingId}")
    public ResponseEntity<List<String>> matrix(@PathVariable String buildingId,
                                                            @MatrixVariable Map<String, String> matrixVars, Model model){
        List<String> dataList = new ArrayList<>();
        dataList.add("building number: ".concat(buildingId));
        matrixVars.forEach((k,v) ->  dataList.add(k + ": " + v));
        model.addAttribute("dataList", dataList);
        return new ResponseEntity<>(dataList, HttpStatus.OK);
    }

    @GetMapping("/reader")
    public String readBody(Reader requestReader, Model model) throws IOException {
        List<String> dataList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(requestReader)){
            String line = null;
            while ((line = br.readLine()) != null) {
                dataList.add(line);
            }
        }
        model.addAttribute("dataList", dataList);
        return "home/sandbox";
    }

    @ResponseBody
    @GetMapping("/writer")
    public void writeBody(Reader requestReader, Writer reponseWriter) throws IOException {
        try(BufferedReader br = new BufferedReader(requestReader)){
            String line = null;
            while ((line = br.readLine()) != null) {
                reponseWriter.append(line).append("\n");
            }
        }
    }

    /**
     * Exception handler method for the HomeController class
     * Works with default bean: SimpleMappingExceptionResolver
     *
     * @return mav
     */
    @ExceptionHandler
    public ModelAndView handleProblem(WebException e, HttpServletRequest req) {
        logger.error("Item requested does not exist");
        ModelAndView mav = new ModelAndView();
        String problem = Stream.of(e.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.joining("<br/>", "[", "]"));
        mav.addObject("problem","problem with request: ".concat(req.getRequestURL().toString()).concat("<br/>" ).concat(problem));
        mav.setViewName("error");
        e.printStackTrace();
        return mav;
    }
}