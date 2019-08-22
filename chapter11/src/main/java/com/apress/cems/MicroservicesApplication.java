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
package com.apress.cems;

import com.apress.cems.discovery.DiscoveryService;

import java.io.IOException;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
public class MicroservicesApplication {

    public static void main(String... args) throws IOException {
        if (args.length == 0) {
            System.out.println("Specify application to start! (Options: reg, person, detective, case)");
        } else {
            switch (args[0]) {
                case "reg":
                    DiscoveryService.main(args);
                    break;
                /*case "user":
                    UserServer.main(args);
                    break;
                case "pet":
                    if (args.length == 2) {
                        System.setProperty("server.port", args[1]);
                    }
                    PetServer.main(args);
                    break;
                case "web":
                    WebServer.main(args);
                    break;*/
                default:
                    System.out.println("Specify application to start! (Options:" +
                            "reg, user, pet, web)");
            }
        }
    }
}
