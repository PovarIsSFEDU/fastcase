package com.holydev.fastcase.controllers;


import com.holydev.fastcase.entities.Role;
import com.holydev.fastcase.utilities.primitives.FilterObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/admin")
public class AdminApiController {


    @RolesAllowed({Role.BOSS})
    @PostMapping("/podvig/all")
    public void all_podvigs(@RequestBody FilterObject filter) {

    }

    @RolesAllowed({Role.BOSS})
    @GetMapping("/podvig/{podvig_id}")
    public void podvig(@PathVariable Long podvig_id) {

    }

    @RolesAllowed({Role.BOSS})
    @GetMapping("/podvig/{podvig_id}/accept")
    public void accept_podvig(@PathVariable Long podvig_id) {

    }

    @RolesAllowed({Role.BOSS})
    @GetMapping("/podvig/{podvig_id}/decline")
    public void decline_podvig(@PathVariable Long podvig_id) {

    }
}
