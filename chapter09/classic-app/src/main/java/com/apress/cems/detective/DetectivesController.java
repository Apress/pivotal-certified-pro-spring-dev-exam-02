/*
Freeware License, some rights reserved

Copyright (c) 2019 Iuliana Cosmina

Permission is hereby granted, free of charge, to anyone obtaining a copy 
of this software and associated documentation files (the "Software"), 
to work with the Software within the limits of freeware distribution and fair use. 
This includes the rights to use, copy, and modify the Software for Detectiveal use. 
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
package com.apress.cems.detective;

import com.apress.cems.detective.services.DetectiveService;
import com.apress.cems.ex.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.apress.cems.base.AbstractEntity.COMPARATOR_BY_ID;

/**
 * @author Iuliana Cosmina
 * @since 1.0
 */
@RestController
@RequestMapping("/detectives")
public class DetectivesController {

    private DetectiveService detectiveService;

    public DetectivesController(DetectiveService detectiveService) {
        this.detectiveService = detectiveService;
    }

    /**
     * Handles requests to list all detectives.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Detective> list() {
        List<Detective> detectives =  detectiveService.findAll();
        detectives.sort(COMPARATOR_BY_ID);
        return detectives;
    }

    /**
     * Returns the {@code detective} instance with id {@code id}
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Detective show(@PathVariable Long id) {
        Optional<Detective> DetectiveOpt = detectiveService.findById(id);
        if(DetectiveOpt.isPresent()) {
            return DetectiveOpt.get();
        } else {
            throw new NotFoundException(Detective.class, id);
        }
    }

    /**
     * Returns the {@code detective} instance with badge number {@code badgeNo}
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/search/{badgeNo}")
    public Detective search(@PathVariable String badgeNo) {
        Optional<Detective> detectiveOpt =  detectiveService.findByBadgeNumber(badgeNo);
        if(detectiveOpt.isPresent() ) {
            return detectiveOpt.get();
        } else {
            throw new DetectivesException("Detective with badge number " + badgeNo +" was not found!");
        }
    }

    /**
     * Updates the {@code detctive} instance with id {@code id}
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody Detective updatedDetective, @PathVariable Long id) {
        Optional<Detective> detectiveOpt = detectiveService.findById(id);
        if(detectiveOpt.isPresent()) {
            Detective detective = detectiveOpt.get();
            detective.setBadgeNumber(updatedDetective.getBadgeNumber());
            detective.setRank(updatedDetective.getRank());
            detective.setArmed(updatedDetective.getArmed());
            detective.setStatus(updatedDetective.getStatus());
            detectiveService.save(detective);
        } else {
            throw new NotFoundException(Detective.class, id);
        }
    }

    /**
     * Delete the {@code Detective} instance with id {@code id}
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<Detective> detectiveOpt = detectiveService.findById(id);
        detectiveOpt.ifPresent(value -> detectiveService.delete(value));
    }
}
