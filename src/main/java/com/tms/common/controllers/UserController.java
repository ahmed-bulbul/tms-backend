package com.tms.common.controllers;


import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("")
    public String getAll(){
        return "Get All Users";
    }

    @PostMapping("")
    public String save(){
        return "Create User";
    }

    @PutMapping("")
    public String update(){
        return "Update/put";
    }
    @PatchMapping("")
    public String edit(){
        return "Update/patch";
    }

    @DeleteMapping("")
    public String delete(){
        return "Delete User";
    }

    @PostMapping("/search")
    public String search(){
        return "Search User";
    }
}
